cimss.namespace("model.onset_admission").AdmissionDetails = function () {
	this.versions = ko.observable();
	this.originalData = null;
	this.strokeBedAvailable = ko.observable();
	this.independent = ko.observable();
	this.riskFactors = ko.observable(new cimss.model.onset_admission.RiskFactors());
	this.lifeStyle = ko.observable(new cimss.model.onset_admission.LifeStyle());
	this.medication = ko.observable(new cimss.model.onset_admission.Medication());
	this.onset = ko.observable(new cimss.model.onset_admission.Onset());
	this.existing = ko.observable(new cimss.model.onset_admission.Existing());
	this.preMorbidAssessment = ko.observable(new cimss.model.onset_admission.PreMorbidAssessment());
	this.firstSeenByList = ko.observableArray([]);
	this.arrival = ko.observable(new cimss.model.onset_admission.Arrival());
	this.inpatientAtOnset = ko.observable();
	this.admissionWard = ko.observable();
	this.arriveByAmbulance = ko.observable();
};