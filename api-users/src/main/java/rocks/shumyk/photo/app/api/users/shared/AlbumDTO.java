package rocks.shumyk.photo.app.api.users.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {
    private String id;
    private String userId;
    private String name;
    private String description;
}
