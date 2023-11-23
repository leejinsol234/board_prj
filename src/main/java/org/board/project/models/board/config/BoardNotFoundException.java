package org.board.project.models.board.config;

import org.board.project.commons.Utils;
import org.board.project.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException(){
        super(Utils.getMessage("NotFound.board","error"));
        setStatus(HttpStatus.NOT_FOUND); //상태코드 404로 변경
    }
}
