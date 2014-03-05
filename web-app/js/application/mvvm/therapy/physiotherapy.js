var physiotherapyViewModel;

cimss.namespace("mvvm.therapy").PhysiotherapyViewModel = function() {
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
				url : getUniqueUrl("/stroke/physiotherapy/getPhysiotherapyPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#physiotherapy-section').append(response);
				},
				async : false
			});
			physiotherapyViewModel.tracker = new changeTracker(physiotherapyViewModel);
			
			ko.applyBindings(physiotherapyViewModel, document.getElementById("physiotherapyForm"));
			$("#physiotherapyForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#physiotherapyForm');
		}
		if ( getGlobalFlag('physiotherapyRefresh')===true ) {
			self.undo();
			setGlobalFlag('physiotherapyRefresh', false)
		}
		self.loaded = true;
	};

	
	
	
	this.updateData = function(self, data) {
		setData(self.therapyManagement, data.therapyManagement);
	};
	
	this.reset = function() {
		$('button', '#physiotherapyForm').button("disable");
		$('.containPanel', '#physiotherapyForm').removeClass('changed');
		refreshRadioGroupAsButtons('#physiotherapyForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/physiotherapy/getPhysiotherapy/"+ $('#careActivityId').val()),
		success : function(data) {
			self.therapyManagement = new cimss.model.therapy.TherapyManagement();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/physiotherapy/getPhysiotherapy/"+ $('#careActivityId').val()),
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
						getUniqueUrl("/stroke/physiotherapy/updatePhysiotherapy/"+ $('#careActivityId').val()),
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
								$('.message', '#physiotherapyForm').show();
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
								$('.message', '#physiotherapyForm').fadeOut(2000);

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
													self.therapyManagement.physiotherapyManagement().physioAssessmentDate(),
													self.therapyManagement.physiotherapyManagement().physioAssessmentTime(),
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
					self.therapyManagement.physiotherapyManagement().physioAssessmentDate(),
					self.therapyManagement.physiotherapyManagement().physioAssessmentTime(),
					{days: 3})) {
				alert("The assessment was not within 72 hours");
												
				$('#physioTherapyAsmt72N').click();
				self.therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs('false');
				
				$('#physioTherapyAsmtY').click();
				self.therapyManagement.physiotherapyManagement().physioAssessmentPerformed('true');			
							
				$('.standardDatePicker').datepicker("hide");
			}
		};
		this.checkAsmtTime2 = function() {
			if (!cimss.utils.checkDateWithinPeriod(self.therapyManagement.admissionDate(),
					self.therapyManagement.admissionTime(),
					self.therapyManagement.physiotherapyManagement().physioAssessmentDate(),
					self.therapyManagement.physiotherapyManagement().physioAssessmentTime(),
					{days: 3})) {
				alert("The assessment WAS within 72 hours");
																
				self.therapyManagement.physiotherapyManagement().physioAssessmentPerformed('false');			
				$('#physioTherapyAsmtN').click();
				
				self.therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs('true');
				$('#physioTherapyAsmt72Y').click();
				$('#physioTherapyAsmt72Y').button("refresh");
				
				$('.standardDatePicker').datepicker("hide");
			}
		};		
		this.toggleTherapyAsmtPerformedAffirmation72HrsOn = function() {
			self.therapyManagement.physiotherapyManagement().physioNoAssessmentReason("");
			$('#physioTherapyAsmtN').click();
			$('#physioTherapyAsmt72Y').button("refresh");
			self.therapyManagement.physiotherapyManagement().physioAssessmentPerformed('false');			
		};
		this.toggleTherapyAsmtPerformedAffirmationOn = function() {
			self.therapyManagement.physiotherapyManagement().physioNo72HrAssessmentReason("");
			$('#physioTherapyAsmt72N').click();
			self.therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs('false');			
		};	
		self.threeDaysLater = ko.computed(function() {
			return cimss.utils.threeDaysHence(self.therapyManagement.admissionDate(),
			self.therapyManagement.admissionTime());}, self);
};

physiotherapyViewModel = new cimss.mvvm.therapy.PhysiotherapyViewModel();
