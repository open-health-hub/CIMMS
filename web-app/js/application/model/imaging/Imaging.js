cimss.namespace("model.imaging").Imaging = function(){
	this.id = ko.observable();
	this.versions  = ko.observable();
	this.scanPostStroke = ko.observable("yes");
	this.noScanReason = ko.observable();
	this.scan = ko.observable(new cimss.model.imaging.Scan());
};