var preMorbidAssessmentViewModel;

cimss.namespace("mvvm.admission_assessment").PreMorbidAssessmentViewModel = function() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([]);

	this.admissionDetails = ko.observable();
	
	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.updateData = function(self, data) {
		self.admissionDetails().versions(data.AdmissionDetails.versions);
		self.admissionDetails().originalData = data.AdmissionDetails;
		
		var preMorbidAssessment;
		
		
		preMorbidAssessment = self.admissionDetails().preMorbidAssessment();
		setData(preMorbidAssessment, data.AdmissionDetails.preMorbidAssessment);
		self.admissionDetails().preMorbidAssessment(preMorbidAssessment);
	};
	
	

	var self = this;

	
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
		self.admissionDetails().preMorbidAssessment().assessments().barthel().clearDetail();
		$('#preMorbidBarthelData').dialog("close");
	};

	
	
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/preMorbidAssessment/getPreMorbidAssessmentPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#pre-morbid-assessment-section').append(response);
				},
				async : false
			});

			preMorbidAssessmentViewModel.tracker = new changeTracker(preMorbidAssessmentViewModel);
			ko.applyBindings(preMorbidAssessmentViewModel, document.getElementById("preMorbidAssessmentForm"));
			self.loaded = true;
			defaultUISetup('#preMorbidAssessmentForm');
			$('.containPanel', '#preMorbidAssessmentForm').removeClass('changed');
			$('button', '#preMorbidAssessmentForm').button("disable");
		}

	};

	this.reset = function() {
		$('button', '#preMorbidAssessmentForm').button("disable");
		$('.containPanel', '#preMorbidAssessmentForm').removeClass('changed');
		refreshRadioGroupAsButtons('#preMorbidAssessmentForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/preMorbidAssessment/getPreMorbidAssessment/"+ $('#careActivityId').val()),
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
			url : getUniqueUrl("/stroke/preMorbidAssessment/getPreMorbidAssessment/"+ $('#careActivityId').val()),
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

	this.save = function() {

		self.admissionDetails().versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
			getUniqueUrl("/stroke/preMorbidAssessment/updatePreMorbidAssessment/" + $('#careActivityId').val()),
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
					$('.message', '#preMorbidAssessmentForm').show();
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
					$('.message', '#preMorbidAssessmentForm').fadeOut(2000);
				},
				async : false
		});
	};
};

preMorbidAssessmentViewModel = new cimss.mvvm.admission_assessment.PreMorbidAssessmentViewModel();

preMorbidAssessmentViewModel.theBarthelScore = ko.dependentObservable(
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
		}, preMorbidAssessmentViewModel);