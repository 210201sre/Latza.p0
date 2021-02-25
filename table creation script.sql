

DROP TABLE IF EXISTS project000.patients CASCADE;
CREATE TABLE project000.patients(
	patient_id SERIAL PRIMARY KEY,
	full_name VARCHAR (2000) NOT NULL CHECK(LENGTH(full_name)>0),
	addr VARCHAR (2000) NOT NULL,
	roll varchar(10)
); 

DROP TABLE IF EXISTS project000.drugs CASCADE; 
CREATE TABLE project000.drugs(
	drug_id SERIAL PRIMARY KEY,
	drug_name VARCHAR (2000) NOT NULL UNIQUE,
	ncd int NOT NULL,
	drug_info varchar
); 
DROP TABLE IF EXISTS project000.patient_meds CASCADE;
CREATE TABLE project000.patient_meds(
	p_ID INT NOT NULL REFERENCES project000.patients(patient_id),
	d_ID INT NOT NULL REFERENCES project000.drugs(drug_id),
	fills_left int
);

insert into project000.patients (full_name,addr) values('tom','911 w ridge');