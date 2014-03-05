package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.grails.web.json.JSONObject;

class DisplayUtils {
	
	static String displayTime(Integer time){
		if(time != null){
			String format = String.format("%%0%dd", 2);
			return "${String.format(format,(int)time/60)}:${String.format(format,time%60)}"
		}else{
			return ""
		}
	}
	
	static Date getDate(Object value){
		if(value && value!=JSONObject.NULL){
			def pattern = ~/^[0-9][[0-9]][\/][0-9][[0-9]][\/][0-9][0-9][0-9][0-9]$/
			if(!pattern.matcher("${value}").matches()){
				throw new IllegalArgumentException("The format must be dd\\MM\\yyyy")
			}
			def parts = value.split("/");
			def c= new GregorianCalendar()
			c.lenient = false
			c.set(Calendar.YEAR,  new Integer(parts[2]))
			c.set(Calendar.MONTH,  new Integer(parts[1])-1)
			c.set(Calendar.DAY_OF_MONTH,  new Integer(parts[0]))
			c.set(Calendar.HOUR_OF_DAY, 0)
			c.set(Calendar.MINUTE,0)
			c.set(Calendar.SECOND,0)
			c.set(Calendar.MILLISECOND,0)
			return c.time
		}
		return null;
	}
	
	
	
	static Date getDate(Object value, Map errors, String field, String errorMessage){
		try{
			return getDate(value)
		}catch(IllegalArgumentException iae){
			errors.put(field, errorMessage)
		}
		return null;
	}
	
	
	static String displayDate(Date date) {
		if(date){
			return date.format("dd/MM/yyyy")
		}
		return ""
	}

	static String displayDateTime(Date date) {
		if(date){
			return date.format("dd/MM/yy HH:mm")
		}
		return ""
	}
	
	static Integer getTime(Object value, Map errors, String field, String errorMessage){
		try{
			return getTime(value)
		}catch(IllegalArgumentException iae){
			errors.put(field, errorMessage)
		}
		return null;
	}
	
	
	static Integer getTime (Object value){
		
		if(value && value!=JSONObject.NULL){
			def pattern = ~/^([01]\d|2[0-3])(:[0-5]\d){0,2}$/
			if(!pattern.matcher("${value}").matches()){
				throw new IllegalArgumentException("The format must be 24 hour clock")
			}
			def hours = value.tokenize(':')[0]
			def minutes = value.tokenize(':')[1]
			return (hours.toInteger() * 60) + minutes.toInteger()
		}
		return null
	}

}
