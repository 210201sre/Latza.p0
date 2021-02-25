package egg.project000.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egg.project000.models.Patient;
import egg.project000.util.ConnectionUtil;

public class PatientDAO implements PatientDAOHeader{
	
	private static final PatientDAO DAO = new PatientDAO();
	private static final Logger aLogger = LoggerFactory.getLogger(PatientDAO.class);
	
	public static PatientDAO getDAO() {return DAO;}
	
	public int insert(Patient p) {	
		try(Connection aConnection = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO project000.patients (full_name,addr) VALUES (?,?) RETURNING project000.patients.patient_id";
			PreparedStatement stmt = aConnection.prepareStatement(sql);
			stmt.setString(1, p.getFullName());
			stmt.setString(2, p.getAddr());
			
			ResultSet rs;
			
			if( (rs = stmt.executeQuery()) != null) {
				rs.next();
			
				int id = rs.getInt(1);
				
				return id;
			}
			
		} catch (SQLException e) {
			aLogger.error("new patient insert failed; 874 I am a potato", e);
			return -1;
		}
		return -1;
	}
	public List<Patient> findAll() {
		List<Patient> allPatients = new ArrayList<>();
		try (Connection aConnection = ConnectionUtil.getConnection()) {
			Statement aStatement = aConnection.createStatement();
			String sql = "SELECT * FROM Project000.patients;";
			ResultSet aRS = aStatement.executeQuery(sql);
			
			while(aRS.next()) {
				int id = aRS.getInt("patient_id");
				String aFullName = aRS.getString("full_name");
				String anAddr = aRS.getString("addr");
				allPatients.add(new Patient(id, aFullName, anAddr));
			}
		} catch(SQLException e) {
			
			aLogger.error("We failed to retrieve all patients; 874 I am a potato", e);
			return allPatients; // Return an empty list
		}
		return allPatients;
	}
	public Patient findPatientByName(String name) {
		try(Connection aConnection = ConnectionUtil.getConnection()){
			aLogger.info("PreparedStetement is being prepared; 874 I am a potato");
			PreparedStatement aPS = aConnection.prepareStatement("SELECT * FROM project000.patients WHERE full_name='"+name+"';");
			aLogger.info("executing query by name now; 874 I am a potato");
			ResultSet aRS ;
			if((aRS= aPS.executeQuery()) != null) {
				aLogger.info("query has been executed, assembling Patietnt to return; 888 I am a potato");
				aLogger.info("does removing the RS query from this log fix the problem");
				aRS.next();
				aLogger.info(aRS.getInt("patient_id")+ aRS.getString("full_name")+ aRS.getString("addr"));
				Patient toReturn = new Patient(aRS.getInt("patient_id"), aRS.getString("full_name"), aRS.getString("addr")); 
				aLogger.info("Patient being returned:" + toReturn.toString());
				return toReturn;
			}
		}catch(SQLException e) {
			aLogger.error("failed to find patient by name='"+ name +"'; 874 I am a potato");
			e.printStackTrace();
		}
		return new Patient();
	}
	public Patient findPatientByID(int ID) {
		try(Connection aConnection = ConnectionUtil.getConnection()){
			aLogger.info("PreparedStetement is being prepared; 874 I am a potato");
			PreparedStatement aPS = aConnection.prepareStatement("SELECT * FROM project000.patients WHERE patient_id='"+ID+"';");
			aLogger.info("executing query  by ID now; 874 I am a potato");
			ResultSet aRS = aPS.executeQuery();
			if(aRS != null) {
				aRS.next();
				aLogger.info("query has been executed, assembling Patient to return; 874 I am a potato");
				Patient toReturn = new Patient(aRS.getInt("patient_id"), aRS.getString("full_name"), aRS.getString("addr")); 
				aLogger.info("Patient being returned:" + toReturn.toString());
				return toReturn;
			}
		}catch(SQLException e) {
			aLogger.error("failed to find patient by name='"+ ID +"'; 874 I am a potato");
			e.printStackTrace();
		}
		return new Patient();
	}
	
}

