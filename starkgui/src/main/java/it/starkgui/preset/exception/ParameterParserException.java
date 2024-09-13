package it.starkgui.preset.exception;

import java.text.ParseException;


/**
 * Signals that an error has been reached unexpectedly while parsing a preset parameter.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public class ParameterParserException
	extends ParseException
{
	
	/**
	 * Constructs a {@code ParameterParserException} with the specified detail message and offset.
	 * 
	 * @param msg the message
	 * @param errorOffset the offset
	 */
	public ParameterParserException(final String msg, final int errorOffset) {
		super(msg, errorOffset);
	}
}
