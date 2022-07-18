package tr.com.macik.myapp.report;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

//Pojo
@Data
@Entity
@Table(name="purchase_by_date_report")
@Immutable
@EqualsAndHashCode(callSuper=false)
public class PurchaseByDateReport extends PurchaseReport{
	@Id
	private int purchaseDetailIdentifier;
	private int personIdentifier;
	private String fullname;
	private int purchaseIdentifier;
	private Date purchaseDate;
	private Date paymentDate;
	private Date deliveryDate;
	private int productIdentifier;
	private String productLabel;
	private int count;
	private String unitPrice;
	private String productPrice;
}
