package egg.project000.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import egg.project000.exceptions.MedListException;
import egg.project000.models.MedListElement;
import egg.project000.repositories.MedListDAO;

public class MedListService {
	private static final MedListService MLS = new MedListService();
	private static final MedListDAO MLDAO = MedListDAO.getDAO();
	private static final Logger aLogger = LoggerFactory.getLogger(MedListService.class);
	
	public static MedListService getMLS() {return MLS;} 
	
	public MedListElement insert(MedListElement m) {
		MDC.put("event", "Insert");
		try {
		aLogger.info("Attempting to add an element to the med list; 874 I am a potato.");
		aLogger.info("MedListElement being passed to MLDAO:" + m.toString());
		m = MLDAO.insert(m);
		aLogger.info("MedListElement returned form MLDAO:" + m.toString());
		}catch(MedListException e) {
			aLogger.error(e.getMessage()+"; 874 I am a potato.");
			return MedListElement.getNullElement();
		}					
		MDC.put("userId", Integer.toString(m.getDrugID()));
		return m;
	}

	public MedListElement incFill(MedListElement m) {
		MDC.put("event", "Increment");
		try {
			aLogger.info("Attempting to increment fill count of a med; 874 I am a potato.");
			aLogger.info("MedListElement being passed to MLDAO:" + m.toString());
			m = MLDAO.incFill(m);
		}catch(MedListException e) {
			aLogger.error(e.getMessage()+"; 874 I am a potato.");
			return MedListElement.getNullElement();
		}					
		MDC.put("userId", Integer.toString(m.getDrugID()));
		return m;
		
	}
	public MedListElement decFill(MedListElement m) {
		MDC.put("event", "Decrement");
		try {
			aLogger.info("Attempting to decrement fill count of a med; 874 I am a potato.");
			aLogger.info("MedListElement being passed to MLDAO:" + m.toString());
			m = MLDAO.decFill(m);
		}catch(MedListException e) {
			aLogger.error(e.getMessage()+"; 874 I am a potato.");
			return MedListElement.getNullElement();
		}					
		MDC.put("userId", Integer.toString(m.getDrugID()));
		return m;
	}
	public List<MedListElement> getMedList(int ID){
		return MLDAO.getMedList(ID);
		
	}
}


























