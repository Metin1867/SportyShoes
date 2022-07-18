package tr.com.macik.gui;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.report.PurchaseByCategoryReport;
import tr.com.macik.myapp.report.PurchaseByDateReport;
import tr.com.macik.myapp.report.PurchaseByDateReportDAO;

public class ReportDto {
	private static PurchaseByDateReportDAO purchaseByDateDAO;
		
	public ReportDto() {
		PurchaseByDateReportDAO purchaseByDateDAO = new PurchaseByDateReportDAO();
	}

	public static List<PurchaseByDateReport> getPurchaseByDate() {
		NetClient client = new NetClient(NetClient.GET, "/PurchaseByDateReport/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toPurchaseByDateList(json);
	}

	private static PurchaseByDateReport firstPurchaseByDate(String json) {
		if (json == null || "".equals(json))
			return null;
		List<PurchaseByDateReport> purchaseReport = toPurchaseByDateList(json);
		return (purchaseReport==null || purchaseReport.size()==1) ? purchaseReport.get(0) : null;
	}
	
	private static List<PurchaseByDateReport> toPurchaseByDateList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<PurchaseByDateReport> purchaseReport = null;
		try {
			purchaseReport = mapper.readValue(json, new TypeReference<List<PurchaseByDateReport>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purchaseReport;
	}

	public static List<PurchaseByCategoryReport> getPurchaseByCategory() {
		NetClient client = new NetClient(NetClient.GET, "/PurchaseByCategoryReport/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toPurchaseByCategoryList(json);
	}
	
	private static PurchaseByCategoryReport firstPurchaseByCategory(String json) {
		if (json == null || "".equals(json))
			return null;
		List<PurchaseByCategoryReport> purchaseReport = toPurchaseByCategoryList(json);
		return (purchaseReport==null || purchaseReport.size()==1) ? purchaseReport.get(0) : null;
	}
	
	private static List<PurchaseByCategoryReport> toPurchaseByCategoryList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<PurchaseByCategoryReport> purchaseReport = null;
		try {
			purchaseReport = mapper.readValue(json, new TypeReference<List<PurchaseByCategoryReport>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purchaseReport;
	}
}
