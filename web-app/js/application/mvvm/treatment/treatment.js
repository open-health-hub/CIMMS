var treatment;
var treatmentViewModel;


cimss.namespace("mvvm.treatment").TreatmentViewModel = function() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);
	this.treatments = ko.observable(new cimss.model.treatment.Treatments());

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};
	this.treatmentTypes = [];

	var self = this;
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/treatment/getTreatmentsPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#treatments-tab').append(response);
				},
				async : false
			});
			treatmentViewModel.tracker = new changeTracker(treatmentViewModel);
			ko.applyBindings(treatmentViewModel, document
					.getElementById("treatmentForm"));
			$("#treatmentForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			$("#treatments-tab .dataStatus").each(function() {
				ko.cleanNode(this);
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#treatmentForm');
		}
		self.loaded = true;
	};

	this.addTreatment = function() {
		this.treatments.additional
				.unshift(new cimss.model.treatment.Treatment());
	};

	this.removeLine = function(line) {
		this.treatments.additional.destroy(line);
	};

	this.updateTreatmentTypes = function(self, data) {
		if (data.Treatments.treatmentTypes.length > 0) {
			for (i = 0; i < data.Treatments.treatmentTypes.length; i++) {
				self.treatmentTypes
						.push(new cimss.model.treatment.TreatmentType(
								data.Treatments.treatmentTypes[i].id,
								data.Treatments.treatmentTypes[i].description));
			}
		}

	};

	this.updateData = function(self, data) {
		self.treatments.versions(data.Treatments.versions);
		self.treatments.respiratory(data.Treatments.respiratory);
		self.treatments.icu(data.Treatments.icu);
		self.treatments.neuro(data.Treatments.neuro);
		self.treatments.otherSpeciality(data.Treatments.otherSpeciality);
		self.treatments.otherText(data.Treatments.otherText);

		//self.treatments.compulsory([]);
		if (data.Treatments.compulsory.length > 0) {
			if (self.treatments.compulsory().length > 0) {
				for (i = 0; i < data.Treatments.compulsory.length; i++) {
					treatment = self.treatments.compulsory()[i];
					treatment.id(data.Treatments.compulsory[i].id);
					treatment.type(data.Treatments.compulsory[i].type);
					treatment
							.startDate(data.Treatments.compulsory[i].startDate);
					treatment
							.startTime(data.Treatments.compulsory[i].startTime);
					treatment.endDate(data.Treatments.compulsory[i].endDate);
					treatment.endTime(data.Treatments.compulsory[i].endTime);
					treatment
							.contraindicated(data.Treatments.compulsory[i].contraindicated);

				}

			} else {
				for (i = 0; i < data.Treatments.compulsory.length; i++) {
					treatment = new cimss.model.treatment.Treatment();
					treatment.id(data.Treatments.compulsory[i].id);
					treatment.type(data.Treatments.compulsory[i].type);
					treatment
							.startDate(data.Treatments.compulsory[i].startDate);
					treatment
							.startTime(data.Treatments.compulsory[i].startTime);
					treatment.endDate(data.Treatments.compulsory[i].endDate);
					treatment.endTime(data.Treatments.compulsory[i].endTime);
					treatment
							.contraindicated(data.Treatments.compulsory[i].contraindicated);
					self.treatments.compulsory.push(treatment);
				}

			}

		}

		//self.treatments.additional([]);
		if (data.Treatments.additional.length > 0) {
			if (self.treatments.additional().length > 0) {
				for (i = 0; i < data.Treatments.additional.length; i++) {
					treatment = self.treatments.additional()[i];
					treatment.id(data.Treatments.additional[i].id);
					treatment.type(data.Treatments.additional[i].type);
					treatment
							.startDate(data.Treatments.additional[i].startDate);
					treatment
							.startTime(data.Treatments.additional[i].startTime);
					treatment.endDate(data.Treatments.additional[i].endDate);
					treatment.endTime(data.Treatments.additional[i].endTime);
				}
			} else {
				for (i = 0; i < data.Treatments.additional.length; i++) {
					treatment = new cimss.model.treatment.Treatment();
					treatment.id(data.Treatments.additional[i].id);
					treatment.type(data.Treatments.additional[i].type);
					treatment
							.startDate(data.Treatments.additional[i].startDate);
					treatment
							.startTime(data.Treatments.additional[i].startTime);
					treatment.endDate(data.Treatments.additional[i].endDate);
					treatment.endTime(data.Treatments.additional[i].endTime);
					self.treatments.additional.push(treatment);
				}
			}
		}
	};

	this.reset = function() {
		$('button', '#treatmentForm').button("disable");
		$('button', '#treatment').button("enable");
		$('.containPanel', '#treatmentForm').removeClass('changed');

		refreshRadioGroupAsButtons('#treatmentForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/treatment/getTreatments/"+ $('#careActivityId').val()),
		success : function(data) {
			self.treatments = new cimss.model.treatment.Treatments();
			self.updateData(self, data);
			self.updateTreatmentTypes(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		$.ajax({
			url : getUniqueUrl("/stroke/treatment/getTreatments/"+ $('#careActivityId').val()),
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
		self.treatments.versions().careActivity =
				dataStatusViewModel.dataStatus().versions.careActivity;
		$
				.ajax(
						getUniqueUrl("/stroke/treatment/updateTreatments/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								Treatments : self.treatments
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
								$('.message', '#treatmentForm').show();
								if (result.FieldsInError.length === 0) {
									dataStatusViewModel.update();
									if ($('#cimssStatusDialogBox').dialog(
											"isOpen") === true) {
										$('#cimssStatusDialogBox').dialog(
												'close');
										dataStatusViewModel
												.cimssStatusDetails();
									}
									self.reset();
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity = result.Treatments.versions.careActivity;
									setCheckboxesAsButtons('#treatment');
								}
								$('.message', '#treatmentForm').fadeOut(2000);
							},
							async : false
						});
	};
};

treatmentViewModel = new cimss.mvvm.treatment.TreatmentViewModel();
