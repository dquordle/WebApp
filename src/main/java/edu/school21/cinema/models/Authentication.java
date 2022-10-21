package edu.school21.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {
    private Long id;
    private Long userId;
    private Date authDate;
    private Time authTime;
    private String ip;
}
