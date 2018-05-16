# gutenberg-backend


## Copy Data from csv to Postgresql

login to the PostgreSQL database as appdev user

`psql -h localhost -U appdev`

When you are logged in as a user then you can run the following command

`\copy TableName FROM '/path/to/file/social_network_nodes.csv' WITH CSV HEADER DELIMITER AS ',';`

## Copy Data from csv to Neo4j

```
LOAD CSV WITH HEADER FROM '/path/to/file/csv.csv' AS line FIELDTERMINATOR ';'

MERGE (:Label {id: toInt(row.Field1), name: row.Field2,});


```


