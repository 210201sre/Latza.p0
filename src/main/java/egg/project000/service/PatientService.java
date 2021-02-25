package egg.project000.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import egg.project000.exceptions.BadNewUserExcepton;
import egg.project000.models.Patient;
import egg.project000.repositories.PatientDAO;

public class PatientService {
	
	private static final Logger aLogger = LoggerFactory.getLogger(PatientService.class);
	private static final PatientService PS = new PatientService();
	private static final PatientDAO pDAO = PatientDAO.getDAO();
	
	public static PatientService getPS() { return PS;}
	
	public Patient insert(Patient p) {
		MDC.put("event", "Insert");
		aLogger.info("Attempting to insert new Patient; 874 I am a potato.");
		
		if (p.getPatientID() != 0) 
			throw new BadNewUserExcepton();
		
		int generatedId = pDAO.insert(p);
		
		if(generatedId != -1 && generatedId != p.getPatientID()) {
			p.setPatientID(generatedId);
		} else {
			//throw new BadNewUserExcepton("Failed to insert the Patient into the DB; 874 I am a potato");
		}			
		MDC.put("userId", Integer.toString(p.getPatientID()));
		return p;
	}
	
	public List<Patient> findAll(){
		return pDAO.findAll();
	}
	
	public Patient findPatientByName(String name) {
		return pDAO.findPatientByName(name);
	}
}
