package uk.ac.leeds.lihs.auecr.cimss.stroke

class DisplayUtilsTagLib {
	
	
	/**
	* Renders the body with a formatted time (24 hour clock)
	*
	* @attr time as an int
	*/
   def displayTime = { attrs, body ->
	   out << body() << DisplayUtils.displayTime(attrs.time?.toInteger())
   }
   
   /**
   * Renders the body with the string errors
   *
   * @attr errorList as a Map of fields in error
   * @attr field as a String representing the field in error
   */
   def inError = {attrs, body ->
	    def errorList = attrs.remove('errorList')
		def field = attrs.remove('field')
		if(errorList && field){
			if(errorList.get(field)){
				out << body() << 'errors'
			}
		}   
   }

	
}
