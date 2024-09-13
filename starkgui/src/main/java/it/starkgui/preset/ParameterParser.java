package it.starkgui.preset;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Parser class for {@code Parameter} objects.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
final class ParameterParser {

	/**
	 * Don't let anyone instantiate this class. 
	 */
	private ParameterParser() { }
	
	/**
	 * Parse the {@code JSON} serialization to
	 * a {@code Parameter} object.
	 * 
	 * @param obj the {@code JSON} object
	 * @return the parsed {@code Parameter} object
	 * @throws JSONException if an error occur during the parsing process
	 */
	public static Parameter parse(JSONObject obj) throws JSONException {
		return new Parameter(
			obj.getString("name"),
			obj.getDouble("min_value"),
			obj.getDouble("max_value")
		);
	}
}
