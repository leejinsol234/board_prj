package org.board.project.entities;

import jakarta.persistence.*;
import lombok.*;
import org.board.project.commons.constants.MemberType;

import java.util.ArrayList;
import java.util.List;

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
public class Member extends Base{
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

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private MemberType mtype = MemberType.USER; //일반 회원이 기본값

    @ToString.Exclude
    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE) //BoardData가 주인(부모).FetchType.LAZY(기본값) -> 항상 BoardData를 조회하는 것은 아니므로
    //cascade = CascadeType.REMOVE 자식 쪽에서 삭제되면 부모 쪽에서도 삭제되도록 한다.
    private List<BoardData> items = new ArrayList<>();

//    @Transient //db에는 반영되지 않고 entity 내부에서만 사용됨
//    private String tmpData;

//    @Lob
//    private String introduction;

//    @Temporal()
//    private Date date;

}
