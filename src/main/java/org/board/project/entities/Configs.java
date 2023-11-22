package org.board.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Configs {
    @Id
    @Column(length=45) //globally_quoted_identifiers: true -> name="code_" 작성하지 않아도 됨.
    private String code;

    @Lob
    private String value;
}
