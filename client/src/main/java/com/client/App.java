package com.client;

import java.time.Duration;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
 * console-based client.
 * This application retrieves media data from a remote API and writes specific
 * information
 * to an output file using reactive, non-blocking patterns.
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
     * Executes when the application starts, triggering various media requests.
     * Combines multiple reactive requests into a single Flux, writing the results
     * to an output file.
     *
     * @param args - command line arguments
     */
    @Override
    public void run(String... args) {
        // Concatenate multiple requests into a single reactive Flux and write each
        // output to file
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
                .doOnError(error -> log.error("Error: " + error.getMessage()));

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
                .timeout(Duration.ofSeconds(10)) // Set a timeout of 10 seconds
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal -> log.info(
                                "Connection with the server not successful. Attempt {}... Trying again...",
                                retrySignal.totalRetries() + 1)))
                .map(media -> "Title: " + media.getTitle() + ", Release Date: " + media.getReleaseDate())
                .onErrorResume(e -> Flux.just("Failed to retrieve media after multiple attempts or timeout."))
                .startWith("---------------------REQ 1------------------------");
    }

    /**
     * Reactive method for REQ 2 - Retrieves the total media count.
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
     * Reactive method for REQ 3 - Retrieves count of media items with rating above
     * 8.
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
     * Reactive method for REQ 4 - Retrieves and logs the media count.
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
                .map(count -> "Media Count: " + count)
                .flux()
                .startWith("---------------------REQ 4------------------------");
    }

    /**
     * Reactive method for REQ 5 - Retrieves and sorts media items from the 1980s by
     * rating.
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
     * Reactive method for REQ 6 - Placeholder for additional request.
     *
     * @return Flux<String> - An empty Flux, to be implemented later
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
     * Reactive method for REQ 7 - Finds the oldest media item.
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
     * Reactive method for REQ 8 - Placeholder for additional request.
     *
     * @return Flux<String> - An empty Flux, to be implemented later
     */
    private Flux<String> req8() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .flatMap(media -> countUsersForMedia(media.getId())
                        .map(userCount -> new AbstractMap.SimpleEntry<>(media, userCount)))
                .reduce(new double[] { 0, 0 }, (acc, entry) -> {
                    acc[0] += 1;
                    acc[1] += entry.getValue();
                    return acc;
                })
                .flatMapMany(result -> {
                    double average = result[0] == 0 ? 0 : result[1] / result[0];
                    return Flux.just(
                            "---------------------REQ 8------------------------",
                            "Average number of users per media item: " + average);
                });
    }

    private Mono<Integer> countUsersForMedia(long mediaId) {
        return webClient.get()
                .uri("/media/" + mediaId + "/users")
                .retrieve()
                .bodyToFlux(Long.class)
                .count()
                .map(Long::intValue);
    }

    /**
     * Reactive method for REQ 9 - Placeholder for additional request.
     *
     * @return Flux<String> - An empty Flux, to be implemented later
     */
    private Flux<String> req9() {
        return webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .flatMap(this::processMedia)
                .startWith("---------------------REQ 9------------------------");
    }

    private Mono<String> processMedia(Media media) {
        return getUsersForMedia(media.getId())
                .flatMap(this::getUserDetails) // Fetch user details for each ID.
                .collectList() // Collect all User details into a list.
                .map(users -> formatUserDetails(media, users));
    }

    private Flux<Long> getUsersForMedia(long mediaId) {
        return webClient.get()
                .uri("/media/" + mediaId + "/users")
                .retrieve()
                .bodyToFlux(Long.class);
    }

    private Mono<User> getUserDetails(Long userId) {
        return webClient.get()
                .uri("/user/" + userId)
                .retrieve()
                .bodyToMono(User.class);
    }

    private String formatUserDetails(Media media, List<User> users) {
        users.sort(Comparator.comparing(User::getAge).reversed()); // Sort users by age in descending order.
        String userDetails = users.stream()
                .map(user -> user.getName() + " (Age: " + user.getAge() + ")")
                .collect(Collectors.joining(", "));
        return "Media Title: " + media.getTitle() + " - Users: " + userDetails;
    }

    /**
     * Reactive method for REQ 10 - Placeholder for additional request.
     *
     * @return Flux<String> - An empty Flux, to be implemented later
     */
    private Flux<String> req10() {
        return webClient.get()
            .uri("/user")
            .retrieve()
            .bodyToFlux(User.class)
            .flatMap(user -> 
                getMediaForUser(user.getId())
                    .flatMap(this::getMediaDetails)
                    .map(Media::getTitle)
                    .collect(Collectors.joining(", "))
                    .map(mediaTitles -> formatUserWithMedia(user, mediaTitles))
            )
            .startWith("---------------------REQ 10------------------------");
    }

    private String formatUserWithMedia(User user, String mediaTitles) {
        return String.format("User: %s, Age: %d, Gender: %s, Subscribed Media: [%s]", 
                             user.getName(), user.getAge(), user.getGender(), mediaTitles);
    }

    private Flux<Long> getMediaForUser(Long userId) {
        return webClient.get()
                .uri("/user/" + userId + "/media")
                .retrieve()
                .bodyToFlux(Long.class);
    }

    private Mono<Media> getMediaDetails(Long mediaId) {
        return webClient.get()
                .uri("/media/" + mediaId)
                .retrieve()
                .bodyToMono(Media.class);
    }

    private String formatUserWithMedia(User user, List<Media> mediaList) {
        String mediaTitles = mediaList.stream()
                .map(Media::getTitle)
                .collect(Collectors.joining(", "));
        return String.format("User: %s, Age: %d, Gender: %s, Subscribed Media: [%s]",
                user.getName(), user.getAge(), user.getGender(), mediaTitles);
    }
}
