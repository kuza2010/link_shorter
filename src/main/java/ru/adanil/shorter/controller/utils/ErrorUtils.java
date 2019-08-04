package ru.adanil.shorter.controller.utils;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import ru.adanil.shorter.model.ErrorDetails;

public class ErrorUtils {

    @Nullable
    public static ErrorDetails getErrorDetailsByCode(Object OstatusCode) {
        if (OstatusCode != null) {
            int statusCode = Integer.parseInt(OstatusCode.toString());

            switch (statusCode) {
                case 404:
                    return new ErrorDetails(statusCode, HttpStatus.NOT_FOUND.getReasonPhrase());
                case 500:
                    return new ErrorDetails(statusCode, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                default:
                    return null;
            }
        }
        return null;
    }
}
