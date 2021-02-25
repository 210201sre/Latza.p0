package egg.project000.models;

public class Drug {

	private int drugID;
	private String drugName;
	private static final Drug nullDrug = new Drug(-1, "you got the nullDrug obj");
	
	/*Constructors*/
	public Drug() {}
	public Drug(int a, String b) {drugID = a; drugName = b;}
	
	/*getters*/
	public String getDrugName() {return drugName;}	
	public int getDrugID() {return drugID;}
	public static Drug getNullDrug() {return nullDrug;}

	/*setters*/
	public void setDrugID(int drugID) {this.drugID = drugID;}
	public void setDrugName(String drugName) {this.drugName = drugName;}
	
	/*overrides from Obj type*/
}
