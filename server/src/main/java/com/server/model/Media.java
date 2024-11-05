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

    private List<Double> ratings;
    private List<Long> userIds;

    // Getters and setters for the new fields
    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public enum MediaType {
        MOVIE, TV_SHOW
    }
}
