cimss.namespace("model.discharge").DischargeAssessment = function (){
	this.id = ko.observable("");
	this.versions = "";
	this.dischargedTo = ko.observable("");
	this.inAfOnDischarge = ko.observable("");
	this.onAnticoagulantAtDischarge = ko.observable("");
	this.assessments = ko.observable(new cimss.model.therapy.Assessments());
};
