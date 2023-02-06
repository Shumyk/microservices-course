package rocks.shumyk.photo.app.api.users.external;

import feign.FeignException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rocks.shumyk.photo.app.api.users.shared.AlbumDTO;

@FeignClient(value = "albums-ws", fallbackFactory = AlbumsFeignClient.AlbumsFallbackFactory.class)
public interface AlbumsFeignClient {
    @GetMapping("/users/{userId}/albums")
    List<AlbumDTO> getAlbums(@PathVariable long userId);

    @Slf4j
    @Component
    class AlbumsFallbackFactory implements FallbackFactory<AlbumsFeignClient> {
        @Override
        public AlbumsFeignClient create(final Throwable cause) {
            logException(cause);
            return x -> List.of();
        }

        private void logException(final Throwable cause) {
            if (cause instanceof FeignException fe && fe.status() == HttpStatus.NOT_FOUND.value()) {
                log.error("404 error took place when getAlbums was called. Error message: {}", fe.getMessage(), fe);
            } else
                log.error("Other error took place: {}", cause.getMessage(), cause);
        }
    }
}
