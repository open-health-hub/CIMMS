cimss.namespace("model.treatment").Treatments = function(){
	this.versions = ko.observable();
	this.compulsory = ko.observableArray([]);
	this.additional = ko.observableArray([]);
	this.respiratory = ko.observable();
	this.icu = ko.observable();
	this.neuro = ko.observable();
	this.otherSpeciality = ko.observable();
	this.otherText = ko.observable();
};