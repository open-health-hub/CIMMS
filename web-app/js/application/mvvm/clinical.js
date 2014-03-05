var clinicalAssessmentViewModel;

function GlasgowComaScore() {
	this.id = ko.observable();
	this.dateAssessed = ko.observable();
	this.timeAssessed = ko.observable();
	this.motorScore = ko.observable();
	this.eyeScore = ko.observable();
	this.verbalScore = ko.observable();

}


function ClinicalAssessment() {
	this.id = ko.observable();
	this.versions = ko.observable();
	this.facialWeakness = ko.observable();
	this.bestGaze = ko.observable();
	this.facialPalsy = ko.observable();
	this.leftFaceAffected = ko.observable();
	this.rightFaceAffected = ko.observable();
	this.neitherFaceAffected = ko.observable();
	this.dysarthria = ko.observable();
	this.aphasia = ko.observable();
	this.hemianopia = ko.observable();
	this.inattention = ko.observable();
	this.limbAtaxia = ko.observable();
	this.other = ko.observable();
	this.otherText = ko.observable();
	this.classification = ko.observable();
	this.independent = ko.observable();
	this.moodAssessmentPerformed = ko.observable();
	this.moodAssessmentDate = ko.observable();
	this.moodAssessmentTime = ko.observable();
	this.facialPalsy = ko.observable();
	this.armSensoryLoss = ko.observable();
	this.legSensoryLoss = ko.observable();
	this.faceSensoryLoss = ko.observable();
	this.leftArmMrcScale = ko.observable();
	this.rightArmMrcScale = ko.observable();
	this.leftLegMrcScale = ko.observable();
	this.rightLegMrcScale = ko.observable();
	
	
	
	this.sensoryLoss = ko.observable();
	
	
	this.leftSideAffected = ko.observable();
	this.rightSideAffected = ko.observable();
	this.neitherSideAffected = ko.observable();
	
	
	
	this.leftArmAffected = ko.observable();
	this.rightArmAffected = ko.observable();
	this.neitherArmAffected = ko.observable();
	this.leftLegAffected = ko.observable();
	this.rightLegAffected = ko.observable();
	this.neitherLegAffected = ko.observable();
	this.dominantHand = ko.observable();
	this.glasgowComaScore = ko.observable(new GlasgowComaScore());
	this.walkAtPresentation = ko.observable();
	this.mobilePreStroke = ko.observable();
	this.swallowScreenPerformed = ko.observable();
	this.swallowScreenDate = ko.observable();
	this.swallowScreenTime = ko.observable();
	this.noSwallowScreenPerformedReason = ko.observable();
	this.noSwallowScreenPerformedReasonAt4Hours = ko.observable();
	
	this.admissionDate = ko.observable();
	this.admissionTime = ko.observable();
	
	
	this.locStimulation = ko.observable();
	this.locQuestions = ko.observable();
	this.locTasks = ko.observable();

}



function ClinicalAssessmentViewModel() {
	this.clinicalAssessment = ko.observable();
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray();
	this.points = ko.observable(1);

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	var self = this;

	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/clinicalAssessment/getClinicalAssessmentPage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#clinical-section').append(response);
						},
						async : false
					});
			clinicalAssessmentViewModel.tracker = new changeTracker(
					clinicalAssessmentViewModel);
			ko.applyBindings(clinicalAssessmentViewModel, document
					.getElementById("clinicalAssessmentForm"));

			defaultUISetup('#clinicalAssessmentForm');
		}
		self.loaded = true;
	};

	this.reset = function() {
		$('button', '#clinicalAssessmentForm').button("disable");
		$('.containPanel', '#clinicalAssessmentForm').removeClass('changed');
		refreshRadioGroupAsButtons('#clinicalAssessmentForm');
		self.tracker = new changeTracker(self);
	};

	this.updateData = function(self, data) {
		self.clinicalAssessment.id(data.ClinicalAssessment.id);
		self.clinicalAssessment.versions(data.ClinicalAssessment.versions);
		self.clinicalAssessment
				.facialWeakness(data.ClinicalAssessment.facialWeakness);
		self.clinicalAssessment
				.facialWeakness(data.ClinicalAssessment.facialPalsy);
		self.clinicalAssessment
				.leftFaceAffected(data.ClinicalAssessment.leftFaceAffected);
		self.clinicalAssessment
				.rightFaceAffected(data.ClinicalAssessment.rightFaceAffected);
		self.clinicalAssessment
				.neitherFaceAffected(data.ClinicalAssessment.neitherFaceAffected);
		self.clinicalAssessment
				.locStimulation(data.ClinicalAssessment.locStimulation);
		self.clinicalAssessment
				.locQuestions(data.ClinicalAssessment.locQuestions);
		self.clinicalAssessment.locTasks(data.ClinicalAssessment.locTasks);
		self.clinicalAssessment.bestGaze(data.ClinicalAssessment.bestGaze);
		self.clinicalAssessment.dysarthria(data.ClinicalAssessment.dysarthria);
		self.clinicalAssessment.aphasia(data.ClinicalAssessment.aphasia);
		self.clinicalAssessment.hemianopia(data.ClinicalAssessment.hemianopia);
		self.clinicalAssessment
				.inattention(data.ClinicalAssessment.inattention);
		self.clinicalAssessment.limbAtaxia(data.ClinicalAssessment.limbAtaxia);
		self.clinicalAssessment.other(data.ClinicalAssessment.other);
		self.clinicalAssessment.otherText(data.ClinicalAssessment.otherText);
		self.clinicalAssessment
				.classification(data.ClinicalAssessment.classification);
		self.clinicalAssessment
				.independent(data.ClinicalAssessment.independent);
		self.clinicalAssessment
				.moodAssessmentPerformed(data.ClinicalAssessment.moodAssessmentPerformed);
		self.clinicalAssessment
				.moodAssessmentDate(data.ClinicalAssessment.moodAssessmentDate);
		self.clinicalAssessment
				.moodAssessmentTime(data.ClinicalAssessment.moodAssessmentTime);
		self.clinicalAssessment
				.armSensoryLoss(data.ClinicalAssessment.armSensoryLoss);
		self.clinicalAssessment
				.leftArmMrcScale(data.ClinicalAssessment.armMrcScale);
		
		self.clinicalAssessment
				.rightArmMrcScale(data.ClinicalAssessment.armMrcScale);
		
		self.clinicalAssessment
				.leftArmAffected(data.ClinicalAssessment.leftArmAffected);
		self.clinicalAssessment
				.rightArmAffected(data.ClinicalAssessment.rightArmAffected);
		self.clinicalAssessment
				.neitherArmAffected(data.ClinicalAssessment.neitherArmAffected);
		
		
		self.clinicalAssessment
		.neitherSideAffected(data.ClinicalAssessment.neitherSideAffected);
		self.clinicalAssessment
		.leftSideAffected(data.ClinicalAssessment.leftSideAffected);
		self.clinicalAssessment
		.rightSideAffected(data.ClinicalAssessment.rightSideAffected);
		
		self.clinicalAssessment
				.facialPalsy(data.ClinicalAssessment.facialPalsy);
		self.clinicalAssessment
				.faceSensoryLoss(data.ClinicalAssessment.faceSensoryLoss);
		self.clinicalAssessment
				.legSensoryLoss(data.ClinicalAssessment.legSensoryLoss);
		self.clinicalAssessment
				.leftLegMrcScale(data.ClinicalAssessment.legMrcScale);
		
		self.clinicalAssessment
		.rightLegMrcScale(data.ClinicalAssessment.legMrcScale);
		
		
		self.clinicalAssessment
				.leftLegAffected(data.ClinicalAssessment.leftLegAffected);
		self.clinicalAssessment
				.rightLegAffected(data.ClinicalAssessment.rightLegAffected);
		self.clinicalAssessment
				.neitherLegAffected(data.ClinicalAssessment.neitherLegAffected);
		self.clinicalAssessment
				.dominantHand(data.ClinicalAssessment.dominantHand);
		self.clinicalAssessment
				.walkAtPresentation(data.ClinicalAssessment.walkAtPresentation);
		self.clinicalAssessment
				.mobilePreStroke(data.ClinicalAssessment.mobilePreStroke);
		self.clinicalAssessment
				.swallowScreenPerformed(data.ClinicalAssessment.swallowScreenPerformed);
		self.clinicalAssessment
				.swallowScreenDate(data.ClinicalAssessment.swallowScreenDate);
		self.clinicalAssessment
				.swallowScreenTime(data.ClinicalAssessment.swallowScreenTime);
		self.clinicalAssessment
				.noSwallowScreenPerformedReason(data.ClinicalAssessment.noSwallowScreenPerformedReason);
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

	$.ajax({
		url : getUniqueUrl("/stroke/clinicalAssessment/getClinicalAssessment/"+ $('#careActivityId').val()),
		success : function(data) {
			self.clinicalAssessment = new ClinicalAssessment();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		$
				.ajax({
					url : getUniqueUrl("/stroke/clinicalAssessment/getClinicalAssessment/"+ $('#careActivityId').val()),
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
		self.clinicalAssessment.versions().careActivity = dataStatusViewModel
				.dataStatus().versions.careActivity;
		$
				.ajax(
						getUniqueUrl("/stroke/clinicalAssessment/updateClinicalAssessment/"+ $('#careActivityId').val()),
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
								$('.message', '#clinicalAssessmentForm').show();
								if (result.FieldsInError.length === 0) {
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
									self.reset();
									
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity = 
										result.ClinicalAssessment.versions.careActivity;
								}
								$('.message', '#clinicalAssessmentForm')
										.fadeOut(2000);
							},
							async : false
						});
	};
}

clinicalAssessmentViewModel = new ClinicalAssessmentViewModel();


clinicalAssessmentViewModel.theGlasgowScoreTotal = ko.dependentObservable(
		function() {
			return clinicalAssessmentViewModel.clinicalAssessment.glasgowComaScore().motorScore() +
						clinicalAssessmentViewModel.clinicalAssessment.glasgowComaScore().eyeScore() +
							clinicalAssessmentViewModel.clinicalAssessment.glasgowComaScore().verbalScore();
		}, clinicalAssessmentViewModel);
