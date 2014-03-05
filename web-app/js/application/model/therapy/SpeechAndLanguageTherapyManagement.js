cimss.namespace("model.therapy").SpeechAndLanguageTherapyManagement = function () {
	this.id = ko.observable("");
	this.communicationAssessmentPerformed = ko.observable("");
	this.communicationAssessmentPerformedIn72Hrs = ko.observable("");
	this.communicationAssessmentDate = ko.observable("");
	this.communicationAssessmentTime = ko.observable("");
	this.communicationNoAssessmentReason = ko.observable("");
	this.communication72HrNoAssessmentReason = ko.observable("");
	this.swallowingAssessmentPerformed = ko.observable("");
	this.swallowingAssessmentPerformedIn72Hrs = ko.observable("");
	
	this.swallowingAssessmentDate = ko.observable("");
	this.swallowingAssessmentTime = ko.observable("");
	this.swallowingNoAssessmentReason = ko.observable("");
	this.swallowing72HrNoAssessmentReason = ko.observable("");
	

};