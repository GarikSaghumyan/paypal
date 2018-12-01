drop table if exists users;
drop table if exists transactions;

create table users (
  id int auto_increment,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  balance real not null default 0
);

create table transactions (
  id int auto_increment primary key,
  user_from int,
  user_to int,
  transaction_amount real not null,
  transaction_date timestamp not null default now()
);
