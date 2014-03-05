cimss.namespace("model.discharge").PostDischargeSupport = function() {
	this.socialServices = ko.observable();
	this.informalCarers = ko.observable();	
	this.palliativeCare = ko.observable();
	this.socialServicesUnavailable = ko.observable();
	this.patientRefused = ko.observable();
	this.strokeNeurologySpecificEsd = ko.observable();
	this.nonSpecialistEsd = ko.observable();
	this.strokeNeurologySpecificRehabilitation = ko.observable();
	this.nonSpecialistRehabilitation = ko.observable();
	this.noTherapy = ko.observable();
	this.esdType = ko.observable();
	this.rehabilitationType = ko.observable();
	
	
	this.set = function(data) {
		if (data) {					
			this.palliativeCare(data.palliativeCare);
			this.socialServices(data.socialServices);
			this.informalCarers(data.informalCarers);
			this.socialServicesUnavailable(data.socialServicesUnavailable);
			this.patientRefused(data.patientRefused);
			this.strokeNeurologySpecificEsd(data.strokeNeurologySpecificEsd);
			this.nonSpecialistEsd(data.nonSpecialistEsd);
			this.strokeNeurologySpecificRehabilitation(data.strokeNeurologySpecificRehabilitation);
			this.nonSpecialistRehabilitation(data.nonSpecialistRehabilitation);	
			this.noTherapy(data.noTherapy);
			
			this.esdType(data.esdType);
			this.rehabilitationType(data.rehabilitationType);
			
		}
	};
};