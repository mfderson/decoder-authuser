create table users(
  id uuid default uuid_generate_v4(),
  username varchar(50) unique not null,
  email varchar(50) unique not null,
  password varchar(255) not null,
  full_name varchar(150) not null,
  status varchar(32) not null,
  type varchar(32) not null,
  phone_number varchar(20),
  cpf varchar(20),
  image_url varchar(255),
  creation_date timestamp without time zone,
  last_update_date timestamp without time zone,
  primary key (id)
);