package tr.com.macik.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Purchase;
import tr.com.macik.myapp.repo.PurchaseRepo;
import tr.com.macik.utils.ServletHTMLUtil;

//all dao operations are written under @Service 
@Service
public class PurchaseDAO {
	@Autowired
	PurchaseRepo repo;
	@Autowired
	DataSource dataSource;

	// DB INSERT operation
	public Purchase insert(Purchase s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Purchase> getall(){
		return repo.findAll();
	}

	// DB SELECT operation, retrieve all founded objects/entities
	public List<Purchase> search(Purchase purchase) {
		// throw new IllegalCallerException("Search function isn't yet implemented!");

		List<Purchase> list = new ArrayList<>();
	    String sql = "SELECT pchid, prsid, basket_created, purchase_activated,"
	    		+ " delivery_address, delivery_date,invoice_address, payment_date"
	    		+ " FROM purchase";
		String whereClause = "";
		ArrayList<Integer> colPositions = new ArrayList<>();
		if (purchase.getPchID() >0) {
			whereClause += ServletHTMLUtil.andWhere(whereClause, "pchid", purchase.getPchID());
			//colPositions.add(1);
		} else {
			if (purchase.getPrsID() >0) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "prsid", purchase.getPrsID());
			}
			if (purchase.getBasketCreated()!= null) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "basket_created", purchase.getBasketCreated().toString());
				//colPositions.add(5);
			}
			if (purchase.getPurchaseActivated()!= null) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "purchase_activated", purchase.getPurchaseActivated().toString());
				//colPositions.add(5);
			}
			if (purchase.getDeliveryAddress() >0) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "delivery_address", purchase.getDeliveryAddress());
			}
			if (purchase.getDeliveryDate()!= null) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "delivery_date", purchase.getDeliveryDate().toString());
				//colPositions.add(5);
			}
			if (purchase.getInvoiceAddress() >0) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "invoice_address", purchase.getInvoiceAddress());
			}
			if (purchase.getPaymentDate()!= null) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "payment_date", purchase.getPaymentDate().toString());
				//colPositions.add(5);
			}
		}
		if (!"".equals(whereClause.trim())) {
			sql += " where" + whereClause;
			System.out.println("Person search " + sql);

			try ( Connection c = dataSource.getConnection();
				  PreparedStatement p = c.prepareStatement(sql) ) {
				int pos = 1;
				for (int colPos : colPositions) {
					System.out.print("Search attribute at Position " + colPos + "/");
					try {
						switch (colPos) {
						case 1: p.setInt(pos, purchase.getPchID()); System.out.println(purchase.getPrsID()); break;
						case 2: p.setInt(pos, purchase.getPrsID()); System.out.println(purchase.getPrsID()); break;
						case 3: p.setDate(pos, purchase.getBasketCreated()); System.out.println(purchase.getBasketCreated()); break;
						case 4: p.setDate(pos, purchase.getPurchaseActivated()); System.out.println(purchase.getPurchaseActivated()); break;
						case 5: p.setInt(pos, purchase.getDeliveryAddress()); System.out.println(purchase.getDeliveryAddress()); break;
						case 6: p.setDate(pos, purchase.getDeliveryDate()); System.out.println(purchase.getDeliveryDate()); break;
						case 7: p.setInt(pos, purchase.getInvoiceAddress()); System.out.println(purchase.getInvoiceAddress()); break;
						case 8: p.setDate(pos, purchase.getPaymentDate()); System.out.println(purchase.getPaymentDate()); break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pos++;
				}
			    ResultSet rs = p.executeQuery(sql);
			    
			    Purchase newPurchase;
			    while(rs.next()) {
			    	newPurchase = new Purchase(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getInt(5), rs.getDate(6), rs.getInt(7), rs.getDate(8));
			    	list.add(newPurchase);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Purchase update(Purchase obj) {
		Purchase db=repo.findById(obj.getPchID()).orElse(null);
		db.setPrsID(obj.getPrsID());
		db.setBasketCreated(obj.getBasketCreated());
		db.setPurchaseActivated(obj.getPurchaseActivated());
		db.setDeliveryAddress(obj.getDeliveryAddress());
		db.setDeliveryDate(obj.getDeliveryDate());
		db.setInvoiceAddress(obj.getInvoiceAddress());
		db.setPaymentDate(obj.getPaymentDate());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Purchase> insertAll(List<Purchase> pchList) {
		return repo.saveAll(pchList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Purchase get(int pchID){
		return repo.findById(pchID).orElse(null);
	}
}
