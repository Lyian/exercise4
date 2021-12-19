package de.hhu.propra.exercise4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    int playlistid;
    String bezeichnung;
    boolean ist_privat;
    String coverbild;
}
