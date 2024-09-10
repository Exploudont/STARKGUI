package it.starkgui.preset;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Describe a parameter of a preset.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0
 */
public record Parameter (
	String name,
	Double min_value,
	Double max_value
)
{
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		
		if(o == this)
			return true;
		
		if(o instanceof Parameter tmp) {
			return this.name.equals(tmp.name);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("name> " + name)
			.append("\nmin value> " + min_value)
			.append("\nmax value> " + max_value)
			.toString();
	}
}