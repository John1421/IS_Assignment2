CREATE TABLE IF NOT EXISTS media (
    id BIGSERIAL PRIMARY KEY,            
    title VARCHAR(255) NOT NULL UNIQUE,         
    release_date DATE NOT NULL,          
    average_rating DECIMAL(4, 2) CHECK (average_rating >= 0 AND average_rating <= 10), 
    type VARCHAR(10) NOT NULL CHECK (type IN ('MOVIE', 'TV_SHOW')) 
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    age INTEGER NOT NULL,
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('MALE', 'FEMALE', 'OTHER'))
);

CREATE TABLE IF NOT EXISTS ratings (
    user_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,
    rating DECIMAL(4, 2) NOT NULL,
    PRIMARY KEY (user_id, media_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE
);

------------------------------------------------------------------USERS

INSERT INTO users (name, age, gender)
VALUES ('John Doe', 30, 'MALE')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (name, age, gender)
VALUES ('Jane Smith', 25, 'FEMALE')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (name, age, gender)
VALUES ('Alex Johnson', 22, 'OTHER')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (name, age, gender)
VALUES ('Emily Davis', 35, 'FEMALE')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (name, age, gender)
VALUES ('Michael Brown', 40, 'MALE')
ON CONFLICT (name) DO NOTHING;

------------------------------------------------------------------MOVIES

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Shawshank Redemption', '1994-09-22', 9.30, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Godfather', '1972-03-24', 9.20, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Dark Knight', '2008-07-18', 9.00, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('12 Angry Men', '1957-04-10', 8.90, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Schindler''s List', '1993-11-30', 9.00, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Lord of the Rings: The Return of the King', '2003-12-17', 9.00, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Pulp Fiction', '1994-10-14', 8.90, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Good, the Bad and the Ugly', '1966-12-23', 8.80, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Fight Club', '1999-10-15', 8.80, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Forrest Gump', '1994-07-06', 8.80, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Inception', '2010-07-16', 8.80, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Matrix', '1999-03-31', 8.70, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Goodfellas', '1990-09-21', 8.70, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Star Wars: Episode V - The Empire Strikes Back', '1980-05-21', 8.70, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Interstellar', '2014-11-07', 8.60, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

-- 1. The Shawshank Redemption (average rating: 9.30)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 1, 9.40),
    (2, 1, 9.20),
    (3, 1, 9.30),
    (4, 1, 9.50),
    (5, 1, 9.20);

-- 2. The Godfather (average rating: 9.20)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 2, 9.10),
    (2, 2, 9.30),
    (3, 2, 9.20),
    (4, 2, 9.00),
    (5, 2, 9.40);

-- 3. The Dark Knight (average rating: 9.00)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 3, 9.10),
    (2, 3, 9.00),
    (3, 3, 8.90),
    (4, 3, 9.20),
    (5, 3, 8.80);

-- 4. 12 Angry Men (average rating: 8.90)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 4, 8.80),
    (2, 4, 9.00),
    (3, 4, 8.90),
    (4, 4, 8.70),
    (5, 4, 9.10);

-- 5. Schindler's List (average rating: 9.00)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 5, 9.10),
    (2, 5, 9.00),
    (3, 5, 9.00),
    (4, 5, 8.90),
    (5, 5, 9.20);

-- 6. The Lord of the Rings: The Return of the King (average rating: 9.00)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 6, 9.00),
    (2, 6, 8.90),
    (3, 6, 9.10),
    (4, 6, 9.20),
    (5, 6, 8.80);

-- 7. Pulp Fiction (average rating: 8.90)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 7, 8.80),
    (2, 7, 9.00),
    (3, 7, 8.90),
    (4, 7, 8.70),
    (5, 7, 9.00);

-- 8. The Good, the Bad and the Ugly (average rating: 8.80)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 8, 8.70),
    (2, 8, 8.90),
    (3, 8, 8.80),
    (4, 8, 8.60),
    (5, 8, 9.00);

-- 9. Fight Club (average rating: 8.80)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 9, 8.70),
    (2, 9, 8.80),
    (3, 9, 8.90),
    (4, 9, 8.60),
    (5, 9, 8.90);

-- 10. Forrest Gump (average rating: 8.80)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 10, 8.90),
    (2, 10, 8.80),
    (3, 10, 8.70),
    (4, 10, 8.90),
    (5, 10, 8.80);

-- 11. Inception (average rating: 8.80)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 11, 8.80),
    (2, 11, 8.90),
    (3, 11, 8.70),
    (4, 11, 8.80),
    (5, 11, 8.90);

-- 12. The Matrix (average rating: 8.70)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 12, 8.80),
    (2, 12, 8.70),
    (3, 12, 8.60),
    (4, 12, 8.70),
    (5, 12, 8.80);

-- 13. Goodfellas (average rating: 8.70)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 13, 8.70),
    (2, 13, 8.60),
    (3, 13, 8.80),
    (4, 13, 8.70),
    (5, 13, 8.70);

-- 14. Star Wars: Episode V - The Empire Strikes Back (average rating: 8.70)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 14, 8.60),
    (2, 14, 8.80),
    (3, 14, 8.70),
    (4, 14, 8.80),
    (5, 14, 8.60);

-- 15. Interstellar (average rating: 8.60)
INSERT INTO ratings (user_id, media_id, rating)
VALUES 
    (1, 15, 8.50),
    (2, 15, 8.70),
    (3, 15, 8.60),
    (4, 15, 8.60),
    (5, 15, 8.70);


UPDATE media
SET average_rating = (
    SELECT ROUND(AVG(rating), 2)
    FROM ratings
    WHERE ratings.media_id = media.id
)
WHERE id IN (SELECT DISTINCT media_id FROM ratings);