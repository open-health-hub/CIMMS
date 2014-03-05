cimss.namespace("model.admission_assessment").NutritionManagement = function(){
	this.id = ko.observable();
	this.versions = ko.observable();
	this.dateScreened = ko.observable();
	this.timeScreened = ko.observable();
	this.unableToScreen = ko.observable();
	this.mustScore = ko.observable();
	this.dietitianReferralDate =  ko.observable();
	this.dietitianReferralTime = ko.observable();
	this.dietitianNotSeen = ko.observable();

	this.adequateAt24 = ko.observable();
	this.inadequateAt24Reason = ko.observable("");
	this.inadequateAt24ReasonOther = ko.observable("");
	this.adequateAt48 = ko.observable();
	this.inadequateAt48Reason = ko.observable("");
	this.inadequateAt48ReasonOther = ko.observable("");
	this.adequateAt72 = ko.observable();
	this.inadequateAt72Reason = ko.observable("");
	this.inadequateAt72ReasonOther = ko.observable("");
	this.hoursSinceAdmission = ko.observable();
	this.periodSinceAdmission = ko.observable();
};