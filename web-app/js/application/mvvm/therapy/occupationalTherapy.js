var occupationalTherapyViewModel;



cimss.namespace("mvvm.therapy").OccupationalTherapyViewModel = function() {
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
				url : getUniqueUrl("/stroke/occupationalTherapy/getOccupationalTherapyPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#occupational-therapy-section').append(response);
				},
				async : false
			});
			occupationalTherapyViewModel.tracker = new changeTracker(occupationalTherapyViewModel);
			
			ko.applyBindings(occupationalTherapyViewModel, document.getElementById("occupationalTherapyForm"));
			$("#occupationalTherapyForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#occupationalTherapyForm');
		} 
		if ( getGlobalFlag('occupationalTherapyRefresh')===true ) {
			self.undo();
			setGlobalFlag('occupationalTherapyRefresh', false)
		}
		self.loaded = true;
		
	};

	
	
	
	this.updateData = function(self, data) {
		setData(self.therapyManagement, data.therapyManagement);
	};
	
	this.reset = function() {
		$('button', '#occupationalTherapyForm').button("disable");
		$('.containPanel', '#occupationalTherapyForm').removeClass('changed');
		refreshRadioGroupAsButtons('#occupationalTherapyForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/occupationalTherapy/getOccupationalTherapy/"+ $('#careActivityId').val()),
		success : function(data) {
			self.therapyManagement = new cimss.model.therapy.TherapyManagement();			
			self.updateData(self, data);

			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/occupationalTherapy/getOccupationalTherapy/"+ $('#careActivityId').val()),
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
						getUniqueUrl("/stroke/occupationalTherapy/updateOccupationalTherapy/"+ $('#careActivityId').val()),
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
								$('.message', '#occupationalTherapyForm').show();
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
								$('.message', '#occupationalTherapyForm').fadeOut(2000);

							},
							async : false
						});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;
		
		
	};
	
	
	
	
	self.over72Hours = ko.computed(
			function() {	
				return cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
													self.therapyManagement.admissionTime(),
													self.therapyManagement.occupationalTherapyManagement().assessmentDate(),
													self.therapyManagement.occupationalTherapyManagement().assessmentTime(),
													{days: 3});
			
			}, self);
	
	this.noAssessmentReasonOptions = ko.observableArray([
	                                             	    new OptionData("unwell","Unwell"),
	                                             	    new OptionData("refused","Patient refused"),
	                                             	    new OptionData("organisational","Organisational reasons"),
	                                             	    new OptionData("unknown","Unknown"),
	                                             	    new OptionData("noproblem","No Relevant Deficit")
	                                         	]);
	
	
	this.checkAsmtTime = function() {
		if (cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
				self.therapyManagement.admissionTime(),
				self.therapyManagement.occupationalTherapyManagement().assessmentDate(),
				self.therapyManagement.occupationalTherapyManagement().assessmentTime(),
				{days: 3})) {
			alert("The assessment was not within 72 hours");
									
			$('#occTherapyAsmt72N').click();
			self.therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs('false');
			
			$('#occTherapyAsmtY').click();
			self.therapyManagement.occupationalTherapyManagement().assessmentPerformed('true');			
						
			$('.standardDatePicker').datepicker("hide");
		}
	};
	this.checkAsmtTime2 = function() {
		if ( cimss.namespace("utils").checkDateIsValid(self.therapyManagement.occupationalTherapyManagement().assessmentDate()) && !cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
				self.therapyManagement.admissionTime(),
				self.therapyManagement.occupationalTherapyManagement().assessmentDate(),
				self.therapyManagement.occupationalTherapyManagement().assessmentTime(),
				{days: 3})) {
			alert("The assessment WAS within 72 hours");
									
			$('#occTherapyAsmtN').click();
			self.therapyManagement.occupationalTherapyManagement().assessmentPerformed('false');
						
			self.therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs('true');
			$('#occTherapyAsmt72Y').click();
			$('#occTherapyAsmt72Y').button('refresh');
			
			$('.standardDatePicker').datepicker("hide");
		}
	};	
	this.toggleOccTherapyAsmtPerformedAffirmation72HrsOn = function() {
		self.therapyManagement.occupationalTherapyManagement().otTherapyNoAssessmentReason("");
		$('#occTherapyAsmtN').click();
		self.therapyManagement.occupationalTherapyManagement().assessmentPerformed(false);		
	};
	this.toggleOccTherapyAsmtPerformedAffirmationOn = function() {
		self.therapyManagement.occupationalTherapyManagement().otTherapyNo72HrAssessmentReason("");
		$('#occTherapyAsmt72N').click();
		self.therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs('false');			
	};	
	self.threeDaysLater = ko.computed(function() {
			return cimss.utils.threeDaysHence(self.therapyManagement.admissionDate(),
			self.therapyManagement.admissionTime());}, self);
};

occupationalTherapyViewModel = new cimss.mvvm.therapy.OccupationalTherapyViewModel();
