package tr.com.macik.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import tr.com.macik.myapp.pojo.Product;

@SpringBootTest
public class ProductServiceControllerTest extends AbstractTest {
	   @Override
	   @Before
	   public void setUp() {
	      super.setUp();
	   }
	   @Test
	   public void getProductsList() throws Exception {
		  System.out.println("TEST: getProductsList()"); 
		  String uri = "/prd/getall";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      Product[] productlist = super.mapFromJson(content, Product[].class);
	      assertTrue(productlist.length > 0);
	   }
	   @Test
	   public void createProduct() throws Exception {
		  System.out.println("TEST: createProduct()"); 
	      String uri = "/prd/insert";
	      Product product = new Product();
	      product.setPrdID(3);
	      product.setCatID(1);
	      product.setProductLabel("Ginger");
	      product.setProductDescription("Description");
	      product.setProductDetails("Dteails");
	      product.setPrice(12.5f);
	      String inputJson = super.mapToJson(product);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(201, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "Product is created successfully");
	   }
	   @Test
	   public void updateProduct() throws Exception {
		  System.out.println("TEST: updateProduct()"); 
	      String uri = "/prd/update/3";
	      Product product = new Product();
	      product.setProductLabel("Lemon");
	      String inputJson = super.mapToJson(product);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "Product is updated successsfully");
	   }
	   @Test
	   public void deleteProduct() throws Exception {
		  System.out.println("TEST: deleteProduct()"); 
	      String uri = "/prd/delete/3";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "Product is deleted successsfully");
	   }
	}