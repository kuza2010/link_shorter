package ru.adanil.shorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.adanil.shorter.configuration.MongoConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static ru.adanil.shorter.MongoDBController.shutdownMongo;
import static ru.adanil.shorter.MongoDBController.startMongo;
import static ru.adanil.shorter.PathUtils.getFileName;

@SpringBootApplication
@EnableConfigurationProperties({MongoConfiguration.class})
public class ShorterApplication {
    private static final String PID_FILE = "/tmp/.shorter_shutdown.pid";
    private static ConfigurableApplicationContext context;
    private static Logger log = LoggerFactory.getLogger(ShorterApplication.class);

    public static void main(String[] args) {
        log.debug("Start shorter application...");
        SpringApplicationBuilder app = new SpringApplicationBuilder(ShorterApplication.class)
                .bannerMode(Banner.Mode.CONSOLE);
        app.build().addListeners(new ApplicationPidFileWriter(PID_FILE));
        context = app.run(args);

        boolean isMongodStartSuccessfully = startMongo(context.getBean(MongoConfiguration.class));
        monitoringProcess(isMongodStartSuccessfully);
    }

    private static void stopApplication() {
        log.info("StopApplication: Stop application...");
        try {
            if (context != null) {
                SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
                context.close();
                log.info("Application successfully stopped. Bye!");
                System.exit(0);
            }
        } catch (Exception e) {
            log.error("StopApplication: caught an exception while stopping application: {}", e.getMessage());
        }
    }

    private static void monitoringProcess(boolean prevStatus) {
        if (prevStatus) {
            log.info("Monitoring: start monitoring..");
            try {
                Path path = new File(PID_FILE).getParentFile().toPath();
                WatchService watchService = FileSystems.getDefault().newWatchService();
                path.register(watchService, StandardWatchEventKinds.ENTRY_DELETE);
                try {
                    for (; ; ) {
                        WatchKey key = watchService.take();
                        List<WatchEvent<?>> events = key.pollEvents();

                        if (!events.isEmpty()) {
                            boolean isShuttingDown = false;
                            for (WatchEvent event : events) {
                                if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                                    Path deletedFileName = ((WatchEvent<Path>) event).context();
                                    if (deletedFileName.toString().equals(getFileName(PID_FILE))) {
                                        log.info("Monitoring: watching file was deleted. Start shutdown process...");
                                        isShuttingDown = true;
                                        break;
                                    }
                                }
                            }
                            if (isShuttingDown) break;
                            else key.reset();
                        }
                    }
                } catch (InterruptedException e1) {
                    log.error("Monitoring: interruptedException while monitoring shutdown file ", e1);
                }
                shutdownMongo(context.getBean(MongoConfiguration.class));
                stopApplication();
            } catch (IOException e) {
                log.error("Monitoring: failed to monitor file. Error message: {}", e.getMessage());
            }
        } else {
            log.error("Mongo DB can not start, shutdown application! See Mongo log for crash details.");
            stopApplication();
        }
    }
}
