cimss.namespace("model.imaging").Scan = function() {
	this.id = ko.observable();
	this.admissionDate= ko.observable("01/01/2011");
	this.admissionTime= ko.observable("12:30");
	this.requestDate = ko.observable("01/01/2011");
	this.requestTime = ko.observable("12:30");
	this.takenDate = ko.observable("01/01/2011");
	this.takenOverLimit=ko.observable(true);
	this.takenOverride=ko.observable();
	
	this.takenTime = ko.observable("12:30");
	this.imageType = ko.observable();
	this.imageDiagnosisCategory = ko.observable();
	this.imageDiagnosisType = ko.observable();
	this.imageDiagnosisTypeOther = ko.observable("Testing");
	
};

