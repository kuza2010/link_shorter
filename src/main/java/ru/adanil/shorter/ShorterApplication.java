package ru.adanil.shorter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class ShorterApplication {

	//TODO start mongo
	public static void main(String[] args) {
		try {
			ProcessBuilder ps = new ProcessBuilder("/home/kyza2010/Documents/Java_Project/link_shorter/src/mongo_start_stop.sh",
					"/home/kyza2010/Application/mongo/mongodb-linux-x86_64-ubuntu1604-4.0.11/bin/mongod",
					"/home/kyza2010/Application/mongo/mongodb-linux-x86_64-ubuntu1604-4.0.11/mongod.conf",
					"off");

			ps.redirectErrorStream(true);

			Process pr = ps.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			pr.waitFor();

			in.close();
			Thread.sleep(1500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.exit(0);

		SpringApplication.run(ShorterApplication.class, args);
	}

}
