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
    create_date      timestamp             default CURRENT_TIMESTAMP not null,
    last_update_date timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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

