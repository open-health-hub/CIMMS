var baselineAssessmentViewModel;

cimss.namespace("mvvm.therapy").BaselineAssessmentViewModel = function() {
	this.therapyManagement = ko.observable();
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
				url : getUniqueUrl("/stroke/baselineAssessment/getBaselineAssessmentPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#baseline-assessments-section').append(response);
				},
				async : false
			});
			baselineAssessmentViewModel.tracker = new changeTracker(baselineAssessmentViewModel);
			
			ko.applyBindings(baselineAssessmentViewModel, document.getElementById("baselineAssessmentForm"));
			$("#baselineAssessmentForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			$("#baselineBarthelScore").mask("9");
			$("#baselineModifiedRankinScore").mask("9");
			
			defaultUISetup('#baselineAssessmentForm');
		}
		self.loaded = true;
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
	
	this.reset = function() {
		$('button', '#baselineAssessmentForm').button("disable");
		$('.containPanel', '#baselineAssessmentForm').removeClass('changed');
		refreshRadioGroupAsButtons('#baselineAssessmentForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/baselineAssessment/getBaselineAssessment/"+ $('#careActivityId').val()),
		success : function(data) {
			self.therapyManagement = new cimss.model.therapy.TherapyManagement();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/baselineAssessment/getBaselineAssessment/"+ $('#careActivityId').val()),
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
		self.therapyManagement.versions.careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		var request = $
				.ajax(
						getUniqueUrl("/stroke/baselineAssessment/updateBaselineAssessment/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								therapyManagement : self.therapyManagement
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
								$('.message', '#baselineAssessmentForm').show();
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
									dataStatusViewModel.dataStatus().versions.careActivity = result.therapyManagement.versions.careActivity;
								}
								$('.message', '#baselineAssessmentForm').fadeOut(2000);

							},
							async : false
						});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;
	};
};

baselineAssessmentViewModel = new cimss.mvvm.therapy.BaselineAssessmentViewModel();

baselineAssessmentViewModel.theBarthelScore = ko.dependentObservable(function() {
	if (this.therapyManagement.assessments().barthel().hasDetail()) {
		if (this.therapyManagement.assessments().barthel().isComplete()) {
			return this.therapyManagement.assessments().barthel().detailScore();
		} else {
			return this.therapyManagement.assessments().barthel().detailScore()+ " (incomplete)";
		}
	} else {
		return this.therapyManagement.assessments().barthel().manualTotal();
	}
}, baselineAssessmentViewModel);
