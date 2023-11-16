package org.board.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@Data
@IdClass(BoardViewId.class)
public class BoardView {

    @Id
    private long seq;
    @Id
    @Column(name = "_uid")
    private Integer uid;
}
