package ru.kds.music.service;

import java.time.ZonedDateTime;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.music.domain.LikedObject;
import ru.kds.music.domain.LikedObjectRepository;
import ru.kds.music.domain.ObjectIdentifier;
import ru.kds.music.domain.User;
import ru.kds.music.domain.UserRepository;
import ru.kds.music.utils.SecurityUtils;

@Service
public class LikeService {

    private final UserRepository userRepository;

    private final LikedObjectRepository likedObjectRepository;

    public LikeService(UserRepository userRepository, LikedObjectRepository likedObjectRepository) {
        this.userRepository = userRepository;
        this.likedObjectRepository = likedObjectRepository;
    }

    @Transactional
    public void likeObject(Long objectId, String objectType) {
        User user = getCurrentUser();

        likedObjectRepository.findByObjectIdentifierAndUser(new ObjectIdentifier(objectId, objectType), user)
                .orElseGet(() -> {
                    LikedObject likedObject = new LikedObject();
                    likedObject.setCreatedAt(ZonedDateTime.now());
                    likedObject.setObjectIdentifier(new ObjectIdentifier<>(objectId, objectType));
                    likedObject.setUser(user);
                    return likedObjectRepository.save(likedObject);
                });
    }

    @Transactional
    public void deleteLikedObject(Long objectId, String objectType) {
        User user = getCurrentUser();
        likedObjectRepository.findByObjectIdentifierAndUser(new ObjectIdentifier(objectId, objectType), user)
                .ifPresent(i -> likedObjectRepository.delete(i));
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityUtils.getPrincipal().getUsername())
                .orElseThrow(() -> new AuthorizationServiceException("You must be authentificated"));
    }
}
