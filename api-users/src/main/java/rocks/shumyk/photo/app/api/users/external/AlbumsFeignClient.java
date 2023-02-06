package rocks.shumyk.photo.app.api.users.external;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rocks.shumyk.photo.app.api.users.shared.AlbumDTO;

@FeignClient("albums-ws")
public interface AlbumsFeignClient {
    @GetMapping("/users/{userId}/albums")
    @Retry(name = "albums-ws")
    @CircuitBreaker(name = "albums-ws", fallbackMethod = "getAlbumsFallback")
    List<AlbumDTO> getAlbums(@PathVariable long userId);

    default List<AlbumDTO> getAlbumsFallback(final long userId, final Throwable cause) {
        System.out.println("albums-ws fallback is triggered for user: " + userId);
        System.out.println("exception: " + cause);
        return List.of();
    }
}
