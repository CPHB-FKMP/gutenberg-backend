CREATE SCHEMA IF NOT EXISTS gutenberg AUTHORIZATION appdev;
SET search_path TO gutenberg;

DROP TABLE IF EXISTS books_cities, authors_books, authorDtos, cities, books;

CREATE TABLE IF NOT EXISTS authorDtos (
    author_id bigint PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS books (
    book_id varchar PRIMARY KEY,
    title varchar
);

CREATE TABLE IF NOT EXISTS cities (
    name varchar,
    location point,
    latitude double precision,
    longitude double precision,
    PRIMARY KEY(latitude, longitude)
);

CREATE TABLE IF NOT EXISTS authors_books(
    book_id varchar references books(book_id),
    author_id bigint references authorDtos(author_id)
);

CREATE TABLE IF NOT EXISTS books_cities(
    book_id varchar references books(book_id),
    latitude double precision,
    longitude double precision,
    FOREIGN KEY(latitude, longitude) REFERENCES cities(latitude, longitude)
);

CREATE INDEX city_name_index ON cities USING btree(name);

CREATE INDEX book_title_index ON books USING btree(title);

CREATE INDEX author_name_index ON authorDtos USING btree(name);

CREATE INDEX city_location_index ON cities USING gist(location);