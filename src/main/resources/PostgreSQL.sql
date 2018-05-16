DROP TABLE IF EXISTS books_locations, authors_books, author, location, book;

CREATE TABLE IF NOT EXISTS author(
    id bigint PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS book(
    id varchar PRIMARY KEY,
    title varchar
);

CREATE TABLE IF NOT EXISTS location(
    latitude double precision,
    longitude double precision,
    city varchar,
    PRIMARY KEY (latitude, longitude)
);

CREATE TABLE IF NOT EXISTS authors_books(
    authorid bigint references author(id),
    bookid varchar references book(id)
);

CREATE TABLE IF NOT EXISTS books_locations(
    bookid varchar references book(id),
    latitude double precision,
    longitude double precision,
    FOREIGN KEY (latitude, longitude) references location(latitude, longitude) 
);