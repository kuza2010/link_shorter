package ru.adanil.shorter.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static ru.adanil.shorter.controller.utils.ErrorUtils.getErrorDetailsByCode;

@Controller
public class ExceptionController implements ErrorController {
    private static final String errorEndPoint = "/error";


    @RequestMapping(errorEndPoint)
    public String handleError(HttpServletRequest request, Model model) {
        Object code = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("error", getErrorDetailsByCode(code));
        return "error";
    }

    @Override
    public String getErrorPath() {
        return errorEndPoint;
    }
}
