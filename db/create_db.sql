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

INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (1, 'Certificate 0', 'Description 0', 119, 25, '2021-09-28 18:13:56', '2021-09-28 18:13:56');
INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (2, 'Certificate 1', 'Description 1', 191, 28, '2021-09-28 18:13:56', '2021-09-28 18:13:56');
INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (3, 'Certificate 2', 'Description 2', 261, 18, '2021-09-28 18:13:56', '2021-09-28 18:13:56');
INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (4, 'Certificate 3', 'Description 3', 171, 21, '2021-09-28 18:13:56', '2021-09-28 18:13:56');
INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (5, 'Certificate 4', 'Description 4', 366, 8, '2021-09-28 18:13:56', '2021-09-28 18:13:56');

INSERT INTO Tag (id, name) VALUES (1, 'Tag 0');
INSERT INTO Tag (id, name) VALUES (2, 'Tag 1');
INSERT INTO Tag (id, name) VALUES (3, 'Tag 2');
INSERT INTO Tag (id, name) VALUES (4, 'Tag 3');
INSERT INTO Tag (id, name) VALUES (5, 'Tag 4');

INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (1, 1);
INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (1, 2);

INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (2, 2);
INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (2, 3);

INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (3, 3);
INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (3, 4);

INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (4, 4);
INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (4, 5);

INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (5, 5);
INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (5, 1);

# create index fk_Gift_certificate_has_Tag_Gift_certificate_idx
#     on Gift_certificate_has_Tag (certificate);
#
# create index fk_Gift_certificate_has_Tag_Tag1_idx
#     on Gift_certificate_has_Tag (tag);


