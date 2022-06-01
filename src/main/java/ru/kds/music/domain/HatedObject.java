package ru.kds.music.domain;

import java.time.ZonedDateTime;
import javax.persistence.Embedded;
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
public class HatedObject extends AbstractDomain<Long> {

    @Id
    @SequenceGenerator(name = "hatedObjectSeq", sequenceName = "hated_object_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hatedObjectSeq")
    private Long id;

    private ZonedDateTime createdAt;

    @Embedded
    private ObjectIdentifier<Long> objectIdentifier;

    @ManyToOne
    private User user;
}
