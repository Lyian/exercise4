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
public class Title {
    int titleid;
    String bezeichnung;
    int dauer;
    String speicherort_lq;
    String speicherort_hq;
}
