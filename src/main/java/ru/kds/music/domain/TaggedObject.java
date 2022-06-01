package ru.kds.music.domain;

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
public class TaggedObject extends AbstractDomain {

    @Id
    @SequenceGenerator(name = "taggedObjectSeq", sequenceName = "tagged_object_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taggedObjectSeq")
    private Long id;

    @ManyToOne
    private Tag tag;

    @Embedded
    private ObjectIdentifier<Long> objectIdentifier;
}
