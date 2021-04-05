CREATE DATABASE ips
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

create table ips(
    ip varchar not null,
    port varchar not null
)