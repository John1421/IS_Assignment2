CREATE TABLE IF NOT EXISTS media (
    id BIGSERIAL PRIMARY KEY,            
    title VARCHAR(255) NOT NULL UNIQUE,         
    release_date DATE NOT NULL,          
    average_rating DECIMAL(4, 2) CHECK (average_rating  >= 0 AND average_rating  <= 10), 
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
    PRIMARY KEY (user_id, media_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE
);

-- Insert Movies (3 from the 80s)
INSERT INTO media (title, release_date, average_rating, type)
VALUES 
    ('Back to the Future', '1985-07-03', 8.5, 'MOVIE'),
    ('The Terminator', '1984-10-26', 8.0, 'MOVIE'),
    ('E.T. the Extra-Terrestrial', '1982-06-11', 7.9, 'MOVIE'),
    ('Inception', '2010-07-16', 8.8, 'MOVIE'),
    ('The Dark Knight', '2008-07-18', 9.0, 'MOVIE'),
    ('Pulp Fiction', '1994-10-14', 8.9, 'MOVIE'),
    ('The Matrix', '1999-03-31', 8.7, 'MOVIE'),
    ('Gladiator', '2000-05-05', 8.5, 'MOVIE'),
    ('Interstellar', '2014-11-07', 8.6, 'MOVIE'),
    ('Parasite', '2019-05-30', 8.6, 'MOVIE')
ON CONFLICT DO NOTHING;

-- Insert TV Shows (3 from the 80s)
INSERT INTO media (title, release_date, average_rating, type)
VALUES 
    ('Cheers', '1982-09-30', 7.9, 'TV_SHOW'),
    ('The A-Team', '1983-01-23', 7.5, 'TV_SHOW'),
    ('MacGyver', '1985-09-29', 7.7, 'TV_SHOW'),
    ('Breaking Bad', '2008-01-20', 9.5, 'TV_SHOW'),
    ('Game of Thrones', '2011-04-17', 9.3, 'TV_SHOW'),
    ('The Office', '2005-03-24', 8.9, 'TV_SHOW'),
    ('Stranger Things', '2016-07-15', 8.7, 'TV_SHOW'),
    ('Friends', '1994-09-22', 8.9, 'TV_SHOW'),
    ('The Mandalorian', '2019-11-12', 8.7, 'TV_SHOW'),
    ('The Crown', '2016-11-04', 8.6, 'TV_SHOW')
ON CONFLICT DO NOTHING;

-- Insert Users
INSERT INTO users (name, age, gender)
VALUES 
    ('Alice Johnson', 28, 'FEMALE'),
    ('Bob Smith', 34, 'MALE'),
    ('Charlie Brown', 22, 'MALE'),
    ('Diana Prince', 30, 'FEMALE'),
    ('Elliot Alderson', 25, 'MALE'),
    ('Frank Castle', 40, 'MALE')
ON CONFLICT DO NOTHING;

-- Insert relationships between users and media items, ordered by media_id
INSERT INTO ratings (user_id, media_id) VALUES
    (1, 1),  -- Alice -> Back to the Future
    (2, 1),  -- Bob -> Back to the Future
    (1, 2),  -- Alice -> The Terminator
    (3, 2),  -- Charlie -> The Terminator
    (2, 3),  -- Bob -> E.T. the Extra-Terrestrial
    (6, 3),  -- Frank -> E.T. the Extra-Terrestrial
    (1, 4),  -- Alice -> Inception
    (4, 4),  -- Diana -> Inception
    (4, 5),  -- Diana -> The Dark Knight
    (1, 6),  -- Alice -> Pulp Fiction
    (2, 6),  -- Bob -> Pulp Fiction
    (2, 7),  -- Bob -> The Matrix
    (5, 7),  -- Elliot -> The Matrix
    (3, 8),  -- Charlie -> Gladiator
    (4, 8),  -- Diana -> Gladiator
    (3, 9),  -- Charlie -> Interstellar
    (5, 9),  -- Elliot -> Interstellar
    (1, 10), -- Alice -> Parasite
    (6, 10), -- Frank -> Parasite
    (6, 11), -- Frank -> The Crown
    (2, 12), -- Bob -> The A-Team
    (6, 13), -- Frank -> MacGyver
    (3, 14), -- Charlie -> Cheers
    (2, 15), -- Bob -> Breaking Bad
    (4, 16), -- Diana -> Game of Thrones
    (3, 16), -- Charlie -> Game of Thrones
    (5, 17), -- Elliot -> The Office
    (4, 18), -- Diana -> The Mandalorian
    (5, 18)  -- Elliot -> The Mandalorian
ON CONFLICT DO NOTHING;