package ru.adanil.shorter.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.adanil.shorter.controller.model.LinkModel;
import ru.adanil.shorter.repository.LinkRepository;
import ru.adanil.shorter.repository.model.Link;
import ru.adanil.shorter.services.AutoincrementService;
import ru.adanil.shorter.services.Base62;
import ru.adanil.shorter.services.ShrinkError;
import ru.adanil.shorter.services.ShrinkLinkService;

@Service
public class ShrinkLinkServiceImpl implements ShrinkLinkService {

    private Logger log = LoggerFactory.getLogger(ShrinkLinkServiceImpl.class);

    private final String host = "http://localhost:8888/";

    @Autowired
    AutoincrementService incService;

    @Autowired
    LinkRepository repository;

    @Override
    public String getShortUrl(LinkModel linkModel) throws ShrinkError {
        if (repository.findByLongLink(linkModel.getLong()) == null) {
            int nextId = incService.getAndIncrement();
            String shortPostfix = convertIDToBase62(nextId);
            linkModel.setShort(host + shortPostfix);

            repository.insert(new Link(linkModel.getLong(), shortPostfix));
            return linkModel.getShort();
        } else {
            Link found = repository.findByLongLink(linkModel.getLong());
            linkModel.setShort(host + found.getShortLink());
            return linkModel.getShort();
        }
    }

    @Override
    public String getCanonicalUrl(String requestURI) {
        Link foundUrl = repository.findByShortLink(escape(requestURI));

        if (foundUrl != null)
            return foundUrl.getLongLink();

        return null;
    }

    private String convertIDToBase62(int id) {
        String postfix = Base62.getBase62From10(id);
        log.info("convertIDToBase62: create short {}, id = {}", postfix, id);

        return postfix;
    }

    private String escape(String uri) {
        return uri.replaceAll("/", "");
    }
}
