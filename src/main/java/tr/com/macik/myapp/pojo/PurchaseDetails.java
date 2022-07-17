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
public class PurchaseDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pcdID;
	private int pchID;
	private int prdID;
	private boolean enabled = true;
	private int count;
	private float price;
}
