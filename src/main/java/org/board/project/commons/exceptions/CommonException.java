package org.board.project.commons.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

@Setter @Getter
public class CommonException extends RuntimeException{

    protected static ResourceBundle bundleValidation;
    protected static ResourceBundle bundleError;

    private HttpStatus status;

    static {
        bundleValidation = ResourceBundle.getBundle("messages.validations");
        bundleError = ResourceBundle.getBundle("messages.errors");
    }

    public CommonException (String message){
        super(message);
        status = HttpStatus.INTERNAL_SERVER_ERROR; //500 에러
    }

    public CommonException (String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }

}
