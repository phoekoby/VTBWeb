CREATE schema user_management;
CREATE schema transaction_management;
CREATE schema market_management;


CREATE TABLE user_management.users
(
    id                 int8 PRIMARY KEY,
    first_name         varchar(255) NOT NULL,
    last_name          varchar(255) NOT NULL,
    email              varchar(255) NOT NULL,
    login              varchar(80)  NOT NULL,
    password           varchar(255) NOT NULL,
    supervisor_user_id int8,
    create_date        timestamp    NOT NULL,
    update_date        timestamp    NOT NULL
);
alter table user_management.users
    add constraint users_supervisor_fk1 FOREIGN KEY (id) references user_management.users (id);

CREATE TABLE user_management.privilege
(
    id          int8 PRIMARY KEY,
    name        varchar(255) NOT NULL,
    create_date timestamp    NOT NULL,
    update_date timestamp    NOT NULL
);

CREATE TABLE user_management.role
(
    id          int8 PRIMARY KEY,
    name        varchar(255) NOT NULL,
    create_date timestamp    NOT NULL,
    update_date timestamp    NOT NULL
);

CREATE TABLE user_management.request
(
    id             int8 PRIMARY KEY,
    user_from      int8 references user_management.users (id) NOT NULL,
    user_to        int8 references user_management.users (id) NOT NULL,
    request_type   varchar(30)                                        NOT NULL,
    request_status varchar(30)                                        NOT NULL,
    create_date    timestamp                                          NOT NULL,
    update_date    timestamp                                          NOT NULL
);

CREATE TABLE user_management.roles_privileges
(
    role_id      int8 references user_management.role (id),
    privilege_id int8 references user_management.privilege (id)
);

CREATE INDEX role_privileges_index on user_management.roles_privileges
    (
     role_id,
     privilege_id
        );

CREATE TABLE user_management.users_roles
(
    user_id int8 references user_management.users (id),
    role_id int8 references user_management.role (id)
);

CREATE INDEX users_roles_index on user_management.users_roles
    (
     user_id,
     role_id
        );

CREATE TABLE user_management.user_mentors
(
    user_id   int8 references user_management.users (id),
    mentor_id int8 references user_management.users (id)
);

CREATE INDEX user_mentors_index on user_management.user_mentors
    (
     user_id,
     mentor_id
        );

--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------

CREATE TABLE transaction_management.course
(
    id           int8 PRIMARY KEY,
    ord          int8      NOT NULL,
    course_prize float4    NOT NULL,
    create_date  timestamp NOT NULL,
    update_date  timestamp NOT NULL
);

CREATE TABLE transaction_management.slide
(
    id          int8 PRIMARY KEY,
    name        varchar(255)                                       NOT NULL,
    text        varchar(255)                                       NOT NULL,
    course_id   int8 references transaction_management.course (id) NOT NULL,
    ord         int8                                               NOT NULL,
    create_date timestamp                                          NOT NULL,
    update_date timestamp                                          NOT NULL
);

CREATE TABLE transaction_management.user_course
(
    id                 int8 PRIMARY KEY,
    user_id            int8                                               NOT NULL,
    course_id          int8 references transaction_management.course (id) NOT NULL,
    course_user_status varchar(30)                                        NOT NULL,
    create_date        timestamp                                          NOT NULL,
    update_date        timestamp                                          NOT NULL
);

CREATE TABLE transaction_management.user_slide
(
    id                int8 PRIMARY KEY,
    user_id           int8                                                    NOT NULL,
    slide_id          int8 references transaction_management.slide (id)       NOT NULL,
    slide_user_status varchar(30)                                             NOT NULL,
    user_course_id    int8 references transaction_management.user_course (id) NOT NULL,
    create_date       timestamp                                               NOT NULL,
    update_date       timestamp                                               NOT NULL
);

CREATE TABLE transaction_management.wallet
(
    id          int8 PRIMARY KEY,
    public_key  varchar(50) NOT NULL,
    private_key varchar(50) NOT NULL,
    user_id     int8        NOT NULL,
    create_date timestamp   NOT NULL,
    update_date timestamp   NOT NULL
);

CREATE TABLE transaction_management.transaction
(
    id               int8 PRIMARY KEY,
    transaction_hash varchar(255)                                       NOT NULL,
    from_wallet_id   int8 references transaction_management.wallet (id) NOT NULL,
    to_wallet_id     int8 references transaction_management.slide (id)  NOT NULL,
    create_date      timestamp                                          NOT NULL,
    update_date      timestamp                                          NOT NULL
);

CREATE TABLE transaction_management.exchange
(
    id                 int8 PRIMARY KEY,
    in_transaction_id  int8                                              NOT NULL,
    out_transaction_id int8 references transaction_management.slide (id) NOT NULL,
    hash               varchar(255)                                       NOT NULL,
    create_date        timestamp                                         NOT NULL,
    update_date        timestamp                                         NOT NULL
);

CREATE TABLE transaction_management.purchase
(
    id                 int8 PRIMARY KEY,
    prev_owner_user_id int8                                                    NOT NULL,
    buyer_user_id      int8                                                    NOT NULL,
    product_id         int8                                                    NOT NULL,
    cost               float4                                                  NOT NULL,
    transaction_id     int8 references transaction_management.transaction (id) NOT NULL,
    create_date        timestamp                                               NOT NULL,
    update_date        timestamp                                               NOT NULL
);

CREATE TABLE transaction_management.nft_picture
(
    id          int PRIMARY KEY,
    url         varchar(255) NOT NULL,
    create_date timestamp    NOT NULL,
    update_date timestamp    NOT NULL
);

CREATE TABLE transaction_management.slides_pictures
(
    slide_id   int8 references transaction_management.slide (id),
    picture_id int8 references transaction_management.nft_picture (id)
);

CREATE INDEX slides_pictures_index on transaction_management.slides_pictures
    (
     slide_id,
     picture_id
        );

--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------

CREATE TABLE market_management.picture
(
    id          int PRIMARY KEY,
    url         varchar(255) NOT NULL,
    create_date timestamp    NOT NULL,
    update_date timestamp    NOT NULL
);

CREATE TABLE market_management.product
(
    id            int8 PRIMARY KEY,
    owner_user_id int8         NOT NULL,
    name          varchar(255) NOT NULL,
    cost          float4       NOT NULL,
    create_date   timestamp    NOT NULL,
    update_date   timestamp    NOT NULL
);

CREATE TABLE market_management.category
(
    id          int8 PRIMARY KEY,
    name        varchar(255) NOT NULL,
    create_date timestamp    NOT NULL,
    update_date timestamp    NOT NULL
);

CREATE TABLE market_management.product_pictures
(
    product_id int8 references market_management.product (id),
    picture_id int8 references market_management.picture (id)
);

CREATE INDEX product_pictures_index on market_management.product_pictures
    (
     product_id,
     picture_id
        );

--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------


-- one to many
/*select * from user u
join product p on u.id = p.owner_user_id*/

-- many to many
/*select * from user u
join users_roles ur on u.id = ur.user_id
join role r on ur.role_id = r.id
join roles_privileges rp on r.id = rp.role_id
join user_management.privilegep on rp.privilege_id = p.id*/


