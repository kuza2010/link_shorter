package ru.adanil.shorter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.adanil.shorter.controller.model.Link;

@Controller
public class IndexPageController {

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String linkForm(Model model) {
        model.addAttribute("link", new Link());
        return "index";
    }
}
