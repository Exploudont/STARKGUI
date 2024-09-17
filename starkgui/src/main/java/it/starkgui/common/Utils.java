package it.starkgui.common;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.io.BufferedReader;
import java.io.FileNotFoundException;


/**
 * Provide to some utilities methods.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public class Utils {

	/**
	 * Don't let anyone instantiate this class.
	 */
	private Utils() {}
	
	
	/**
	 * Load a resource file.
	 *
	 * @param file_name the file name
	 * @return return the file stream
	 * @throws FileNotFoundException if the file can't be found
	 */
	public static InputStream loadFile(final String file_name) throws FileNotFoundException {
		InputStream in = ClassLoader
					.getSystemClassLoader()
					.getResourceAsStream(file_name);
		
		if(in == null)
			throw new FileNotFoundException("Can not find the file.");
		
		return in;
	}
	
	/**
	 * Load a resource file.
	 * Actually doesn't support loading resources from nested directory
	 * from a JAR file.
	 *
	 * @param file_path the file path
	 * @return return the file {@code URL}
	 * @throws FileNotFoundException if the file can't be found
	 */
	public static URL loadResource(final String file_path) throws FileNotFoundException {
		return ClassLoader
				.getSystemClassLoader()
				.getResource(file_path);
	}
	
	/**
	 * Load and return the reader for a specific file.
	 * 
	 * @param file_name the file name
	 * @return the {@code Reader} object
	 * @throws IOException
	 */
	public static Reader getFileReader(final String file_name) throws IOException {
		return new InputStreamReader(Utils.loadFile(file_name));
	}
	
	/**
	 * Read all the file content.
	 * 
	 * @param in_stream the file input stream
	 * @return the content of the file
	 * @throws IOException
	 */
	public static String readAllFile(InputStream in_stream) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in_stream));
		String line = null;
		while((line = br.readLine()) != null)
			sb.append(line);
		
		return sb.toString();
	}
}