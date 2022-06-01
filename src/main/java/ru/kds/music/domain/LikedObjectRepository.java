package ru.kds.music.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikedObjectRepository extends JpaRepository<LikedObject, Long> {

    Optional<LikedObject> findByObjectIdentifierAndUser(ObjectIdentifier objectIdentifier, User user);

    @Query("select lo from LikedObject lo where lo.user = ?1 and lo.objectIdentifier.objectType = ?2")
    List<LikedObject> findLastByUser(User currentUser, String name, Pageable pageable);
}
