cimss.namespace("model.discharge").Discharge = function(){
	this.versions = ko.observable();
	this.originalData = null;
	this.inRandomisedTrial =  ko.observable();
	this.esdInvolved =  ko.observable();
	this.fitForDischargeDate = ko.observable();
	this.fitForDischargeTime = ko.observable();
	this.fitForDischargeDateUnknown = ko.observable();	
	this.socialWorkerReferral = ko.observable();
	this.documentedEvidence = ko.observable();
	this.documentationPostDischarge = ko.observable();
	this.numberOfSocialServiceVisitsUnknown = ko.observable();
	this.numberOfSocialServiceVisits = ko.observable();
	this.dischargeLocationDifferent = ko.observable();
	this.dischargedTo = ko.observable(); 
	this.strokeSpecialist = ko.observable();	
	this.patientPreviouslyResident = ko.observable();
	this.temporaryOrPerm = ko.observable();
	this.socialWorkerReferralDate = ko.observable();
	this.socialWorkerReferralUnknown = ko.observable();
	this.socialWorkerAssessment = ko.observable();
	this.socialWorkerAssessmentDate = ko.observable();
	this.socialWorkerAssessmentUnknown = ko.observable();
	
	this.supportOnDischargeNeeded = ko.observable();
	
	
	
	
	this.esdReferral = ko.observable();
	this.esdReferralDate = ko.observable();
	this.esdReferralDateUnknown = ko.observable();
	this.postDischargeTherapy = ko.observable(new cimss.model.discharge.PostDischargeTherapy());
	this.postDischargeSupport = ko.observable(new cimss.model.discharge.PostDischargeSupport());
	

	
};