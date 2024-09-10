package it.starkgui.common;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;


/**
 * Provide to some utilities methods.
 * 
 * @author  Daniele Longobardi
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
	 * @exception FileNotFoundException
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
	 * Load and return the reader for a specific file.
	 * 
	 * @param file_name the file name
	 * @return the {@code Reader} object
	 * @exception IOException
	 */
	public static Reader getFileReader(final String file_name) throws IOException {
		return new InputStreamReader(Utils.loadFile(file_name));
	}
	
	/**
	 * Read all the file content.
	 * 
	 * @param in_stream the file input stream
	 * @return the content of the file
	 * @exception IOException
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