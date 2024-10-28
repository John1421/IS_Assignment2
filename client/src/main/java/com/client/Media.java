package com.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {

    private long id;
    private String title;
    private LocalDate releaseDate;
    private double averageRating;
    private MediaType type;

    public enum MediaType {
        MOVIE, TV_SHOW
    }
}
