package ru.adanil.shorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.adanil.shorter.configuration.MongoConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class MongoDBController {

    public static final String ON = "on";
    public static final String OFF = "off";

    private static Logger log = LoggerFactory.getLogger(MongoDBController.class);


    static boolean startMongo(MongoConfiguration configuration) {
        String exec = configuration.getScriptPath();
        String mongoPath = configuration.getMongoPath();
        String mongoConfigPath = configuration.getMongoPath();

        try {
            ProcessBuilder ps = new ProcessBuilder(exec, mongoPath, mongoConfigPath, ON);

            ps.redirectErrorStream(true);
            Process pr = ps.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            pr.waitFor(30, TimeUnit.SECONDS);

            in.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Can not startup mongo database. ", e);
        }
        return false;
    }

    public boolean shutdown(MongoConfiguration configuration) {
        String exec = configuration.getScriptPath();
        String mongoPath = configuration.getMongoPath();
        String mongoConfigPath = configuration.getMongoPath();
        try {
            ProcessBuilder ps = new ProcessBuilder(exec, mongoPath, mongoConfigPath, OFF);

            ps.redirectErrorStream(true);
            Process pr = ps.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            pr.waitFor(30, TimeUnit.SECONDS);

            in.close();
            Thread.sleep(1500);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Can not shutdown mongo database. ", e);
        }
        return false;
    }

}
