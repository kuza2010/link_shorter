package ru.adanil.shorter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpPageController {

    @GetMapping(value = "/help_page")
    public String shortForms(Model model) {
        return "help_page";
    }
}
