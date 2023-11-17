package org.board.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardData extends BaseMember {

    @Id
    @GeneratedValue
    private Long seq; //기본키 게시글 번호

    @Column(length = 100, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime modDt;

    @ManyToOne(fetch = FetchType.LAZY) //다대일: 게시글(다)과 회원(일) -> foreign key (member_user_no) 엔티티명_기본키명BoardData가 주인
    @JoinColumn(name="userNo") //외래키의 이름을 직접 정할 수 있다. (name="userNo")-> 엔티티 기준이므로 Member 필드의 userNo이다.
    private Member member;
    //Many쪽 엔티티(BoardData)로 만들어진 테이블에 One(Member)의 컬럼의 기본키가 외래키로 추가된다.



}
