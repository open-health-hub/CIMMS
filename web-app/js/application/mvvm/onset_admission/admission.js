cimss.namespace("mvvm.onset_admission").AdmissionViewModel = function() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);

	this.admissionDetails = ko.observable();
	
	
	

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	var self = this;

	this.reset = function() {
		$('button', '#admissionForm').button("disable");
		$('button', '#admission').button("enable");
		$('.containPanel', '#admissionForm').removeClass('changed');
		//setCheckboxesAsButtons('#admission');
		refreshRadioGroupAsButtons('#admission');
		self.tracker = new changeTracker(self);
	};

	this.updateData = function(self, data) {
		
		var firstSeenBy,
			existing,
			preMorbidAssessment,
			lifeStyle,
			riskFactors,
			medication,
			onset
			; 
		
		self.admissionDetails().versions(data.AdmissionDetails.versions);
		self.admissionDetails().originalData = data.AdmissionDetails;

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
		
		
		preMorbidAssessment = self.admissionDetails().preMorbidAssessment();
		setData(preMorbidAssessment, data.AdmissionDetails.preMorbidAssessment);
		
		self.admissionDetails().preMorbidAssessment(preMorbidAssessment);
		
		
		
		

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

		onset = self.admissionDetails().onset();
		onset.duringSleep(data.AdmissionDetails.onset.duringSleep);
		onset.onsetDate(data.AdmissionDetails.onset.onsetDate);
		onset.onsetDateEstimated(data.AdmissionDetails.onset.onsetDateEstimated);
		onset.onsetDateUnknown(data.AdmissionDetails.onset.onsetDateUnknown);
		onset.onsetTime(data.AdmissionDetails.onset.onsetTime);
		onset.onsetTimeUnknown(data.AdmissionDetails.onset.onsetTimeUnknown);
		onset.onsetTimeEstimated(data.AdmissionDetails.onset.onsetTimeEstimated);
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
	
	this.editBarthel = function() {
		setRadioGroupAsButtons("#preMorbidBarthelData");
		if(($("#preMorbidBarthelData").dialog("isOpen") === false)){
			$("#preMorbidBarthelData").dialog("open");
		} else {
			$("#preMorbidBarthelData").dialog("close");
		}
		refreshRadioGroupAsButtons("#preMorbidBarthelData");
	};

	this.editModifiedRankin = function() {
		setRadioGroupAsButtons("#preMorbidModifiedRankinData");
		if(($("#preMorbidModifiedRankinData").dialog("isOpen") === false)){
			$("#preMorbidModifiedRankinData").dialog("open");
		}else {
			$("#preMorbidModifiedRankinData").dialog("close");
		} 
		refreshRadioGroupAsButtons("#preMorbidModifiedRankinData");
	};

	this.updateBarthelTotal = function() {
		$('#preMorbidBarthelData').dialog("close");
	};

	this.cancelBarthel = function() {
		self.dischargeAssessment.assessments().barthel().clearDetail();
		$('#preMorbidBarthelData').dialog("close");
	};

	

	$.ajax({
		url : getUniqueUrl("/stroke/admission/getAdmissionDetails/" + $('#careActivityId').val()),
		success : function(data) {
			self.admissionDetails(new cimss.model.onset_admission.AdmissionDetails());
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {

		$.ajax({
			url : getUniqueUrl("/stroke/admission/getAdmissionDetails/"+ $('#careActivityId').val()),
			success : function(result) {
				self.updateData(self, result);
				self.fieldsInError(result.FieldsInError);
				self.errorsAsList(result.ErrorsAsList);
				self.infoMessage("");
				self.reset();
			},
			async : false

		});

	};

	this.save = function() {
		self.admissionDetails().versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
						getUniqueUrl("/stroke/admission/updateAdmissionDetails/"+ $('#careActivityId').val()),
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
								self.fieldsInError(result.FieldsInError);
								self.updateData(self, result);
								self.errorsAsList(result.ErrorsAsList);
								self.infoMessage(result.InfoMessage);
								$('.message', '#admissionForm').show();
								if (result.FieldsInError.length === 0 ) {
									self.reset();
									dataStatusViewModel.update();
									if ($('#cimssStatusDialogBox').dialog("isOpen") === true) {
										$('#cimssStatusDialogBox').dialog('close');
										dataStatusViewModel
												.cimssStatusDetails();
									}
									if ($('#ssnapStatusDialogBox').dialog("isOpen") === true) {
										$('#ssnapStatusDialogBox').dialog('close');
										dataStatusViewModel
												.ssnapStatusDetails();
									}
									if ($('#ssnap72StatusDialogBox').dialog('isOpen') === true) {
										$('#ssnap72StatusDialogBox').dialog('close');
										dataStatusViewModel.ssnap72StatusDetails();
									}
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity = result.AdmissionDetails.versions.careActivity;
									//setCheckboxesAsButtons('#admission');
								}
								$('.message', '#admissionForm').fadeOut(2000);
							},
							async : false
						});
	};
};

var admissionViewModel = new cimss.mvvm.onset_admission.AdmissionViewModel();
admissionViewModel.tracker = new changeTracker(admissionViewModel);

admissionViewModel.theBarthelScore = ko.dependentObservable(
		function() {
			if (this.admissionDetails().preMorbidAssessment().assessments().barthel().hasDetail()) {
				if (this.admissionDetails().preMorbidAssessment().assessments().barthel()
						.isComplete()) {
					return this.admissionDetails().preMorbidAssessment().assessments().barthel()
							.detailScore();
				} else {
					return this.admissionDetails().preMorbidAssessment().assessments().barthel()
							.detailScore() + " (incomplete)";
				}
			} else {
				return this.admissionDetails().preMorbidAssessment().assessments().barthel()
						.manualTotal();
			}
		}, admissionViewModel);

ko.applyBindings(admissionViewModel, document.getElementById("admissionForm"));
