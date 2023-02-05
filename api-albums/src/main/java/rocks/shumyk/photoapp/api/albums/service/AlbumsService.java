package rocks.shumyk.photoapp.api.albums.service;

import rocks.shumyk.photoapp.api.albums.data.AlbumEntity;
import java.util.List;

public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}
