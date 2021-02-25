package egg.project000.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egg.project000.exceptions.MedListException;
import egg.project000.models.MedListElement;
import egg.project000.util.ConnectionUtil;

public class MedListDAO implements MedListDAOHeader {
	 /*fields*/
	private static final MedListDAO DAO = new MedListDAO();
	private static final PatientDAO pDAO = PatientDAO.getDAO();
	private static final DrugDAO dDAO = DrugDAO.getDAO();
	private static final Logger aLogger = LoggerFactory.getLogger(MedListDAO.class);
	/*getters and setters*/
	public static MedListDAO getDAO() {return DAO;}
	
	/*DAO functions*/
	public MedListElement insert(MedListElement m) {
		trySettingIDs(m);
		aLogger.info("checking if the passed element is already in the list");
		if (!elementAlreadyExists(m)) {
			try(Connection aConnection = ConnectionUtil.getConnection()){
				aLogger.info("preparing statement: '''INSERT INTO project000.patient_meds VALUES(" + m.getPatientID() + "," + m.getDrugID() +"," + m.getFills() + ");'''");
				PreparedStatement PS = aConnection.prepareStatement("INSERT INTO project000.patient_meds VALUES (" + m.getPatientID() + "," + m.getDrugID() + "," + m.getFills() + ") RETURNING project000.patient_meds.new_int;");
				aLogger.info("a PreparedStatement obj has been instantiated; 874 i am a potato.");
				ResultSet RS;
				if( (RS = PS.executeQuery()) != null) {
					RS.next();
					RS = aConnection.prepareStatement("SELECT * from project000.patient_meds WHERE new_int='"+RS.getInt("new_int")+"';").executeQuery();
					RS.next();
					aLogger.info("ResultSet has been defined and the following values can be obtained from it: " + RS.getInt("p_id")+", "+RS.getInt("d_id")+","+RS.getInt("fills_left"));
					return new MedListElement(RS.getInt("p_id"), RS.getInt("d_id"), RS.getInt("fills_left"));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return MedListElement.getNullElement();
	}
	public MedListElement incFill(MedListElement m) {
		aLogger.info("incFill method has been accessed");
		trySettingIDs(m);
		try(Connection aConnection = ConnectionUtil.getConnection()){
			PreparedStatement PS = aConnection.prepareStatement("SELECT * FROM project000.patient_meds WHERE p_id = '"+m.getPatientID()+"' AND d_id = '"+m.getDrugID()+"';");
			aLogger.info("a PreparedStatement obj has been instantiated; 874 i am a potato.");
			ResultSet RS;
			if( (RS = PS.executeQuery()) != null) {
				aLogger.info("statement executed. Rx to update as been found");
				RS.next();
				if (aConditionalFunc(RS)) {
					aLogger.info("updating fill count");
					int  FL = RS.getInt("fills_left") + 1;
					aLogger.info("putting updated fillcout into table");
					RS = aConnection.prepareStatement("UPDATE project000.patient_meds SET fills_left = "+FL+" WHERE p_id = '"+m.getPatientID()+"' AND d_id = '"+m.getDrugID()+"'RETURNING fills_left;").executeQuery();
				}
				RS.next();
				aLogger.info("repopulating MedListElement with new values: "+RS.getInt("fills_left"));
				m.setFills(RS.getInt("fills_left"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	public MedListElement decFill(MedListElement m) {
		aLogger.info("decFill method has been accessed");
		boolean cf = false;
		trySettingIDs(m);
		try(Connection aConnection = ConnectionUtil.getConnection()){
			PreparedStatement PS = aConnection.prepareStatement("SELECT * FROM project000.patient_meds WHERE p_id = '"+m.getPatientID()+"' AND d_id = '"+m.getDrugID()+"';");
			aLogger.info("a PreparedStatement obj has been instantiated; 874 i am a potato.");
			ResultSet RS;
			if( (RS = PS.executeQuery()) != null) {
				aLogger.info("statement executed. Rx to update as been found");
				RS.next();
				if (aConditionalFunc(RS)) {
					aLogger.info("updating fill count from: "+RS.getInt("fills_left"));
					int  FL = RS.getInt("fills_left") - 1;
					if (FL < 0) {
						FL = 0;
						cf = true;
					}
					aLogger.info("putting updated fillcout into table: "+FL);
					RS = aConnection.prepareStatement("UPDATE project000.patient_meds SET fills_left = "+FL+" WHERE p_id = '"+m.getPatientID()+"' AND d_id = '"+m.getDrugID()+"'RETURNING fills_left;").executeQuery();
				}
				RS.next();
				aLogger.info("repopulating MedListElement with new values: "+RS.getInt("fills_left"));
				m.setFills(RS.getInt("fills_left"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(cf)
			m.setPatientName("Cary Flag Thrown!");
		return m;
	}
	public List<MedListElement> getMedList(int ID){
		List<MedListElement> medList = new ArrayList<>();
		try(Connection aConnection = ConnectionUtil.getConnection()){
			ResultSet RS = aConnection.prepareStatement("SELECT * FROM project000.patient_meds WHERE p_id = '"+ID+"';").executeQuery();
			String pName = pDAO.findPatientByID(ID).getFullName();
			while(RS.next()) {
				medList.add(new MedListElement(RS.getInt("p_id"), RS.getInt("d_id"), RS.getInt("fills_left"), pName, dDAO.findByID(RS.getInt("d_id")).getDrugName()));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return medList;
	}
	
	
	/*utility functions*/
 	private void setElementIDs(MedListElement m) {
		m.setPatientID(pDAO.findPatientByName(m.getPatientName()).getPatientID());
		m.setDrugID(dDAO.findByName(m.getDrugName()).getDrugID());
		if (m.getDrugID() < 1) 
			throw new MedListException("bad drug ID");
		if (m.getPatientID() < 1) 
			throw new MedListException("bad patient ID");
	}
	private void trySettingIDs(MedListElement m) {
		try(Connection aConnection = ConnectionUtil.getConnection()){
			setElementIDs(m);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	private boolean aConditionalFunc(ResultSet RS) throws SQLException{
		aLogger.info("from insid aConditionalFunc()");
		aLogger.info(RS.getInt("d_id")+","+RS.getInt("p_id"));
		return ( (RS.getInt("p_id") > 0) && (RS.getInt("d_id")>0) );
	}
	private boolean elementAlreadyExists(MedListElement m) {
		try(Connection aConnection = ConnectionUtil.getConnection()){
			ResultSet RS = aConnection.prepareStatement("SELECT * FROM project000.patient_meds;").executeQuery();
			while(RS.next()) {
				aLogger.info((m.getPatientID() == RS.getInt("p_id")) +" & "+ (m.getDrugID() == RS.getInt("d_id")));
				if( (m.getPatientID() == RS.getInt("p_id")) && (m.getDrugID() == RS.getInt("d_id")))
					return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
