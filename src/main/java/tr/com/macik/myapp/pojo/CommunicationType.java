package tr.com.macik.myapp.pojo;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

//Pojo
@Data
@Entity
public class CommunicationType {
	@Id
	private String cmmCd;
	private String masterCd;
	private String label;
	private String rexpression;
}

class CommunicationTypeData extends ArrayList<String[]> {
	private static final long serialVersionUID = 1L;
	CommunicationType tmpCommunicationType;
	
	public CommunicationTypeData() {
		super();
		this.add(getArr("EMAIL", null, "Email", "abc@a.b"));
		this.add(getArr("PHONE", null, "Phone", "076 321 1234"));
		this.add(getArr("MADR", null, "Main Address", null));
		this.add(getArr("MADR_LN1", "MADR", "Address Line 1", null));
		this.add(getArr("MADR_LN2", "MADR", "Address Line 2", null));
		this.add(getArr("MADR_PCD", "MADR", "Postal Code", null));
		this.add(getArr("DADR", null, "Delivery Address", null));
		this.add(getArr("DADR_LN1", "DADR", "Address Line 1", null));
		this.add(getArr("DADR_LN2", "DADR", "Address Line 2", null));
		this.add(getArr("DADR_PCD", "DADR", "Postal Code", null));
		this.add(getArr("IADR", null, "Invoice Address", null));
		this.add(getArr("IADR_LN1", "IADR", "Address Line 1", null));
		this.add(getArr("IADR_LN2", "IADR", "Address Line 2", null));
		this.add(getArr("IADR_PCD", "IADR", "Postal Code", null));
		this.add(getArr("TWITTER", null, "Twitter", "@metin"));

	}

		private String[] getArr(String s1, String s2, String s3, String s4) {
			String[] sarr = {s1, s2, s3, s4};
			return sarr;
		}
}