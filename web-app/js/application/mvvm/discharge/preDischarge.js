var preDischargeViewModel;

cimss.namespace("mvvm.discharge").PreDischargeViewModel = function() {
	this.dataNeedsLoading = true;
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);
	this.discharge = ko.observable();
	this.dischargeInfo = ko.observable();

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	var self = this;
	var loaded = false;

	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/preDischarge/getPreDischargePage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#pre-discharge-section').append(response);
				},
				async : false
			});
			this.checkDischargeInfo();
			preDischargeViewModel.tracker = new changeTracker(
					preDischargeViewModel);
			ko.applyBindings(preDischargeViewModel, document
					.getElementById("dischargeForm"));
			$("#dischargeForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#dischargeForm');
		} else if ( getGlobalFlag('predischargeRefresh')==true ) {
			self.undo();
		}
		self.loaded = true;
	};

	this.updateData = function(self, data) {
		self.discharge.originalData = data.Discharge;
		self.discharge.versions(data.Discharge.versions);
		self.discharge.inRandomisedTrial(data.Discharge.inRandomisedTrial);
		self.discharge.esdInvolved(data.Discharge.esdInvolved);
		self.discharge.fitForDischargeDate(data.Discharge.fitForDischargeDate);
		self.discharge.fitForDischargeTime(data.Discharge.fitForDischargeTime);
		self.discharge
				.fitForDischargeDateUnknown(data.Discharge.fitForDischargeDateUnknown);
		self.discharge
				.socialWorkerReferral(data.Discharge.socialWorkerReferral);
		self.discharge
				.socialWorkerReferralDate(data.Discharge.socialWorkerReferralDate);
		self.discharge
				.socialWorkerReferralUnknown(data.Discharge.socialWorkerReferralUnknown);
		self.discharge
				.socialWorkerAssessment(data.Discharge.socialWorkerAssessment);
		self.discharge
				.socialWorkerAssessmentDate(data.Discharge.socialWorkerAssessmentDate);
		self.discharge
				.socialWorkerAssessmentUnknown(data.Discharge.socialWorkerAssessmentUnknown);
		self.discharge.esdReferral(data.Discharge.esdReferral);
		self.discharge.esdReferralDate(data.Discharge.esdReferralDate);
		self.discharge
				.esdReferralDateUnknown(data.Discharge.esdReferralDateUnknown);
		self.discharge.documentedEvidence(data.Discharge.documentedEvidence);
		self.discharge
				.documentationPostDischarge(data.Discharge.documentationPostDischarge);
		self.discharge
				.numberOfSocialServiceVisitsUnknown(data.Discharge.numberOfSocialServiceVisitsUnknown);
		self.discharge
				.numberOfSocialServiceVisits(data.Discharge.numberOfSocialServiceVisits);
		self.discharge.dischargedTo(data.Discharge.dischargedTo);
		self.discharge.strokeSpecialist(data.Discharge.strokeSpecialist);
		self.discharge
				.patientPreviouslyResident(data.Discharge.patientPreviouslyResident);
		self.discharge.temporaryOrPerm(data.Discharge.temporaryOrPerm);
		self.discharge.postDischargeTherapy().set(
				data.Discharge.postDischargeTherapy);
		self.discharge.postDischargeSupport().set(
				data.Discharge.postDischargeSupport);
		self.discharge.supportOnDischargeNeeded(data.Discharge.supportOnDischargeNeeded);
		
			
		
	};

	this.reset = function() {

		$('button', '#dischargeForm').button("disable");
		$('button', '#dischargeForm').button("enable");
		$('.containPanel', '#dischargeForm').removeClass('changed');
		$(':checked', '#dischargeForm').button("refresh");
		setCheckboxesAsButtons('#dischargeForm');
		refreshRadioGroupAsButtons('#dischargeForm');
		self.tracker = new changeTracker(self);
	};

	this.loadData = function() {
		if (self.dataNeedsLoading) {
			setRadioGroupAsButtons('#dischargeForm');
			self.dataNeedsLoading = false;
		}
	};

	$.ajax({
		url : getUniqueUrl("/stroke/preDischarge/getPreDischarge/"+ $('#careActivityId').val()),
		success : function(data) {

			self.discharge = new cimss.model.discharge.Discharge();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		$.ajax({
			url : getUniqueUrl("/stroke/preDischarge/getPreDischarge/"+ $('#careActivityId').val()),
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
		self.discharge.versions().careActivity =
			dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
						getUniqueUrl("/stroke/preDischarge/updatePreDischarge/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								Discharge : self.discharge
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
								$('.message', '#dischargeForm').show();
								if (result.FieldsInError.length === 0) {
									dataStatusViewModel.update();
									if ($('#cimssStatusDialogBox').dialog(
											"isOpen") === true) {
										$('#cimssStatusDialogBox').dialog(
												'close');
										dataStatusViewModel
												.cimssStatusDetails();
									}
									if ($('#ssnapStatusDialogBox').dialog(
											"isOpen") === true) {
										$('#ssnapStatusDialogBox').dialog(
												'close');
										dataStatusViewModel
												.ssnapStatusDetails();
									}
									if ($('#ssnap72StatusDialogBox').dialog('isOpen') === true) {
										$('#ssnap72StatusDialogBox').dialog('close');
										dataStatusViewModel.ssnap72StatusDetails();
									}									
									self.reset();
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity =
											result.Discharge.versions.careActivity;
								}
								$('.message', '#dischargeForm').fadeOut(2000);
							},
							async : false
						});
	};

	this.checkDischargeInfo = function() {
		
		if(self.discharge.supportOnDischargeNeeded()==='Yes'){
			if (self.discharge.postDischargeSupport().socialServicesUnavailable() ||
					self.discharge.postDischargeSupport().patientRefused()) {
				self.dischargeInfo(false);
			} else if (self.discharge.postDischargeSupport().socialServices()=== true) {
				self.dischargeInfo(true);
			} else {
				self.dischargeInfo(false);
			}
		}else{
			self.dischargeInfo(false);
			
		}
		return true;
	};

	this.clearDischarge = function() {
		if (self.discharge.postDischargeSupport().socialServicesUnavailable() ||
				self.discharge.postDischargeSupport().patientRefused()) {
			self.dischargeInfo(false);
		}
		return true;
	};
};

preDischargeViewModel = new cimss.mvvm.discharge.PreDischargeViewModel();
