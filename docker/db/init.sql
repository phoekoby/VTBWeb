CREATE schema user_management;
CREATE schema transaction_management;
CREATE schema market_management;


CREATE TABLE user_management.users
(
    id                 BIGSERIAL PRIMARY KEY,
    first_name         varchar(255) NOT NULL,
    last_name          varchar(255) NOT NULL,
    email              varchar(255) NOT NULL,
    login              varchar(80)  NOT NULL,
    password           varchar(255) NOT NULL,
    supervisor_user_id int8,
    create_date        timestamp    NOT NULL DEFAULT now(),
    update_date        timestamp    NOT NULL DEFAULT now()
);
alter table user_management.users
    add constraint users_supervisor_fk1 FOREIGN KEY (id) references user_management.users (id);

CREATE TABLE user_management.privilege
(
    id          BIGSERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL,
    create_date timestamp    NOT NULL DEFAULT now(),
    update_date timestamp    NOT NULL DEFAULT now()
);

CREATE TABLE user_management.role
(
    id          BIGSERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL,
    create_date timestamp    NOT NULL DEFAULT now(),
    update_date timestamp    NOT NULL DEFAULT now()
);

CREATE TABLE user_management.request
(
    id                 BIGSERIAL PRIMARY KEY,
    user_from          int8 references user_management.users (id) NOT NULL,
    user_to            int8 references user_management.users (id) NOT NULL,
    request_type       varchar(30)                                NOT NULL,
    requesting_role_id int8 references user_management.role (id)  NOT NULL,
    request_status     varchar(30)                                NOT NULL,
    create_date        timestamp                                  NOT NULL DEFAULT now(),
    update_date        timestamp                                  NOT NULL DEFAULT now()
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

--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------

CREATE TABLE transaction_management.user_account
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        int8      NOT NULL,
    daily_multiply float4    NOT NULL,
    create_date    timestamp NOT NULL DEFAULT now(),
    update_date    timestamp NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.course
(
    id           BIGSERIAL PRIMARY KEY,
    ord          int8      NOT NULL,
    course_prize float4    NOT NULL,
    create_date  timestamp NOT NULL DEFAULT now(),
    update_date  timestamp NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.slide
(
    id          BIGSERIAL PRIMARY KEY,
    name        varchar(255)                                       NOT NULL,
    text        varchar(255)                                       NOT NULL,
    course_id   int8 references transaction_management.course (id) NOT NULL,
    ord         int8                                               NOT NULL,
    create_date timestamp                                          NOT NULL DEFAULT now(),
    update_date timestamp                                          NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.user_course
(
    id                 BIGSERIAL PRIMARY KEY,
    user_account_id    int8 references transaction_management.user_account (id) NOT NULL,
    course_id          int8 references transaction_management.course (id)       NOT NULL,
    course_user_status varchar(30)                                              NOT NULL,
    create_date        timestamp                                                NOT NULL DEFAULT now(),
    update_date        timestamp                                                NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.user_slide
(
    id                BIGSERIAL PRIMARY KEY,
    user_account_id   int8 references transaction_management.user_account (id) NOT NULL,
    slide_id          int8 references transaction_management.slide (id)        NOT NULL,
    slide_user_status varchar(30)                                              NOT NULL,
    user_course_id    int8 references transaction_management.user_course (id)  NOT NULL,
    create_date       timestamp                                                NOT NULL DEFAULT now(),
    update_date       timestamp                                                NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.wallet
(
    id              BIGSERIAL PRIMARY KEY,
    public_key      varchar(50)                                              NOT NULL,
    private_key     varchar(50)                                              NOT NULL,
    user_account_id int8 references transaction_management.user_account (id) NOT NULL,
    create_date     timestamp                                                NOT NULL DEFAULT now(),
    update_date     timestamp                                                NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.transaction
(
    id               BIGSERIAL PRIMARY KEY,
    transaction_hash varchar(255)                                       NOT NULL,
    from_wallet_id   int8 references transaction_management.wallet (id) NOT NULL,
    to_wallet_id     int8 references transaction_management.slide (id)  NOT NULL,
    create_date      timestamp                                          NOT NULL DEFAULT now(),
    update_date      timestamp                                          NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.exchange
(
    id                 BIGSERIAL PRIMARY KEY,
    in_transaction_id  int8                                              NOT NULL,
    out_transaction_id int8 references transaction_management.slide (id) NOT NULL,
    hash               varchar(255)                                      NOT NULL,
    create_date        timestamp                                         NOT NULL DEFAULT now(),
    update_date        timestamp                                         NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.purchase
(
    id                         BIGSERIAL PRIMARY KEY,
    prev_owner_user_account_id int8 references transaction_management.user_account (id) NOT NULL,
    buyer_user_account_id      int8 references transaction_management.user_account (id) NOT NULL,
    product_id                 int8                                                     NOT NULL,
    cost                       float4                                                   NOT NULL,
    transaction_id             int8 references transaction_management.transaction (id)  NOT NULL,
    create_date                timestamp                                                NOT NULL DEFAULT now(),
    update_date                timestamp                                                NOT NULL DEFAULT now()
);

CREATE TABLE transaction_management.nft_picture
(
    id          BIGSERIAL PRIMARY KEY,
    url         varchar(255) NOT NULL,
    create_date timestamp    NOT NULL DEFAULT now(),
    update_date timestamp    NOT NULL DEFAULT now()
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
    id          BIGSERIAL PRIMARY KEY,
    url         varchar(255) NOT NULL,
    create_date timestamp    NOT NULL DEFAULT now(),
    update_date timestamp    NOT NULL DEFAULT now()
);

CREATE TABLE market_management.product
(
    id            BIGSERIAL PRIMARY KEY,
    owner_user_id int8         NOT NULL,
    name          varchar(255) NOT NULL,
    cost          float4       NOT NULL,
    create_date   timestamp    NOT NULL DEFAULT now(),
    update_date   timestamp    NOT NULL DEFAULT now()
);

CREATE TABLE market_management.category
(
    id          BIGSERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL,
    create_date timestamp    NOT NULL DEFAULT now(),
    update_date timestamp    NOT NULL DEFAULT now()
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

INSERT INTO user_management.users (first_name, last_name, email, login, password)
VALUES ('noname', 'noname', 'noname', 'admin', '21232f297a57a5a743894a0e4a801fc3');

INSERT INTO user_management.privilege (name)
VALUES ('EXCHANGE'),         -- 1
       ('SHOPPING'),         -- 2
       ('COINS_ACCRUAL'),    -- 3
       ('STORE_MANAGEMENT'), -- 4
       ('USER_MANAGEMENT'),  -- 5
       ('PUBLICATION'); -- 6

INSERT INTO user_management.role (name)
VALUES ('USER'),          -- 1
       ('ADMINISTRATOR'), -- 2
       ('SUPERVISOR'),    -- 3
       ('EDITOR'); -- 4

INSERT INTO user_management.roles_privileges (role_id, privilege_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('2', '3'),
       ('2', '4'),
       ('2', '5'),
       ('3', '3'),
       ('4', '6');

INSERT INTO user_management.users_roles (user_id, role_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('1', '3'),
       ('1', '4');

--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------


/*select * from user_management.users
join user_management.users_roles on users.id = users_roles.user_id
join user_management.role on users_roles.role_id = role.id
join user_management.roles_privileges on role.id = roles_privileges.role_id
join user_management.privilege on roles_privileges.privilege_id = privilege.id;*/

-- one to many
/*select * from user u
join product p on u.id = p.owner_user_id*/

-- many to many
/*select * from user u
join users_roles ur on u.id = ur.user_id
join role r on ur.role_id = r.id
join roles_privileges rp on r.id = rp.role_id
join user_management.privilegep on rp.privilege_id = p.id*/


