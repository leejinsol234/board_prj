package org.board.project.commons;

import jakarta.servlet.http.HttpServletResponse;
import org.board.project.commons.exceptions.AlertBackException;
import org.board.project.commons.exceptions.AlertException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ScriptExceptionProcess {
    /**
     * 예외를 자바스크립트 처리하는 공통 인터페이스
     *
     */
    @ExceptionHandler(AlertException.class)
    default String scriptHandler(AlertException e, Model model, HttpServletResponse response) {

        response.setStatus(e.getStatus().value());
        String script = String.format("alert('%s');", e.getMessage());
        if (e instanceof AlertBackException) {
            script += "history.back();";
        }

        model.addAttribute("script", script);

        return "commons/_execute_script";
    }
}
