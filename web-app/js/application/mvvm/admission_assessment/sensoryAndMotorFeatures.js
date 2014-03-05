var sensoryAndMotorFeatureViewModel;

cimss.namespace("mvvm.admission_assessment").SensoryAndMotorFeatureViewModel = function() {
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
		self.clinicalAssessment.facialWeakness(data.ClinicalAssessment.facialWeakness);
		self.clinicalAssessment.facialWeakness(data.ClinicalAssessment.facialPalsy);
		self.clinicalAssessment.leftFaceAffected(data.ClinicalAssessment.leftFaceAffected);
		self.clinicalAssessment.rightFaceAffected(data.ClinicalAssessment.rightFaceAffected);
		self.clinicalAssessment.neitherFaceAffected(data.ClinicalAssessment.neitherFaceAffected);
		self.clinicalAssessment.locStimulation(data.ClinicalAssessment.locStimulation);
		self.clinicalAssessment.locQuestions(data.ClinicalAssessment.locQuestions);
		self.clinicalAssessment.locTasks(data.ClinicalAssessment.locTasks);
		self.clinicalAssessment.bestGaze(data.ClinicalAssessment.bestGaze);
		self.clinicalAssessment.dysarthria(data.ClinicalAssessment.dysarthria);
		self.clinicalAssessment.aphasia(data.ClinicalAssessment.aphasia);
		self.clinicalAssessment.hemianopia(data.ClinicalAssessment.hemianopia);
		self.clinicalAssessment.inattention(data.ClinicalAssessment.inattention);
		self.clinicalAssessment.limbAtaxia(data.ClinicalAssessment.limbAtaxia);
		self.clinicalAssessment.other(data.ClinicalAssessment.other);
		self.clinicalAssessment.otherText(data.ClinicalAssessment.otherText);
		self.clinicalAssessment.classification(data.ClinicalAssessment.classification);
		self.clinicalAssessment.independent(data.ClinicalAssessment.independent);
		self.clinicalAssessment.moodAssessmentPerformed(data.ClinicalAssessment.moodAssessmentPerformed);
		self.clinicalAssessment.moodAssessmentDate(data.ClinicalAssessment.moodAssessmentDate);
		self.clinicalAssessment.moodAssessmentTime(data.ClinicalAssessment.moodAssessmentTime);
		self.clinicalAssessment.armSensoryLoss(data.ClinicalAssessment.armSensoryLoss);
		self.clinicalAssessment.rightArmMrcScale(data.ClinicalAssessment.rightArmMrcScale);
		self.clinicalAssessment.leftArmMrcScale(data.ClinicalAssessment.leftArmMrcScale);
		self.clinicalAssessment.leftArmAffected(data.ClinicalAssessment.leftArmAffected);
		self.clinicalAssessment.rightArmAffected(data.ClinicalAssessment.rightArmAffected);
		self.clinicalAssessment.neitherArmAffected(data.ClinicalAssessment.neitherArmAffected);
		self.clinicalAssessment.facialPalsy(data.ClinicalAssessment.facialPalsy);
		self.clinicalAssessment.faceSensoryLoss(data.ClinicalAssessment.faceSensoryLoss);
		

		self.clinicalAssessment.sensoryLoss(data.ClinicalAssessment.sensoryLoss);
		
		
		self.clinicalAssessment.leftSideAffected(data.ClinicalAssessment.leftSideAffected);
		self.clinicalAssessment.rightSideAffected(data.ClinicalAssessment.rightSideAffected);
		self.clinicalAssessment.neitherSideAffected(data.ClinicalAssessment.neitherSideAffected);
		
		
		
		self.clinicalAssessment.legSensoryLoss(data.ClinicalAssessment.legSensoryLoss);
		self.clinicalAssessment.rightLegMrcScale(data.ClinicalAssessment.rightLegMrcScale);
		self.clinicalAssessment.leftLegMrcScale(data.ClinicalAssessment.leftLegMrcScale);
		self.clinicalAssessment.leftLegAffected(data.ClinicalAssessment.leftLegAffected);
		self.clinicalAssessment.rightLegAffected(data.ClinicalAssessment.rightLegAffected);
		self.clinicalAssessment.neitherLegAffected(data.ClinicalAssessment.neitherLegAffected);
		self.clinicalAssessment.dominantHand(data.ClinicalAssessment.dominantHand);
		self.clinicalAssessment.walkAtPresentation(data.ClinicalAssessment.walkAtPresentation);
		self.clinicalAssessment.mobilePreStroke(data.ClinicalAssessment.mobilePreStroke);
		self.clinicalAssessment.swallowScreenPerformed(data.ClinicalAssessment.swallowScreenPerformed);
		self.clinicalAssessment.swallowScreenDate(data.ClinicalAssessment.swallowScreenDate);
		self.clinicalAssessment.swallowScreenTime(data.ClinicalAssessment.swallowScreenTime);
		self.clinicalAssessment.noSwallowScreenPerformedReason(data.ClinicalAssessment.noSwallowScreenPerformedReason);
		


	};
	
	

	var self = this;

	
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/sensoryAndMotorFeature/getSensoryAndMotorFeaturePage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#nihss-sensory-section').append(response);
						},
						async : false
					});

			sensoryAndMotorFeatureViewModel.tracker = new changeTracker(
					sensoryAndMotorFeatureViewModel);
			ko.applyBindings(sensoryAndMotorFeatureViewModel, document
					.getElementById("sensorAndMotorFeatureForm"));

			self.loaded = true;
			defaultUISetup('#sensorAndMotorFeatureForm');
			$('.containPanel', '#sensorAndMotorFeatureForm').removeClass('changed');
			$('button', '#sensorAndMotorFeatureForm').button("disable");
		}

	};

	this.reset = function() {
		$('button', '#sensorAndMotorFeatureForm').button("disable");
		$('.containPanel', '#sensorAndMotorFeatureForm').removeClass('changed');
		refreshRadioGroupAsButtons('#sensorAndMotorFeatureForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/sensoryAndMotorFeature/getSensoryAndMotorFeature/"+ $('#careActivityId').val()),
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
			url : getUniqueUrl("/stroke/sensoryAndMotorFeature/getSensoryAndMotorFeature/"+ $('#careActivityId').val()),
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
		getUniqueUrl("/stroke/sensoryAndMotorFeature/updateSensoryAndMotorFeature/" +
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
				$('.message', '#sensorAndMotorFeatureForm').show();
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
				$('.message', '#sensorAndMotorFeatureForm').fadeOut(2000);
			},
			async : false
		});
	};
};

sensoryAndMotorFeatureViewModel = new cimss.mvvm.admission_assessment.SensoryAndMotorFeatureViewModel();

