package org.board.project.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.board.project.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice("org.board.controllers")
public class CommonController {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        response.setStatus(status.value()); //value(): int형의 정수로 담아준다.

        Map<String, String> attrs = new HashMap<>();

        attrs.put("status",String.valueOf(status.value()));
        attrs.put("path",request.getRequestURI());
        attrs.put("method",request.getMethod());
        attrs.put("message",e.getMessage());
        attrs.put("timestamp", LocalDateTime.now().toString());

        model.addAllAttributes(attrs);

        Writer writer = new StringWriter(); //메모리 기반 문자열
        PrintWriter pr = new PrintWriter(writer); //문자열로 예외 정보를 가져오기

        e.printStackTrace(pr);

        String errorMessage = ((StringWriter)writer).toString();
        log.error(errorMessage); //log에 에러 메세지 담기

        return "error/common";
    }
}
