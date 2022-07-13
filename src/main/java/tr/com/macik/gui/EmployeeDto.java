package tr.com.macik.gui;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.pojo.Employee;

public class EmployeeDto {
	// CRUD
	// Create
	public static Employee create(Employee employee) {
		if (employee==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/emp/insert", employee);
		client.read();
		String json = client.toString();
		client.end();
		return firstEmployee(json);
	}

	// Read
	public static Employee readByID(Employee employee) {
		if (employee==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/emp/"+employee.getEmpID());
		client.read();
		String json = client.toString();
		client.end();
		return firstEmployee(json);
	}

	public static List<Employee> readAll() {
		NetClient client = new NetClient(NetClient.GET, "/emp/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toEmployeeList(json);
	}

	// Update
	public static Employee update(Employee employee) {
		if (employee==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/emp/update", employee);
		client.read();
		String json = client.toString();
		client.end();
		return firstEmployee(json);
	}

	// Delete
	public static Employee delete(Employee employee) {
		if (employee==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/emp/delete", employee);
		client.read();
		String json = client.toString();
		client.end();
		return firstEmployee(json);
	}

	// Helper
	private static Employee firstEmployee(String json) {
		if (json == null || "".equals(json))
			return null;
		List<Employee> employees = toEmployeeList(json);
		return (employees==null || employees.size()==1) ? employees.get(0) : null;
	}
	
	private static List<Employee> toEmployeeList(String json) {
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<Employee> employees = null;
		try {
			employees = mapper.readValue(json, new TypeReference<List<Employee>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
	}

	public static List<Employee> search(Employee employee) {
		if (employee==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/emp/search", employee);
		client.read();
		String json = client.toString();
		client.end();
		return toEmployeeList(json);
	}

	public static boolean isEmployee(int prsID) {
		Employee employee = new Employee(0, prsID, null, null, -1, null, null, null, null);
		List<Employee> employees = search(employee);
		if (employees.size()>0 && employees.get(0).getPrsID() == prsID)
			return true;
		else
			return false;
	}
}
