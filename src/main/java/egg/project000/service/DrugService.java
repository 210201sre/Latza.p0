package egg.project000.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import egg.project000.exceptions.BadNewDrugExcepton;
import egg.project000.exceptions.BadNewUserExcepton;
import egg.project000.models.Drug;
import egg.project000.models.Patient;
import egg.project000.repositories.DrugDAO;

public class DrugService {
	private static final DrugService DS = new DrugService();	
	private static final DrugDAO aDrugDAO = DrugDAO.getDAO();
	private static final Logger aLogger = LoggerFactory.getLogger(PatientService.class);
	
	public static DrugService getDS() {return DS;}
	
	public Drug insert(Drug d) {
		MDC.put("event", "Insert");
		aLogger.info("Attempting to insert new Patient; 874 I am a potato.");
		
		if (d.getDrugID() != 0) 
			throw new BadNewDrugExcepton();
		
		int generatedId = aDrugDAO.insert(d);
		
		if(generatedId != -1 && generatedId != d.getDrugID()) {
			d.setDrugID(generatedId);
		} else {
			//throw new BadNewUserExcepton("Failed to insert the Patient into the DB; 874 I am a potato");
		}			
		MDC.put("userId", Integer.toString(d.getDrugID()));
		return d;
	}
	public Drug findByName(String name) {
		MDC.put("event","Retireve");
		aLogger.info("attempting to retrieve a drug by drug name ; 874 i am a potato.");
		return aDrugDAO.findByName(name);
	}
	public List<Drug> findAll(){
		return aDrugDAO.findAll();
		
	}
}
