var glasgowComaScoreViewModel;

cimss.namespace("mvvm.admission_assessment").GlasgowComaScoreViewModel = function() {
	this.clinicalAssessment = ko.observable();
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([]);

	
	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.updateData = function(self, data) {
		self.clinicalAssessment.id(data.ClinicalAssessment.id);
		self.clinicalAssessment.versions(data.ClinicalAssessment.versions);
		self.updateGlasgowComaScore(self.clinicalAssessment.glasgowComaScore(),
				data.ClinicalAssessment.glasgowComaScore);

	};
	
	this.updateGlasgowComaScore = function(glasgowComaScore, data) {
		glasgowComaScore.id(data.id);
		glasgowComaScore.dateAssessed(data.dateAssessed);
		glasgowComaScore.timeAssessed(data.timeAssessed);
		glasgowComaScore.motorScore(data.motorScore);
		glasgowComaScore.eyeScore(data.eyeScore);
		glasgowComaScore.verbalScore(data.verbalScore);
	};

	var self = this;

	
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/glasgowComaScore/getGlasgowComaScorePage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#gcs-section').append(response);
						},
						async : false
					});

			glasgowComaScoreViewModel.tracker = new changeTracker(
					glasgowComaScoreViewModel);
			ko.applyBindings(glasgowComaScoreViewModel, document
					.getElementById("glasgowComaScoreForm"));

			self.loaded = true;
			defaultUISetup('#glasgowComaScoreForm');
			$('.containPanel', '#glasgowComaScoreForm').removeClass('changed');
			$('button', '#glasgowComaScoreForm').button("disable");
		}

	};

	this.reset = function() {
		$('button', '#glasgowComaScoreForm').button("disable");
		$('.containPanel', '#glasgowComaScoreForm').removeClass('changed');
		refreshRadioGroupAsButtons('#glasgowComaScoreForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/glasgowComaScore/getGlasgowComaScore/"+ $('#careActivityId').val()),
		success : function(data) {
			self.clinicalAssessment = new ClinicalAssessment();
			self.updateData(self, data);
			self.errorsAsList("<div class='errors'  style='display:none'><ul id='messageBox'></ul></div>");
			self.infoMessage("");
		},
		async : false
	});

	
	this.undo = function() {

		$.ajax({
			url : getUniqueUrl("/stroke/glasgowComaScore/getGlasgowComaScore/"+ $('#careActivityId').val()),
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

		self.clinicalAssessment.versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
		getUniqueUrl("/stroke/glasgowComaScore/updateGlasgowComaScore/" +
					$('#careActivityId').val()),
		{
			data : ko.toJSON({
				ClinicalAssessment : self.clinicalAssessment
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
					dataStatusViewModel.dataStatus().versions.careActivity = 
						result.ClinicalAssessment.versions.careActivity;
				}
				$('.message', '#glasgowComaScoreForm').fadeOut(2000);
			},
			async : false
		});
	};
};

glasgowComaScoreViewModel = new cimss.mvvm.admission_assessment.GlasgowComaScoreViewModel();

glasgowComaScoreViewModel.theGlasgowScoreTotal = ko.dependentObservable(
		function() {
			return 0 + (glasgowComaScoreViewModel.clinicalAssessment.glasgowComaScore().motorScore()?parseInt(glasgowComaScoreViewModel.clinicalAssessment.glasgowComaScore().motorScore()):0) +
				(glasgowComaScoreViewModel.clinicalAssessment.glasgowComaScore().eyeScore()?parseInt(glasgowComaScoreViewModel.clinicalAssessment.glasgowComaScore().eyeScore()):0) +
				(glasgowComaScoreViewModel.clinicalAssessment.glasgowComaScore().verbalScore()?parseInt(glasgowComaScoreViewModel.clinicalAssessment.glasgowComaScore().verbalScore()):0);
		}, glasgowComaScoreViewModel);

