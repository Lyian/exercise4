package de.hhu.propra.exercise4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    int nutzerId;
    String email;
    String password;
    String benutzername;
}
