-- liquibase formatted sql

-- changeset sergei: 1
CREATE INDEX name_index ON student (name)

-- changeset sergei: 2
CREATE INDEX name_and_color_index ON faculty (name, color)