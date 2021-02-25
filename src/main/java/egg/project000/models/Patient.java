package egg.project000.models;

public class Patient {

	private int patientID;
	private String fullName;
	private String addr;
	
	/*Constructors*/
	public Patient() {}
	public Patient(int id, String name, String anAddr) {
		patientID = id;
		fullName = name;
		addr = anAddr;
	}

	/*getters*/
	public String getFullName() {return fullName;}
	public String getAddr() {return addr;}
	public int getPatientID() {return patientID;}

	/*setters*/
	public void setFullName(String fullName) {this.fullName = fullName;}
	public void setAddr(String addr) {this.addr = addr;}
	public void setPatientID(int patientID) {this.patientID = patientID;}
	
	/*overrides from Obj type*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addr == null) ? 0 : addr.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (addr == null) {
			if (other.addr != null)
				return false;
		} else if (!addr.equals(other.addr))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Patient [paientID=" + patientID + ", fullName=" + fullName + ", addr=" + addr + "]";
	}
}
