CREATE TABLE my_schema.users (
   username varchar primary key,
   firstname varchar,
   lastname varchar,
   // first iteraction: email varchar,   
   email varchar[],   // second iteration
   password varchar,
   created_date timestamp
);