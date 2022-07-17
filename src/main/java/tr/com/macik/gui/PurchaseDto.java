package tr.com.macik.gui;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.Purchase;
import tr.com.macik.myapp.pojo.PurchaseDetails;

public class PurchaseDto {
	private static PurchaseDto thisSingleInstance;
	
	private PurchaseDto() {
	}
	
	public static PurchaseDto getInstance() {
		if (thisSingleInstance == null);
			thisSingleInstance = new PurchaseDto();
			
		return thisSingleInstance;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	// CRUD
	// Create
	public static Purchase create(Purchase purchase) {
		if (purchase==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pch/insert", purchase);
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchase(json);
	}

	public static PurchaseDetails create(PurchaseDetails purchaseDetails) {
		if (purchaseDetails==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pcd/insert", purchaseDetails);
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchaseDetails(json);
	}

	// Read
	public static Purchase readByID(Purchase purchase) {
		if (purchase==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/pch/"+purchase.getPchID());
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchase(json);
	}

	public static PurchaseDetails readByID(PurchaseDetails purchaseDetails) {
		if (purchaseDetails==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/pcd/"+purchaseDetails.getPcdID());
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchaseDetails(json);
	}

	public static List<Purchase> readAll() {
		NetClient client = new NetClient(NetClient.GET, "/pch/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toPurchaseList(json);
	}

	public static List<PurchaseDetails> readAllDetails() {
		NetClient client = new NetClient(NetClient.GET, "/pcd/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toPurchaseDetailsList(json);
	}

	// Update
	public static Purchase update(Purchase purchase) {
		if (purchase==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pch/update", purchase);
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchase(json);
	}

	public static PurchaseDetails update(PurchaseDetails purchaseDetails) {
		if (purchaseDetails==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pcd/update", purchaseDetails);
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchaseDetails(json);
	}

	// Delete
	public static Purchase delete(Purchase purchase) {
		if (purchase==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/pch/delete", purchase);
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchase(json);
	}

	public static PurchaseDetails delete(PurchaseDetails purchaseDetails) {
		if (purchaseDetails==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/pcd/delete", purchaseDetails);
		client.read();
		String json = client.toString();
		client.end();
		return firstPurchaseDetails(json);
	}

	// Helper
	private static Purchase firstPurchase(String json) {
		if (json == null || "".equals(json))
			return null;
		List<Purchase> purchases = toPurchaseList(json);
		return (purchases==null || purchases.size()==1) ? purchases.get(0) : null;
	}

	private static PurchaseDetails firstPurchaseDetails(String json) {
		if (json == null || "".equals(json))
			return null;
		List<PurchaseDetails> purchaseDetails = toPurchaseDetailsList(json);
		return (purchaseDetails==null || purchaseDetails.size()==1) ? purchaseDetails.get(0) : null;
	}
	
	private static List<Purchase> toPurchaseList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<Purchase> purchases = null;
		try {
			purchases = mapper.readValue(json, new TypeReference<List<Purchase>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purchases;
	}
	
	private static List<PurchaseDetails> toPurchaseDetailsList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<PurchaseDetails> purchaseDetails = null;
		try {
			purchaseDetails = mapper.readValue(json, new TypeReference<List<PurchaseDetails>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purchaseDetails;
	}

	public static List<Purchase> search(Purchase purchase) {
		if (purchase==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pch/search", purchase);
		client.read();
		String json = client.toString();
		client.end();
		return toPurchaseList(json);
	}

	public static List<PurchaseDetails> search(PurchaseDetails purchaseDetails) {
		if (purchaseDetails==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pcd/search", purchaseDetails);
		client.read();
		String json = client.toString();
		client.end();
		return toPurchaseDetailsList(json);
	}
}
