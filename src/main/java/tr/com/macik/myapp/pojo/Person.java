package tr.com.macik.myapp.pojo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

//Pojo
@Data
@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int prsID;
	private String salutation;	// unknown, Herr, Frau
	private String firstname;
	private String lastname;
	private Date birthday;
}
