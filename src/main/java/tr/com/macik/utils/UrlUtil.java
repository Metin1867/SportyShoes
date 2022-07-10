package tr.com.macik.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlUtil {
	public static String toQueryParameter(String[][] queryParameters) {
		if (queryParameters == null || queryParameters.length == 0)
			return null;
		String queryParameterString = "";
		boolean isFirst = true;
		
		for (int i=0; i<queryParameters.length; i++) {
			if (!isFirst) {
				queryParameterString += "&";
				isFirst = false;
			}
			queryParameterString += queryParameters[i][0];
			if (queryParameters[i][1] != null) {
				queryParameterString += "=" + queryParameters[i][1];
			}
		}
		return queryParameterString;
	}

	public static byte[] toQueryParameter(Map<String, Object> params) {
	    StringBuilder postData = new StringBuilder();
        try {
		    for (Map.Entry<String,Object> param : params.entrySet()) {
		        if (postData.length() != 0) postData.append('&');
					postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			        postData.append('=');
			        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		    }
			return postData.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
