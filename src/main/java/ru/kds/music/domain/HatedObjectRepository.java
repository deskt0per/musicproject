package ru.kds.music.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HatedObjectRepository extends JpaRepository<HatedObject, Long> {

    Optional<HatedObject> findByObjectIdentifierAndUser(ObjectIdentifier objectIdentifier, User user);

}
