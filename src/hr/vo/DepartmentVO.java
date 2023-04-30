package hr.vo;

public class DepartmentVO {
	
	private int deptNo;
	private String departmentName;
	
	public DepartmentVO() {
	
	}
	
	public DepartmentVO(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public int getDeptNo() {
		return deptNo;
	}
	
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
	
	

}
