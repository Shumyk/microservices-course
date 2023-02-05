package rocks.shumyk.photoapp.api.albums.io.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocks.shumyk.photoapp.api.albums.data.AlbumEntity;
import rocks.shumyk.photoapp.api.albums.service.AlbumsService;
import rocks.shumyk.photoapp.api.albums.ui.model.AlbumDTO;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/albums")
public class AlbumsController {

    private final ModelMapper mapper;
    private final AlbumsService albumsService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AlbumDTO> userAlbums(@PathVariable final String userId) {
        final List<AlbumEntity> albumEntities = albumsService.getAlbums(userId);
        log.info("Returning {} albums", albumEntities.size());
        return mapper.map(albumEntities, new TypeToken<List<AlbumDTO>>(){}.getType());
    }
}
