package org.koreait.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor //멤버 변수 초기화할 수 있도록
@NoArgsConstructor //기본 생성자 필수
public class BoardViewId {

    private long seq;
    private Integer uid;
}
