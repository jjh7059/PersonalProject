package consult_prog;

public class ConsultDTO {
	private String code;
	private String consultant;
	private String name;
	private String con_date;
	private String con_stat;
	
	public ConsultDTO() {
		// TODO Auto-generated constructor stub
	}

	public ConsultDTO(String code, String consultant, String name, String con_date, String con_stat) {
		super();
		this.code = code;
		this.consultant = consultant;
		this.name = name;
		this.con_date = con_date;
		this.con_stat = con_stat;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConsultant() {
		return consultant;
	}

	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCon_date() {
		return con_date;
	}

	public void setCon_date(String con_date) {
		this.con_date = con_date;
	}

	public String getCon_stat() {
		return con_stat;
	}

	public void setCon_stat(String con_stat) {
		this.con_stat = con_stat;
	}

	
}
