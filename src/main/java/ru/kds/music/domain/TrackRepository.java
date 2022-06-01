package ru.kds.music.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("select new ru.kds.music.domain.TrackRow(t.id, t.name, t.artist.name, t.url, lo.id is not null, " +
            "ho.id is not null ) from Track t " +
            "left join LikedObject lo on lo.objectIdentifier.objectId=t.id " +
            "and lo.objectIdentifier.objectType='ru.kds.music.domain.Track' " +
            "left join HatedObject ho on ho.objectIdentifier.objectId=t.id " +
            "and ho.objectIdentifier.objectType='ru.kds.music.domain.Track' " + "where t.album = ?1")
    List<TrackRow> findByAlbum(Album album);

    @Query("select new ru.kds.music.domain.TrackRow(t.id, t.name, t.artist.name, t.url, lo.id is not null, " +
            "ho.id is not null) from Track t " +
            "left join LikedObject lo on lo.objectIdentifier.objectId=t.id " +
            "and lo.objectIdentifier.objectType='ru.kds.music.domain.Track' " +
            "left join HatedObject ho on ho.objectIdentifier.objectId=t.id " +
            "and ho.objectIdentifier.objectType='ru.kds.music.domain.Track' " + "where t.artist = ?1")
    List<TrackRow> findByArtist(Artist artist);

    @Query("select new ru.kds.music.domain.TrackRow(t.id, t.name, t.artist.name, t.url, lo.id is not null, " +
            "ho.id is not null) from Track t " + "join t.artist a " +
            "join TaggedObject t_o on a.id=t_o.objectIdentifier.objectId " +
            "and t_o.objectIdentifier.objectType='ru.kds.music.domain.Artist' " +
            "left join LikedObject lo on lo.objectIdentifier.objectId=t.id " +
            "and lo.objectIdentifier.objectType='ru.kds.music.domain.Track' " +
            "left join HatedObject ho on ho.objectIdentifier.objectId=t.id " +
            "and ho.objectIdentifier.objectType='ru.kds.music.domain.Track' " +
            "where t_o.tag in (select t_o2.tag from TaggedObject t_o2 where t_o2.objectIdentifier in (?2)) " +
            "and t.id not in (select ho.objectIdentifier.objectId from HatedObject ho where ho.user=?1 " +
            "and ho.objectIdentifier.objectType='ru.kds.music.domain.Track') " + "order by t.createdAt desc")
    List<TrackRow> findBySameTags(User user, List<ObjectIdentifier<Long>> objectIdentifiers, Pageable pageable);

    @Query("select new ru.kds.music.domain.TrackRow(t.id, t.name, t.artist.name, t.url, lo.id is not null," +
            "ho.id is not null) from Track t " +
            "left join LikedObject lo on lo.objectIdentifier.objectId=t.id " +
            "and lo.objectIdentifier.objectType='ru.kds.music.domain.Track' " +
            "left join HatedObject ho on ho.objectIdentifier.objectId=t.id " +
            "and ho.objectIdentifier.objectType='ru.kds.music.domain.Track' ")
    List<TrackRow> findTracks(Pageable tracksPageable);
}
