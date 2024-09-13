package it.starkgui.preset;

import org.json.JSONObject;

import it.starkgui.common.Utils;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Describe a generic STARK preset.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class Preset {
	
	/**
	 * Create a new {@code Preset} object.
	 * 
	 * @param parameters the mapped parameters
	 * @throws IllegalArgumentException if the parameters has not corrects values
	 */
	protected Preset(final Map<String, Parameter> parameters) {
		if(parameters == null)
			throw new IllegalArgumentException();
		
		if(parameters.isEmpty())
			throw new IllegalArgumentException();
		
		this.parameters = parameters;
	}
	
	/**
	 * Return the requested parameter object.
	 * 
	 * @param name the parameter name
	 * @return the requested parameter object
	 */
	public Parameter getParameter(final String name) {
		return parameters.get(name);
	}
	
	/**
	 * Return the parameter's variable names.
	 * 
	 * @return the parameter's variable names
	 */
	public String[] getNames() {
		return parameters
				.keySet()
				.stream()
				.toArray(size -> new String[size]);
	}
	
	@Override
	public String toString() {
		return parameters
				.entrySet()
				.stream()
				.map(param -> param.toString())
				.collect(Collectors.reducing("", (str1,str2) -> str1 + "\n" + str2))
				.toString();
	}
	
	
	private final Map<String, Parameter> parameters;
}
