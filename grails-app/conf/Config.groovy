
//grails.project.groupId = cimss // change this to alter the default package name and Maven publishing destination

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts


if(System.properties["${appName}.config.location"]) {
    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
} else {
	grails.config.locations = [ 
		"classpath:${appName}-config.properties",
	    "file:${userHome}/.grails/${appName}-config.properties"]

}


grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
					  pdf: 'application/pdf',
					  rtf: 'application/rtf',
					  excel: 'application/vnd.ms-excel',
					  ods: 'application/vnd.oasis.opendocument.spreadsheet',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://localhost:8080/${appName}"					
	}
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

trust.name = "anhst"

// added  re jquery plugin instructions
grails.views.javascript.library="jquery"


jqueryUi.minified = false



// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
	
	info   'grails.app.controller.uk.ac.leeds.lihs.auecr.cimss.stroke'

	debug  'uk.ac.leeds.lihs.auecr.cimss.stroke.therapy'
	
//	debug  'grails.app.controller.uk.ac.leeds.lihs.auecr.cimss.stroke',
//           'grails.app.controller.uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap',
//		   'org.springframework.security',
//		   'uk.ac.leeds.lihs.auecr.cimss.stroke.therapy'
	
//    trace  'org.hibernate'
	
//	debug  'org.springframework.security.authentication',
//		   'org.springframework.security.web.authentication.preauth',
//		   'org.springframework.security.web',
//		   'org.springframework.security'	
	
	//trace 'uk.nhs.bradfordresearch.bib.warehouse.audit',
		   //'org.springframework.security.authentication',
		   //'org.springframework.security.web.authentication.preauth',
		   
		  //'org.springframework.security.web',
		   //'org.springframework.security'	
}




auditLog {
	actorClosure = { request, session ->
		if (request.applicationContext.springSecurityService.principal instanceof java.lang.String){
			return request.applicationContext.springSecurityService.principal
		}
		return request.applicationContext.springSecurityService.principal?.username
	}
}



// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'uk.ac.leeds.lihs.auecr.cimss.access.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'uk.ac.leeds.lihs.auecr.cimss.access.UserRole'
grails.plugins.springsecurity.authority.className = 'uk.ac.leeds.lihs.auecr.cimss.access.Role'
grails.plugins.springsecurity.requestMap.className = 'uk.ac.leeds.lihs.auecr.cimss.access.RequestMap'
grails.plugins.springsecurity.securityConfigType = 'InterceptUrlMap'
//grails.plugins.springsecurity.password.algorithm = 'bcrypt'
grails.plugins.springsecurity.password.algorithm = 'SHA-256'
grails.plugins.springsecurity.interceptUrlMap = [
	'/therapistAdmin/**':    	['ROLE_ADMIN'],
	'/therapy/**':   			['ROLE_THERAPIST'],
	'/case/**':   				['ROLE_THERAPIST','ROLE_ADMIN'],
	'/patient/**':   			['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN' ],
	'/dataStatus/**':   		['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/patientHistory/**':   	['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/strokeOnset/**':   		['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/preMorbidAssessment/**':  ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/js/**':        ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/css/**':       ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/images/**':    ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/static/**':    ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/login/**':     ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/logout/**':    ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN'],
	'/**':           ['ROLE_ANONYMOUS','ROLE_THERAPIST','ROLE_ADMIN']
 ]

// to use home page as a login form
grails.plugins.springsecurity.auth.loginFormUrl = '/login/login.gsp'
grails.plugins.springsecurity.failureHandler.defaultFailureUrl = '/login/login.gsp?error=true'

// grails.plugins.springsecurity.providerNames=[ 'preAuthenticatedAuthenticationProvider' ]
grails.plugins.springsecurity.providerNames=[ 'preAuthenticatedAuthenticationProvider', 'daoAuthenticationProvider' ]

//grails.plugins.springsecurity.filterChain.chainMap = [
//	'/**': 'securityContextPersistenceFilter,logoutFilter,iframeFilter,authenticationProcessingFilter,securityContextHolderAwareRequestFilter,anonymousAuthenticationFilter,exceptionTranslationFilter,filterInvocationInterceptor',
//]

//grails.plugins.springsecurity.providerNames=[ 'preAuthenticatedAuthenticationProvider', 'daoAuthenticationProvider', 'anonymousAuthenticationProvider', 'rememberMeAuthenticationProvider']
grails.resources.debug = false
