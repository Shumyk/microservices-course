package rocks.shumyk.photo.app.api.users.service;

import static java.lang.String.format;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rocks.shumyk.photo.app.api.users.data.UserEntity;
import rocks.shumyk.photo.app.api.users.data.UserRepository;
import rocks.shumyk.photo.app.api.users.shared.AlbumDTO;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    @Value("${api-albums.url}")
    private String apiAlbumsUrl;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper converter;
    private final RestTemplate rest;

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
        dto.setAlbums(getAlbums(entity.getId()));
        return dto;
    }

    private List<AlbumDTO> getAlbums(final long userId) {
        final String url = format(apiAlbumsUrl, userId);
        final ResponseEntity<List<AlbumDTO>> albumsResponse = rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return albumsResponse.getBody();
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
