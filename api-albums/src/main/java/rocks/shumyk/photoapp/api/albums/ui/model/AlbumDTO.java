package rocks.shumyk.photoapp.api.albums.ui.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {
    private Long id;
    private String albumId;
    private String userId;
    private String name;
    private String description;
}
