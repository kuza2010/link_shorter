package ru.adanil.shorter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.adanil.shorter.repository.model.Link;

//TODO вынести в отдельный класс хз
public interface LinkRepository extends MongoRepository<Link, String> {
    Link findByShortLink(String shortLink);

    Link findByLongLink(String longLink);
}
