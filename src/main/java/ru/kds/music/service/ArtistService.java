package ru.kds.music.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.kds.music.domain.ArtistRepository;
import ru.kds.music.domain.ArtistRow;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<ArtistRow> findArtistsByNameContains(String name) {
        if (StringUtils.isNotBlank(name)) {
            // TODO: filtered artists
        }

        return artistRepository.findArtists();
    }
}
