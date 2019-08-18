package ru.adanil.shorter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.adanil.shorter.controller.model.LinkModel;
import ru.adanil.shorter.ShrinkError;
import ru.adanil.shorter.services.ShrinkLinkService;

@Controller
//TODO validate, create failedPage
public class ShrinkPageController {

    private Logger log = LoggerFactory.getLogger(ShrinkPageController.class);

    @Autowired
    ShrinkLinkService shrinkService;

    @GetMapping(value = "/short_link")
    public String shortForms(Model model) {
        return "shrink_link";
    }

    @PostMapping(value = "/short_link")
    public String createShrinkLink(@ModelAttribute LinkModel linkModel) throws ShrinkError {
        if (isValid(linkModel)) {
            shrinkService.setShortURL(linkModel);
            return "shrink_link";
        }

        return "help_page";
    }

    @ExceptionHandler(ShrinkError.class)
    public String GenerateShrinkFailed() {
        return "help_page";
    }

    private boolean isValid(LinkModel linkModel) {
        if (linkModel.getLong() == null || linkModel.getLong().isEmpty()) {
            log.info("isValid: original link `{}` is invalid!", linkModel.getLong());
            return false;
        }

        return true;
    }
}
