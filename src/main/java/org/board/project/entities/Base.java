package org.board.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //공통으로 적용될 클래스임을 알려주는 어노테이션
@Getter @Setter
@EntityListeners(AuditingEntityListener.class) //entity 변화를 감지하는 어노테이션.
//설정 활성화는 MvcConfig에서 @EnableConfigurationProperties(FileUploadConfig.class)로 한다.
public abstract class Base {
    //등록일자, 수정일자와 같이 항상 사용하게 되는 엔티티는 따로 빼서 다른 곳에서도 속성을 사용할 수 있도록 추상클래스로 한다.

    //@CreationTimestamp //쿼리 수행 시점(비표준형)
    @CreatedDate //엔티티 변화 감지를 통해서 변경(DB와 상관 X) (표준형)
    @Column(updatable = false) //(처음에)추가O 수정X
    private LocalDateTime createdAt;

    //@UpdateTimestamp //쿼리 수행 시점(비표준형)
    @LastModifiedDate //(표준형)
    @Column(insertable = false) //추가X 수정O
    private LocalDateTime modifiedAt;
}
