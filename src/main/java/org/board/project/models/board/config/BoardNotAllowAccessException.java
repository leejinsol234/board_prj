package org.board.project.models.board.config;

import org.board.project.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException {
    public BoardNotAllowAccessException() {
        super(bundleValidation.getString("Validation.board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
