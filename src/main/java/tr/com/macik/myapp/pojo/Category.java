package tr.com.macik.myapp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

//Pojo
@Data
@Entity
@AllArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int catID;
	private String categoryLabel;
	private String categoryDescription;
	private byte[] categoryImage;
}
