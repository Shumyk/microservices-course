package rocks.shumyk.photo.app.api.users.external;

import feign.FeignException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;

@Slf4j
//@Component
public class AlbumsOpenFeignFallbackFactory implements FallbackFactory<AlbumsFeignClient> {
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
