package org.board.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class BaseMember extends Base {

    @CreatedBy
    @Column(updatable = false, length = 65)
    private String createdBy;

    @LastModifiedBy
    @Column(insertable = false, length = 65)
    private String modifiedBy;

}
