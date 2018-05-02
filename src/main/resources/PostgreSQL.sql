CREATE TABLE IF NOT EXISTS author(
    id bigint PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS book(
    id bigint PRIMARY KEY,
    title varchar
);

CREATE TABLE IF NOT EXISTS location(
    latitude double precision,
    longitude double precision,
    city varchar
    PRIMARY KEY(latitude, longitude)
);

CREATE TABLE IF NOT EXISTS authors_books(
    authorid bigint references author(id),
    bookid bigint references book(id)
);

CREATE TABLE IF NOT EXISTS books_locations(
    bookid bigint references book(id),
    latitude double precision,
    longitude double precision
    FOREIGN KEY (latitude, longitude) references location(latitude, longitude) 
    
);
