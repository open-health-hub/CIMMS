cimss.namespace("model.clinical_summary").ClinicalSummary = function(){
	this.id = ko.observable();
	this.versions  = ko.observable();
	this.worstLevelOfConsciousness = ko.observable();
	this.urinaryTractInfection = ko.observable();
	this.newPneumonia = ko.observable();
	this.palliativeCare = ko.observable();
	this.palliativeCareDate = ko.observable();
	this.endOfLifePathway = ko.observable();
	this.classification = ko.observable();
};