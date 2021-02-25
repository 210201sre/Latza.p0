package egg.project000.repositories;

import java.util.List;

import egg.project000.models.Patient;

public interface PatientDAOHeader {
	public int insert(Patient p);
	public List<Patient> findAll();
	public Patient findPatientByName(String name);
}