create table if not exists Users (
id identity,
email varchar(255) not null,
first_name varchar(50) not null,
last_name varchar(100) not null,
password varchar(255) not null,
role varchar(20) default User,
status varchar(20) default active;