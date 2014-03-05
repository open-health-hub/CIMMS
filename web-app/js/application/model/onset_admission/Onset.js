cimss.namespace("model.onset_admission").Onset = function() {
	this.onsetDate = ko.observable();
	this.onsetDateEstimated = ko.observable();
	this.onsetDateUnknown = ko.observable();

	this.duringSleep = ko.observable();

	this.onsetTime = ko.observable();
	this.onsetTimeEstimated = ko.observable();
	this.onsetTimeUnknown = ko.observable();
	
	this.didNotStayInStrokeWard = ko.observable();
	this.strokeWardAdmissionTime = ko.observable();
	this.strokeWardAdmissionDate = ko.observable();
	
	this.strokeWardDischargeTime = ko.observable();
	this.strokeWardDischargeDate = ko.observable();
	
	this.hospitalDischargeTime = ko.observable();
	this.hospitalDischargeDate = ko.observable();
	
};