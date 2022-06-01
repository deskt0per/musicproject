package ru.kds.music.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.music.domain.Artist;
import ru.kds.music.domain.ArtistRepository;
import ru.kds.music.domain.LikedObject;
import ru.kds.music.domain.LikedObjectRepository;
import ru.kds.music.domain.ObjectIdentifier;
import ru.kds.music.domain.Track;
import ru.kds.music.domain.TrackRepository;
import ru.kds.music.domain.TrackRow;
import ru.kds.music.domain.User;
import ru.kds.music.domain.UserRepository;
import ru.kds.music.utils.SecurityUtils;

@Service
public class TrackService {

    private final UserRepository userRepository;

    private final LikedObjectRepository likedObjectRepository;

    private final ArtistRepository artistRepository;

    private final TrackRepository trackRepository;

    private final int recommendLikeLimit;

    private final int recommendTrackLimit;

    public TrackService(UserRepository userRepository, LikedObjectRepository likedObjectRepository,
            ArtistRepository artistRepository, TrackRepository trackRepository,
            @Value("${recommend-like-limit}") int recommendLikeLimit, @Value("${playlist-limit}") int playlistLimit) {
        this.userRepository = userRepository;
        this.likedObjectRepository = likedObjectRepository;
        this.artistRepository = artistRepository;
        this.trackRepository = trackRepository;
        this.recommendLikeLimit = recommendLikeLimit;
        this.recommendTrackLimit = playlistLimit;
    }

    @Transactional(readOnly = true)
    public List<TrackRow> findLastTracks() {
        Pageable tracksPageable = PageRequest.of(0, recommendTrackLimit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return trackRepository.findTracks(tracksPageable);
    }

    @Transactional(readOnly = true)
    public List<TrackRow> findRecommendedTracks() {
        User user = getCurrentUser();
        Pageable likedPageable = PageRequest.of(0, recommendLikeLimit, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<LikedObject> likedArtists = likedObjectRepository.findLastByUser(user, Artist.class.getName(),
                likedPageable);
        List<LikedObject> likedTracks = likedObjectRepository.findLastByUser(user, Track.class.getName(),
                likedPageable);
        Stream<ObjectIdentifier<Long>> artistsIdentifierStream = likedArtists.stream()
                .map(lo -> lo.getObjectIdentifier());
        List<Long> tracksIds = likedTracks.stream()
                .map(lo -> lo.getObjectIdentifier().getObjectId())
                .collect(Collectors.toList());

        Stream<ObjectIdentifier<Long>> tracksIdentifierStream = Stream.empty();
        if (!tracksIds.isEmpty()) {
            tracksIdentifierStream = artistRepository.findArtistIdsByTrackIds(tracksIds)
                    .stream()
                    .map(id -> new ObjectIdentifier<>(id, Artist.class.getName()));
        }

        List<ObjectIdentifier<Long>> objectIdentifiers = Stream.concat(artistsIdentifierStream, tracksIdentifierStream)
                .collect(Collectors.toList());

        if (objectIdentifiers.isEmpty()) {
            return Collections.emptyList();
        }

        Pageable tracksPageable = PageRequest.of(0, recommendTrackLimit);
        return trackRepository.findBySameTags(user, objectIdentifiers, tracksPageable);
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityUtils.getPrincipal().getUsername())
                .orElseThrow(() -> new AuthorizationServiceException("You must be authentificated"));
    }
}
