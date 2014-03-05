package uk.ac.leeds.lihs.auecr.cimss.stroke.access

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

class PreAuthenticatedProcessingFilter extends
		AbstractPreAuthenticatedProcessingFilter {
	
	public PreAuthenticatedProcessingFilter(){
		super();
		setCheckForPrincipalChanges(false);
	}
			
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest arg0) {
		return "N/A";
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
		
		String paramUid = httpServletRequest.getParameter('userId');
		String sessionUid = null;
		HttpSession session = httpServletRequest.getSession() 
		if(session){
			sessionUid = session.getAttribute('authId')
		}
		if(session && paramUid){
			session.setAttribute('authId', paramUid)
			sessionUid = session.getAttribute('authId')
		} 
		if ( sessionUid == null ) {
			sessionUid = getCurrentUser()
		}		
//		println "*** getPreAuthenticatedPrincipal *** : UID -> "+sessionUid
		return sessionUid;
	}
	
	private String getCurrentUser() {
		Authentication currentUser = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
		if ( currentUser ) {
			return currentUser.getName();
		} else {
			return null;
		}
	}

}
