package ru.kds.music.domain;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectIdentifier<ID> {

    private ID objectId;

    private String objectType;
}
