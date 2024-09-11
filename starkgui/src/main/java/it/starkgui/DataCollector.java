package it.starkgui;

import java.util.Collection;
import java.util.Date;
import java.util.TreeMap;
import java.util.List;
import java.util.SortedMap;

import it.unicam.quasylab.jspear.SampleSet;
import it.unicam.quasylab.jspear.SystemState;


/**
 * Class that allow to collect data.
 *
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0
 */
public final class DataCollector {
	
	private static DataCollector singleton;
	
	/**
	 * Returns the {@code DataCollector} object.
	 *
	 * @return the singleton instance
	 */
	public static DataCollector getInstance() {
		if(singleton == null)
			singleton = new DataCollector();
		
		return singleton;
	}
	
	/**
	 * Create a new empty {@code DataCollector} object.
	 */
	private DataCollector() {
		this.collector= new TreeMap<>(); 
	}
	
	/**
	 * Add a new dated sample set into the collector.
	 * 
	 * @param datedSampleSet the dated sample set to add
	 */
	public void add(DatedSampleSet datedSampleSet) {
		this.collector.put(datedSampleSet.getDate(), datedSampleSet);
	}
	
	/**
	 * Return the sample set ordered by date.
	 * 
	 * @return the sample set ordered by date
	 */
	public List<SampleSet> getSampleSets() {
		return collector
				.keySet()
				.stream()
				.map(dss -> collector.get(dss).getSampleSet())
				.toList();
	}
	
	/**
	 * Return the ordered sample set's dates.
	 * 
	 * @return the ordered sample set's dates
	 */
	public Date[] getDates() {
		return collector
				.keySet()
				.toArray(size -> new Date[size]);
	}
	
	/**
	 * Returns {@code true} if contains the specified date.
	 *
	 * @param date the date to search
	 * @return {@code true} if contains the specified date, otherwise {@code false}
	 */
	public boolean contains(final Date date) {
		return collector.containsKey(date);
	}
	
	/**
	 * Return the date's associated dated sample set.
	 * 
	 * @return the date's associated dated sample set
	 */
	public DatedSampleSet get(final Date date) {
		return collector.get(date);
	}
	
	
	private SortedMap<Date, DatedSampleSet> collector;
}
