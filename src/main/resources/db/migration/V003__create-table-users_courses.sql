create table users_courses(
  id uuid default uuid_generate_v4(),
  user_id uuid not null,
  course_id uuid not null,
  primary key (id),
  constraint fk_user_id_user_id
      foreign key (user_id)
          references users(id)
);