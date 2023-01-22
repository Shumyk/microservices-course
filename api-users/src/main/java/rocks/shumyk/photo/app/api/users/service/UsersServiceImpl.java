package rocks.shumyk.photo.app.api.users.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import rocks.shumyk.photo.app.api.users.data.UserEntity;
import rocks.shumyk.photo.app.api.users.data.UserRepository;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(final UserDTO userDetails) {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        final UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
        userEntity.setPassword("test");

        final UserEntity savedUser = userRepository.save(userEntity);
        return mapper.map(savedUser, UserDTO.class);
    }
}
