package rocks.shumyk.photo.app.api.users.shared;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private String encryptedPassword;
    private String email;
}
