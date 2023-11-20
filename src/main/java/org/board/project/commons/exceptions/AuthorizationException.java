package org.board.project.commons.exceptions;

import org.board.project.commons.Utils;
import org.springframework.http.HttpStatus;

public class AuthorizationException extends CommonException{
    /**
     * 인증되지 않은 접근에 대한 예외를 생성합니다.
     */
    public AuthorizationException() {
        super(Utils.getMessage("UnAuthorization", "error"), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 코드를 인자로 받는 생성자.
     * 인증되지 않은 접근에 대한 예외를 생성하며, 코드에 해당하는 오류 메시지를 사용.
     *
     * @param code 오류 코드
     */
    public AuthorizationException(String code) {
        super(Utils.getMessage(code, "error"), HttpStatus.UNAUTHORIZED);
    }
}
