cimss.namespace("model.treatment").Treatment = function(){
	this.id = ko.observable();
	this.type = ko.observable();
	this.startDate = ko.observable();
	this.startTime = ko.observable();
	this.endDate = ko.observable();
	this.endTime = ko.observable();
	this.contraindicated = ko.observable();
};
