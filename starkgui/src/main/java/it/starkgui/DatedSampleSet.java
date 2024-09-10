package it.starkgui;

import java.util.Date;

import it.unicam.quasylab.jspear.SampleSet;
import it.unicam.quasylab.jspear.SystemState;

/**
 * Class that represent a sample set with associated a date object.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0
 */
public class DatedSampleSet {
	
	/**
	 * Create a new {@code DatedSampleSet} object.
	 * 
	 * @param date the sample set associated date
	 */
	public DatedSampleSet(final Date date) {
		this.date = date;
		this.sampleSet = new SampleSet();
	}
	
	/**
	 * Return the sample set associated name.
	 * 
	 * @return the sample set associated name
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Return the sample set.
	 * 
	 * @return the sample set object
	 */
	public SampleSet getSampleSet() {
		return this.sampleSet;
	}
	
	/**
	 * Add a system state into the collector.
	 * 
	 * @param data the system state data
	 */
	public void add(final SystemState data) {
		this.sampleSet.add(data);
	}
	
	/**
	 * Remove all the contained values.
	 */
	public void clear() {
		sampleSet = new SampleSet();
	}
	
	/**
	 * Return the size of the contained values.
	 * 
	 * @return the size of the contained values
	 */
	public int size() {
		return sampleSet.size();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		
		if(o == this)
			return true;
		
		if(o instanceof DatedSampleSet dss)
			return dss.getDate().equals(this.getDate());
		
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("date> " + getDate());
		sb.append("values> \n");
		sb.append(getSampleSet().toString());
		
		return sb.toString();
	}
	
	
	private SampleSet sampleSet;
	private Date date;	
}
