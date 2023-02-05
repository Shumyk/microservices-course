package rocks.shumyk.photo.app.api.users.external;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Value("${api-albums.exception.not-found}")
    private String albumsNotFoundException;

    @Override
    public Exception decode(final String methodKey, final Response response) {
        return switch (response.status()) {
            case 400 -> new RuntimeException("Bad request for " + response);
            case 404 -> {
                if (methodKey.contains("getAlbums")) {
                    yield new ResponseStatusException(HttpStatusCode.valueOf(response.status()), albumsNotFoundException);
                }
                yield new RuntimeException("Not found, man :( " + response);
            }
            default -> new Exception(response.reason());
        };
    }
}
