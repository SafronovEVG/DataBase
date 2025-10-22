-- liquibase formatted sql

--changeset esafronov:1
 CREATE TABLE HYINYA(
 );

 -- changeset esafronov:2
 CREATE INDEX student_name_index ON student (name);

 -- changeset esafronov:3
 CREATE INDEX fname_fcolor_inbdex ON faculty (name,color);