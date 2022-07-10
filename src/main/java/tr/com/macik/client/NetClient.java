package tr.com.macik.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.utils.UrlUtil;

/*
 * HTTP response: String header = con.getRequestProperty("Content-Type");
 */
public class NetClient {
	public static final String GET = "GET";
	public static final String POST = "GET";
	public static final String DELETE = "DELETE";
	public static final String PUT = "PUT";
	public static final String HEAD = "HEAD";
	// ...
	public static final String CONTENT_JSON = "application/json";
	
	
	private String baseUrl;
	private HttpURLConnection con;
	private BufferedReader reader;
	private String contentType = CONTENT_JSON;
	
	public NetClient() {
		this((String) null);
	}

	public NetClient(String baseUrl) {
		if (baseUrl == null)
			this.baseUrl = "http://localhost:8080";
		else
			this.baseUrl = baseUrl;
	}

	public NetClient(String baseUrl, String methode, String call, Object object) {
		this(baseUrl);
		start(methode, call);
		setJasonContent(object);
		
	}

	public NetClient(String methode, String call, Object object) {
		this(null, methode, call, object);
	}

	public NetClient(Object object) {
		this(null, null, object);
	}

	public NetClient(String methode, String call) {
		this();
		start(methode, call, null);
		
	}

	public void end() {
		try {
			reader.close();
			con.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String nextLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int read() {
		int code = -1;
		try {
			code = con.getResponseCode();
			/*
			 * code in the 200 range is for success,
			 * 400s are for client-side error and
			 * 500s are for server-side error
			 */
			if (code >= 200 && code <= 299)
				reader = new BufferedReader(new InputStreamReader( con.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}

	public void start(String methode, String call) {
		start(methode, call, null);
	}

	public void start(String methode, String call, Map<String, Object> params) {
		URL url;
		try {
			url = new URL(baseUrl + call);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(methode);
			// con.setRequestProperty("Content-Type", "application/json");
			
			if (params != null)
				setQueryParameters(params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}

	private void setQueryParameters(Map<String, Object> params) {
		con.setDoOutput(true);
    	
		try {
			byte[] postDataBytes = UrlUtil.toQueryParameter(params);
	        con.getOutputStream().write(postDataBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public void setJasonContent(Object obj) {
		// con.setRequestProperty("Content-Type", "application/json");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(obj);
			System.out.println(jsonString);

			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os;
			os = con.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();
			
			if (con.getResponseCode() != HttpURLConnection.HTTP_CREATED
					&& con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ con.getResponseCode());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String toString() {
		String text = "";
		String inputLine;
		while ((inputLine = nextLine()) != null) {
			text += inputLine + " ";
		}
		return text.trim();
	}
}
