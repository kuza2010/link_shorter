package ru.adanil.shorter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.adanil.shorter.services.ShrinkError;
import ru.adanil.shorter.services.impl.ShrinkLinkServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
//TODO validate, create failedPage aka Ooops...
public class OriginalUrlController {

    private Logger log = LoggerFactory.getLogger(OriginalUrlController.class);

    @Autowired
    ShrinkLinkServiceImpl shrinkService;

    @GetMapping(value = "/*")
    public RedirectView shortUrlHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String originalURL = shrinkService.getCanonicalUrl(uri);

        if (originalURL == null) return goHome();

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalURL);
        return redirectView;
    }

    private RedirectView goHome() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return redirectView;
    }

    @ExceptionHandler(ShrinkError.class)
    public String GenerateShrinkFailed() {
        return "help_page";
    }
}
