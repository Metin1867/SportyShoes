package tr.com.macik.myapp.pojo;

import java.sql.Timestamp;

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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int usrID;		// workaround
	/*@Id
	@GenericGenerator(
		        name = "assigned-identity",
		        strategy = "tr.com.macik.myapp.pojo.AssignedIdentityGenerator"
    )
    @GeneratedValue(
        generator = "assigned-identity",
        strategy = GenerationType.IDENTITY
    )*/
	private String usrLogin;		// email
	private String usrPassword;
	private int prsID = -1;
	private boolean activated = false;
	private Timestamp  lastSuccessLogin;
	private Timestamp  lastFailedLogin;
	private int counterLogin = 0;	// -4			blocked
									// -1, -2, -3	failed
									// 0			initial
									// 1, 2, 3, ...	succesful
}
