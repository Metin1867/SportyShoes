package tr.com.macik.myapp.pojo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

//Pojo
@Data
@Entity
public class User {
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private String usrLogin;		// email
	private String usrPassword;
	private int prsID = -1;
	private Date lastSuccessLogin;
	private Date lastFailedLogin;
	private int counterLogin = 0;	// -4			blocked
									// -1, -2, -3	failed
									// 0			initial
									// 1, 2, 3, ...	succesful
}
