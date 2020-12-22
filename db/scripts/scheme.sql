create table if not exists accounts(
    id serial primary key,
    username text,
    phone_number text
);

create table if not exists halls(
    id serial primary key,
    row int,
    col int,
    price int,
    account_id int references accounts(id)
);