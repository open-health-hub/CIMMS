cimss.namespace("model.admission_assessment").ContinenceManagement = function(){
	this.id = ko.observable("");
	this.versions = ko.observable("");
	this.newlyIncontinent = ko.observable("");
	this.hasContinencePlan = ko.observable("");
	this.inappropriateForContinencePlan = ko.observable();
	this.continencePlanDate= ko.observable("");
	this.continencePlanTime= ko.observable("");
	this.noContinencePlanReason= ko.observable("");
	this.noContinencePlanReasonOther= ko.observable("");
	this.priorCatheter= ko.observable("");
	this.catheterisedSinceAdmission= ko.observable("");
	this.catheterHistory = ko.observableArray([]);
};
