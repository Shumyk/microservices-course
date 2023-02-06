package rocks.shumyk.photo.app.api.users.external;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rocks.shumyk.photo.app.api.users.shared.AlbumDTO;

@FeignClient(value = "albums-ws", fallback = AlbumsFeignClient.AlbumsFallback.class)
public interface AlbumsFeignClient {
    @GetMapping("/users/{userId}/albums")
    List<AlbumDTO> getAlbums(@PathVariable long userId);

    @Component
    class AlbumsFallback implements AlbumsFeignClient {
        @Override
        public List<AlbumDTO> getAlbums(final long userId) {
            return List.of(
                    AlbumDTO.builder()
                            .id("0")
                            .name("Default Album")
                            .albumId("album0")
                            .description("Default Album returned from Circuit Breaker")
                            .userId(String.valueOf(userId))
                            .build()
            );
        }
    }
}
