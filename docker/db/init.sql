CREATE DATABASE user_database;
CREATE DATABASE transaction_database;
CREATE DATABASE market_database;


CREATE TABLE user_database.privilege
(
    id          int8 PRIMARY KEY,
    name        varchar(255)                      NOT NULL,
    privileges  varchar(30) references role (id) NOT NULL,
    create_date   DATETIME NOT NULL,
    update_date   DATETIME NOT NULL,
);

CREATE TABLE user_database.role
(
    id          int8 PRIMARY KEY,
    name        varchar(255)                      NOT NULL,
    roles       varchar(30) references user (id) NOT NULL,
    create_date   DATETIME NOT NULL,
    update_date   DATETIME NOT NULL,
);

CREATE TABLE user_database.roles_privileges
(
    role_id      int8 references role (id),
    privilege_id int8 references privilege (id),
);

CREATE INDEX role_privileges_index on user_database.roles_privileges
(
     role_id,
     privilege_id,
);

CREATE TABLE user_database.user
(
    id                 int8 PRIMARY KEY,
    name               varchar(255)                          NOT NULL,
    login              varchar(80)                          NOT NULL,
    password           varchar(255)                          NOT NULL,
    supervisor_user_id int8 references user (id),
    create_date   DATETIME NOT NULL,
    update_date   DATETIME NOT NULL,
);

CREATE TABLE user_database.users_roles
(
    user_id int8 references user (id),
    role_id int8 references role (id),
);

CREATE INDEX users_roles_index on user_database.users_roles
(
    user_id,
    role_id,
);

CREATE TABLE user_database.user_mentors
(
    user_id int8 references user (id),
    mentor_id int8 references user (id),
);

CREATE INDEX user_mentors_index on user_database.user_mentors
(
    user_id,
    mentor_id,
);

CREATE TABLE market_database.picture
(
    id         int PRIMARY KEY,
    url varchar(255) NOT NULL,
    create_date   DATETIME NOT NULL,
    update_date   DATETIME NOT NULL,
);

CREATE TABLE market_database.product
(
    id            int8 PRIMARY KEY,
    owner_user_id int8 references user (id) NOT NULL,
    name          varchar(255)              NOT NULL,
    cost          double precision(5) NOT NULL,
    create_date   DATETIME NOT NULL,
    update_date   DATETIME NOT NULL,
);

CREATE TABLE market_database.product_pictures
(
    product_id int8 references product (id),
    picture_id int8 references picture (id),
);

CREATE INDEX product_pictures_index on market_database.product_pictures
    (
     product_id,
     picture_id,
);

CREATE TABLE transaction_database.purchase
(
    id                 int8 PRIMARY KEY,
    prev_owner_user_id int8 references user (id)    NOT NULL,
    buyer_user_id      int8 references user (id)    NOT NULL,
    product_id         int8 references product (id) NOT NULL,
    cost               double precision(5) NOT NULL,
    transaction_id     int8 references transaction (id) NOT NULL,
    create_date        DATETIME                     NOT NULL,
    update_date        DATETIME                     NOT NULL,
);

CREATE TABLE transaction_database.transaction
(
    id          int8 PRIMARY KEY,
    transaction_hash   varchar(255) references wallet (id) NOT NULL,
    wallet_id   int8 references wallet (id) NOT NULL,
    create_date   DATETIME NOT NULL,
    update_date   DATETIME NOT NULL,
);

CREATE TABLE transaction_database.wallet
(
    id          int8 PRIMARY KEY,
    public_key  varchar(50)               NOT NULL,
    private_key varchar(50)               NOT NULL,
    user_id     int8 references user (id) NOT NULL,
    create_date   DATETIME NOT NULL,
    update_date   DATETIME NOT NULL,
);

-- one to many
/*select * from user u
join product p on u.id = p.owner_user_id*/

-- many to many
/*select * from user u
join users_roles ur on u.id = ur.user_id
join role r on ur.role_id = r.id
join roles_privileges rp on r.id = rp.role_id
join privilege p on rp.privilege_id = p.id*/


