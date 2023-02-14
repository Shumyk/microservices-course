package rocks.shumyk.photo.app.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;

public interface UsersService extends UserDetailsService {
    UserDTO createUser(UserDTO userDetails);
    UserDTO getUserDetailsByEmail(String email);
    UserDTO getUser(long userId);
}
