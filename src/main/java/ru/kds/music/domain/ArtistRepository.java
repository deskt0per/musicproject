package ru.kds.music.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("select new ru.kds.music.domain.ArtistRow(a.id, a.name, a.description, lo.id is not null) from " +
            "Artist a left join LikedObject lo on lo.objectIdentifier.objectId=a.id " +
            "and lo.objectIdentifier.objectType='ru.kds.music.domain.Artist' " + "order by a.createdAt desc")
    List<ArtistRow> findArtists();

    @Query("select distinct t.artist.id from Track t where t.id in (?1)")
    List<Long> findArtistIdsByTrackIds(Iterable<Long> trackIds);
}
