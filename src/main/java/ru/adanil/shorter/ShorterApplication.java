package ru.adanil.shorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.adanil.shorter.repository.LinkRepository;
import ru.adanil.shorter.services.AutoincrementService;

@SpringBootApplication
public class ShorterApplication implements CommandLineRunner {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private AutoincrementService service;

	public static void main(String[] args) {
		SpringApplication.run(ShorterApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
//		int od = service.getNextLinkId();

//		linkRepository.save(new Link("very long link from Spring"));
//		linkRepository.save(new Link("very long link from Spring 2"));
//
//		// fetch all customers
//		System.out.println("Link found with findAll():");
//		System.out.println("-------------------------------");
//		for (Link link : linkRepository.findAll()) {
//			System.out.println(link);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Link found with findByFirstName('this is a very long link'):");
//		System.out.println("--------------------------------");
//		System.out.println(linkRepository.findByLongLink("this is a very long link"));
    }

}
