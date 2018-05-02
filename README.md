# gutenberg-backend


## Copy Data from csv to Database

login to the PostgreSQL database as appdev user

`psql -h localhost -U appdev`

When you are logged in as a user then you can run the following command

`\copy TableName FROM '/path/to/file/social_network_nodes.csv' WITH CSV HEADER DELIMITER AS ',';`
