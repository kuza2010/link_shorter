package ru.adanil.shorter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.adanil.shorter.controller.model.LinkModel;
import ru.adanil.shorter.services.ShrinkError;
import ru.adanil.shorter.services.ShrinkLinkService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
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
            shrinkService.getShortUrl(linkModel);
            return "shrink_link";
        }

        return "help_page";
    }

    private boolean isValid(LinkModel linkModel) {
        if (linkModel.getLong() != null && !linkModel.getLong().isEmpty()) {
            Pattern p = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            Matcher matcher = p.matcher(linkModel.getLong());
            if (matcher.find())
                return true;

            log.info("isValid: original link `{}` no matches.", linkModel.getLong());
        }

        log.info("isValid: original link `{}` is invalid!", linkModel.getLong());
        return false;
    }
}
