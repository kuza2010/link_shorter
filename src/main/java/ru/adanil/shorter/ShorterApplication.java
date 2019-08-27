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

@SpringBootApplication
@EnableConfigurationProperties({MongoConfiguration.class})
public class ShorterApplication {

	private static final String SHUTDOWN_PID = "/tmp/.shorter_shutdown.pid";
	private static ConfigurableApplicationContext context;
	private static Logger log = LoggerFactory.getLogger(ShorterApplication.class);

	public static void main(String[] args) {
		log.info("Start shorter application...");
		SpringApplicationBuilder app = new SpringApplicationBuilder(ShorterApplication.class)
				.bannerMode(Banner.Mode.CONSOLE);

		log.info("App PID written to {}", SHUTDOWN_PID);
		app.build().addListeners(new ApplicationPidFileWriter(SHUTDOWN_PID));

		context = app.run(args);

		monitoringProcess();
		//startMongoDb(context.getBean(MongoConfiguration.class));
	}

	private static void stopApplication() {
		log.info("Stop application...");
		if (context != null) {
			SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
			context.close();
			log.info("Application successfully stopped. Bye!");
			System.exit(0);
		}
	}

	private static void startMongoDb(MongoConfiguration mongoConfiguration) {
		log.debug("Start mongod...");
		boolean isSuccessful = MongoDBController.startMongo(mongoConfiguration);
	}

	private static void stopMongoDB() {

	}

	private static void monitoringProcess() {
		log.info("Start monitoring..");
		try {
			Path path = new File(SHUTDOWN_PID).getParentFile().toPath();
			WatchService watchService = FileSystems.getDefault().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_DELETE);
			try {
				do {
					WatchKey key = watchService.take();
					List<WatchEvent<?>> events = key.pollEvents();
					if (!events.isEmpty()) {
						boolean isDelete = false;
						for (WatchEvent event : events) {
							if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
								Path fileName = ((WatchEvent<Path>) event).context();
								if (fileName.toAbsolutePath().toString().equals(SHUTDOWN_PID)) {
									isDelete = true;
									break;
								}
							}
						}
						if (isDelete)
							break;
					}
				} while (true);
			} catch (InterruptedException e1) {
			}
			log.info("monitoring: stop shorter app");
			stopApplication();
		} catch (IOException e) {
			log.error("monitoring: failed to monitor file. Error message: {}", e.getMessage());
		}
	}

}
