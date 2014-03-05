cimss.namespace("model.admission_assessment").FluidManagement = function (){
	this.id = ko.observable("");
	this.versions = null;
	this.originalData = null ;
	this.litrePlusAt24 = ko.observable("");
	this.inadequateAt24Reason = ko.observable("");
	this.inadequateAt24ReasonOther = ko.observable("");
	this.litrePlusAt48 = ko.observable("");
	this.inadequateAt48Reason = ko.observable("");
	this.inadequateAt48ReasonOther = ko.observable("");
	this.litrePlusAt72 = ko.observable("");
	this.inadequateAt72Reason = ko.observable("");
	this.inadequateAt72ReasonOther = ko.observable("");
	this.hoursSinceAdmission = ko.observable();
	this.periodSinceAdmission = ko.observable();
};