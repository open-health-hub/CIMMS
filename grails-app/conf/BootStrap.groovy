import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import grails.converters.JSON
import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.SsnapExtract
import org.codehaus.groovy.grails.commons.ApplicationAttributes

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class BootStrap {

    def init = { servletContext ->
		SpringSecurityUtils.clientRegisterFilter('iframeFilter', SecurityFilterPosition.PRE_AUTH_FILTER)
		JSON.registerObjectMarshaller(SsnapExtract) {
			def returnArray = [:]
			returnArray['errorList'] = it.errorList
			returnArray['inError'] = it.inError
			return returnArray
		}
		
		// Get spring
		//def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
		def springContext =
			servletContext.getAttribute(ApplicationAttributes.APPLICATION_CONTEXT)
		// Custom marshalling
		springContext.getBean( "customObjectMarshallers" ).register()
		
    }
    def destroy = {
    }
}
