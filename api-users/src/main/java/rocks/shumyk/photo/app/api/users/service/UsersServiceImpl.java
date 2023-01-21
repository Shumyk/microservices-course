package rocks.shumyk.photo.app.api.users.service;

import java.util.UUID;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;

public class UsersServiceImpl implements UsersService {

    @Override
    public UserDTO createUser(final UserDTO userDetails) {
        userDetails.setId(UUID.randomUUID().toString());
        return null;
    }
}
