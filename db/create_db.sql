create table Gift_certificate
(
    id               bigint unsigned auto_increment
        primary key,
    name             varchar(45)                         not null,
    description      varchar(300)                        not null,
    price            int unsigned                        not null,
    duration         int                                 not null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create table Tag
(
    id   bigint unsigned auto_increment
        primary key,
    name varchar(45) not null
);

create table Gift_certificate_has_Tag
(
    certificate bigint unsigned not null,
    tag         bigint unsigned not null,
    primary key (certificate, tag),
    constraint fk_Gift_certificate_has_Tag_Gift_certificate
        foreign key (certificate) references Gift_certificate (id),
    constraint fk_Gift_certificate_has_Tag_Tag1
        foreign key (tag) references Tag (id)
);

create index fk_Gift_certificate_has_Tag_Gift_certificate_idx
    on Gift_certificate_has_Tag (certificate);

create index fk_Gift_certificate_has_Tag_Tag1_idx
    on Gift_certificate_has_Tag (tag);


