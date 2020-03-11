create table vacancy (
   id serial primary key not null,
   name varchar(250),
   text text,
   link varchar(512)
);