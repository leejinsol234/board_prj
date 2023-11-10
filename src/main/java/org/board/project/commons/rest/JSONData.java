package org.board.project.commons.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class JSONData<T> {
    private boolean success = true;
    private HttpStatus status = HttpStatus.OK;
    @NonNull
    private T data; //성공 시 전송할 데이터. 생성자 매개변수로 넣어준다.
    private String message;
}
