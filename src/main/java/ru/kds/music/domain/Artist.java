package ru.kds.music.domain;

import java.time.ZonedDateTime;
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
public class Artist extends AbstractDomain<Long> {

    @Id
    @SequenceGenerator(name = "artistSeq", sequenceName = "artist_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artistSeq")
    private Long id;

    private ZonedDateTime createdAt;

    @Column(length = 128, nullable = false)
    private String name;

    private String description;
}
