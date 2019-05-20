create table if not exists Uzytkownik
(
id int AUTO_INCREMENT PRIMARY KEY not null,
login varchar(255),
haslo varchar(255),
uprawnienia varchar(255)
);

create table if not exists Klient(
id integer AUTO_INCREMENT PRIMARY KEY NOT NULL,
users int,
lodowki int
);

create table if not exists laczanie
(
Kleint_id int,
Uzytkownik_id int
);

CREATE SEQUENCE hibernate_sequence START 1;