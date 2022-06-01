package ru.kds.music.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtist(Artist artist);

    @Query("select album from Album album join fetch album.artist where album.id = ?1")
    Optional<Album> findByIdWithArtist(Long id);
}
