cimss.namespace("model.therapy").Assessments = function () {
	this.id = ko.observable("");
	this.barthelNotKnown = ko.observable();
	this.modifiedRankinNotKnown = ko.observable();
	this.barthel = ko.observable(new cimss.model.therapy.Barthel());
	this.modifiedRankin = ko.observable(new cimss.model.therapy.ModifiedRankin());	
};
