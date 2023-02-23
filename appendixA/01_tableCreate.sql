CREATE TABLE superhero(
	superhero_id SERIAL,
	name VARCHAR(50),
	alias VARCHAR(50),
	origin VARCHAR(50)
);

CREATE TABLE assistant(
	assistant_id SERIAL,
	name VARCHAR(50)
);

CREATE TABLE power(
	power_id SERIAL,
	name VARCHAR(50),
	description TEXT
);

ALTER TABLE superhero
ADD CONSTRAINT pk_superhero_id
PRIMARY KEY(superhero_id);

ALTER TABLE power
ADD CONSTRAINT pk_power_id
PRIMARY KEY(power_id);

ALTER TABLE assistant
ADD CONSTRAINT pk_assistant_id
PRIMARY KEY(assistant_id);