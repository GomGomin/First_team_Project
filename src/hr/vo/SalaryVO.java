package hr.vo;

public class SalaryVO {
	private String id;
	private String bankName;
	private String accountNumber;
	private int payment;
	private String paymentDate;
	private String insertId;

	public SalaryVO(String id, String accountNumber, String bankName, int payment, String paymentDate) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.bankName = bankName;
		this.payment = payment;
		this.paymentDate = paymentDate;

	}

	public SalaryVO(String id, String accountNumber, String bankName, int payment, String paymentDate,
			String insertId) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.bankName = bankName;
		this.payment = payment;
		this.paymentDate = paymentDate;
		this.insertId = insertId;
	}

	public SalaryVO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getInsertId() {
		return insertId;
	}

	public void setInsertId(String insertId) {
		this.insertId = insertId;
	}

}
