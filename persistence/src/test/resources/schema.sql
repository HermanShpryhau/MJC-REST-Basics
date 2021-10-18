DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Gift_certificate_has_Tag;
DROP TABLE IF EXISTS Gift_certificate;
DROP TABLE IF EXISTS Tag;

create table Gift_certificate
(
    id               bigint auto_increment primary key,
    name             varchar(45)  not null,
    description      varchar(300) not null,
    price            int          not null,
    duration         int          not null,
    create_date      timestamp    not null default CURRENT_TIMESTAMP ,
    last_update_date timestamp    not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table Tag
(
    id   bigint auto_increment primary key,
    name varchar(45) not null
);

create table Gift_certificate_has_Tag
(
    certificate bigint not null,
    tag         bigint not null,
    primary key (certificate, tag),
    constraint fk_Gift_certificate_has_Tag_Gift_certificate
        foreign key (certificate) references Gift_certificate (id) on delete cascade,
    constraint fk_Gift_certificate_has_Tag_Tag1
        foreign key (tag) references Tag (id) on delete cascade
);

create table User
(
    id   bigint auto_increment primary key,
    name varchar(50) not null,
    constraint User_id_uindex
        unique (id)
);

create table Orders
(
    id              bigint auto_increment primary key,
    user_id         bigint                              not null,
    certificate_id  bigint                              not null,
    quantity        int                                 not null,
    total_price     int                                 not null,
    submission_date timestamp default CURRENT_TIMESTAMP not null,
    constraint Order_id_uindex
        unique (id),
    constraint Order_Gift_certificate_id_fk
        foreign key (certificate_id) references Gift_certificate (id)
            on delete restrict,
    constraint Order_User_id_fk
        foreign key (user_id) references User (id)
            on delete cascade
);

