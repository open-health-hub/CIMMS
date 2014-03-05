cimss.namespace("model.therapy").PhysiotherapyManagement = function () {
	this.id = ko.observable("");
	this.physioAssessmentPerformed = ko.observable();
	this.physioAssessmentPerformedIn72Hrs = ko.observable();
	this.physioAssessmentDate = ko.observable();
	this.physioAssessmentTime = ko.observable();
	this.physioNoAssessmentReason = ko.observable();
	this.physioNo72HrAssessmentReason = ko.observable();
};
