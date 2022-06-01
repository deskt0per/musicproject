package ru.kds.music.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tag extends AbstractDomain<Long> {

    @Id
    @SequenceGenerator(name = "tagSeq", sequenceName = "tag_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagSeq")
    private Long id;

    @Column(length = 128, nullable = false)
    private String name;

}
