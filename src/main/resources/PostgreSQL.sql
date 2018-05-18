DROP TABLE IF EXISTS books_cities, authors_books, authors, cities, books;

CREATE TABLE IF NOT EXISTS authors (
    author_id bigint PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS books (
    book_id varchar PRIMARY KEY,
    title varchar
);

CREATE TABLE IF NOT EXISTS cities (
    name varchar,
    location point PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS authors_books(
    book_id varchar references book(id),
    author_id bigint references author(id)
);

CREATE TABLE IF NOT EXISTS books_locations(
    book_id varchar references book(id),
    location point references cities(location)
);

CREATE INDEX city_name_index ON cities USING btree(name);

CREATE INDEX book_title_index ON books USING btree(title);

CREATE INDEX author_name_index ON authors USING btree(name);

CREATE INDEX city_location_index ON cities USING gist(location);