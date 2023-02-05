package rocks.shumyk.photo.app.api.users.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
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
    @JsonIgnore private String password;
    private String email;
    private List<AlbumDTO> albums;
}
