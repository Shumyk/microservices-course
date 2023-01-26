package rocks.shumyk.photo.app.api.users.ui.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
