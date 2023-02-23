CREATE TABLE superhero_power(
	superhero_id INT,
	power_id INT,
	PRIMARY KEY(superhero_id, power_id),
	FOREIGN KEY(superhero_id) REFERENCES superhero(superhero_id),
	FOREIGN KEY(power_id) REFERENCES power(power_id)
);