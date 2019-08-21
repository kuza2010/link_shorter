package ru.adanil.shorter.services;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import ru.adanil.shorter.controller.model.LinkModel;

public interface ShrinkLinkService {

    /**
     * This method create a short URL from original URL
     *
     * @param model - {@link LinkModel} presentation model containing original URL
     * @return presentation model with original and shorted URL
     * @throws ShrinkError - if unique id could not generate
     */
    String getShortUrl(@NonNull LinkModel model) throws ShrinkError;

    @Nullable
    String getCanonicalUrl(String requestURI);
}
