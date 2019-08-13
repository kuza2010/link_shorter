package ru.adanil.shorter.controller.utils;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import ru.adanil.shorter.model.ErrorDetails;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ErrorUtils {

    @Nullable
    public static ErrorDetails getErrorDetailsByCode(Object OstatusCode) {
        if (OstatusCode != null) {
            int statusCode = Integer.parseInt(OstatusCode.toString());

            switch (statusCode) {
                case 404:
                    return new ErrorDetails(statusCode, getPhrase(NOT_FOUND));
                case 500:
                    return new ErrorDetails(statusCode, getPhrase(INTERNAL_SERVER_ERROR));
                default:
                    return null;
            }
        }
        return null;
    }


    private static String getPhrase(HttpStatus status) {
        return status.getReasonPhrase();
    }
}
