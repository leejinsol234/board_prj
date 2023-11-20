package org.board.project.commons.exceptions;

import org.board.project.commons.Utils;

public class BadRequestException extends AlertBackException{
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super(Utils.getMessage("BadRequest", "error"));
    }

}
