var clinicalSummaryViewModel;

cimss.namespace("mvvm.clinical_summary").ClinicalSummaryViewModel = function() {
	this.clinicalSummary = ko.observable();
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	var self = this;
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/clinicalSummary/getClinicalSummaryPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#clinical-summary-tab').append(response);
				},
				async : false
			});
			clinicalSummaryViewModel.tracker = new changeTracker(clinicalSummaryViewModel);
			ko.applyBindings(clinicalSummaryViewModel, document
					.getElementById("clinicalSummaryForm"));

			$("#clinicalSummaryForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			$("#clinical-summary-tab .dataStatus").each(function() {
				ko.cleanNode(this);
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#clinicalSummaryForm');
		} else if ( getGlobalFlag('palliativeCareRefresh')==true ) {
			self.undo();
		}
		self.loaded = true;
	};

	
	this.id = ko.observable();
	this.versions  = ko.observable();
	this.worstLevelOfConsciousness = ko.observable();
	this.urinaryTractInfection = ko.observable();
	this.newPneumonia = ko.observable();
	
	this.updateData = function(self, data) {
		self.clinicalSummary.id(data.clinicalSummary.id);
		self.clinicalSummary.versions(data.clinicalSummary.versions);
		self.clinicalSummary.worstLevelOfConsciousness(data.clinicalSummary.worstLevelOfConsciousness);
		self.clinicalSummary.urinaryTractInfection(data.clinicalSummary.urinaryTractInfection);
		self.clinicalSummary.newPneumonia(data.clinicalSummary.newPneumonia);
		self.clinicalSummary.palliativeCare(data.clinicalSummary.palliativeCare);
		self.clinicalSummary.palliativeCareDate(data.clinicalSummary.palliativeCareDate);
		self.clinicalSummary.endOfLifePathway(data.clinicalSummary.endOfLifePathway);
		self.clinicalSummary.classification(data.clinicalSummary.classification);
	};

	
	
	this.reset = function() {
		$('button', '#clinicalSummaryForm').button("disable");
		$('.containPanel', '#clinicalSummaryForm').removeClass('changed');
		refreshRadioGroupAsButtons('#clinicalSummaryForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/clinicalSummary/getClinicalSummary/"+ $('#careActivityId').val()),
		success : function(data) {
			self.clinicalSummary = new cimss.model.clinical_summary.ClinicalSummary();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/clinicalSummary/getClinicalSummary/"+ $('#careActivityId').val()),
			success : function(result) {
				self.updateData(self, result);
				self.fieldsInError(result.FieldsInError);
				self.errorsAsList(result.ErrorsAsList);
				self.infoMessage("");
				self.reset();

			},
			async : false
		});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;

	};

	this.save = function() {
		self.clinicalSummary.versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		var request = $
				.ajax(
						getUniqueUrl("/stroke/clinicalSummary/updateClinicalSummary/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								clinicalSummary : self.clinicalSummary
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
								$('.message', '#clinicalSummaryForm').show();
								if (result.FieldsInError.length === 0) {
									self.reset();
									dataStatusViewModel.update();
									
									if ($('#cimssStatusDialogBox').dialog("isOpen") === true) {
										$('#cimssStatusDialogBox').dialog('close');
										dataStatusViewModel.cimssStatusDetails();
									}
									
									
									
									if ($('#ssnapStatusDialogBox').dialog("isOpen") === true) {
										$('#ssnapStatusDialogBox').dialog('close');
										dataStatusViewModel.ssnapStatusDetails();
									}
									if ($('#ssnap72StatusDialogBox').dialog('isOpen') === true) {
										$('#ssnap72StatusDialogBox').dialog('close');
										dataStatusViewModel.ssnap72StatusDetails();
									}									
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity = result.clinicalSummary.versions.careActivity;
								}
								$('.message', '#clinicalSummaryForm').fadeOut(2000);

							},
							async : false
						});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;
	};
};

clinicalSummaryViewModel = new cimss.mvvm.clinical_summary.ClinicalSummaryViewModel();
