package de.hhu.propra.exercise4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    int premiumnutzerid;
    int nutzerid;
    String kuenstlername;
    LocalDate ablaufdatum;
    String email;
    String passwort;
    String benutzername;
}
