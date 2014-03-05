var dischargeAssessmentViewModel;

cimss.namespace("mvvm.discharge").DischargeAssessmentViewModel = function() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);

	this.dischargeAssessment = ko.observable();

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	var self = this;
	var loaded = false;

	this.reset = function() {
		$('button', '#dischargeAssessmentForm').button("disable");
		$('.containPanel', '#dischargeAssessmentForm').removeClass('changed');
		refreshRadioGroupAsButtons('#dischargeAssessmentForm');
		self.tracker = new changeTracker(self);
	};

	this.editBarthel = function() {
		setRadioGroupAsButtons("#DischargeBarthelData");
		if(($("#DischargeBarthelData").dialog("isOpen") === false)){
			$("#DischargeBarthelData").dialog("open");
		} else {
			$("#DischargeBarthelData").dialog("close");
		}
		refreshRadioGroupAsButtons("#DischargeBarthelData");
	};

	this.editModifiedRankin = function() {
		setRadioGroupAsButtons("#dischargeModifiedRankinData");
		if(($("#dischargeModifiedRankinData").dialog("isOpen") === false)){
			$("#dischargeModifiedRankinData").dialog("open");
		}else {
			$("#dischargeModifiedRankinData").dialog("close");
		} 
		refreshRadioGroupAsButtons("#dischargeModifiedRankinData");
	};

	this.updateBarthelTotal = function() {
		$('#DischargeBarthelData').dialog("close");
	};

	this.cancelBarthel = function() {
		self.dischargeAssessment.assessments().barthel().clearDetail();
		$('#DischargeBarthelData').dialog("close");
	};

	this.updateData = function(self, data) {
		setData(self.dischargeAssessment, data.dischargeAssessment);
	};

	this.load = function() {
		if (!self.loaded) {
			$.ajax({
						url : getUniqueUrl("/stroke/dischargeAssessment/getDischargeAssessmentPage/"+ $('#careActivityId').val()),
						success : function(response) {
							$('#discharge-assessments-section')
									.append(response);
						},
						async : false
					});
			//this.checkDischargeInfo();
			dischargeAssessmentViewModel.tracker = new changeTracker(
					dischargeAssessmentViewModel);
			ko.applyBindings(dischargeAssessmentViewModel, document
					.getElementById("dischargeAssessmentForm"));
			$("#dischargeForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#dischargeAssessmentForm');
		} else if ( getGlobalFlag('predischargeRefresh')==true ) {
			self.undo();			
		}
		self.loaded = true;
	};

	$.ajax({
				url : getUniqueUrl("/stroke/dischargeAssessment/getDischargeAssessment/"+ $('#careActivityId').val()),
				success : function(data) {

					self.dischargeAssessment = new cimss.model.discharge.DischargeAssessment();

					self.updateData(self, data);
					self.infoMessage("");
				},
				async : false
			});

	this.undo = function() {
		$.ajax({
					url : getUniqueUrl("/stroke/dischargeAssessment/getDischargeAssessment/"+ $('#careActivityId').val()),
					success : function(result) {
						self.updateData(self, result);
						self.dischargeAssessment.dischargedTo.valueHasMutated();
						self.fieldsInError(result.FieldsInError);
						self.errorsAsList(result.ErrorsAsList);
						self.infoMessage("");
						self.reset();
					},
					async : false
				});
	};

	this.save = function() {
		self.dischargeAssessment.versions.careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		$.ajax(
						getUniqueUrl("/stroke/dischargeAssessment/updateDischargeAssessment/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								DischargeAssessment : self.dischargeAssessment
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
								$('.message', '#dischargeAssessmentForm')
										.show();
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
									dataStatusViewModel.dataStatus().versions.careActivity = result.therapyManagement.versions.careActivity;
								}
								$('.message', '#dischargeAssessmentForm')
										.fadeOut(2000);
							},
							async : false
						});
	};

};

dischargeAssessmentViewModel = new cimss.mvvm.discharge.DischargeAssessmentViewModel();

dischargeAssessmentViewModel.theBarthelScore = ko.dependentObservable(
		function() {
			if (this.dischargeAssessment.assessments().barthel().hasDetail()) {
				if (this.dischargeAssessment.assessments().barthel()
						.isComplete()) {
					return this.dischargeAssessment.assessments().barthel()
							.detailScore();
				} else {
					return this.dischargeAssessment.assessments().barthel()
							.detailScore() + " (incomplete)";
				}
			} else {
				return this.dischargeAssessment.assessments().barthel()
						.manualTotal();
			}
		}, dischargeAssessmentViewModel);
