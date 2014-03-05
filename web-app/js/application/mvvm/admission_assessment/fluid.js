var fluidManagementViewModel;

cimss.namespace("mvvm.admission_assessment").FluidManagementViewModel = function() {
	this.fluidManagement = ko.observable();
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.updateData = function(self, result) {
		self.fluidManagement.id(result.FluidManagement.id);
		self.fluidManagement.versions = result.FluidManagement.versions;

		self.fluidManagement
				.litrePlusAt24(result.FluidManagement.litrePlusAt24);
		self.fluidManagement
				.inadequateAt24Reason(result.FluidManagement.inadequateAt24Reason);
		self.fluidManagement
				.inadequateAt24ReasonOther(result.FluidManagement.inadequateAt24ReasonOther);
		self.fluidManagement
				.litrePlusAt48(result.FluidManagement.litrePlusAt48);
		self.fluidManagement
				.inadequateAt48Reason(result.FluidManagement.inadequateAt48Reason);
		self.fluidManagement
				.inadequateAt48ReasonOther(result.FluidManagement.inadequateAt48ReasonOther);
		self.fluidManagement
				.litrePlusAt72(result.FluidManagement.litrePlusAt72);
		self.fluidManagement
				.inadequateAt72Reason(result.FluidManagement.inadequateAt72Reason);
		self.fluidManagement
				.inadequateAt72ReasonOther(result.FluidManagement.inadequateAt72ReasonOther);
		self.fluidManagement
				.hoursSinceAdmission(result.FluidManagement.hoursSinceAdmission);
		self.fluidManagement
				.periodSinceAdmission(result.FluidManagement.periodSinceAdmission);
	};

	var self = this;
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/fluidManagement/getFluidManagementPage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#fluid-section').append(response);
						},
						async : false
					});
			fluidManagementViewModel.tracker = new changeTracker(
					fluidManagementViewModel);
			ko.applyBindings(fluidManagementViewModel, document
					.getElementById("fluidForm"));

			self.loaded = true;
			defaultUISetup('#fluidForm');
			$('.containPanel', '#fluidForm').removeClass('changed');
			$('button', '#fluidForm').button("disable");

		}

	};

	self.inadequateFluidReasons = ko.observableArray([ 'Nil By Mouth',
			'Nasogastric tube not applicable',
			'Nasogastric tube refused by patient',
			'Nasogastric tube dislodged', 'Nasogastric tube not used - other',
			'No IV access', 'Palliative care', 'Other' ]);

	this.reset = function() {
		$('button', '#fluidForm').button("disable");
		$('.containPanel', '#fluidForm').removeClass('changed');
		refreshRadioGroupAsButtons('#fluidForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
				url : getUniqueUrl("/stroke/fluidManagement/getFluidManagement/"+ $('#careActivityId').val()),
				success : function(data) {
					self.fluidManagement = new cimss.model.admission_assessment.FluidManagement();
					self.fluidManagement.originalData = data.FluidManagement;
					self.updateData(self, data);
					self.infoMessage("");
				},
				async : false
			});

	this.undo = function() {
		$.ajax({
			url : getUniqueUrl("/stroke/fluidManagement/getFluidManagement/"+ $('#careActivityId').val()),
			success : function(result) {
				self.fluidManagement.originalData = result.FluidManagement;
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
		self.fluidManagement.versions.careActivity =
				dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(getUniqueUrl("/stroke/fluidManagement/updateFluidManagement/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								FluidManagement : self.fluidManagement
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
								$('.message', '#fluidForm').show();
								if (result.FieldsInError.length === 0) {
									self.fluidManagement.originalData = result.FluidManagement;
									self.reset();
									dataStatusViewModel.update();
									if ($('#cimssStatusDialogBox').dialog(
											"isOpen") === true) {
										$('#cimssStatusDialogBox').dialog(
												'close');
										dataStatusViewModel
												.cimssStatusDetails();
									}
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity =
											result.FluidManagement.versions.careActivity;
								}
								$('.message', '#fluidForm').fadeOut(2000);
							},
							async : false
						});
	};
};

fluidManagementViewModel = new cimss.mvvm.admission_assessment.FluidManagementViewModel();
