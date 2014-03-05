var therapyManagementViewModel;

function TherapyManagementViewModel() {
	this.therapyManagement = ko.observable();
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.editBarthel = function() {
		setRadioGroupAsButtons("#baselineBarthelData");
		if(($("#baselineBarthelData").dialog("isOpen") === false)){
			$("#baselineBarthelData").dialog("open");
		}else {
			$("#baselineBarthelData").dialog("close");	
		}
		refreshRadioGroupAsButtons("#barthelData");
	};

	this.editModifiedRankin = function() {
		setRadioGroupAsButtons("#baselineModifiedRankinData");
		if(($("#baselineModifiedRankinData").dialog("isOpen") === false)){
			$("#baselineModifiedRankinData").dialog("open"); 
		}else{
			$("#baselineModifiedRankinData").dialog("close");
		}
		
		refreshRadioGroupAsButtons("#modifiedRankinData");
	};

	this.updateBarthelTotal = function() {
		$('#baselineBarthelData').dialog("close");
	};

	this.cancelBarthel = function() {
		self.therapyManagement.assessments().barthel().clearDetail();
		$('#baselineBarthelData').dialog("close");
	};

	this.updateData = function(self, data) {
		setData(self.therapyManagement, data.therapyManagement);
	};

	var self = this;
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/therapyManagement/getTherapyManagementPage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#therapy-section').append(response);
						},
						async : false
					});
			therapyManagementViewModel.tracker = new changeTracker(
					therapyManagementViewModel);
			ko.applyBindings(therapyManagementViewModel, document
					.getElementById("therapyForm"));
			defaultUISetup('#therapyForm');
		}
		self.loaded = true;
	};

	this.reset = function() {
		$('button', '#therapyForm').button("disable");
		$('.containPanel', '#therapyForm').removeClass('changed');
		refreshRadioGroupAsButtons('#therapyForm');
		self.tracker = new changeTracker(self);
	};

	$
			.ajax({
				url : getUniqueUrl("/stroke/therapyManagement/getTherapyManagement/"+ $('#careActivityId').val()),
				success : function(data) {
					self.therapyManagement = new TherapyManagement();
					self.therapyManagement.occupationalTherapyManagement(new cimss.model.therapy.OccupationalTherapyManagement());
					self.therapyManagement.speechAndLanguageTherapyManagement(new cimss.model.therapy.SpeechAndLanguageTherapyManagement());
					self.therapyManagement.physiotherapyManagement(new cimss.model.therapy.PhysiotherapyManagement());
					self.therapyManagement.assessments(new cimss.model.therapy.Assessments());

					self.updateData(self, data);
					self.infoMessage("");
				},
				async : false
			});

	this.undo = function() {
		$
				.ajax({
					url : getUniqueUrl("/stroke/therapyManagement/getTherapyManagement/"+ $('#careActivityId').val()),
					success : function(data) {
						self.updateData(self, data);
						self.fieldsInError(data.FieldsInError);
						self.errorsAsList(data.ErrorsAsList);
						self.infoMessage("");
						self.reset();
					},
					async : false
				});
	};

	this.save = function() {
		self.therapyManagement.versions.careActivity = dataStatusViewModel
				.dataStatus().versions.careActivity;
		$
				.ajax(
						getUniqueUrl("/stroke/therapyManagement/updateTherapyManagement/" + $('#careActivityId').val()),
						{
							data : ko.toJSON({
								TherapyManagement : self.therapyManagement
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
								$('.message', '#therapyForm').show();
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
									dataStatusViewModel.dataStatus().versions.careActivity = result.TherapyManagement.versions.careActivity;
								}
								$('.message', '#therapyForm').fadeOut(2000);
							},
							async : false
						});
	};
}

therapyManagementViewModel = new TherapyManagementViewModel();

therapyManagementViewModel.theBarthelScore = ko.dependentObservable(function() {
	if (this.therapyManagement.assessments().barthel().hasDetail()) {
		if (this.therapyManagement.assessments().barthel().isComplete()) {
			return this.therapyManagement.assessments().barthel().detailScore();
		} else {
			return this.therapyManagement.assessments().barthel().detailScore()+ " (incomplete)";
		}
	} else {
		return this.therapyManagement.assessments().barthel().manualTotal();
	}
}, therapyManagementViewModel);
