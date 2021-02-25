package egg.project000.models;

public class MedListElement {
	private String patientName;
	private String drugName;
	private int patientID;
	private int drugID;
	private int fills;
	private static final MedListElement nullElement = setNullElement();
	
	/*constructors*/
	public MedListElement() {}
	public MedListElement(int a, int b) {
		patientID = a;
		drugID = b;
	}
	public MedListElement(int a, int b, int c) {
		patientID = a;
		drugID = b;
		fills = c;
	}
	public MedListElement(int a, int b, int c, String d, String e) {
		patientID = a;
		drugID = b;
		fills = c;
		patientName = d;
		drugName = e;
	}


	/*getters*/
	public String getPatientName() {return patientName;}
	public String getDrugName() {return drugName;}
	public int getPatientID() {return patientID;}
	public int getDrugID() {return drugID;}
	public int getFills() {return fills;}
	public static MedListElement getNullElement() {return nullElement;}

	/*setters*/
	public void setPatientName(String patientName) {this.patientName = patientName;}
	public void setDrugName(String drugName) {this.drugName = drugName;}
	public void setPatientID(int patientID) {this.patientID = patientID;}
	public void setDrugID(int drugID) {this.drugID = drugID;}
	public void setFills(int fills) {this.fills = fills;}
	private static MedListElement setNullElement() {
		MedListElement m = new MedListElement(-1,-1,-1);
		m.setPatientName("this is the patient name of thenull element");
		m.setDrugName("this is the drug name of the nullElement");
		return m;
	}
	
	/*Overrides from Obj*/
	@Override
	public String toString() {
		return "MedListElement [patientName=" + patientName + ", drugName=" + drugName + ", patientID=" + patientID
				+ ", drugID=" + drugID + ", fills=" + fills + "]";
	}
	
	
}
