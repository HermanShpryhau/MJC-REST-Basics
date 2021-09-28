import mysql.connector as mysql
from random import randint, sample

connection = mysql.connect(
    host='',
    user='',
    password='',
    database=''
)
cursor = connection.cursor()

# Clear tables
clear_certificate_has_tag_query = 'DELETE * FROM Gift_certificate_has_Tag'
cursor.execute(clear_certificate_has_tag_query)
connection.commit()

clear_certificates_query = 'DELETE * FROM Gift_certificate'
cursor.execute(clear_certificates_query)
connection.commit()

clear_tags_query = 'DELETE * FROM Tag'
cursor.execute(clear_tags_query)
connection.commit()

# Fill "Gift_certificate" table
add_certificate_query = 'INSERT INTO Gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (%s, %s, %s, %s, DEFAULT, DEFAULT)'

certificate_values = [(f'Certificate {i}', f'Description {i}', randint(
    100, 500), randint(1, 31)) for i in range(1001)]

cursor.executemany(add_certificate_query, certificate_values)
connection.commit()

# Fill "Tag" table
add_tags_query = 'INSERT INTO Tag (name) VALUES (%s)'

tag_values = [((f'Tag {i}'),) for i in range(101)]
cursor.executemany(add_tags_query, tag_values)
connection.commit()

# Add m-2-m relations for Certificates and Tags
get_certificates_query = "SELECT * FROM `spring-gift-certificates`.Gift_certificate"
get_tags_query = "SELECT * FROM `spring-gift-certificates`.Tag"
add_certificate_has_tag_query = "INSERT INTO `spring-gift-certificates`.Gift_certificate_has_Tag (certificate, tag) VALUES (%s, %s)"

# Fetch all certificates
cursor.execute(get_certificates_query)
certificates = cursor.fetchall()

# Fetch all tags
cursor.execute(get_tags_query)
tags = cursor.fetchall()

# Generate and add relations
for certificate in certificates:
    related_tags = sample(tags, randint(1, 3))
    for tag in related_tags:
        cursor.execute(add_certificate_has_tag_query, (certificate[0], tag[0]))
    connection.commit()

connection.close()
