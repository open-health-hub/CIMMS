function TherapyManagement(){
	this.id = ko.observable("");
	this.versions = "";
	this.cognitiveStatusAssessed = ko.observable("");
	this.cognitiveStatusAssessmentDate = ko.observable("");
	this.cognitiveStatusAssessmentTime = ko.observable("");
	this.cognitiveStatusNoAssessmentReason = ko.observable("");
	
	this.rehabGoalsSet = ko.observable("");
	this.rehabGoalsSetDate = ko.observable("");
	this.rehabGoalsSetTime = ko.observable("");
	this.rehabGoalsNotSetReason = ko.observable("");
	
	this.occupationalTherapyManagement = ko.observable("");
	this.speechAndLanguageTherapyManagement = ko.observable("");
	this.physiotherapyManagement = ko.observable("");
	this.assessments = ko.observable("");
}

cimss.namespace("model.therapy").TherapyManagement = function () {
	this.id = ko.observable("");
	this.versions = "";
	
	this.cognitiveStatusAssessed = ko.observable();
	this.cognitiveStatusAssessmentDate = ko.observable();
	this.cognitiveStatusAssessmentTime = ko.observable();
	this.cognitiveStatusNoAssessmentReason = ko.observable();	
	
	this.rehabGoalsSet = ko.observable();
	this.rehabGoalsSetDate = ko.observable();
	this.rehabGoalsSetTime = ko.observable();
	this.rehabGoalsNotSetReason = ko.observable();
	
	this.admissionDate = ko.observable();
	this.admissionTime = ko.observable();
	
	
	
	this.occupationalTherapyManagement = ko.observable(new cimss.model.therapy.OccupationalTherapyManagement());
	this.speechAndLanguageTherapyManagement = ko.observable(new cimss.model.therapy.SpeechAndLanguageTherapyManagement());
	this.physiotherapyManagement = ko.observable(new cimss.model.therapy.PhysiotherapyManagement());
	
	this.assessments = ko.observable(new cimss.model.therapy.Assessments());
};