package ru.kds.music.domain;

import java.time.ZonedDateTime;
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
public class Track extends AbstractDomain<Long> {

    @Id
    @SequenceGenerator(name = "trackSeq", sequenceName = "track_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trackSeq")
    private Long id;

    private ZonedDateTime createdAt;

    @Column(length = 128, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Artist artist;

    @ManyToOne
    private Album album;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private String url;
}
