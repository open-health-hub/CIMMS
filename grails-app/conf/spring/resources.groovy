import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider

import uk.ac.leeds.lihs.auecr.cimss.stroke.access.PreAuthenticatedProcessingFilter
import uk.ac.leeds.lihs.auecr.ssnapexporter.controller.ColumnChecker
import uk.ac.leeds.lihs.auecr.ssnapexporter.controller.RowChecker
import uk.ac.leeds.lihs.auecr.ssnapexporter.dao.DaoImpl
import uk.ac.leeds.lihs.auecr.cimss.stroke.PatientProxyMarshaller
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapistMarshaller
import uk.ac.leeds.lihs.auecr.cimss.stroke.util.CommonObjectMarshallers

// Place your Spring DSL code here


beans = {
		
	iframeFilter(PreAuthenticatedProcessingFilter) {
		authenticationManager = ref('authenticationManager')
	}
	
	
	authenticationUserDetailService(UserDetailsByNameServiceWrapper){
		userDetailsService = ref('userDetailsService')
	}
	
	preAuthenticatedAuthenticationProvider(PreAuthenticatedAuthenticationProvider){
		preAuthenticatedUserDetailsService = ref('authenticationUserDetailService')
	}
	
	dao(DaoImpl) {
	
	}
	
	columnChecker(ColumnChecker) {
		dao = ref('dao')
	}
		
	rowChecker(RowChecker) {
		dao = ref('dao')
	}

	
	customObjectMarshallers( CommonObjectMarshallers ) {
		marshallers = [
			new PatientProxyMarshaller(),
			new TherapistMarshaller()
		]
	}
}
