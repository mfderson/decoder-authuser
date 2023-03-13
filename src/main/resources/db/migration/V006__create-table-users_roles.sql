create table users_roles(
  user_id uuid not null,
  role_id uuid not null,
  constraint fk_user_id_user_id
      foreign key (user_id)
      references users(id),
  constraint fk_role_id_role_id
      foreign key (role_id)
      references roles(id),
  primary key (user_id, role_id)
);