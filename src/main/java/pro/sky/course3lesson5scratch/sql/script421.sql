ALTER table student
    add constraint age_constraint check (age > 15);

ALTER table student
    add constraint name_constraint unique (name)

ALTER table student
    ALTER COLUMN name SET NOT NULL

ALTER TABLE faculty
    add constraint name_and_color_unique UNIQUE (name, color)

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20