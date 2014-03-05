cimss.namespace("model.onset_admission").Arrival = function() {

    this.transferredFromAnotherHospital = ko.observable();

    this.thisHospitalArrivalDate = ko.observable();
	this.thisHospitalArrivalTime = ko.observable();

    this.arriveByAmbulance = ko.observable();
	this.ambulanceTrust = ko.observable();
	this.cadNumber = ko.observable();
	this.cadNumberUnknown = ko.observable();
	this.outcomeQuestionnairOptOut = ko.observable();

};