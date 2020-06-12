create user bookstore
    createdb
    createrole
    valid until 'infinity';
CREATE DATABASE bookstore_local_db;
ALTER DATABASE bookstore_local_db OWNER TO bookstore;

\connect bookstore_local_db