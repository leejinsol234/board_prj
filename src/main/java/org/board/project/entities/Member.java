package org.board.project.entities;

import lombok.Data;
import org.board.project.commons.constants.MemberType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Member {
    @Id
    private Long userNo;

    private String email;
    private String password;
    private String userNm;
    private String mobile;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private MemberType mtype = MemberType.USER; //일반 회원이 기본값
}
