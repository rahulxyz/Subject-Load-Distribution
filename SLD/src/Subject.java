
public class Subject {

		public String sid;//subjectID
		public String lid;//Labid
		
		
		public static final String SUBJECT_INFO_TABLE_NAME = "Subject";
		public static final String SUBJECT_ID= "subjectId";
		public static final String LAB_INFO_TABLE_NAME = "Lab";
		public static final String LAB_ID= "labId";
		
		public Subject() {
		}
		
		public Subject(String sid) {
			super();
			this.sid = sid;
		}
		
		public String getLid() {
			return lid;
		}

		public Subject(String sid, String lid) {
			super();
			this.sid = sid;
			this.lid = lid;
		}

		public void setLid(String lid) {
			this.lid = lid;
		}

		public String getSid() {
			return sid;
		}
		
		public void setSid(String sid) {
			this.sid = sid;
		}
		
		
}
