package rocks.shumyk.photoapp.api.albums.service;

import rocks.shumyk.photoapp.api.albums.data.AlbumEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlbumsServiceImpl implements AlbumsService {

    @Override
    public List<AlbumEntity> getAlbums(final String userId) {
        return List.of(
                AlbumEntity.builder()
                        .id(1L)
                        .userId(userId)
                        .albumId("album1Id")
                        .description("Album 1 Description")
                        .name("Album 1 Name")
                        .build(),
                AlbumEntity.builder()
                        .id(2L)
                        .userId(userId)
                        .albumId("album2Id")
                        .description("Album 2 Description")
                        .name("Album 2 Name")
                        .build()
        );
    }

}
