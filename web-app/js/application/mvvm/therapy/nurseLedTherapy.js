var nurseLedTherapyViewModel;



cimss.namespace("mvvm.therapy").NurseLedTherapyViewModel = function() {
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
				url : getUniqueUrl("/stroke/nurseLedTherapy/getNurseLedTherapyPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#nurse-led-therapy-section').append(response);
				},
				async : false
			});
			nurseLedTherapyViewModel.tracker = new changeTracker(nurseLedTherapyViewModel);
			
			ko.applyBindings(nurseLedTherapyViewModel, document.getElementById("nurseLedTherapyForm"));
			$("#nurseLedTherapyForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			defaultUISetup('#nurseLedTherapyForm');
		}
		self.loaded = true;
	};

	
	
	
	this.updateData = function(self, data) {
		setData(self.therapyManagement, data.therapyManagement);
	};
	
	this.reset = function() {
		$('button', '#nurseLedTherapyForm').button("disable");
		$('.containPanel', '#nurseLedTherapyForm').removeClass('changed');
		refreshRadioGroupAsButtons('#nurseLedTherapyForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/nurseLedTherapy/getNurseLedTherapy/"+ $('#careActivityId').val()),
		success : function(data) {
			self.therapyManagement = new cimss.model.therapy.TherapyManagement();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/nurseLedTherapy/getNurseLedTherapy/"+ $('#careActivityId').val()),
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
						getUniqueUrl("/stroke/nurseLedTherapy/updateNurseLedTherapy/"+ $('#careActivityId').val()),
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
								$('.message', '#nurseLedTherapyForm').show();
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
								$('.message', '#nurseLedTherapyForm').fadeOut(2000);

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
													self.therapyManagement.rehabGoalsSetDate(),
													self.therapyManagement.rehabGoalsSetTime(),
													{days: 3});
			
			}, self);
};

nurseLedTherapyViewModel = new cimss.mvvm.therapy.NurseLedTherapyViewModel();
