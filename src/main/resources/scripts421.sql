ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age > 16),
ADD CONSTRAINT name_constraint UNIQUE (name) AND name SET NOT NULL,
ALTER COLUMN age SET DEFAULT (20);

ALTER TABLE faculty
Add CONSTRAINT name_color_constraint UNIQUE (name,color);
