package de.hhu.propra.exercise4.service;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static de.hhu.propra.exercise4.service.helpers.FilterHelpers.filterEqualsIfNotNull;
import static de.hhu.propra.exercise4.service.helpers.FilterHelpers.filterStringContainsIfNotNull;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    public List<Album> getAllAlbumsByFilter(Integer tracks, String bezeichnung){
        List<Album> allAlbum = albumRepository.getAllAlbenWithTracks();

        return allAlbum
                .stream()
                .filter(album -> filterStringContainsIfNotNull(bezeichnung, album.getBezeichnung()))
                .filter(album -> filterEqualsIfNotNull(tracks, album.getTracks()))
                .collect(Collectors.toList());
    }

    public int createNewAlbum(Album album, User user) throws Exception {
        // Insert Album get Album Id inser to Kuenstler macht album
            int id = albumRepository.createNewAlbum(album);
            album.setAlbumdid(id);
            albumRepository.insertNewAlbumForArtist(user, album);
            return id;
    }
}
