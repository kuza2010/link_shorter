package ru.adanil.shorter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.adanil.shorter.controller.model.Link;
import ru.adanil.shorter.services.AutoincrementService;
import ru.adanil.shorter.services.ShrinkLinkService;

@Controller
public class ShrinkPageController {
    @Autowired
    ShrinkLinkService sLService;

    @GetMapping(value = "/short_link")
    public String shortForms(Model model) {
        return "shrink_link";
    }

    @PostMapping(value = "/short_link")
    public String shortForm(@ModelAttribute Link link) {
        String shortLink = sLService.getShortLink(link.getLongLink());
        link.setShortLink(shortLink);

        return "shrink_link";
    }
}
