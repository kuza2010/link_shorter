package ru.adanil.shorter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.adanil.shorter.model.Link;

@Controller
public class ShrinkPageController {

    // throw 404
    @GetMapping(value = "/short_link")
    public String shortForms(Model model) {
        return "shrink_link";
    }

    @PostMapping(value = "/short_link")
    public String shortForm(@ModelAttribute Link link) {
        return "shrink_link";
    }
}
