package rocks.shumyk.photo.app.api.users.external;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rocks.shumyk.photo.app.api.users.shared.AlbumDTO;

@FeignClient("albums-ws")
public interface AlbumsFeignClient {
    @GetMapping("/users/{userId}/albums")
    List<AlbumDTO> getAlbums(@PathVariable String userId);
}
