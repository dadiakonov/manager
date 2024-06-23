create table public.users (
    user_id uuid primary key default gen_random_uuid(),
    email varchar(255) not null unique,
    password varchar(128) not null,
    name varchar(255)
);

create table public.external_projects (
    external_project_id uuid primary key default gen_random_uuid(),
    user_id uuid not null references users (user_id) on delete cascade,
    name varchar(255) not null
);