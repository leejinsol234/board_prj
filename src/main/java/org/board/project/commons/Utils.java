package org.board.project.commons;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class Utils {
    private static ResourceBundle validationsBundle;
    private static ResourceBundle errorsBundle;

    //처음 로드 시 초기화
    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    public static String getMessage(String code, String bundleType) {
        bundleType = Objects.requireNonNullElse(bundleType, "validation");
        ResourceBundle bundle = bundleType.equals("errors") ? errorsBundle : validationsBundle;
        try{
            return bundle.getString(code);
        } catch (Exception e){
            return null;
        }
    }
}
