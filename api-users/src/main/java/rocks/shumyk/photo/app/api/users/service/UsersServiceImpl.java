package rocks.shumyk.photo.app.api.users.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rocks.shumyk.photo.app.api.users.data.UserEntity;
import rocks.shumyk.photo.app.api.users.data.UserRepository;
import rocks.shumyk.photo.app.api.users.external.AlbumsFeignClient;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final AlbumsFeignClient albumsClient;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper converter;

    @Override
    public UserDTO createUser(final UserDTO userDetails) {
        final UserEntity userEntity = converter.map(userDetails, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        final UserEntity savedUser = userRepository.save(userEntity);
        return converter.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUser(final String userId) {
        return userRepository.findById(userId)
                .map(this::enhanceUserEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
    }

    private UserDTO enhanceUserEntity(final UserEntity entity) {
        final UserDTO dto = converter.map(entity, UserDTO.class);
        dto.setAlbums(albumsClient.getAlbums(entity.getId()));
        return dto;
    }

    @Override
    public UserDTO getUserDetailsByEmail(final String email) {
        return userRepository.findByEmail(email)
                .map(u -> converter.map(u, UserDTO.class))
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(u -> User.builder()
                                .username(u.getEmail())
                                .password(u.getPassword())
                                .authorities(List.of())
                                .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
