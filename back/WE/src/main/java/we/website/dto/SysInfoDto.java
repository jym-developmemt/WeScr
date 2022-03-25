package we.website.dto;


import we.base.dto.ReceiveDto;

public class SysInfoDto  extends ReceiveDto {
	
	private String id;
	
	private int ctrl_id;
	
	private String desception;
	
	private String value;
	
	private String type;
	
	private String admin;
	
	private String use_flg;
	
	private String edit_page;
	
	private String created_date;
	
	private String created_by;
	
	private String updated_date;
	
	private String updated_by;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCtrl_id() {
		return ctrl_id;
	}

	public void setCtrl_id(int ctrl_id) {
		this.ctrl_id = ctrl_id;
	}

	public String getDesception() {
		return desception;
	}

	public void setDesception(String desception) {
		this.desception = desception;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getUse_flg() {
		return use_flg;
	}

	public void setUse_flg(String use_flg) {
		this.use_flg = use_flg;
	}

	public String getEdit_page() {
		return edit_page;
	}

	public void setEdit_page(String edit_page) {
		this.edit_page = edit_page;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	
	
	

}
