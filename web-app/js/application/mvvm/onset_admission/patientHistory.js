var patientHistoryViewModel;

var AmbulanceTrust = function(code, description) {
	this.code = code;
	this.description = description; 
}

cimss.namespace("mvvm.admission_assessment").PatientHistoryViewModel = function() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([]);
	this.knownAmbulanceTrusts = ko.observableArray([
		new AmbulanceTrust('ISA1','ISA1 - Isle of Man Ambulance and Paramedic Service'),
		new AmbulanceTrust('ISA2','ISA2 - Guernsey Ambulance and Rescue Service'),
		new AmbulanceTrust('ISA3','ISA3 - Jersey Ambulance Service'),
		new AmbulanceTrust('NIA','NIA - Northern Ireland Ambulance Service'),
		new AmbulanceTrust('RRU','RRU - London Ambulance Service'),
		new AmbulanceTrust('RX5','RX5 - Great Western Ambulance Service'),
		new AmbulanceTrust('RX6','RX6 - North East Ambulance Service'),
		new AmbulanceTrust('RX7','RX7 - North West Ambulance Service'),
		new AmbulanceTrust('RX8','RX8 - Yorkshire Ambulance Service'),
		new AmbulanceTrust('RX9','RX9 - East Midlands Ambulance Service'),
		new AmbulanceTrust('RYA','RYA - West Midlands Ambulance Service'),
		new AmbulanceTrust('RYC','RYC - East of England Ambulance Service'),
		new AmbulanceTrust('RYD','RYD - South East Coast Ambulance Service'),
		new AmbulanceTrust('RYE','RYE - South Central Ambulance Service'),
		new AmbulanceTrust('RYF','RYF - South Western Ambulance Service'),
		new AmbulanceTrust('WAL','WAL - Welsh Ambulance Service'),
		new AmbulanceTrust('5QT','5QT - Isle of Wight Ambulance Service'),
		new AmbulanceTrust('-','UNKNOWN')
	]);
	this.admissionDetails = ko.observable();
	
	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.updateData = function(self, data) {
		
		var existing,
			preMorbidAssessment,
			lifeStyle,
			riskFactors,
			medication,
			arrival,
			inpatientAtOnset; 
		
		self.admissionDetails().versions(data.AdmissionDetails.versions);
		self.admissionDetails().originalData = data.AdmissionDetails;

		self.admissionDetails().inpatientAtOnset(data.AdmissionDetails.inpatientAtOnset);
		
		existing = self.admissionDetails().existing();
		existing.previousStroke(data.AdmissionDetails.existing.previousStroke);
		existing.previousTIA(data.AdmissionDetails.existing.previousTIA);
		existing.diabetes(data.AdmissionDetails.existing.diabetes);
		existing.atrialFibrillation(data.AdmissionDetails.existing.atrialFibrillation);
		existing.myocardialInfarction(data.AdmissionDetails.existing.myocardialInfarction);
		existing.hyperlipidaemia(data.AdmissionDetails.existing.hyperlipidaemia);
		existing.hypertension(data.AdmissionDetails.existing.hypertension);
		existing.valvularHeartDisease(data.AdmissionDetails.existing.valvularHeartDisease);
		existing.ischaemicHeartDisease(data.AdmissionDetails.existing.ischaemicHeartDisease);
		existing.congestiveHeartFailure(data.AdmissionDetails.existing.congestiveHeartFailure);
		existing.assessedInVascularClinic(data.AdmissionDetails.existing.assessedInVascularClinic);

		self.admissionDetails().existing(existing);
		
		
		
		
		self.admissionDetails().independent(data.AdmissionDetails.independent);

		lifeStyle = self.admissionDetails().lifeStyle();
		lifeStyle.livedAlone(data.AdmissionDetails.lifeStyle.livedAlone);

		riskFactors = self.admissionDetails().riskFactors();
		riskFactors.smoker(data.AdmissionDetails.riskFactors.smoker);
		riskFactors.alcohol(data.AdmissionDetails.riskFactors.alcohol);
		self.admissionDetails().riskFactors(riskFactors);

		medication = self.admissionDetails().medication();
		medication.lipidLowering(data.AdmissionDetails.medication.lipidLowering);
		medication.warfarin(data.AdmissionDetails.medication.warfarin);
		medication.antiplatelet(data.AdmissionDetails.medication.antiplatelet);
		self.admissionDetails().medication(medication);
		
		arrival = self.admissionDetails().arrival();
		arrival.transferredFromAnotherHospital(data.AdmissionDetails.arrival.transferredFromAnotherHospital);

        arrival.thisHospitalArrivalDate(data.AdmissionDetails.arrival.thisHospitalArrivalDate);
        arrival.thisHospitalArrivalTime(data.AdmissionDetails.arrival.thisHospitalArrivalTime);

		arrival.arriveByAmbulance(data.AdmissionDetails.arrival.arriveByAmbulance);
		
		arrival.ambulanceTrust(data.AdmissionDetails.arrival.ambulanceTrust);
		arrival.cadNumber(data.AdmissionDetails.arrival.cadNumber);
		arrival.cadNumberUnknown(data.AdmissionDetails.arrival.cadNumberUnknown);
		arrival.outcomeQuestionnairOptOut(data.AdmissionDetails.arrival.outcomeQuestionnairOptOut);
		
		self.admissionDetails().arrival(arrival);		
	};
	
	

	var self = this;

	
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/patientHistory/getPatientHistoryPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#patient-history-section').append(response);
				},
				async : false
			});

			patientHistoryViewModel.tracker = new changeTracker(patientHistoryViewModel);
			ko.applyBindings(patientHistoryViewModel, document.getElementById("patientHistoryForm"));
			self.loaded = true;
			defaultUISetup('#patientHistoryForm');
			$('.containPanel', '#patientHistoryForm').removeClass('changed');
			$('button', '#patientHistoryForm').button("disable");
		}

	};

	this.reset = function() {
		$('button', '#patientHistoryForm').button("disable");
		$('.containPanel', '#patientHistoryForm').removeClass('changed');
		refreshRadioGroupAsButtons('#patientHistoryForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/patientHistory/getPatientHistory/"+ $('#careActivityId').val()),
		success : function(data) {
			self.admissionDetails(new cimss.model.onset_admission.AdmissionDetails());
			self.updateData(self, data);
			self.errorsAsList("<div class='errors'  style='display:none'><ul id='messageBox'></ul></div>");
			self.infoMessage("");
		},
		async : false
	});

	
	this.undo = function() {

		$.ajax({
			url : getUniqueUrl("/stroke/patientHistory/getPatientHistory/"+ $('#careActivityId').val()),
			success : function(result) {
				self.updateData(self, result);
				self.fieldsInError(result.FieldsInError);
				self.errorsAsList("<div class='errors' style='display:none'><ul id='messageBox'></ul></div>");
				self.infoMessage("");
				self.reset();
			},
			async : false
		});
	};

	this.save = function() {
		// Some other Objects need to be refreshed. Here we set flags
		// to indicate which ones. The refresh is done elsewhere
		setGlobalFlag('occupationalTherapyRefresh',true);
		setGlobalFlag('saltRefresh',true);
		setGlobalFlag('physiotherapyRefresh',true);
		
		self.admissionDetails().versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
			getUniqueUrl("/stroke/patientHistory/updatePatientHistory/" + $('#careActivityId').val()),
			{
				data : ko.toJSON({
					AdmissionDetails : self.admissionDetails
				}),
				type : "post",
				contentType : "application/json",
				statusCode : {
					500 : function() {
						alert('There was a problem on the server. Please report to your system administrator');
					}
				},
				success : function(result) {
					self.updateData(self, result);
					self.fieldsInError(result.FieldsInError);
					self.errorsAsList(result.ErrorsAsList);
					self.infoMessage(result.InfoMessage);
					$('.message', '#patientHistoryForm').show();
					if (result.FieldsInError.length === 0) {
						self.reset();
						dataStatusViewModel.update();
						
						if ($('#cimssStatusDialogBox').dialog('isOpen') === true) {
							$('#cimssStatusDialogBox').dialog('close');
							dataStatusViewModel.cimssStatusDetails();
						}
						if ($('#ssnapStatusDialogBox').dialog('isOpen') === true) {
							$('#ssnapStatusDialogBox').dialog('close');
							dataStatusViewModel.ssnapStatusDetails();
						}
						
						if ($('#ssnap72StatusDialogBox').dialog('isOpen') === true) {
							$('#ssnap72StatusDialogBox').dialog('close');
							dataStatusViewModel.ssnap72StatusDetails();
						}						
					} else {
						dataStatusViewModel.dataStatus().versions.careActivity = result.AdmissionDetails.versions.careActivity;
					}
					$('.message', '#patientHistoryForm').fadeOut(2000);
				},
				async : false
		});
	};
};

patientHistoryViewModel = new cimss.mvvm.admission_assessment.PatientHistoryViewModel();

