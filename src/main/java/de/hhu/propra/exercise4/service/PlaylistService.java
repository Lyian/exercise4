package de.hhu.propra.exercise4.service;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.Playlist;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.AlbumRepository;
import de.hhu.propra.exercise4.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static de.hhu.propra.exercise4.service.helpers.FilterHelpers.*;

@Service
public class PlaylistService {

    @Autowired
    PlaylistRepository playlistRepository;

    public List<Playlist> getAllPlaylistsByFilter(String isPrivate, String bezeichnung) throws Exception{
        List<Playlist> playlists = playlistRepository.getAllPlaylists();

        return playlists
                .stream()
                .filter(playlist -> filterStringContainsIfNotNull(bezeichnung, playlist.getBezeichnung()))
                .filter(playlist -> filterIsTrueStringToBoolean(isPrivate, playlist.isIst_privat()))
                .collect(Collectors.toList());
    }

    /*public int createNewAlbum(Album album, User user) throws Exception {

    }*/
}
