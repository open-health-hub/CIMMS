cimss.namespace("model.therapy_summary").TherapySummary = function(){
	this.id = ko.observable();
	this.versions  = ko.observable({});
	this.requiredTherapies  = ko.observable(new cimss.model.therapy_summary.RequiredTherapies());
	this.daysOfTherapy  = ko.observable(new cimss.model.therapy_summary.DaysOfTherapy());
	this.minutesOfTherapy  = ko.observable(new cimss.model.therapy_summary.MinutesOfTherapy());

};



cimss.namespace("model.therapy_summary").RequiredTherapies = function(){
	this.physiotherapy = ko.observable();
	this.occupational = ko.observable();
	this.salt = ko.observable();
	this.psychology  = ko.observable();
	this.nurse =  ko.observable();
};

cimss.namespace("model.therapy_summary").DaysOfTherapy = function(){
	this.physiotherapy = ko.observable();
	this.occupational = ko.observable();
	this.salt = ko.observable();
	this.psychology  = ko.observable();
	this.nurse =  ko.observable();
};

cimss.namespace("model.therapy_summary").MinutesOfTherapy = function(){
	this.physiotherapy = ko.observable();
	this.occupational = ko.observable();
	this.salt = ko.observable();
	this.psychology  = ko.observable();
	this.nurse =  ko.observable();
};