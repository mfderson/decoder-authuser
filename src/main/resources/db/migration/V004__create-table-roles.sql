create table roles(
  id uuid default uuid_generate_v4(),
  type varchar(30) not null unique,
  primary key (id)
);