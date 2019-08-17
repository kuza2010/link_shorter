package ru.adanil.shorter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.adanil.shorter.Base62;

@Service
public class ShrinkLinkService {

    private Logger log = LoggerFactory.getLogger(ShrinkLinkService.class);

    private final String host = "http://localhost:8888/";

    @Autowired
    AutoincrementService aUService;

    public String getShortLink(String longLink) {
        int nextId = aUService.getNextLinkId();
        String shortPostfix = getShort(nextId);

        return host + shortPostfix;
    }

    private String getShort(int id) {
        String postfix = Base62.getBase62From10(id);
        log.info("getShort: create short {}, id = {}",postfix,id);

        return postfix;
    }
}
