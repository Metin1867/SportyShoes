package tr.com.macik.myapp.pojo;

import java.sql.Date;

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
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pchID;
	private int prsID;
	private Date basketCreated;
	private Date purchaseActivated;
	private int deliveryAddress;
	private Date deliveryDate;
	private int invoiceAddress;
	private Date paymentDate;
}
