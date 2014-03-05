var strokeOnsetViewModel;

cimss.namespace("mvvm.admission_assessment").StrokeOnsetViewModel = function() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([]);

	this.admissionDetails = ko.observable();
	
	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.updateData = function(self, data) {
		
		var firstSeenBy,
			
			onset
			; 
		
		self.admissionDetails().versions(data.AdmissionDetails.versions);
		self.admissionDetails().originalData = data.AdmissionDetails;

		self.admissionDetails().inpatientAtOnset(data.AdmissionDetails.inpatientAtOnset);
		self.admissionDetails().admissionWard(data.AdmissionDetails.admissionWard);
		self.admissionDetails().arriveByAmbulance(data.AdmissionDetails.arriveByAmbulance);
		
		onset = self.admissionDetails().onset();
		onset.duringSleep(data.AdmissionDetails.onset.duringSleep);
		onset.onsetDate(data.AdmissionDetails.onset.onsetDate);
		onset.onsetDateEstimated(data.AdmissionDetails.onset.onsetDateEstimated);
		onset.onsetDateUnknown(data.AdmissionDetails.onset.onsetDateUnknown);
		onset.onsetTime(data.AdmissionDetails.onset.onsetTime);
		onset.onsetTimeUnknown(data.AdmissionDetails.onset.onsetTimeUnknown);
		
		onset.onsetTimeEstimated(data.AdmissionDetails.onset.onsetTimeEstimated);
		onset.didNotStayInStrokeWard(data.AdmissionDetails.onset.didNotStayInStrokeWard);
		onset.strokeWardAdmissionTime(data.AdmissionDetails.onset.strokeWardAdmissionTime);
		onset.strokeWardAdmissionDate(data.AdmissionDetails.onset.strokeWardAdmissionDate);
		
		onset.strokeWardDischargeTime(data.AdmissionDetails.onset.strokeWardDischargeTime);
		onset.strokeWardDischargeDate(data.AdmissionDetails.onset.strokeWardDischargeDate);

		onset.hospitalDischargeTime(data.AdmissionDetails.onset.hospitalDischargeTime);
		onset.hospitalDischargeDate(data.AdmissionDetails.onset.hospitalDischargeDate);
		
		self.admissionDetails().onset(onset);

		self.admissionDetails().strokeBedAvailable(data.AdmissionDetails.strokeBedAvailable);

		//self.admissionDetails().firstSeenByList().length = 0;
		if (data.AdmissionDetails.firstSeenByList.length > 0) {
			if (self.admissionDetails().firstSeenByList().length > 0) {
				for (i = 0; i < data.AdmissionDetails.firstSeenByList.length; i++) {
					firstSeenBy = self.admissionDetails().firstSeenByList()[i];
					firstSeenBy
							.category(data.AdmissionDetails.firstSeenByList[i].category);
					firstSeenBy
							.firstSeenDate(data.AdmissionDetails.firstSeenByList[i].firstSeenDate);
					firstSeenBy
							.firstSeenTime(data.AdmissionDetails.firstSeenByList[i].firstSeenTime);
					firstSeenBy
							.notSeen(data.AdmissionDetails.firstSeenByList[i].notSeen);
					firstSeenBy
							.display(data.AdmissionDetails.firstSeenByList[i].display);
					firstSeenBy
							.id('fsb_'+data.AdmissionDetails.firstSeenByList[i].display.replace(/ /g,'_'));
							
				}

			} else {
				for (i = 0; i < data.AdmissionDetails.firstSeenByList.length; i++) {
					firstSeenBy = new cimss.model.onset_admission.FirstSeenBy();
					firstSeenBy
							.category(data.AdmissionDetails.firstSeenByList[i].category);
					firstSeenBy
							.firstSeenDate(data.AdmissionDetails.firstSeenByList[i].firstSeenDate);
					firstSeenBy
							.firstSeenTime(data.AdmissionDetails.firstSeenByList[i].firstSeenTime);
					firstSeenBy
							.notSeen(data.AdmissionDetails.firstSeenByList[i].notSeen);
					firstSeenBy
							.display(data.AdmissionDetails.firstSeenByList[i].display);		
					firstSeenBy
							.id('fsb_'+data.AdmissionDetails.firstSeenByList[i].display.replace(/ /g,'_'));
			
					self.admissionDetails().firstSeenByList.push(firstSeenBy);
					
				}
			}

		}
	};
	
	

	var self = this;

	
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/strokeOnset/getStrokeOnsetPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#stroke-onset-section').append(response);
				},
				async : false
			});

			strokeOnsetViewModel.tracker = new changeTracker(strokeOnsetViewModel);
			
			ko.applyBindings(strokeOnsetViewModel, document.getElementById("strokeOnsetForm"));
			self.loaded = true;
			defaultUISetup('#strokeOnsetForm');
						
			$('.containPanel', '#strokeOnsetForm').removeClass('changed');
			$('button', '#strokeOnsetForm').button("disable");
		}

	};

	this.reset = function() {
		$('button', '#strokeOnsetForm').button("disable");
		
		$('.containPanel', '#strokeOnsetForm').removeClass('changed');
		refreshRadioGroupAsButtons('#strokeOnsetForm');
		self.tracker = new changeTracker(self);
	};	
	
	$.ajax({
		url : getUniqueUrl("/stroke/strokeOnset/getStrokeOnset/"+ $('#careActivityId').val()),
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
			url : getUniqueUrl("/stroke/strokeOnset/getStrokeOnset/"+ $('#careActivityId').val()),
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

	this.toggleDidNotStayInSU = function() {
		if ( self.admissionDetails().admissionWard() === 'SU' ) {
			self.admissionDetails().onset().didNotStayInStrokeWard(false);
		}
	}
	
	this.resetOnsetTimeUnknown = function() {
		self.admissionDetails().onset().onsetTimeUnknown(false);		
	}
	
	this.save = function() {

		self.admissionDetails().versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
			getUniqueUrl("/stroke/strokeOnset/updateStrokeOnset/" + $('#careActivityId').val()),
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
					$('.message', '#strokeOnsetForm').show();
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
					$('.message', '#strokeOnsetForm').fadeOut(2000);
				},
				async : false
		});
	};
};

strokeOnsetViewModel = new cimss.mvvm.admission_assessment.StrokeOnsetViewModel();

