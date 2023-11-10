package org.board.project.restcontrollers;

import org.board.project.commons.exceptions.CommonException;
import org.board.project.commons.rest.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("org.board.restcontrollers")  //범위 한정
public class CommonRestController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //기본값 500
        if(e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus(); //상태 코드 직접 가져오기
        }

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(e.getMessage());

        e.printStackTrace(); //자세한 예외 정보 확인을 위해 콘솔에서 확인

        return ResponseEntity.status(status).body(data);
    }
}
