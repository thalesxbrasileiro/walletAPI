create table users(
    id SERIAL,
    name VARCHAR(50),
    password VARCHAR,
    email VARCHAR(100),

    PRIMARY KEY(id)
);


create table wallet(
    id SERIAL,
    name VARCHAR(60),
    amount NUMERIC(10,2),

    PRIMARY KEY(id)
);

create table users_wallet(
     id SERIAL,
     wallet INTEGER,
     users INTEGER,

     PRIMARY KEY(id),
     FOREIGN KEY(users) REFERENCES users(id),
     FOREIGN KEY(wallet) REFERENCES wallet(id)
);

create table wallet_items(
     id SERIAL,
     wallet INTEGER,
     date DATE,
     type VARCHAR(2),
     description VARCHAR(500),
     amount NUMERIC(10,2),

     PRIMARY KEY(id),
     FOREIGN KEY(wallet) REFERENCES wallet(id)
);