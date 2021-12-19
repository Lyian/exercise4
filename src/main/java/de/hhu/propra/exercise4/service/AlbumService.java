package de.hhu.propra.exercise4.service;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    private boolean filterStringContainsIfNotNull(String filter, String value){
        if(filter != null){
            return value.toLowerCase(Locale.ROOT).contains(filter.toLowerCase(Locale.ROOT));
        }
        return true;
    }

    private <T> boolean filterEqualsIfNotNull(T filter, T value){
        if(filter != null) return filter == value;
        return true;
    }
}
