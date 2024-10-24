package com.server;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MediaRepository extends R2dbcRepository<Media, Long> {

}
