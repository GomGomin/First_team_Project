package hr.vo;

public class WorkRecordVO {
	private int WorkRecordNo;
	private String id;
	private String department;
	private String position;
	private String startWorkingDate;
	private String endWorkingDate;

	public int getWorkRecordNo() {
		return WorkRecordNo;
	}

	public void setWorkRecordNo(int workRecordNo) {
		WorkRecordNo = workRecordNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStartWorkingDate() {
		return startWorkingDate;
	}

	public void setStartWorkingDate(String startWorkingDate) {
		this.startWorkingDate = startWorkingDate;
	}

	public String getEndWorkingDate() {
		return endWorkingDate;
	}

	public void setEndWorkingDate(String endWorkingDate) {
		this.endWorkingDate = endWorkingDate;
	}
}