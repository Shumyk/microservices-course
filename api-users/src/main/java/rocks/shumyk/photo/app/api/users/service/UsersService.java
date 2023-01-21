package rocks.shumyk.photo.app.api.users.service;

import rocks.shumyk.photo.app.api.users.shared.UserDTO;

public interface UsersService {
    UserDTO createUser(UserDTO userDetails);
}
