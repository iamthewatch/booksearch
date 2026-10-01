CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    rating INTEGER,
    name        VARCHAR(255),
    year         INTEGER,
    rating_ball  FLOAT,
    description     TEXT,
    author     VARCHAR(255),
    url_logo     VARCHAR(255)
);