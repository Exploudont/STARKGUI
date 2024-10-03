package it.starkgui;

import java.util.Date;
import java.util.Calendar;

import java.util.List;
import java.util.LinkedList;


/**
 * Utility class that fill the empty period dates.
 *
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class DateFiller {
	
	/**
	 * Don't let anyone instance this class.
	 */
	private DateFiller() { }
	
	/**
	 * Create a new {@code Date} object.
	 *
	 * @param day the day of month
	 * @param month the month
	 * @param year the year
	 * @return the builded date
	 */
    private static Date createDate(final int day, final int month, final int year) {
        return new Calendar.Builder()
            .setDate(year, month, day)
            .build()
            .getTime();
    }
    
	/**
	 * Compare the year and the month of two dates.
	 *
	 * @param d1 the first date
	 * @param d2 the second date
	 * @return {@code true} only if the dates are partially equals, otherwise {@code false}
	 */
    private static boolean partialEquals(final Date d1, final Date d2) {
        if(d1.getYear() > d2.getYear())
            return false;
        
        if(d1.getYear() < d2.getYear())
            return false;
        
        if(d1.getMonth() > d2.getMonth())
            return false;
            
        if(d1.getMonth() > d2.getMonth())
            return false;
        
        return true;
    }
    
	/**
	 * Fill the dates from a starting date to an ending date.
	 *
	 * @param dates the current dates
	 * @param min the starting date
	 * @param max the ending date
	 * @return the array of filled dates
	 */
    public static Date[] fillByMonths(final Date[] dates, final Date min, final Date max) {
		List<Date> filled = new LinkedList<Date>();
		
		int pos = 0;
		boolean isFirst = true;
		
		for(int year = min.getYear() ; year <= max.getYear() ; year++) {
		    
			int max_month = (year == max.getYear()) ? 12 : max.getMonth();
			
			int month = 0;
			if(isFirst) {
			    month = min.getMonth();
			    isFirst = false;
			}
			
			
			for(/*int month = isFirst ? min.getMonth() : 0*/; month<max_month ; month++) {
			    isFirst = false;
				
			    Date tmp = createDate(1, month, year);
				
				if(pos != dates.length && partialEquals(dates[pos], tmp))
					filled.add(dates[pos++]);
				else
					filled.add(tmp);
			}
		}
		
		
		return filled
				.stream()
				.toArray(size -> new Date[size]);
	}
    
    /**
     * Return the maximum date of the array.
     * 
     * @param dates the array of dates
     * @return the maximum date of the array
     */
    private static Date getMax(final Date[] dates) {
    	int max = 0;
    	
    	for(int i=1 ; i<dates.length ; i++)
    		if(dates[max].compareTo(dates[i]) > 0)
    			max = i;
    	
    	return dates[max];
    }

    /**
     * Return the minimum date of the array.
     * 
     * @param dates the array of dates
     * @return the minimum date of the array
     */
    private static Date getMin(final Date[] dates) {
    	int min = 0;
    	
    	for(int i=1 ; i<dates.length ; i++)
    		if(dates[min].compareTo(dates[i]) < 0)
    			min = i;
    	
    	return dates[min];
    }

    /**
     * Return the maximum date of two periods.
     * 
     * @param period_1 the first array of dates
     * @param period_2 the second array of dates
     * @return the maximum date of the two periods
     */
    public static Date getMax(final Date[] period_1, final Date[] period_2) {
    	Date max_p1 = getMax(period_1);
    	Date max_p2 = getMax(period_2);
    	
    	return max_p1.compareTo(max_p2) > 0 ? max_p1 : max_p2;
    }

    /**
     * Return the minimum date of two periods.
     * 
     * @param period_1 the first array of dates
     * @param period_2 the second array of dates
     * @return the minimum date of the two periods
     */
    public static Date getMin(final Date[] period_1, final Date[] period_2) {
    	Date min_p1 = getMin(period_1);
    	Date min_p2 = getMin(period_2);
    	
    	return min_p1.compareTo(min_p2) < 0 ? min_p1 : min_p2;
    }
}