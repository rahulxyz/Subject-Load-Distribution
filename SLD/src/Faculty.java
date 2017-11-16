
public class Faculty {

	public String sapid;
	public String name;
	public String designation;
	public String year;
	
	//these two need to be calculated from year and designation
	/*public String hours_allotted;
	public String hours_available;*/
	public String credits;
	
	//Priority_core_1
	public String pc1;
	public String pc2;
	public String pc3;
	public String pc4;
	public String pc5;
	
	//Priority_domain_1
	public String pd1;
	public String pd2;
	public String pd3;
	public String pd4;
	public String pd5;

	public static final String FACULTY_INFO_TABLE_NAME = "Faculty";
	public static final String FINALFACULTY_INFO_TABLE_NAME = "FinalFaculty";
	public static final String FACULTY_INFO_TABLE_NAME_Sorted= "FacultySorted";
	
	public static final String SAPID ="sapid";
	public static final String NAME ="name";
	public static final String DESIGNATION ="designation";
	public static final String YEAR = "year";
	public static final String CREDITS="credits";
	/*public static final String HOURS_ALLOTED="hours_alloted";
	public static final String HOURS_AVAILABLE="hours_available";*/
	public static final String PRIORITY_CORE_1="priority_core_1";
	public static final String PRIORITY_CORE_2="priority_core_2";
	public static final String PRIORITY_CORE_3="priority_core_3";
	public static final String PRIORITY_CORE_4="priority_core_4";
	public static final String PRIORITY_CORE_5="priority_core_5";
	public static final String PRIORITY_DOMAIN_1="priority_domain_1";
	public static final String PRIORITY_DOMAIN_2="priority_domain_2";
	public static final String PRIORITY_DOMAIN_3="priority_domain_3";
	public static final String PRIORITY_DOMAIN_4="priority_domain_4";
	public static final String PRIORITY_DOMAIN_5="priority_domain_5";
	
	public static final String SUBJECT_1="subject_1";
	public static final String SUBJECT_2="subject_2";
	public static final String SUBJECT_3="subject_3";
	public static final String SUBJECT_4="subject_4";
	public static final String SUBJECT_5="subject_5";
	public static final String SUBJECT_6="subject_6";
	public static final String SUBJECT_7="subject_7";
	public static final String SUBJECT_8="subject_8";
	public static final String SUBJECT_9="subject_9";
	public static final String SUBJECT_10="subject_10";
	
	public static final int Subject1=4;
	public static final int Subject10=13;
	public static final int CORE_1= 6;
	public static final int CORE_5= 10;
	public static final int DOMAIN_1= 11;
	public static final int DOMAIN_5= 15;
	
	public Faculty() {	
	}

	
	public Faculty(String sapid, String name, String designation, String year) {
		super();
		this.sapid = sapid;
		this.name = name;
		this.designation = designation;
		this.year = year;
	}
	
	
	

	
	public Faculty(String sapid, String name, String designation, String year, String credits) {
		super();
		this.sapid = sapid;
		this.name = name;
		this.designation = designation;
		this.year = year;
		this.credits = credits;
	}

	

	public Faculty(String sapid, String name, String designation, String year, String pc1, String pc2, String pc3,
			String pc4, String pc5, String pd1, String pd2, String pd3, String pd4, String pd5) {
		super();
		this.sapid = sapid;
		this.name = name;
		this.designation = designation;
		this.year = year;
		this.pc1 = pc1;
		this.pc2 = pc2;
		this.pc3 = pc3;
		this.pc4 = pc4;
		this.pc5 = pc5;
		this.pd1 = pd1;
		this.pd2 = pd2;
		this.pd3 = pd3;
		this.pd4 = pd4;
		this.pd5 = pd5;
	}


	


	public Faculty(String sapid, String name, String designation, String year, String credits, String pc1, String pc2,
			String pc3, String pc4, String pc5, String pd1, String pd2, String pd3, String pd4, String pd5) {
		super();
		this.sapid = sapid;
		this.name = name;
		this.designation = designation;
		this.year = year;
		this.credits = credits;
		this.pc1 = pc1;
		this.pc2 = pc2;
		this.pc3 = pc3;
		this.pc4 = pc4;
		this.pc5 = pc5;
		this.pd1 = pd1;
		this.pd2 = pd2;
		this.pd3 = pd3;
		this.pd4 = pd4;
		this.pd5 = pd5;
	}



	public String getCredits() {
		return credits;
	}


	public void setCredits(String credits) {
		this.credits = credits;
	}


	public String getSapid() {
		return sapid;
	}

	public void setSapid(String sapid) {
		this.sapid = sapid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


	public String getPc1() {
		return pc1;
	}


	public void setPc1(String pc1) {
		this.pc1 = pc1;
	}


	public String getPc2() {
		return pc2;
	}


	public void setPc2(String pc2) {
		this.pc2 = pc2;
	}


	public String getPc3() {
		return pc3;
	}


	public void setPc3(String pc3) {
		this.pc3 = pc3;
	}


	public String getPc4() {
		return pc4;
	}


	public void setPc4(String pc4) {
		this.pc4 = pc4;
	}


	public String getPc5() {
		return pc5;
	}


	public void setPc5(String pc5) {
		this.pc5 = pc5;
	}


	public String getPd1() {
		return pd1;
	}


	public void setPd1(String pd1) {
		this.pd1 = pd1;
	}


	public String getPd2() {
		return pd2;
	}


	public void setPd2(String pd2) {
		this.pd2 = pd2;
	}


	public String getPd3() {
		return pd3;
	}


	public void setPd3(String pd3) {
		this.pd3 = pd3;
	}


	public String getPd4() {
		return pd4;
	}


	public void setPd4(String pd4) {
		this.pd4 = pd4;
	}


	public String getPd5() {
		return pd5;
	}


	public void setPd5(String pd5) {
		this.pd5 = pd5;
	}
	
	
	
}
