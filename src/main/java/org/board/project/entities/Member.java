package org.board.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.board.project.commons.constants.MemberType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
//@Entity(name = "Users")
//@Table(name = "Member") //테이블 명: Member, 엔티티 명: User
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes= {
        @Index(name="idx_member_userNm", columnList = "userNm"), //columnList = "userNm DESC" 정렬 가능
        @Index(name = "idx_member_mobile", columnList = "mobile")
})
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNo;

    @Column(unique = true, nullable = false, length = 65) //unique = true: 유니크 제약 조건 설정 / nullable = false: not null
    private String email;

    @Column(name = "pw", nullable = false, length = 65) //테이블 속성명을 pw로
    private String password;

    @Column(nullable = false, length = 40)
    private String userNm;

    @Column(length = 11)
    private String mobile;

    @CreationTimestamp //쿼리 수행 시점
    @Column(updatable = false) //(처음에)추가O 수정X
    private LocalDateTime regDt;

    @UpdateTimestamp //쿼리 수행 시점
    @Column(insertable = false) //추가X 수정O
    private LocalDateTime modDt;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private MemberType mtype = MemberType.USER; //일반 회원이 기본값

//    @Transient //db에는 반영되지 않고 entity 내부에서만 사용됨
//    private String tmpData;

//    @Lob
//    private String introduction;

//    @Temporal()
//    private Date date;

}
