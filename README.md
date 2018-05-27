# Gutenberg Project - Database
Made by Florent Haxha, Kasper Vetter, Mikkel Jensen & Phillip Brink
## Database engines
### Neo4j
We decided to use Neo4j because we liked the way the cypher query language looks and works. When using a graph database we also have an ability to filter out data while you are writing your query. If you follow a relation from one node, you only get the data back that is coming from that relation. The queries was also much easier to read and understand compared to queries in a relational database. We were surprised of how easy it was to use and wanted to explore the potential of a graph database further.

### PostgreSQL
We have worked with relation databases before and know how they work. Relational databases are used by most companies. We wanted to compare a traditional relational database to the graph database that we picked. 

## Database data model
### Neo4j
We started to look at the data to get an idea of how complex our data model should look. We found three main areas, that we could make into nodes. We started to compare this to our PostgreSQL model, which had many-to-many relations, so we tried to copy this for Neo4j by having two directed relations between Author to Book, and Book to City.

![old neo4j data model](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/Neo4jModelOld.png)

We knew each node didn’t needed a lot of properties, so it was easy to keep the model simple. We also wanted to make latitude and longitude into a composite key, since we knew a city name couldn’t be unique.
After some research and a meeting, we decided that it was enough to only have one relation between our nodes. We found out that you can still see if there is a relation when you are doing queries on an Author, even though it’s not a directed relation from Author to Book. We ended up removing two relations so we only have one relation connection each node. Our model ended up looking like this:

![new neo4j data model](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/GutenbergNeo4JNew.png)

We also changed some properties for the City node, so instead of having both a latitude and longitude property, we are combining them into a single string. We did this because Neo4j doesn’t support composite keys, which was our initial intention.

### Postgres
Like with our Neo4j model, we knew the data we had to work with was not very complex. We could keep the three entities we also had in our other model, and just combine them with another table to reflect the many-to-many relation.
 
![old postgresql data model](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/GutenbergPostgresOld.png)

Like with our Neo4j model, we tried to keep it simple, and each table only hold a few properties to describe the entity. Our many-to-many tables have references to the primary keys in the tables they have a relation to.

We didn’t make that many changes from our original model, other than renaming a few properties, and saving an extra property in our Cities table.

![new postgresql data model](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/GutenbergPostgresNew.png)

By going for the Boyce-Codd normal form, we could make sure that there would be no insert anomalies if we were to consider future inserts when new books were released to be public domain. 
We thought it was easier for us to combine the latitude and longitude into a point, and save that in the table. We thought we were going to use that when searching for a geolocation within a vicinity, because PostgreSQL support geometric types that are easy to use for geometric operations. We ended up never using that point, so looking back, we could just remove that property from the table. 

## Application data model
Our application is created with Java. We have created three entities which are “Author”, “Book” and “City”. These entities have the same attributes as the data in our models. We have created a mapper for each database, because our two databases don’t look 100% alike. We are using a set in our “Book” entity to reflect the many-to-many relation that the entity have to “Author” and “City”. We are using JPA to handle the mapping from our Neo4j database, while we are using both JPA and custom mappers for our PostgreSQL.

## How the data is imported.
The data is imported by first running a Python script to extract all the data from the text files from project Gutenberg. This is done with regexes and some filtering on specific words. 

The script can be found here: https://github.com/CPHB-FKMP/book-extractor. 

After the script is done running, we ended up with 5 csv files for each database. The reason we chose to go with different files for each database was, that Neo4j has some smart mapping of ID’s if annotated correctly in the csv file, thus making the import much easier. 

For importing the data in PostgreSQL we first ran this script: https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/src/main/resources/PostgreSQL.sql
 
This creates a schema and creates all the tables and indexes we wanted. After that we ran the following commands in the `psql` command line tool:
 
- `\copy gutenberg.books FROM 'import/postgres-books.csv' WITH CSV HEADER DELIMITER AS '|';`
- `\copy gutenberg.cities FROM 'import/postgres-cities.csv' WITH CSV HEADER DELIMITER AS '|';`
- `\copy gutenberg.authorDtos FROM 'import/postgres-authorDtos.csv' WITH CSV HEADER DELIMITER AS '|';`
- `\copy gutenberg.authors_books FROM 'import/postgres-books-authorDtos.csv' WITH CSV HEADER DELIMITER AS '|';`
- `\copy gutenberg.books_cities FROM 'import/postgres-books-cities.csv' WITH CSV HEADER DELIMITER AS '|';`

This imported all our data in the correct tables. We had an issue with a single book having the same ID as another book, so we had to delete that entry manually in the CSV since PostgreSQL doesn’t have the same option as Neo4j to ignore duplicates or skip entries if there is an error.
To import the data into Neo4j, we simply ran the following command: 
```
docker exec neo4j-data sh -c 'neo4j-admin import \
    --nodes:Author /import/neo-authorDtos.csv --nodes:Book /import/neo-books.csv --nodes:City /import/neo-cities.csv \
    --relationships:WRITTEN_BY /import/neo-books-authorDtos.csv --relationships:CONTAINS /import/neo-books-cities.csv \
    --ignore-missing-nodes=true \
    --ignore-duplicate-nodes=true \
--delimiter "|"'
```
It’s expected to have this image: https://hub.docker.com/r/aaxa/gutenberg-neo4j/ running where the data is already present. It can be run by following the description on the image page.

## Behavior of query test set

After multiple benchmarks and performance tests (performance through the API) with different data we can conclude that our Neo4j queries are the fastest by a large margin. The benchmarks are telling us that Neo4j is way faster than PostgreSQL when executing these queries. When we’re adding the application on top, where we need object mapping, Neo4j wins even more here, since a node can be directly translated into an object. 
With PostgreSQL it’s a bit different. We chose to use Spring Boot, Hibernate and JPA for our mapping and data access. To be as transparent as possible with what is going on, we chose to implement native queries. This later proved to be an issue because the mapping of many to many relationships are really difficult, if not impossible, to do by hand. We couldn’t find any documentation of this whatsoever, so our queries are executed, but the objects are not mapped correctly. This leads to an extreme performance decrease since Hibernate is querying the database each time we ask for one of the objects in the many to many collections. 
We wanted to give JPQL a try, which comes native with Spring Boot, to compare the execution and planning of our native query and our JPQL query. This proved that the execution of the native query was identical to the JPQL query, but JPA could then do the mapping and we had a huge performance increase. This is only implemented when requesting books mentioning a given city as a proof of concept. To prove that it’s the mapping that went wrong, we made a simple EXPLAIN ANALYZE on our own native query and the query generated by JPA.
The first image is our own native query execution plan, and the second is JPQL.

![native execute](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/native-execution.png)

![jpql execute](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/jpql-execution.png)

To compare the results through our application and directly benchmarked on the database, we have conducted tests on both sides. One through HTTP and one directly through the driver for Java without object mapping or HTTP requests. What we see is that Neo4j is falling a bit behind when calling it through the API. It’s still the fastest in most cases, but in some of the more expensive operations it’s sometimes slower or the same as PostgreSQL.

Neo4j benchmarks:
![neo4j benchmark](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/benchmarks/Neo4jBenchmark.png)

Neo4j application performance:
![neo4j performance](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/benchmarks/neo4j-performance.png)

PostgreSQL benchmarks:
![postgres benchmark](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/benchmarks/PostgresBenchmark.png)

PostgreSQL application performance:
![postgres performance](https://github.com/CPHB-FKMP/gutenberg-backend/blob/master/images/benchmarks/postgres-performance.png)
 
We’re certain that the reason for the huge gap in performance on some operations lie within the mapping of the objects. We had some issues with PostgreSQL and the mapping from native queries, which makes PostgreSQL a lot slower. When querying with a city name, we see approximately the same results, while Neo4j is generally faster in all other cases. This also means that we’re seeing contradicting results. Our benchmarks are telling us that Neo4j is the clear winner here, while our performance tests through the application tells us it’s a much closer race.

Though, we can also conclude from our results, that Neo4j is generally much faster than PostgreSQL when executing the queries natively and without Java object mapping.
## Recomendation
Of the two databases we chose to benchmark and work with here, we’d highly recommend Neo4j based on performance alone. When it comes to scaling, Neo4j has a big drawback because there is no possibility to scale horizontally. This could be a huge handicap in this project because every day a new book can be released as public domain. Where with PostgreSQL using we would have a lot of different ways to handle horizontal scaling, like Sharding, Clustering or Pooling. This would make it so that PostgreSQL could be a better choice. So it really comes down to scaling and performance. We could probably have optimized PostgreSQL performance by denormalizing the tables.
This makes it really difficult to recommend a database strategy. If we look 10 or 20 years into the future, we probably won’t have any scaling issues, if we consider the amount of data and the complexity of it. There is on average added 50 books per week. So with that in mind our recommendation would be Neo4j.

## What could we have done differently
When discussing our database choices, we thought a lot about the requirements for this project. We noticed that there was no insert or update cases and that we only needed to focus on read performance. With this in mind, we talked about several other ways we could structure our databases.

##### NoSQL
The first thing we thought about was to use MongoDB. With a MongoDB database we could store our data as JSON, and could send that response directly to our frontend. We would have a “Book” stored, which would have contained a list of “Authors” and “Cities”. That would have a really good read performance, since we could just get the book/books we needed and then have all data available without have to query for any other data. We would have a lot of repetitive data stored, but since we only need to “read” from the database, this likely wouldn’t be an issue.

##### Denormalized postgres 
We ended up using a relational PostgreSQL database. We talked about denormalizing our tables to tune the database for read performance. We thought that reducing repetitive data and apply to Boyce-Codd normal form to our dataset, would have a performance increase. When analyzing our queries we realized that the complexity we created by applying Boyce-Codd normal form reduced the performance, since we had to rely on too many joins for us to get the data we wanted. When using Joins we create a big overhead of data and postgres have to spend time on filtering most of it away to only give us what we search on. By denormalizing the data  we could remove the need to use joins, and optimize our queries. This would use more memory because we would have repetitive data, but would likely get faster read performance. 
##### Frontend limitations
Our frontend is created with some simple html and is handled by some Javascript with jQuery. We are also using Google Map to display the cities. With jQuery we are performing an HTTP request (ajax) to our backend. As it is now, we receives all the data at once and are trying to map book titles and author names into a html table, while we make pointers on our map with the city locations. This can take a long time, since we get a lot of data from our response. It takes time for our backend to map data from the database to our entities, and then again, it takes time for us to get the response on the frontend and display that data. When looking back we should have changed the way we receive data to a smaller resultset. When we search for London, we have to wait for over 22.000 results to be mapped. A solution we talked about would have been to only query for ten or twenty results at a time, and then have a way to “ask” for more data, so we minimize the amount of data we need to show and query after. As it is now, we have to wait a long time (+10 sec) for queries with a big result set and for a “working” frontend, noone wants to wait that long before they can see the data.  
