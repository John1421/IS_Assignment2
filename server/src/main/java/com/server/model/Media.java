package com.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {

    @Id
    private long id;
    private String title;
    private LocalDate releaseDate;
    private double averageRating;
    private MediaType type;
    private List<Long> users;

    public enum MediaType {
        MOVIE, TV_SHOW
    }
}
