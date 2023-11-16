package org.board.project.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardViewId {
    //기본키를 여러 개 정의하는 경우
    private long seq;
    private Integer uid;

}
