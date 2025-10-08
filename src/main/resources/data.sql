-- Roles
INSERT INTO user_role (role_name)
VALUES ('ROLE_USER');
INSERT INTO user_role (role_name)
VALUES ('ROLE_ADMIN');

-- Users
INSERT INTO app_user (user_name, password, enabled, email)
VALUES ('admin', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXX', true, 'admin@admin.com'),
       ('alanQuintero', '$2a$10$YYYYYYYYYYYYYYYYYYYYYYYYYYYY', true, 'alan_q_b@hotmail.com'),
       ('test', '$2a$10$ZZZZZZZZZZZZZZZZZZZZZZZZZZZZ', true, 'test@test.com');

-- Profiles
INSERT INTO profile (quote, user_id)
VALUES ('Where we''re going, we don''t need roads.', 1),
       ('Be the force be with you.', 2),
       ('I am not here.', 3);

-- Movies
INSERT INTO movie (title, image_url, rating, total_rating, release_year, synopsis, trailer_url)
VALUES ('Back to the Future',
        'back-to-the-future.jpg',
        5, 1, 1985,
        'Marty McFly, a 17-year-old high school student, is accidentally sent 30 years into the past in a time-traveling DeLorean invented by his close friend, the maverick scientist Doc Brown.',
        'https://www.youtube.com/embed/qvsgGtivCgs'),
       ('Star Wars III - Revenge of the Sith',
        'star-wars-iii.jpg',
        4, 1, 2005,
        'During the near end of the clone wars, Darth Sidious has revealed himself and is ready to execute the last part of his plan to rule the Galaxy. Sidious is ready for his new apprentice, Lord Vader, to step into action and kill the remaining Jedi. Vader, however, struggles to choose the dark side and save his wife or remain loyal to the Jedi order.',
        'https://www.youtube.com/embed/5UnjrG_N8hU');

-- Ratings
INSERT INTO rating (rating, movie_id, profile_id)
VALUES (5, 1, 1),
       (4, 2, 2);

-- Reviews
INSERT INTO review (title, comment, published_date, movie_id, profile_id)
VALUES ('Great movie!', 'I love this movie!', CURRENT_TIMESTAMP, 1, 2),
       ('I am your father!', 'My favorite movie.', CURRENT_TIMESTAMP, 2, 2);
