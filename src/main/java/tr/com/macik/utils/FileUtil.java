package tr.com.macik.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class FileUtil {
	public static String toFilename(String path) {
		String filename = null;
		try {
			filename = new File(new URI(path).getPath()).getName();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
}
