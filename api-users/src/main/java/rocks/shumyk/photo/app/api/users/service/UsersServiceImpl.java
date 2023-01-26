package rocks.shumyk.photo.app.api.users.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rocks.shumyk.photo.app.api.users.data.UserEntity;
import rocks.shumyk.photo.app.api.users.data.UserRepository;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(final UserDTO userDetails) {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        final UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        final UserEntity savedUser = userRepository.save(userEntity);
        return mapper.map(savedUser, UserDTO.class);
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
