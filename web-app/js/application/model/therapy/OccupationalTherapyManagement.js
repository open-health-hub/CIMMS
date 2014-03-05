cimss.namespace("model.therapy").OccupationalTherapyManagement = function () {
	this.id = ko.observable();
	this.assessmentPerformed = ko.observable();
	this.assessmentPerformedIn72Hrs = ko.observable();
	this.assessmentDate = ko.observable();
	this.assessmentTime = ko.observable();
	this.otTherapyNoAssessmentReason = ko.observable();
	this.otTherapyNo72HrAssessmentReason = ko.observable();
	
	this.moodAssessmentPerformed = ko.observable();
	this.moodAssessmentDate = ko.observable();
	this.moodAssessmentTime = ko.observable();
	this.noMoodAssessmentReason = ko.observable();
};
