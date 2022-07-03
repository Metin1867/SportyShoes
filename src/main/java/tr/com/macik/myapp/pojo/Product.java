package tr.com.macik.myapp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

//Pojo
@Data
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int prdID;
	private int catID;
	private String productLabel;
	private String productDescription;
	private String productDetails;
	// private Image productImage;
	private float price;
}
