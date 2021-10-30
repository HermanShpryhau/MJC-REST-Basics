drop table if exists Orders;
drop table if exists Gift_certificate_has_Tag;
drop table if exists User;
drop table if exists Tag;
drop table if exists Gift_certificate;

create table if not exists Gift_certificate
(
    id                  bigint auto_increment primary key,
    name                varchar(45)  not null,
    description         varchar(300) not null,
    price               int          not null,
    duration            int          not null,
    create_date         timestamp             default CURRENT_TIMESTAMP not null,
    last_update_date    timestamp    not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    operation           varchar(10),
    operation_timestamp timestamp
);

create table if not exists Tag
(
    id                  bigint auto_increment primary key,
    name                varchar(45) not null unique,
    operation           varchar(10),
    operation_timestamp timestamp
);

create table if not exists Gift_certificate_has_Tag
(
    certificate bigint not null,
    tag         bigint not null,
    primary key (certificate, tag),
    constraint fk_Gift_certificate_has_Tag_Gift_certificate
        foreign key (certificate) references Gift_certificate (id) on delete cascade,
    constraint fk_Gift_certificate_has_Tag_Tag1
        foreign key (tag) references Tag (id) on delete restrict
);

create table if not exists User
(
    id                  bigint auto_increment primary key,
    name                varchar(50) not null,
    operation           varchar(10),
    operation_timestamp timestamp,
    constraint User_id_uindex
        unique (id)
);

create table if not exists Orders
(
    id                  bigint auto_increment primary key,
    user_id             bigint                              not null,
    certificate_id      bigint                              not null,
    quantity            int                                 not null,
    total_price         int                                 not null,
    submission_date     timestamp default CURRENT_TIMESTAMP not null,
    operation           varchar(10),
    operation_timestamp timestamp,
    constraint Order_id_uindex
        unique (id),
    constraint Order_Gift_certificate_id_fk
        foreign key (certificate_id) references Gift_certificate (id)
            on delete restrict,
    constraint Order_User_id_fk
        foreign key (user_id) references User (id)
            on delete restrict
);



INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date, operation, operation_timestamp)
VALUES (1, 'Certificate 1', 'Description 1', 119, 25, '2021-09-28 18:13:56', '2021-09-28 18:13:56', 'CREATED', '2021-09-27 18:13:56');
INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date, operation, operation_timestamp)
VALUES (2, 'Certificate 2', 'Description 2', 191, 28, '2021-09-27 18:13:56', '2021-09-27 18:13:56', 'CREATED', '2021-09-27 18:13:56');

INSERT INTO Tag (id, name, operation, operation_timestamp)
VALUES (1, 'Tag 1', 'CREATED', '2021-09-27 18:13:56');
INSERT INTO Tag (id, name, operation, operation_timestamp)
VALUES (2, 'Tag 2', 'CREATED', '2021-09-27 18:13:56');
INSERT INTO Tag (id, name, operation, operation_timestamp)
VALUES (3, 'Tag 3', 'CREATED', '2021-09-27 18:13:56');

INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (1, 1);
INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (1, 2);

INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (2, 2);
INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (2, 3);

INSERT INTO User (id, name, operation, operation_timestamp)
VALUES (1, 'User 1', 'CREATED', '2021-09-27 18:13:56');
INSERT INTO User (id, name, operation, operation_timestamp)
VALUES (2, 'User 2', 'CREATED', '2021-09-27 18:13:56');

INSERT INTO Orders (id, user_id, certificate_id, quantity, total_price, submission_date, operation, operation_timestamp)
VALUES (1, 1, 1, 2, 238, '2021-09-27 18:13:56', 'CREATED', '2021-09-27 18:13:56');
INSERT INTO Orders (id, user_id, certificate_id, quantity, total_price, submission_date, operation, operation_timestamp)
VALUES (2, 1, 2, 3, 573, '2021-09-27 18:13:56', 'CREATED', '2021-09-27 18:13:56');
INSERT INTO Orders (id, user_id, certificate_id, quantity, total_price, submission_date, operation, operation_timestamp)
VALUES (3, 2, 1, 4, 476, '2021-09-27 18:13:56', 'CREATED', '2021-09-27 18:13:56');
INSERT INTO Orders (id, user_id, certificate_id, quantity, total_price, submission_date, operation, operation_timestamp)
VALUES (4, 2, 2, 5, 955, '2021-09-27 18:13:56', 'CREATED', '2021-09-27 18:13:56');