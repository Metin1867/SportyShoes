package tr.com.macik.gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.pojo.CommunicationType;

public class CommunicationTypeDto {
	private static CommunicationTypeDto thisSingleInstance;
	private LinkedHashMap<CommunicationType, List<CommunicationType>> cmmLookup;
	
	private CommunicationTypeDto() {
		List<CommunicationType> communicationTypes = CommunicationTypeDto.readAll();

		cmmLookup = new LinkedHashMap<>();
		List<CommunicationType> cmmDetails;
		
		for(CommunicationType cmm : communicationTypes ) {
			if (cmm.getMasterCd() == null) {
				System.out.println("Main CommunicationType Entry " + cmm);
				cmmDetails = null;
				
				for(CommunicationType cmd : communicationTypes ) {
					if (cmd.getMasterCd() != null && cmd.getMasterCd().equals(cmm.getCmmCd())) {
						if (cmmDetails == null)
							cmmDetails = new ArrayList<>();
						cmmDetails.add(cmd);
					}
				}
				cmmLookup.put(cmm, cmmDetails);
			}
		}

	}
	
	public static CommunicationTypeDto getInstance() {
		if (thisSingleInstance == null);
			thisSingleInstance = new CommunicationTypeDto();
			
		return thisSingleInstance;
	}
	
	public static LinkedHashMap<CommunicationType, List<CommunicationType>> getLookup() {
		if (thisSingleInstance == null);
			thisSingleInstance = new CommunicationTypeDto();
			
		return thisSingleInstance.cmmLookup;
	}
	
	@Override
	public String toString() {
		String outp = "";
		Set<CommunicationType> cmmLookupKeys = cmmLookup.keySet();
        // printing the elements of LinkedHashMap
        for (CommunicationType cmm : cmmLookupKeys) {
        	outp += "**** "+cmm.getCmmCd()+" ****************************************\n";
        	outp += cmm+"\n";
        	List<CommunicationType> cmdList = cmmLookup.get(cmm);
        	if (cmdList == null)
        		outp += "   "+"No details available\n";
        	else
	            for (CommunicationType cmd : cmdList) {
	            	outp += "   "+cmd+"\n";
	            }
        }
		return outp;
	}
	
	// CRUD
	// Create
	public static CommunicationType create(CommunicationType pommunicationType) {
		if (pommunicationType==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/cmm/insert", pommunicationType);
		client.read();
		String json = client.toString();
		client.end();
		return firstCommunicationType(json);
	}

	// Read
	public static CommunicationType readByID(CommunicationType pommunicationType) {
		if (pommunicationType==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/cmm/"+pommunicationType.getCmmCd());
		client.read();
		String json = client.toString();
		client.end();
		return firstCommunicationType(json);
	}

	public static List<CommunicationType> readAll() {
		NetClient client = new NetClient(NetClient.GET, "/cmm/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toCommunicationTypeList(json);
	}

	// Update
	public static CommunicationType update(CommunicationType communicationType) {
		if (communicationType==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/cmm/update", communicationType);
		client.read();
		String json = client.toString();
		client.end();
		return firstCommunicationType(json);
	}

	// Delete
	public static CommunicationType delete(CommunicationType communicationType) {
		if (communicationType==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/cmm/delete", communicationType);
		client.read();
		String json = client.toString();
		client.end();
		return firstCommunicationType(json);
	}

	// Helper
	private static CommunicationType firstCommunicationType(String json) {
		if (json == null || "".equals(json))
			return null;
		List<CommunicationType> communicationType = toCommunicationTypeList(json);
		return (communicationType==null || communicationType.size()==1) ? communicationType.get(0) : null;
	}
	
	private static List<CommunicationType> toCommunicationTypeList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<CommunicationType> communicationTypes = null;
		try {
			communicationTypes = mapper.readValue(json, new TypeReference<List<CommunicationType>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return communicationTypes;
	}
}
