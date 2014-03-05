var swallowScreeningViewModel;

cimss.namespace("utils").sevenHoursHence = function(startDate, startTime) {
	var theStart;
	var threeDaysLater;
	var ms;
	
	theStart = Date.parseExact(startDate +  ' ' + startTime, "dd/MM/yyyy HH:mm");
	
	if ( theStart == null ) {
		theStart = new Date();
	}
	ms = theStart.getTime() + (7 * 3600000) ;
	
	threeDaysLater = new Date(ms);
	
	return threeDaysLater.toString('ddd, dd/MM/yyyy HH:mm');
};


cimss.namespace("utils").threeDaysHence = function(startDate, startTime) {
	var theStart;
	var threeDaysLater;
	var ms;
	
	theStart = Date.parseExact(startDate +  ' ' + startTime, "dd/MM/yyyy HH:mm");
	
	if ( theStart == null ) {
		theStart = new Date();
	}
	ms = theStart.getTime() + (3 * 86400000) ;
	
	threeDaysLater = new Date(ms);
	
	return threeDaysLater.toString('ddd, dd/MM/yyyy HH:mm');
};

cimss.namespace("utils").checkDateIsValid = function(myDate) {
	return ( myDate != null && Date.parseExact(myDate, "dd/MM/yyyy") != null);
};

cimss.namespace("utils").checkDateWithinPeriod = function(startDate, startTime, endDate, endTime, period){
			
	if(!endTime){ endTime = "00:00"; }
	if(!endDate){ endDate = startDate; }
	
	var theStart, theEnd;
	if( startTime && startTime!==""){
		if(endDate){
			
			if(endTime && endTime !==""){
				theEnd = Date.parseExact(endDate +  ' ' + endTime, "dd/MM/yyyy HH:mm");
				theStart = Date.parseExact(startDate +  ' ' + startTime, "dd/MM/yyyy HH:mm");
				
				if(theStart !== null && theEnd !==null){
				
					theStart.add( period); 
					if (theStart.compareTo(theEnd) === -1) {
						return true;
					} else {
						return false;
					}
				}else{
					return false;
				}
			}	
		}
	}else{
		if(endDate){			
				theEnd = Date.parseExact(endDate, "d/M/yyyy");
				theStart = Date.parseExact(startDate, "d/M/yyyy");
				
				if(theEnd !== null && theStart !==null){
					theStart = theStart.add( period); 
					if (theStart.compareTo(theEnd) === -1) {
						return true;
					} else {	
						return false;
					}
					
				}else{	
					return false;
				}						
		}
		
	}
	
	return false;
	
	
	
};



cimss.namespace("mvvm.admission_assessment").SwallowScreeningViewModel = function() {
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
		self.clinicalAssessment.leftArmAffected(data.ClinicalAssessment.leftArmAffected);
		self.clinicalAssessment.rightArmAffected(data.ClinicalAssessment.rightArmAffected);
		self.clinicalAssessment.neitherArmAffected(data.ClinicalAssessment.neitherArmAffected);
		self.clinicalAssessment.facialPalsy(data.ClinicalAssessment.facialPalsy);
		self.clinicalAssessment.faceSensoryLoss(data.ClinicalAssessment.faceSensoryLoss);
		self.clinicalAssessment.legSensoryLoss(data.ClinicalAssessment.legSensoryLoss);
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
		self.clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours(data.ClinicalAssessment.noSwallowScreenPerformedReasonAt4Hours);
		self.clinicalAssessment.admissionDate(data.ClinicalAssessment.admissionDate);
		self.clinicalAssessment.admissionTime(data.ClinicalAssessment.admissionTime);

	};
	
	

	var self = this;

	
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/swallowScreening/getSwallowScreeningPage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#swallow-screen-section').append(response);
						},
						async : false
					});

			swallowScreeningViewModel.tracker = new changeTracker(
					swallowScreeningViewModel);
			ko.applyBindings(swallowScreeningViewModel, document
					.getElementById("swallowScreeningForm"));

			self.loaded = true;
			defaultUISetup('#swallowScreeningForm');
			$('.containPanel', '#swallowScreeningForm').removeClass('changed');
			$('button', '#swallowScreeningForm').button("disable");
		}

	};

	this.reset = function() {
		$('button', '#swallowScreeningForm').button("disable");
		$('.containPanel', '#swallowScreeningForm').removeClass('changed');
		refreshRadioGroupAsButtons('#swallowScreeningForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/swallowScreening/getSwallowScreening/"+ $('#careActivityId').val()),
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
			url : getUniqueUrl("/stroke/swallowScreening/getSwallowScreening/"+ $('#careActivityId').val()),
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
		getUniqueUrl("/stroke/swallowScreening/updateSwallowScreening/" +
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
				$('.message', '#swallowScreeningForm').show();
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
				$('.message', '#swallowScreeningForm').fadeOut(2000);
			},
			async : false
		});
	};
	
	self.over72Hours = ko.computed(
			function() {	
				return cimss.utils.checkDateWithinPeriod(self.clinicalAssessment.admissionDate(),
													self.clinicalAssessment.admissionTime(),
													self.clinicalAssessment.swallowScreenDate(),
													self.clinicalAssessment.swallowScreenTime(),
													{days: 3});
			
			}, self);
	
	self.over4Hours = ko.computed(
			function() {	
				return cimss.utils.checkDateWithinPeriod(self.clinicalAssessment.admissionDate(),
													self.clinicalAssessment.admissionTime(),
													self.clinicalAssessment.swallowScreenDate(),
													self.clinicalAssessment.swallowScreenTime(),
													{hours: 4});
			
			}, self);
};

swallowScreeningViewModel = new cimss.mvvm.admission_assessment.SwallowScreeningViewModel();

