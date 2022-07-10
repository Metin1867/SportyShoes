package tr.com.macik.gui;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil<T> {
	private static final ObjectMapper mapper = new ObjectMapper();
	String json;

	public MapperUtil(String json) {
		if (json == null || "".equals(json))
			this.json = null;
		if (json.startsWith("{"))
			this.json = "["+json+"]";
		else
			this.json = json;
	}

	public List<T> toList() {
		if (json == null)
			return null;
		List<T> list = null;
		try {
			list = mapper.readValue(json, new TypeReference<List<T>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
