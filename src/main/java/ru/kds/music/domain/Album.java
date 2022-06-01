package ru.kds.music.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Album extends AbstractDomain<Long> {

    @Id
    @SequenceGenerator(name = "albumSeq", sequenceName = "album_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "albumSeq")
    private Long id;

    @Column(length = 128, nullable = false)
    private String name;

    private String description;

    @ManyToOne(optional = false)
    private Artist artist;

    private int year;
}
