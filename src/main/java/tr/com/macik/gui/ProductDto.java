package tr.com.macik.gui;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.pojo.Product;

public class ProductDto {
	private static ProductDto thisSingleInstance;
	
	private ProductDto() {
	}
	
	public static ProductDto getInstance() {
		if (thisSingleInstance == null);
			thisSingleInstance = new ProductDto();
			
		return thisSingleInstance;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	// CRUD
	// Create
	public static Product create(Product product) {
		if (product==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/prd/insert", product);
		client.read();
		String json = client.toString();
		client.end();
		return firstProduct(json);
	}

	// Read
	public static Product readByID(Product product) {
		if (product==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/prd/"+product.getPrdID());
		client.read();
		String json = client.toString();
		client.end();
		return firstProduct(json);
	}

	public static List<Product> readAll() {
		NetClient client = new NetClient(NetClient.GET, "/prd/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toProductList(json);
	}

	// Update
	public static Product update(Product product) {
		if (product==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/prd/update", product);
		client.read();
		String json = client.toString();
		client.end();
		return firstProduct(json);
	}

	// Delete
	public static Product delete(Product product) {
		if (product==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/prd/delete", product);
		client.read();
		String json = client.toString();
		client.end();
		return firstProduct(json);
	}

	// Helper
	private static Product firstProduct(String json) {
		if (json == null || "".equals(json))
			return null;
		List<Product> product = toProductList(json);
		return (product==null || product.size()==1) ? product.get(0) : null;
	}
	
	private static List<Product> toProductList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<Product> products = null;
		try {
			products = mapper.readValue(json, new TypeReference<List<Product>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

}
