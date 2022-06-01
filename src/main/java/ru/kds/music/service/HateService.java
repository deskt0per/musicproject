package ru.kds.music.service;

import java.time.ZonedDateTime;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.music.domain.HatedObject;
import ru.kds.music.domain.HatedObjectRepository;
import ru.kds.music.domain.ObjectIdentifier;
import ru.kds.music.domain.User;
import ru.kds.music.domain.UserRepository;
import ru.kds.music.utils.SecurityUtils;

@Service
public class HateService {

    private final UserRepository userRepository;

    private final HatedObjectRepository hatedObjectRepository;

    public HateService(UserRepository userRepository, HatedObjectRepository hatedObjectRepository) {
        this.userRepository = userRepository;
        this.hatedObjectRepository = hatedObjectRepository;
    }

    @Transactional
    public void hateObject(Long objectId, String objectType) {
        User user = getCurrentUser();

        hatedObjectRepository.findByObjectIdentifierAndUser(new ObjectIdentifier(objectId, objectType), user)
                .orElseGet(() -> {
                    HatedObject hatedObject = new HatedObject();
                    hatedObject.setCreatedAt(ZonedDateTime.now());
                    hatedObject.setObjectIdentifier(new ObjectIdentifier<>(objectId, objectType));
                    hatedObject.setUser(user);
                    return hatedObjectRepository.save(hatedObject);
                });
    }

    @Transactional
    public void deleteHatedObject(Long objectId, String objectType) {
        User user = getCurrentUser();
        hatedObjectRepository.findByObjectIdentifierAndUser(new ObjectIdentifier(objectId, objectType), user)
                .ifPresent(i -> hatedObjectRepository.delete(i));
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityUtils.getPrincipal().getUsername())
                .orElseThrow(() -> new AuthorizationServiceException("You must be authentificated"));
    }
}
