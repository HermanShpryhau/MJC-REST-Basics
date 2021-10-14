DROP TABLE IF EXISTS Gift_certificate_has_Tag;
DROP TABLE IF EXISTS Gift_certificate;
DROP TABLE IF EXISTS Tag;

create table Gift_certificate
(
    id               bigint auto_increment primary key,
    name             varchar(45) not null,
    description      varchar(300) not null,
    price            int not null,
    duration         int not null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table Tag
(
    id bigint auto_increment primary key,
    name varchar(45) not null
);

create table Gift_certificate_has_Tag
(
    certificate bigint not null,
    tag bigint not null,
    primary key (certificate, tag),
    constraint fk_Gift_certificate_has_Tag_Gift_certificate
        foreign key (certificate) references Gift_certificate (id) on delete cascade,
    constraint fk_Gift_certificate_has_Tag_Tag1
        foreign key (tag) references Tag (id) on delete cascade
);

INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (1, 'Certificate 1', 'Description 1', 119, 25, '2021-09-28 18:13:56', '2021-09-28 18:13:56');
INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (2, 'Certificate 2', 'Description 2', 191, 28, '2021-09-27 18:13:56', '2021-09-27 18:13:56');

INSERT INTO Tag (id, name) VALUES (1, 'Tag 1');
INSERT INTO Tag (id, name) VALUES (2, 'Tag 2');
INSERT INTO Tag (id, name) VALUES (3, 'Tag 3');

INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (1, 1);
INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (1, 2);

INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (2, 2);
INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES (2, 3);

