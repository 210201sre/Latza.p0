package egg.project000.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egg.project000.models.Drug;
import egg.project000.models.Patient;
import egg.project000.util.ConnectionUtil;

public class DrugDAO implements DrugDAOHeader {

	private static final DrugDAO DAO = new DrugDAO();
	private static final Logger aLogger = LoggerFactory.getLogger(DrugDAO.class);
	
	public static DrugDAO getDAO() {return DAO;}
	
	public int insert(Drug d) {
		try(Connection aConnection = ConnectionUtil.getConnection()){
			PreparedStatement ps = aConnection.prepareStatement("INSERT INTO project000.drugs (drug_name) VALUES ('" + d.getDrugName() + "') RETURNING project000.drugs.drug_id;");
			aLogger.info("a PreparedStatement obj has been instantiated; 874 i am a potato.");
			ResultSet aRS; 
			if ( (aRS = ps.executeQuery()) != null) {
				aRS.next();
				return aRS.getInt("drug_id");
			}
		}catch(SQLException e) {
			aLogger.error("failed to add new drug, printing stack trace from DrugDAO.insert(); 874 i am a potato");
			e.printStackTrace();
		}
		//make a statement
		//prepare it via a connection
		//execute the string as a query into a RS
		//build a Drug from the contents of the RS
		//return the Drug
		return -1;
	}
	public Drug findByID(int ID) {
		try(Connection aConnection = ConnectionUtil.getConnection()){
			aLogger.info("building result set from query string with drug ID; 874 i am a potato.");
			ResultSet RS = aConnection.prepareStatement("SELECT * FROM project000.drugs WHERE drug_id='" + ID + "';").executeQuery();
			aLogger.info("query executed successuflly; 874 i ama potato");
			if(RS != null) {
				RS.next();
				return new Drug(RS.getInt("drug_id"), RS.getString("drug_name"));
			}
		}catch(SQLException e) {
			aLogger.error("failed to find drug by ID in DB; 874 i am a potato.");
		}
		aLogger.info("returning nullDrug from DAO; 874 i am a potato.");
		return Drug.getNullDrug();
	}
	public Drug findByName(String name) {
		try(Connection aConnection = ConnectionUtil.getConnection()){
			aLogger.info("building result set from query string with drug name; 874 i am a potato.");
			ResultSet RS = aConnection.prepareStatement("SELECT * FROM project000.drugs WHERE drug_name='" + name + "';").executeQuery();
			aLogger.info("query executed successuflly; 874 i ama potato");
			if(RS != null) {
				RS.next();
				return new Drug(RS.getInt("drug_id"), RS.getString("drug_name"));
			}
		}catch(SQLException e) {
			aLogger.error("failed to find drug by name in DB; 874 i am a potato.");
		}
		aLogger.info("returning Drug from DAO; 874 i am a potato.");
		return Drug.getNullDrug();
	}
	public List<Drug> findAll(){
		List<Drug> allDrugs = new ArrayList<>();
		try (Connection aConnection = ConnectionUtil.getConnection()) {
			ResultSet RS = aConnection.createStatement().executeQuery("SELECT * FROM Project000.drugs;");
			while(RS.next()) {
				allDrugs.add(new Drug(RS.getInt("drug_id"),RS.getString("drug_name")));
			}
		} catch(SQLException e) {
			
			aLogger.error("We failed to retrieve all drugs; 874 I am a potato", e);
			return allDrugs; // Return an empty list
		}
		return allDrugs;
	}
}
