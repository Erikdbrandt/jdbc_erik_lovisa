ALTER TABLE assistant
ADD COLUMN assisted_superhero INT,
ADD CONSTRAINT fk_superhero_id
FOREIGN KEY(assisted_superhero)
REFERENCES superhero(superhero_id);

