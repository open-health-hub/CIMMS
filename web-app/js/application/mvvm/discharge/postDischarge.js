var postDischargeViewModel;

cimss.namespace("mvvm.discharge").PostDischargeViewModel = function() {

	this.dataNeedsLoading = true;
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);
	this.postDischarge = ko.observable();

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	var self = this;
	var loaded = false;

	this.load = function() {
		
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/postDischarge/getPostDischargePage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#post-discharge-section').append(response);
						},
						async : false
					});
			postDischargeViewModel.tracker = new changeTracker(
					postDischargeViewModel);
			ko.applyBindings(postDischargeViewModel, document
					.getElementById("postDischargeForm"));

	
			$("#postDischargeForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#postDischargeForm');
		} else if ( getGlobalFlag('palliativeCareRefresh')==true ) {
			self.undo();
		}
		self.loaded = true;
	};
	
	
	this.updateData = function(self, data) {
		self.postDischarge.originalData = data.Discharge;
		self.postDischarge.versions(data.Discharge.versions);
		
		self.postDischarge.palliativeCare(data.Discharge.palliativeCare);		
		self.postDischarge.palliativeCareDate(data.Discharge.palliativeCareDate);
		self.postDischarge.endOfLifePathway(data.Discharge.endOfLifePathway);

		self.postDischarge.palliativeCare72(data.Discharge.palliativeCare72);		
		self.postDischarge.palliativeCareDate72(data.Discharge.palliativeCareDate72);
		self.postDischarge.endOfLifePathway72(data.Discharge.endOfLifePathway72);
		
		self.postDischarge.esdReferral(data.Discharge.esdReferral);
		self.postDischarge.esdReferralDate(data.Discharge.esdReferralDate);
		self.postDischarge.esdReferralDateUnknown(data.Discharge.esdReferralDateUnknown);
		self.postDischarge.dischargedHome(data.Discharge.dischargedHome);

		self.postDischarge.dischargedTo(data.Discharge.dischargedTo);

		self.postDischarge.strokeSpecialist(data.Discharge.strokeSpecialist);
		self.postDischarge.patientPreviouslyResident(data.Discharge.patientPreviouslyResident);
		self.postDischarge.temporaryOrPermanent(data.Discharge.temporaryOrPermanent);
		self.postDischarge.alonePostDischarge(data.Discharge.alonePostDischarge);
		self.postDischarge.alonePostDischargeUnknown(data.Discharge.alonePostDischargeUnknown);
		self.postDischarge.shelteredAccommodation(data.Discharge.shelteredAccommodation);

		self.postDischarge.dischargedChomeWith(data.Discharge.dischargedChomeWith);
		self.postDischarge.esdReferralDischarge(data.Discharge.esdReferralDischarge);
		self.postDischarge.esdReferralDateDischarge(data.Discharge.esdReferralDateDischarge);
		self.postDischarge.esdReferralDateDischargeUnknown(data.Discharge.esdReferralDateDischargeUnknown);
		
		self.postDischarge.ssnapParticipationConsent(data.Discharge.ssnapParticipationConsent);
		self.postDischarge.newCareTeam(data.Discharge.newCareTeam);
		self.postDischarge.deathDate(data.Discharge.deathDate);
		self.postDischarge.strokeUnitDeath(data.Discharge.strokeUnitDeath);
		
		self.postDischarge.strokeWardDischargeDate(data.Discharge.strokeWardDischargeDate);
		self.postDischarge.strokeWardDischargeTime(data.Discharge.strokeWardDischargeTime);
		
		self.postDischarge.hospitalDischargeDate(data.Discharge.hospitalDischargeDate);
		self.postDischarge.hospitalDischargeTime(data.Discharge.hospitalDischargeTime);
		
		self.postDischarge.didNotStayInStrokeUnit(data.Discharge.didNotStayInStrokeUnit);
	};

	this.reset = function() {
		$('button', '#postDischargeForm').button("disable");
		$('.containPanel', '#postDischargeForm').removeClass('changed');

		refreshRadioGroupAsButtons('#postDischargeForm');
		self.tracker = new changeTracker(self);
	};

	this.loadData = function() {
		if (self.dataNeedsLoading) {
			setRadioGroupAsButtons('#postDischargeForm');
			self.dataNeedsLoading = false;
		}
	};

	$.ajax({
		url : getUniqueUrl("/stroke/postDischarge/getPostDischarge/"+ $('#careActivityId').val()),
		success : function(data) {

			self.postDischarge = new cimss.model.discharge.PostDischarge();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		$.ajax({
			url : getUniqueUrl("/stroke/postDischarge/getPostDischarge/"+ $('#careActivityId').val()),
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
		self.postDischarge.versions().careActivity =
							dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
				getUniqueUrl("/stroke/postDischarge/updatePostDischarge/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								Discharge : self.postDischarge
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
								$('.message', '#postDischargeForm').show();
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
									dataStatusViewModel.dataStatus().versions.careActivity = result.Discharge.versions.careActivity;
								}
								$('.message', '#postDischargeForm').fadeOut(
										2000);
							},
							async : false
						});
	};
};

postDischargeViewModel = new cimss.mvvm.discharge.PostDischargeViewModel();
