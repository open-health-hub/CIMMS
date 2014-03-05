var therapySummaryViewModel;

cimss.namespace("mvvm.therapy").TherapySummaryViewModel = function() {
	this.therapySummary = ko.observable();
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
				url : getUniqueUrl("/stroke/therapySummary/getTherapySummaryPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#therapy-summary-section').append(response);
					$(".daysOfTherapy",'#therapy-summary-section').mask("9?99");
					$(".minutesOfTherapy",'#therapy-summary-section').mask("9?9999");
				},
				async : false
			});
			therapySummaryViewModel.tracker = new changeTracker(therapySummaryViewModel);
			ko.applyBindings(therapySummaryViewModel, document
					.getElementById("therapySummaryForm"));
			$("#therapySummaryForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});

			defaultUISetup('#therapySummaryForm');
		}
		self.loaded = true;
	};

	
	
	
	this.updateData = function(self, data) {
		self.therapySummary.id(data.therapySummary.id);
		self.therapySummary.versions(data.therapySummary.versions);
		
		
		var requiredTherapies = self.therapySummary.requiredTherapies();
		requiredTherapies.physiotherapy(data.therapySummary.requiredTherapies.physiotherapy);
		requiredTherapies.occupational(data.therapySummary.requiredTherapies.occupational);
		requiredTherapies.salt(data.therapySummary.requiredTherapies.salt);
		requiredTherapies.psychology(data.therapySummary.requiredTherapies.psychology);
		requiredTherapies.nurse(data.therapySummary.requiredTherapies.nurse);
		self.therapySummary.requiredTherapies(requiredTherapies);
		
		
		var daysOfTherapy = self.therapySummary.daysOfTherapy();
		daysOfTherapy.physiotherapy(data.therapySummary.daysOfTherapy.physiotherapy);
		daysOfTherapy.occupational(data.therapySummary.daysOfTherapy.occupational);
		daysOfTherapy.salt(data.therapySummary.daysOfTherapy.salt);
		daysOfTherapy.psychology(data.therapySummary.daysOfTherapy.psychology);
		daysOfTherapy.nurse(data.therapySummary.daysOfTherapy.nurse);
		self.therapySummary.daysOfTherapy(daysOfTherapy);
		
		var minutesOfTherapy = self.therapySummary.minutesOfTherapy();
		minutesOfTherapy.physiotherapy(data.therapySummary.minutesOfTherapy.physiotherapy);
		minutesOfTherapy.occupational(data.therapySummary.minutesOfTherapy.occupational);
		minutesOfTherapy.salt(data.therapySummary.minutesOfTherapy.salt);
		minutesOfTherapy.psychology(data.therapySummary.minutesOfTherapy.psychology);
		minutesOfTherapy.nurse(data.therapySummary.minutesOfTherapy.nurse);
		self.therapySummary.minutesOfTherapy(minutesOfTherapy);
		
		
	};

	
	this.reset = function() {
		$('button', '#therapySummaryForm').button("disable");
		$('.containPanel', '#therapySummaryForm').removeClass('changed');
		refreshRadioGroupAsButtons('#therapySummaryForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/therapySummary/getTherapySummary/"+ $('#careActivityId').val()),
		success : function(data) {
			self.therapySummary = new cimss.model.therapy_summary.TherapySummary();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/therapySummary/getTherapySummary/"+ $('#careActivityId').val()),
			success : function(result) {
				self.updateData(self, result);
				self.fieldsInError(result.FieldsInError);
				self.errorsAsList(result.ErrorsAsList);
				self.infoMessage("");
				self.reset();

			},
			async : false
		});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;

	};

	this.save = function() {
		self.therapySummary.versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		var request = $
				.ajax(
						getUniqueUrl("/stroke/therapySummary/updateTherapySummary/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								therapySummary : self.therapySummary
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
								$('.message', '#therapySummaryForm').show();
								if (result.FieldsInError.length === 0) {
									self.reset();
									dataStatusViewModel.update();
									
									if ($('#ssnapStatusDialogBox').dialog("isOpen") === true) {
										$('#ssnapStatusDialogBox').dialog('close');
										dataStatusViewModel.ssnapStatusDetails();
									}
									if ($('#ssnap72StatusDialogBox').dialog('isOpen') === true) {
										$('#ssnap72StatusDialogBox').dialog('close');
										dataStatusViewModel.ssnap72StatusDetails();
									}									
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity = result.therapySummary.versions.careActivity;
								}
								$('.message', '#therapySummaryForm').fadeOut(2000);

							},
							async : false
						});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;
	};
};

therapySummaryViewModel = new cimss.mvvm.therapy.TherapySummaryViewModel();
