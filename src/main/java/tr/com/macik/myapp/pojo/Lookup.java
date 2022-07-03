package tr.com.macik.myapp.pojo;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

//Pojo
@Data
@Entity
public class Lookup{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lkpID;
	private String lkClass;
	private String lkKey;
	private String lkValue;
}

class LookupData extends ArrayList<String[]> {
	private static final long serialVersionUID = 1L;
	
	public LookupData() {
		super();
		this.add(getArr("PLZ", "8820", "Wädenswil"));
		this.add(getArr("SALUTATION", "u", "Unknown"));
		this.add(getArr("SALUTATION", "f", "Miss"));
		this.add(getArr("SALUTATION", "m", "Mister"));
		this.add(getArr("CIVILSTAT", "u", "Unknown"));
		this.add(getArr("CIVILSTAT", "s", "Single"));
		this.add(getArr("CIVILSTAT", "m", "Married"));
		this.add(getArr("CIVILSTAT", "d", "Divorced"));
		this.add(getArr("PLZ", "8804", "Au ZH"));
		this.add(getArr("PLZ", "8810", "Horgen"));
		this.add(getArr("PLZ", "8820", "Hirzel"));
		this.add(getArr("PLZ", "8820", "Richterswil"));
		this.add(getArr("PLZ", "8820", "Oberrieden"));
		this.add(getArr("PLZ", "8800", "Thalwil"));
		this.add(getArr("PLZ", "8820", "Rüschlikon"));

	}

		private String[] getArr(String s1, String s2, String s3) {
			String[] sarr = {s1, s2, s3};
			return sarr;
		}
}