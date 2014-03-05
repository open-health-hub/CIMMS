cimss.namespace("model.onset_admission").FirstSeenBy = function() {
	this.category = ko.observable();
	this.firstSeenDate = ko.observable();
	this.firstSeenTime = ko.observable();
	this.notSeen = ko.observable();
	this.display = ko.observable();
	this.id = ko.observable();
};