package ru.adanil.shorter.services;

import com.mongodb.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.adanil.shorter.ShrinkError;
import ru.adanil.shorter.controller.model.LinkModel;
import ru.adanil.shorter.repository.LinkRepository;
import ru.adanil.shorter.repository.model.Link;

@Service
public class ShrinkLinkService {

    private Logger log = LoggerFactory.getLogger(ShrinkLinkService.class);

    private final String host = "http://localhost:8888/";

    @Autowired
    AutoincrementService incService;
    @Autowired
    LinkRepository repository;

    /**
     * This method create a short URL from original URL
     *
     * @param model - {@link LinkModel} presentation model containing original URL
     * @return presentation model with original and shorted URL
     * @throws ShrinkError - if unique id could not generate
     */
    public String setShortURL(@NonNull LinkModel model) throws ShrinkError {
        if (repository.findBylongLink(model.getLong()) == null) {
            String shortPostfix = incService.getUniqPostfix();
            model.setShort(host + shortPostfix);

            repository.insert(new Link(model.getLong(), shortPostfix));
            return model.getShort();
        } else {
            Link found = repository.findBylongLink(model.getLong());
            model.setShort(host + found.getShortLink());
            return model.getShort();
        }
    }

    @Nullable
    public String getOriginalURL(String uri) {
        Link foundUrl = repository.findByShortLink(uri.replaceAll("/", ""));

        if (foundUrl != null)
            return foundUrl.getLongLink();

        return null;
    }
}
