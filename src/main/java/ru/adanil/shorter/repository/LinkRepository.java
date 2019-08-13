package ru.adanil.shorter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.adanil.shorter.repository.model.Link;

public interface LinkRepository extends MongoRepository<Link, String> {
}
