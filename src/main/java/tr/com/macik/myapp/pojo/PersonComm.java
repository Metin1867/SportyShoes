package tr.com.macik.myapp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Pojo
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PersonComm {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pcoID;
	private int prsID;
	private String cmmCd;
	private String subinfo;
	private String label;
	private String info;
}
