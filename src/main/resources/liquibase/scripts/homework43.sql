-- liquibase formatted sql

-- changeset sergei: 1
CREATE INDEX name- index ON student(name)

-- changeset sergei: 2
CREATE INDEX name- and -color- index ON faculty(name, color)