cimss.namespace("model.onset_admission").PreMorbidAssessment = function (){
	this.id = ko.observable("");
	this.versions = "";
	this.assessments = ko.observable(new cimss.model.therapy.Assessments());
};
