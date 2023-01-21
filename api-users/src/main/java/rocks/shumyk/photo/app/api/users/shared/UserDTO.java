package rocks.shumyk.photo.app.api.users.shared;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String encryptedPassword;
    private String email;
}
