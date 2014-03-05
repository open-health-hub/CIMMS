package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap



class SsnapReportRecord {

	String hospitalStayId
	Integer hsiVersion
	Map fieldMap =  [:]
	
	static belongsTo = [report:SsnapReport]
	
	static hasMany = [fields:SsnapReportField]
	
    static constraints = {
		hospitalStayId(nullable:false)
		hsiVersion(nullable:false)
    }
	static mapping =  {
		version false
	}
	static transients = ['fieldMap']
	
	def SsnapReportRecord() {
		SsnapReportRecord.metaClass.getProperty = { name ->
			def mp = SsnapReportRecord.metaClass.getMetaProperty(name)
			if ( mp ) {
				return mp.getProperty(delegate)
			} else {
				delegate.property(name)
			}
		}
	}
	
	def property(String property) {

		if ( fieldMap.isEmpty() ){
			fields.each {
				fieldMap[it.fieldName] = it.fieldValue
			}	
		}
//		System.out.println("getProperty(${property}) == "+ fieldMap[property])
		
		return fieldMap[property]; 			
	}
}
