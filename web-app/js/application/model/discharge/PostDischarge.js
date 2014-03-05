cimss.namespace("model.discharge").PostDischarge = function(){
	this.versions = ko.observable();
	this.originalData = null;
	
	this.dischargedHome = ko.observable();
	this.dischargedChomeWith = ko.observable();
	this.esdReferral = ko.observable();
	this.esdReferralDate = ko.observable();
	this.esdReferralDateUnknown = ko.observable();
	this.esdReferralDischarge = ko.observable();
	this.esdReferralDateDischarge = ko.observable();
	this.esdReferralDateDischargeUnknown = ko.observable();		
	
	
	this.dischargedTo = ko.observable(); 
	this.strokeSpecialist = ko.observable();	
	this.patientPreviouslyResident = ko.observable();
	this.temporaryOrPermanent = ko.observable();
	this.alonePostDischarge = ko.observable();
	this.alonePostDischargeUnknown = ko.observable();
	this.shelteredAccommodation = ko.observable();
	this.palliativeCare = ko.observable();
	this.palliativeCareDate = ko.observable();
	this.endOfLifePathway  = ko.observable();
	this.palliativeCare72 = ko.observable();
	this.palliativeCareDate72 = ko.observable();
	this.endOfLifePathway72  = ko.observable();
	this.deathDate  = ko.observable();
	this.strokeUnitDeath = ko.observable();
	
	this.ssnapParticipationConsent =  ko.observable();
	this.newCareTeam =  ko.observable();
	
	this.strokeWardDischargeDate = ko.observable();
	this.strokeWardDischargeTime = ko.observable();
	
	this.hospitalDischargeDate = ko.observable();
	this.hospitalDischargeTime = ko.observable();
	this.didNotStayInStrokeUnit = ko.observable();
};
