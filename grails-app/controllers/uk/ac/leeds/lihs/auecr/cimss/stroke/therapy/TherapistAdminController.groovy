package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy

import uk.ac.leeds.lihs.auecr.cimss.access.Role
import uk.ac.leeds.lihs.auecr.cimss.access.UserRole
import uk.ac.leeds.lihs.auecr.cimss.access.User

class TherapistAdminController {

	def springSecurityService
	final def PASSWORD_PROXY = "0#0#" 
	
    def index = { 
		List l = UserRole.findAll()
		[therapistList: l]
	}
	
	def showTherapist = {
		User therapist = User.get(params.therapistId)
		if(!therapist) {
			flash.message = "Therapist not found for id ${params.therapistId}"
			redirect(action:index)
		}
		[therapist: therapist ] 
	}

	def newTherapistBegin = {
		render (view: "newTherapist", model:[therapist: new User() ])
	}	
	
	def newTherapist = {
		
		Role role = Role.findByAuthority("ROLE_THERAPIST")
		if ( ! role ) {
			log.error ("Technical error: Therapists group [ERR-01]")
			flash.error_message = "Technical error: Therapists group [ERR-01]"
			render(view:"newTherapist", model:[therapist: new User() ])
			return
		} 
		if ( ! validatePassword(params.password) ) {
			flash.error_message = "Password must be between 3 and 8 characters"
			render(view:"newTherapist", model:[therapist: new User() ])
			return
		}		
		
		User therapist = new User()
		
		therapist.surname = params.surname
		therapist.firstName = params.firstName
		therapist.username  = params.username
		therapist.password  = encryptPassword(params.password)
		
		therapist.accountLocked = false
		therapist.enabled = true
		therapist.accountExpired = false		
		therapist.passwordExpired = false

		if( therapist.save() ) {
			UserRole userRole = UserRole.create(therapist, role, true)
			flash.message = "Therapist ${therapist.username} enrolled"
			redirect(action:index)			
		} else {
			flash.error_message = "Therapist enrolment invalid "
			userRole.discard();
			render(view:"newTherapist", model:[therapist: therapist ])
		}	
	}
	
	def updateAccount = {
		User therapist = User.get(params.therapistId)
		if(!therapist) {
			flash.message = "Therapist not found for id ${params.therapistId}"
			redirect(action:index)
		}
		if ( ! validatePassword(params.password) ) {
			flash.error_message = "Password must be between 3 and 8 characters"
			render(view:"showTherapist", model:[therapist: new User(params) ])
			return
		}
		
		therapist.surname = params.surname
		therapist.firstName = params.firstName
		therapist.username  = params.username
		if ( params.password != PASSWORD_PROXY ) {
			therapist.password  = encryptPassword(params.password)
		}
		if ( params.accountLocked ) {
			therapist.accountLocked = params.accountLocked
		} else {
			therapist.accountLocked = false
		}
		
		if( therapist.save() ) {			
			flash.message = "Therapist updated"
		} else {
			flash.error_message = "Therapist update invalid "
			therapist.discard();
		}
		
		render(view:"showTherapist", model:[therapist: therapist ])
	}
	
	private def encryptPassword = { password ->
		def encryptedPassword = springSecurityService.encodePassword(password)
		log.debug("***** Encrypted: "+password+" -> "+encryptedPassword) 
		return encryptedPassword
	}
	
	private def validatePassword = { password -> 
		return (			
			password != null 
			&& ( (password.size() > 3 
			      && password.size() < 9)
			      || password == PASSWORD_PROXY )) 
	}
}
