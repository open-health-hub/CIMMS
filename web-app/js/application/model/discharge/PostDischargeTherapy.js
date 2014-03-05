cimss.namespace("model.discharge").PostDischargeTherapy = function() {
	this.intermediateCare = ko.observable();
	this.home = ko.observable();
	this.careHome = ko.observable();
	this.other = ko.observable();	
	
	this.set = function(data) {
		if (data) {
			this.intermediateCare(data.intermediateCare);
			this.home(data.home);
			this.careHome(data.careHome);
			this.other(data.other);
		}
	};
};