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
@Table(name="purchase_by_category_report")
@Immutable
@EqualsAndHashCode(callSuper=false)
public class PurchaseByCategoryReport {
	private int categoryIdentifier;
	private String category;
	@Id
	private int productIdentifier;
	private String productLabel;
	private int count;
	private String avgUnitPrice;
	private String productPrice;
	private Date lastPurchaseDate;
}