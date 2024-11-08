package com.client;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

/**
 * The main application class that runs a Spring Boot application as a
 * console-based client. It interacts with a remote media API and performs
 * various
 * data retrieval tasks in a non-blocking, reactive manner.
 */
@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

    private final WebClient webClient;

    /**
     * Constructor to initialize the WebClient with a base URL.
     * 
     * @param webClientBuilder - the WebClient.Builder to configure the WebClient
     */
    @Autowired
    public App(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://host.docker.internal:8080").build();
    }

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Executes when the application starts, triggering various media requests
     * and logging the results.
     * 
     * It concatenates multiple requests into a single reactive Flux and logs
     * each output.
     *
     * @param args - command line arguments
     */
    @Override
    public void run(String... args) {
        // Concatenate multiple requests into a single reactive Flux and log each
        // output
        Flux<String> outputFlux = Flux.concat(
                req1(),
                req2(),
                req3(),
                req4(),
                req5(),
                req6(),
                req7(),
                req8(),
                req9(),
                req10())
                .doOnNext(line -> log.info(line))
                .onErrorResume(error -> {
                    log.error("ERROR: Failed to run this client");
                    return Flux.just("ERROR: Failed to run this client");
                });

        // Trigger the Flux by subscribing
        outputFlux.subscribe();
    }

    /**
     * Reactive method for REQ 1 - Retrieves media titles and release dates.
     * 
     * @return Flux<String> - A Flux stream of media titles and release dates
     */
    private Flux<String> req1() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal -> log.info(
                                "Connection with the server not successful. Attempt {}... Trying again...",
                                retrySignal.totalRetries() + 1)))
                .map(media -> "Title: " + media.getTitle() + ", Release Date: " + media.getReleaseDate())
                .startWith("---------------------REQ 1------------------------");
    }

    /**
     * Reactive method for REQ 2 - Retrieves the total count of media items.
     * 
     * @return Flux<String> - A Flux containing the total media count message
     */
    private Flux<String> req2() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .count()
                .map(totalCount -> "Total Media Count: " + totalCount)
                .flux()
                .startWith("---------------------REQ 2------------------------");
    }

    /**
     * Reactive method for REQ 3 - Retrieves the count of media items with an
     * average rating above 8.
     * 
     * @return Flux<String> - A Flux containing the count of high-rated media items
     */
    private Flux<String> req3() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .filter(media -> media.getAverageRating() > 8)
                .count()
                .map(count -> "Total Really Good Media Count (Rating > 8): " + count)
                .flux()
                .startWith("---------------------REQ 3------------------------");
    }

    /**
     * Reactive method for REQ 4 - Retrieves and logs the media count that has
     * associated users.
     * 
     * @return Flux<String> - A Flux containing the media count message
     */
    private Flux<String> req4() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .flatMap(media -> webClient.get()
                        .uri("/media/" + media.getId() + "/users")
                        .retrieve()
                        .bodyToFlux(Long.class)
                        .hasElements()
                        .filter(hasUsers -> hasUsers)
                        .map(hasUsers -> media))
                .count()
                .map(count -> "Subscribed Media Count: " + count)
                .flux()
                .startWith("---------------------REQ 4------------------------");
    }

    /**
     * Reactive method for REQ 5 - Retrieves and sorts media items from the 1980s
     * by their rating in ascending order.
     * 
     * @return Flux<String> - A Flux stream of sorted media information from the
     *         1980s
     */
    private Flux<String> req5() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .filter(media -> media.getReleaseDate().isAfter(LocalDate.of(1980, 1, 1))
                        && media.getReleaseDate().isBefore(LocalDate.of(1989, 12, 31)))
                .sort((m1, m2) -> Double.compare(m1.getAverageRating(), m2.getAverageRating()))
                .map(media -> media.toString())
                .startWith("---------------------REQ 5------------------------");
    }

    /**
     * Reactive method for REQ 6 - Calculates the average and standard deviation
     * of the media ratings.
     * 
     * @return Flux<String> - A Flux containing the average and standard deviation
     *         of media ratings
     */
    private Flux<String> req6() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .map(Media::getAverageRating)
                .reduce(new double[] { 0, 0, 0 }, (acc, rating) -> {
                    acc[0] += rating; // sum of ratings
                    acc[1] += 1; // count
                    acc[2] += rating * rating; // sum of squares
                    return acc;
                })
                .flatMapMany(result -> {
                    double sum = result[0];
                    double count = result[1];
                    double sumOfSquares = result[2];
                    double average = count > 0 ? sum / count : 0.0;
                    double stdDev = count > 0 ? Math.sqrt((sumOfSquares / count) - (average * average)) : 0.0;

                    return Flux.just(
                            "---------------------REQ 6------------------------",
                            "Average Rating: " + average,
                            "Standard Deviation of Ratings: " + stdDev);
                });
    }

    /**
     * Reactive method for REQ 7 - Finds the oldest media item based on the release
     * date.
     * 
     * @return Flux<String> - A Flux containing the oldest media item
     */
    private Flux<String> req7() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .reduce((m1, m2) -> m1.getReleaseDate().isBefore(m2.getReleaseDate()) ? m1 : m2)
                .map(media -> media.toString())
                .flux()
                .startWith("---------------------REQ 7------------------------");
    }

    /**
     * Reactive method for REQ 8 - Calculates the average number of users per media
     * item.
     * 
     * @return Flux<String> - A Flux containing the average number of users per
     *         media item
     */
    private Flux<String> req8() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .flatMap(media -> countUsersForMedia(media.getId()))
                .reduce(new double[] { 0, 0 }, (acc, mediaUsersCount) -> {
                    acc[0] += 1;
                    acc[1] += mediaUsersCount;
                    return acc;
                })
                .flatMapMany(result -> {
                    double average = result[0] == 0 ? 0 : result[1] / result[0];
                    return Flux.just(
                            "---------------------REQ 8------------------------",
                            "Average number of users per media item: " + average);
                });
    }

    /**
     * Retrieves the number of users associated with a specific media item.
     * 
     * @param mediaId - The ID of the media item
     * @return Flux<Long> - A Flux containing the count of users for the media item
     */
    private Mono<Long> countUsersForMedia(long mediaId) {
        return getUsersForMedia(mediaId)
                .count();
    }

    /**
     * Reactive method for REQ 9 - Finds and sorts media items' users by age in
     * descending order, and formats the results.
     * 
     * @return Flux<String> - A Flux containing the media items and associated user
     *         information
     */
    private Flux<String> req9() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .flatMap(media -> getUsersForMedia(media.getId())
                        .flatMap(this::getUserDetails)
                        .sort(Comparator.comparingInt(UserInfo::getAge).reversed())
                        .reduce(
                                new Object[] { "", 0 }, // Initial state: [userString, count,
                                                        // formattedDetails]
                                (state, user) -> {
                                    state[0] = state[0] + user.getName() + ", ";
                                    state[1] = (int) state[1] + 1;
                                    return state;
                                })
                        .map(state -> {
                            String userString = (String) state[0];
                            int count = (int) state[1];

                            // Remove the trailing comma and space, if present
                            if (userString.endsWith(", ")) {
                                userString = userString.substring(0, userString.length() - 2);
                            }

                            // Return the formatted result with the user count
                            return String.format("Media Title: %s - Users: [%s] - Total Users: %d", media.getTitle(),
                                    userString, count);
                        }))
                .startWith("---------------------REQ 9------------------------");
    }

    /**
     * Retrieves the users associated with a media item.
     * 
     * @param mediaId - The ID of the media item
     * @return Flux<Long> - A Flux containing the user IDs
     */
    private Flux<Long> getUsersForMedia(long mediaId) {
        return webClient.get()
                .uri("/media/" + mediaId + "/users")
                .retrieve()
                .bodyToFlux(Long.class); // Returns user IDs for the media item
    }

    /**
     * Retrieves detailed user information based on user ID.
     * 
     * @param userId - The ID of the user
     * @return Mono<User> - A Mono containing the user details
     */
    private Mono<UserInfo> getUserDetails(Long userId) {
        return webClient.get()
                .uri("/user/" + userId)
                .retrieve()
                .bodyToMono(User.class)
                .map(user -> new UserInfo(user.getName(), user.getAge())); // Returns user details from user
    }

    /**
     * Reactive method for REQ 10 - Retrieves a user's details and their associated
     * media items.
     * 
     * @return Flux<String> - A Flux containing the formatted user and media details
     */
    private Flux<String> req10() {
        return webClient.get()
                .uri("/user")
                .retrieve()
                .bodyToFlux(User.class)
                .flatMap(user -> getMediaForUser(user.getId())
                        .flatMap(this::getMediaTitle)
                        .reduce("", (result, title) -> result.isEmpty() ? title : result + ", " + title)
                        .map(mediaTitles -> formatUserWithMedia(user, mediaTitles)))
                .startWith("---------------------REQ 10------------------------");
    }

    /**
     * Retrieves the media items associated with a specific user.
     * 
     * @param userId - The ID of the user
     * @return Flux<Long> - A Flux containing media IDs
     */
    private Flux<Long> getMediaForUser(Long userId) {
        return webClient.get()
                .uri("/user/" + userId + "/media")
                .retrieve()
                .bodyToFlux(Long.class);
    }

    /**
     * Retrieves the title of a media item based on its ID.
     * 
     * @param mediaId - The ID of the media item
     * @return Mono<String> - A Mono containing the media title
     */
    private Mono<String> getMediaTitle(Long mediaId) {
        return webClient.get()
                .uri("/media/" + mediaId)
                .retrieve()
                .bodyToMono(Media.class)
                .map(Media::getTitle);
    }

    /**
     * Formats a user's details along with the titles of the media items they are
     * associated with.
     * 
     * @param user        - The user object
     * @param mediaTitles - The list of media titles the user is associated with
     * @return String - The formatted string containing the user and media
     *         information
     */
    private String formatUserWithMedia(User user, String mediaTitles) {
        return String.format("User(%d): %s, Age: %d, Gender: %s, Subscribed Media: [%s]",
                user.getId(), user.getName(), user.getAge(), user.getGender(), mediaTitles);
    }
}
