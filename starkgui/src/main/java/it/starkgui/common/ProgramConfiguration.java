package it.starkgui.common;


import java.util.Properties;
import java.util.Set;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class that descrive a project configuration.
 *
 * Can be usefull for setup procedures and projects.
 * The project configuration is unique into a project, then the class use the singleton design pattern.
 *
 * The file reading is case-sensitive. If some parameters override the value into the configuration file the value will result in the last override value.
 *
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0
 * @since JDK 17
 */
public class ProgramConfiguration {
	
	
	/**
	 * Define the configuration file name.
	 */
	public static final String CONFIGURATION_FILE_NAME;
	
	
	/**
	 * The singleton object for the {@code ProgramConfiguration}.
	 */
	private static final ProgramConfiguration singleton;
	
	
	/**
	 * Return the instance of the program configiration.
	 *
	 * @return return the instance of the program configiration
	 */
	public static ProgramConfiguration getInstance() {
		return singleton;
	}
	
	
	/**
	 * Create a new empty {@code ProgramConfiguration} object.
	 */
	private ProgramConfiguration() {
		properties = new Properties();
	}
	
	
	/**
	 * Create a new {@code ProgramConfiguration} object.
	 *
	 * @param in_stream the configuration input stream
	 * @exception IllegalArgumentException
	 * @exception IOException
	 */
	private ProgramConfiguration(final InputStream in_stream) throws IOException {
		if(in_stream == null)
			throw new IllegalArgumentException("The configuration file can not be null. Parameter name: config.");
		
		properties = loadProperties(in_stream);
	}
	
	
	/**
	 * Load the configuration file.
	 *
	 * @param in_stream the configuration input stream
	 * @return return loaded configuration
	 * @exception IOException
	 */
	private static Properties loadProperties(final InputStream in_stream) throws IOException {
		Properties prop = new Properties();
		prop.load(in_stream);
		return prop;
	}
	
	
	/**
	 * Return the value of a specific parameter.
	 *
	 * @param parameter the parameter name
	 * @return return the parameter value
	 */
	public String getValue(final String parameter) {
		return properties.getProperty(parameter); 
	}
	
	
	/**
	 * Return an unmodifiable set of parameter from this configuration.
	 *
	 * @return return an unmodifiable set of parameter from this configuration.
	 */
	public Set<String> getAllParameters() {
		return properties.stringPropertyNames();
	}
	
	
	private Properties properties;
	
	
	
	
	static {
		CONFIGURATION_FILE_NAME = "conf.ini";
		
		ProgramConfiguration conf_tmp = null;
		
		try {
			InputStream in_stream = Utils.loadFile(CONFIGURATION_FILE_NAME);
			conf_tmp = new ProgramConfiguration(in_stream);
		} catch(Exception e){
			throw new RuntimeException("Can not initialize the project configuration.");
		}
		
		singleton = conf_tmp;
	}

}