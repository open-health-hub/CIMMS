var nutritionManagementViewModel;

cimss.namespace("mvvm.admission_assessment").NutritionManagementViewModel = function() {
	this.nutritionManagement = ko.observable();
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([]);

	this.warningMessage = ko.observable("");

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.updateData = function(self, result) {
		self.nutritionManagement.id(result.NutritionManagement.id);
		self.nutritionManagement.versions(result.NutritionManagement.versions);
		self.nutritionManagement.dateScreened(result.NutritionManagement.dateScreened);
		self.nutritionManagement.timeScreened(result.NutritionManagement.timeScreened);
		self.nutritionManagement.mustScore(result.NutritionManagement.mustScore);
		self.nutritionManagement.dietitianReferralDate(result.NutritionManagement.dietitianReferralDate);
		self.nutritionManagement.dietitianReferralTime(result.NutritionManagement.dietitianReferralTime);
		self.nutritionManagement.unableToScreen(result.NutritionManagement.unableToScreen);
		self.nutritionManagement.dietitianNotSeen(result.NutritionManagement.dietitianNotSeen);		
		self.nutritionManagement.adequateAt24(result.NutritionManagement.adequateAt24);
		self.nutritionManagement.inadequateAt24Reason(result.NutritionManagement.inadequateAt24Reason);
		self.nutritionManagement.inadequateAt24ReasonOther(result.NutritionManagement.inadequateAt24ReasonOther);
		self.nutritionManagement.adequateAt48(result.NutritionManagement.adequateAt48);
		self.nutritionManagement.inadequateAt48Reason(result.NutritionManagement.inadequateAt48Reason);
		self.nutritionManagement.inadequateAt48ReasonOther(result.NutritionManagement.inadequateAt48ReasonOther);
		self.nutritionManagement.adequateAt72(result.NutritionManagement.adequateAt72);
		self.nutritionManagement.inadequateAt72Reason(result.NutritionManagement.inadequateAt72Reason);
		self.nutritionManagement.inadequateAt72ReasonOther(result.NutritionManagement.inadequateAt72ReasonOther);
		self.nutritionManagement.hoursSinceAdmission(result.NutritionManagement.hoursSinceAdmission);
		self.nutritionManagement.periodSinceAdmission(result.NutritionManagement.periodSinceAdmission);

	};

	var self = this;

	self.inadequateNutritionReasons = ko.observableArray([ 'Nil By Mouth',
			'Nasogastric tube not applicable',
			'Nasogastric tube refused by patient',
			'Nasogastric tube dislodged', 'Nasogastric tube not used - other',
			'Palliative care', 'Other' ]);

	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/nutritionManagement/getNutritionManagementPage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#nutrition-section').append(response);
						},
						async : false
					});

			nutritionManagementViewModel.tracker = new changeTracker(
					nutritionManagementViewModel);
			ko.applyBindings(nutritionManagementViewModel, document
					.getElementById("nutritionForm"));

			self.loaded = true;
			defaultUISetup('#nutritionForm');
			$('.containPanel', '#nutritionForm').removeClass('changed');
			$('button', '#nutritionForm').button("disable");
		}

	};

	this.reset = function() {
		$('button', '#nutritionForm').button("disable");
		$('.containPanel', '#nutritionForm').removeClass('changed');
		refreshRadioGroupAsButtons('#nutritionForm');
		self.tracker = new changeTracker(self);
	};

	$
			.ajax({
				url : getUniqueUrl("/stroke/nutritionManagement/getNutritionManagement/"+ $('#careActivityId').val()),
				success : function(data) {
					self.nutritionManagement = new cimss.model.admission_assessment.NutritionManagement();
					self.updateData(self, data);
					self
							.errorsAsList("<div class='errors'  style='display:none'><ul id='messageBox'></ul></div>");
					self.infoMessage("");
				},
				async : false
			});

	self.hasWarnings = ko.computed(
					function() {
						if (self.nutritionManagement.mustScore() >= 2) {
							if (!self.nutritionManagement
									.dietitianReferralDate()) {
								self
										.warningMessage("This patient is at risk of malnutrition and has not yet been referred to a dietitian");
							} else {
								self
										.warningMessage("This patient is at risk of malnutrition");
							}

							return true;
						} else {
							return false;
						}

					}, self);

	this.undo = function() {

		$
				.ajax({
					url : getUniqueUrl("/stroke/nutritionManagement/getNutritionManagement/"+ $('#careActivityId').val()),
					success : function(result) {
						self.updateData(self, result);
						self.fieldsInError(result.FieldsInError);
						self
								.errorsAsList("<div class='errors' style='display:none'><ul id='messageBox'></ul></div>");
						self.infoMessage("");
						self.reset();
					},
					async : false
				});
	};

	this.save = function() {

		self.nutritionManagement.versions().careActivity = dataStatusViewModel
				.dataStatus().versions.careActivity;
		$.ajax(
						getUniqueUrl("/stroke/nutritionManagement/updateNutritionManagement/" +
									$('#careActivityId').val()),
						{
							data : ko.toJSON({
								NutritionManagement : self.nutritionManagement
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
								$('.message', '#nutritionForm').show();
								if (result.FieldsInError.length === 0) {
									self.reset();
									dataStatusViewModel.update();
									if ($('#chftStatusDialogBox').dialog(
											"isOpen") === true) {
										$('#chftStatusDialogBox').dialog(
												'close');
										dataStatusViewModel.chftStatusDetails();
									}
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
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity = result.NutritionManagement.versions.careActivity;
								}
								$('.message', '#nutritionForm').fadeOut(2000);
							},
							async : false
						});
	};
};

nutritionManagementViewModel = new cimss.mvvm.admission_assessment.NutritionManagementViewModel();
