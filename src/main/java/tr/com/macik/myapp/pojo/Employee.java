package tr.com.macik.myapp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

//Pojo
@Data
@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empID;
	private int prsID;
	private String socialSecurityNumber;
	private String civilStatus;				// unknown, single, married, divorced
	private int numberOfChild = 0;
	private String phone;
	private String email;
	private String emergencyContact;
	private String emergencyNumber;
}
