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

CREATE TABLE IF NOT EXISTS user_media (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,                
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

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Silence of the Lambs', '1991-02-14', 8.60, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Saving Private Ryan', '1998-07-24', 8.60, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Green Mile', '1999-12-10', 8.60, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Spirited Away', '2001-07-20', 8.60, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Parasite', '2019-05-30', 8.50, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

----80s

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Shining', '1980-05-23', 8.40, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Back to the Future', '1985-07-03', 8.50, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Blade Runner', '1982-06-25', 8.10, 'MOVIE')
ON CONFLICT (title) DO NOTHING;

------------------------------------------------------------------TV_SHOWS

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Breaking Bad', '2008-01-20', 9.50, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Game of Thrones', '2011-04-17', 9.20, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Wire', '2002-06-02', 9.30, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Friends', '1994-09-22', 8.90, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Sopranos', '1999-01-10', 9.20, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Sherlock', '2010-07-25', 9.10, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Stranger Things', '2016-07-15', 8.70, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Office', '2005-03-24', 9.00, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Mandalorian', '2019-11-12', 8.80, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Boys', '2019-07-26', 8.70, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Black Mirror', '2011-12-04', 8.80, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Crown', '2016-11-04', 8.60, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('True Detective', '2014-01-12', 8.90, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The Walking Dead', '2010-10-31', 8.10, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Better Call Saul', '2015-02-08', 8.90, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('House of Cards', '2013-02-01', 8.70, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Westworld', '2016-10-02', 8.50, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Rick and Morty', '2013-12-02', 9.10, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Narcos', '2015-08-28', 8.80, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Chernobyl', '2019-05-06', 9.40, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

----80s

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Miami Vice', '1984-09-16', 7.50, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('The A-Team', '1983-01-23', 7.50, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO media (title, release_date, average_rating, type)
VALUES ('Magnum, P.I.', '1980-12-11', 7.50, 'TV_SHOW')
ON CONFLICT (title) DO NOTHING;
