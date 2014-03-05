package uk.ac.leeds.lihs.auecr.cimss.stroke

import java.util.Date;

import uk.ac.leeds.lihs.auecr.cimss.stroke.post.discharge.PostDischargeSupport;
import uk.ac.leeds.lihs.auecr.cimss.stroke.post.discharge.PostDischargeTherapy;

class PostDischargeCare {
	
	String documentedEvidence
	Boolean documentationPostDischarge
	Date esdReferralDate
	Boolean esdReferralDateUnknown
	Integer numberOfSocialServiceVisits
	Boolean numberOfSocialServiceVisitsUnknown
	String dischargedTo
	Boolean strokeSpecialist
	Boolean alonePostDischarge
	Boolean alonePostDischargeUnknown
	Boolean shelteredAccommodation
	Boolean patientPreviouslyResident
	String temporaryOrPermanent
	
	String supportOnDischargeNeeded;
	String ssnapParticipationConsent;
	String newCareTeam;

	String palliativeCare;
	Date palliativeCareDate;
	String endOfLifePathway;
	
	static hasMany = [postDischargeTherapy:PostDischargeTherapy,postDischargeSupport:PostDischargeSupport ]
	
	static belongsTo = [careActivity:CareActivity]
	
	static auditable = [ignore:[]]


    static constraints = {		
	
		esdReferralDate(nullable:true,validator: { esdReferralDate, postDischargeCare ->
			validateEsdReferralDate(esdReferralDate, postDischargeCare )
		})
		esdReferralDateUnknown(nullable:true,validator: { esdReferralDateUnknown, postDischargeCare ->
			validateEsdReferralDateUnknown(esdReferralDateUnknown, postDischargeCare)
		})
		documentedEvidence(nullable:true)
		documentationPostDischarge(nullable:true)
		numberOfSocialServiceVisitsUnknown(nullable:true)
		numberOfSocialServiceVisits(nullable:true)
		dischargedTo(nullable:true)
		patientPreviouslyResident(nullable:true)
		temporaryOrPermanent(nullable:true)
		supportOnDischargeNeeded(nullable:true)
		alonePostDischarge(nullable:true)
		alonePostDischargeUnknown(nullable:true)
		shelteredAccommodation(nullable:true)
		
		strokeSpecialist(nullable:true)
		ssnapParticipationConsent(nullable:true)
		newCareTeam(nullable:true,validator: { newCareTeam, postDischargeCare ->
			validateNewCareTeam(newCareTeam, postDischargeCare )
		})

		palliativeCare(nullable:true)
		palliativeCareDate(nullable:true,
			validator:{palliativeCareDate, postDischargeCare ->
				if(palliativeCareDate){
					if(palliativeCareDate.after(new Date())){
						return "palliative.care.date.not.in.future"
					}
					if(postDischargeCare.careActivity.beforeAdmission(palliativeCareDate, null)){
						return "palliative.care.date.not.before.admission"
					}
					if(postDischargeCare.careActivity.afterDischarge(palliativeCareDate, null)){
						return "palliative.care.date.not.after.discharge"
					}
					if(postDischargeCare.careActivity.hoursSinceAdmission(palliativeCareDate, null) < 72){
						return "palliative.care.date.72hrs.after.admission"
					}

				}
				
			})
		
		endOfLifePathway(nullable:true)
    }
	
	private static validateNewCareTeam(newCareTeam, postDischargeCare) {
		if ( newCareTeam ) {
			if (dischargedToIntermediateCare(postDischargeCare) && !validInpatientTeamCode(newCareTeam)) {
				return "postDischargeCare.invalid.dischargedTo.esd_commmunity_care_team_code"
			} else if (dischargedToEsdOrCommunityCare(postDischargeCare) && !validEsdOrCommunityCareTeamCode(newCareTeam)) {
				return "postDischargeCare.invalid.dischargedTo.intermediate_care_team_code"
			} 
		} /*else if (dischargedToEsdOrCommunityCare(postDischargeCare)) {
			return "postDischargeCare.missing.dischargedTo.esd_commmunity_care_team_code"
		}*/
	}
	
	private static dischargedToEsdOrCommunityCare(postDischargeCare) {
		return ( postDischargeCare.dischargedTo == "otherHospital")
	}
	
	private static dischargedToIntermediateCare(postDischargeCare) {
		return ( postDischargeCare.dischargedTo == "intermediateCare")
	}
	
	private static dischargedToSomewhereElse(postDischargeCare) {
		return ( postDischargeCare.dischargedTo == "somewhere")
	}
	
	private static validHospitalCode(newCareTeam) {
		return newCareTeam ==~ /\d{3}/	
	}

	private static validInpatientTeamCode(newCareTeam) {
		return newCareTeam ==~ /[A-Za-z]\d{3}/
	}
	
	private static validEsdOrCommunityCareTeamCode(newCareTeam) {
		return newCareTeam ==~ /\d{3}/
	}
	
	private static validateEsdReferralDate(esdReferralDate, postDischargeCare ) {
		
		if (esdReferralDate) {
			if(esdReferralDate > new Date()){
				return "esd.referral.date.not.in.future"
			}
			if(postDischargeCare.careActivity.afterDischarge(esdReferralDate, null)){
				return "esd.referral.date.not.after.discharge"
			}
			if(postDischargeCare.careActivity.beforeAdmission(esdReferralDate, null)){
				return "esd.referral.date.not.before.admission"
			}
		}
	}
	
	private static validateEsdReferralDateUnknown(esdReferralDateUnknown, postDischargeCare) {
		if (esdReferralDateUnknown && postDischargeCare.esdReferralDate) {
			return "postDischargeCare.esdReferralDateUnknown.true.when.esdReferralDate.set"
		}
	}
	
	
	
	def hasTherapy(description) {
		def result = false
		postDischargeTherapy.each { therapy ->
			if (therapy.type.description==description) {
				result = true
			}
		}
		return result
	}
	
	def deleteTherapy(description){
		def therapyToDelete = null
		postDischargeTherapy.each { therapy ->
			if (therapy.type.description==description) {
				therapyToDelete = therapy 
			}
		}
		if(therapyToDelete){
			removeFromPostDischargeTherapy(therapyToDelete)
			therapyToDelete.delete()
		}
	}
	
	def hasSupport(description) {
		def result = false
		postDischargeSupport.each { support ->
			if (support?.type?.description==description) {
				result = true
			}
		}
		return result
	}
	
	def deleteSupport(description){
		def supportToDelete = null
		postDischargeSupport.each { support ->
			if (support.type.description==description) {
				supportToDelete = support
			}
		}
		if(supportToDelete){
			removeFromPostDischargeSupport(supportToDelete)
			supportToDelete.delete()
		}
	}
}
