var speechAndLanguageTherapyViewModel;

cimss.namespace("mvvm.therapy").SpeechAndLanguageTherapyViewModel = function() {
	this.therapyManagement = ko.observable();

	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);
    

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};
	$('.toggleButtonSet').buttonset({
		text: false, icons:{primary: null, secondary: null}
	});
	var self = this;
	var loaded = false;
	var triggeredCount = 0;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/speechAndLanguageTherapy/getSpeechAndLanguageTherapyPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#speech-and-language-therapy-section').append(response);
				},
				async : false
			});
			speechAndLanguageTherapyViewModel.tracker = new changeTracker(speechAndLanguageTherapyViewModel);

		    
			ko.applyBindings(speechAndLanguageTherapyViewModel, document.getElementById("speechAndLanguageTherapyForm"));
			$("#speechAndLanguageTherapyForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});

			defaultUISetup('#speechAndLanguageTherapyForm');			
		}
		if ( getGlobalFlag('saltRefresh')===true ) {
			self.undo();
			setGlobalFlag('saltRefresh', false)
		}
		self.loaded = true;
	};

	
	
	
	this.updateData = function(self, data) {
		setData(self.therapyManagement, data.therapyManagement);
	};
	
	this.reset = function() {
		$('button', '#speechAndLanguageTherapyForm').button("disable");
		$('.containPanel', '#speechAndLanguageTherapyForm').removeClass('changed');
		refreshRadioGroupAsButtons('#speechAndLanguageTherapyForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/speechAndLanguageTherapy/getSpeechAndLanguageTherapy/"+ $('#careActivityId').val()),
		success : function(data) {
			self.therapyManagement = new cimss.model.therapy.TherapyManagement();			
			self.updateData(self, data);
			$('.containPanel', '#speechAndLanguageTherapyForm').removeClass('changed');
			self.tracker = new changeTracker(self);
//			self.infoMessage("");	

		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/speechAndLanguageTherapy/getSpeechAndLanguageTherapy/"+ $('#careActivityId').val()),
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
						getUniqueUrl("/stroke/speechAndLanguageTherapy/updateSpeechAndLanguageTherapy/"+ $('#careActivityId').val()),
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
								$('.message', '#speechAndLanguageTherapyForm').show();
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
								$('.message', '#speechAndLanguageTherapyForm').fadeOut(2000);

							},
							async : false
						});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;
	};
	
	
	self.swallowingOver72Hours = ko.computed(
			function() {	
				return cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
													self.therapyManagement.admissionTime(),
													self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentDate(),
													self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentTime(),
													{days: 3});
			
			}, self);
	
	
	
	self.threeDaysLater = ko.computed(function() {
		return cimss.utils.threeDaysHence(self.therapyManagement.admissionDate(),
		self.therapyManagement.admissionTime());}, self);
	
	self.communicationOver72Hours = ko.computed(
			function() {	
				return cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
													self.therapyManagement.admissionTime(),
													self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentDate(),
													self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentTime(),
													{days: 3});
			
			}, self);
	this.resetCommsDateTime = function() { 
		if (self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs() !== 'true') {
			self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentDate(null);
			self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentTime(null);
		}
	};
	this.checkCommsAsmtTime = function() {
		if (cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
				self.therapyManagement.admissionTime(),
				self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentDate(),
				self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentTime(),
				{days: 3})) {
			alert("The Communications Assessment was not within 72 hours");
			
			$('#commsAsmt72N').click();
			self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs('false');
			
			$('#commsAsmtY').click();
			self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformed('true');			
						
			$('.standardDatePicker').datepicker("hide");
		}
	};
	this.checkCommsAsmtTime2 = function() {
		if (!cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
				self.therapyManagement.admissionTime(),
				self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentDate(),
				self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentTime(),
				{days: 3})) {
			alert("The Communications Assessment WAS within 72 hours");
						
			self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformed('false');
			$('#commsAsmtN').click();
			
			self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs('true');
			$('#commsAsmt72Y').click();
			$('#commsAsmt72Y').button('refresh');
			
			$('.standardDatePicker').datepicker("hide");
		}
	};

	this.toggleCommsAsmtPerformedAffirmation72HrsOn = function() {
		self.therapyManagement.speechAndLanguageTherapyManagement().communicationNoAssessmentReason(null);
//		$('#commsAsmtN').click();
		self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformed(null);			
	};
	this.toggleCommsAsmtPerformedAffirmationOn = function() {
		self.therapyManagement.speechAndLanguageTherapyManagement().communication72HrNoAssessmentReason("");
		$('#commsAsmt72N').click();
		self.therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs('false');			
	};		
	self.resetSwallowingDateTime = function() {
				
		if ( !cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
						self.therapyManagement.admissionTime(),
						self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentDate(),
						self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentTime(),
						{days: 3}  ) ) {
			self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentDate(null);
			self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentTime(null);
		}
	};	
	this.checkSwallowingAsmtTime = function() {
		if (cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
				self.therapyManagement.admissionTime(),
				self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentDate(),
				self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentTime(),
				{days: 3})) {
			
									
			$('#swallowAsmt72N').click();
			self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs('false');
			
			$('#swallowAsmtY').click();
			self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformed('true');			
						
			$('.standardDatePicker').datepicker("hide");
		}
	};
	this.checkSwallowingAsmtTime2 = function() {
		if (!cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
				self.therapyManagement.admissionTime(),
				self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentDate(),
				self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentTime(),
				{days: 3})) {
			
												
			$('#swallowAsmtN').click();
			self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformed('false');
			
			self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs('true');
			$('#swallowAsmt72Y').click();
			$('#swallowAsmt72Y').button('refresh');

			$('.standardDatePicker').datepicker("hide");
		}
	};
	this.toggleSwallowingAsmtPerformedAffirmation72HrsOn = function() {
		self.therapyManagement.speechAndLanguageTherapyManagement().swallowingNoAssessmentReason("");
		$('#swallowAsmtN').click();
		self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformed('false');			
	};
	this.toggleSwallowingAsmtPerformedAffirmationOn = function() {
		self.therapyManagement.speechAndLanguageTherapyManagement().swallowing72HrNoAssessmentReason("");
		$('#swallowAsmt72N').click();
		self.therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs('false');			
	};	
	
	this.noSwallowingReasonOptions = ko.observableArray([
	                                                    new OptionData("unwell","Unwell"),
	                                             	    new OptionData("refused","Patient refused"),
	                                             	    new OptionData("organisational","Organisational reasons"),
	                                             	    new OptionData("unknown","Unknown"),
	                                             	    new OptionData("passedswallowscreen","Patient passed swallow screen")
	                                         	]);
	this.noSwallowingReasonOptions2 = ko.observableArray([
		                                                    new OptionData("unwell","Unwell"),
		                                             	    new OptionData("refused","Patient refused"),
		                                             	    new OptionData("organisational","Organisational reasons"),
		                                             	    new OptionData("unknown","Unknown")		                                             	    
		                                         	]);
	
	this.noCommunicationReasonOptions = ko.observableArray([
	                                         	       new OptionData("unwell","Unwell"),
	                                         	       new OptionData("refused","Patient refused"),
	                                         	       new OptionData("organisational","Organisational reasons"),
	                                         	       new OptionData("unknown","Unknown"),
	                                         	       new OptionData("noproblem","No Relevant Deficit")
	                                         	]);		
};

speechAndLanguageTherapyViewModel = new cimss.mvvm.therapy.SpeechAndLanguageTherapyViewModel();
		
