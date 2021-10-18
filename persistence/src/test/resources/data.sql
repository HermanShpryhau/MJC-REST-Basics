INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'Certificate 1', 'Description 1', 119, 25, '2021-09-28 18:13:56', '2021-09-28 18:13:56');
INSERT INTO Gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (2, 'Certificate 2', 'Description 2', 191, 28, '2021-09-27 18:13:56', '2021-09-27 18:13:56');

INSERT INTO Tag (id, name)
VALUES (1, 'Tag 1');
INSERT INTO Tag (id, name)
VALUES (2, 'Tag 2');
INSERT INTO Tag (id, name)
VALUES (3, 'Tag 3');

INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (1, 1);
INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (1, 2);

INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (2, 2);
INSERT INTO Gift_certificate_has_Tag (certificate, tag)
VALUES (2, 3);
