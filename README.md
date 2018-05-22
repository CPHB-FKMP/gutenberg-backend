# gutenberg-backend


## Copy Data from csv to Postgresql

Run the script in the resources folder to create all necessary tables. 

`docker exec -it postgres-data /bin/bash`

login to the PostgreSQL database as appdev user

`psql -h localhost -U appdev`

When you are logged in as a user then you can run the following commands

`\copy books FROM 'import/postgres-books.csv' WITH CSV HEADER DELIMITER AS '|';`
`\copy cities FROM 'import/postgres-cities.csv' WITH CSV HEADER DELIMITER AS '|';`
`\copy authors FROM 'import/postgres-authors.csv' WITH CSV HEADER DELIMITER AS '|';`
`\copy authors_books FROM 'import/postgres-books-authors.csv' WITH CSV HEADER DELIMITER AS '|';`
`\copy books_cities FROM 'import/postgres-books-cities.csv' WITH CSV HEADER DELIMITER AS '|';`


## Copy Data from csv to Neo4j

```
docker exec neo4j-data sh -c 'neo4j-admin import \
    --nodes:Author /import/authors.csv --nodes:Book /import/books.csv --nodes:City /import/cities.csv \
    --relationships:WRITTEN_BY /import/books-authors.csv --relationships:CONTAINS /import/books-cities.csv \
    --ignore-missing-nodes=true \
    --ignore-duplicate-nodes=true \
--delimiter "|"'
```


