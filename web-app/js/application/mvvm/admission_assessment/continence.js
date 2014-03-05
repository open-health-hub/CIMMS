var continenceViewModel;

cimss.namespace("mvvm.admission_assessment").ContinenceViewModel = function() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);
	this.continenceManagement = ko
			.observable(new cimss.model.admission_assessment.ContinenceManagement());
	this.catheterChanged = ko.observable("");

	this.catheterHistoryCache = null;

	this.catheters = 1;

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.categories = [
			new cimss.model.admission_assessment.Category("retention",
					"Retention Of Urine"),
			new cimss.model.admission_assessment.Category("incontinent",
					"Incontinent"),
			new cimss.model.admission_assessment.Category("skin",
					"Critical Skin Care"),
			new cimss.model.admission_assessment.Category("preexist",
					"Pre-existing Catheter"),
			new cimss.model.admission_assessment.Category("fluidbalance",
					"Fluid Balancing Monitoring"),
			new cimss.model.admission_assessment.Category("other", "Other"),
			new cimss.model.admission_assessment.Category("unknown", "Unknown") ];

	this.category = ko.observable("retention");

	this.addCatheter = function() {
		this.continenceManagement.catheterHistory
				.unshift(new cimss.model.admission_assessment.Catheter());
		setRadioGroupAsButtons('#catheterHistory');
	};

	this.addCatheterHistory = function() {
		self.addACatheter(new cimss.model.admission_assessment.Catheter(
				self.catheters));
		self.catheterHasChanged(true);
	};

	this.addACatheter = function(catheter) {
		var rendered = Mustache
				.to_html($('#catheterTemplate').html(), catheter);
		$('#catheters').prepend(rendered);
		$('.reason', '#catheter' + self.catheters).val(catheter.reason);
		setDatePicker('#catheter' + self.catheters);
	
		setTimeEntry('#catheter' + self.catheters);
		$('button', '#catheter' + self.catheters).button();
		$('button', '#catheter' + self.catheters).click(function(event) {			
			continenceViewModel.deleteCatheter(event);
		});

		$(':text', '#catheter' + self.catheters).each(function(index) {
			$(this).change(function(event) {				
				continenceViewModel.catheterHasChanged(event);
			});
		});

		$('.reason', '#catheter' + self.catheters).each(function(index) {
			if ($("option:selected", this).text() === "Clinical Reason") {
				$('.clinicalOther', $(this).closest('.catheter')).show();
			} else {
				$('.clinicalOther', $(this).closest('.catheter')).hide();
			}
			$(this).change(function(event) {
				continenceViewModel.catheterHasChanged(event);
				if ($("option:selected", this).text() === "Clinical Reason") {
					$('.clinicalOther', $(this).closest('.catheter')).show();
				} else {
					$('.clinicalOther', $(this).closest('.catheter')).hide();
				}
			});

		});

		self.catheters = self.catheters + 1;
	};

	this.catheterHasChanged = function(event) {		
		self.catheterChanged(true);
	};

	this.deleteCatheter = function(event) {
		var values = [];
		var wrapper = $(event.target).closest('.catheter');

		$(':text', wrapper).each(function(index) {
			values[index] = $(this).val();
		});
		var catheter = new cimss.model.admission_assessment.Catheter(0);
		catheter.id = values[0];
		catheter.insertDate = values[1];
		catheter.insertTime = values[2];
		catheter.removalDate = values[3];
		catheter.removalTime = values[4];
		catheter._destroy = true;
		catheter.reason = $('.reason', wrapper).val();
		self.continenceManagement.catheterHistory.push(catheter);

		wrapper.hide();
	};

	this.removeLine = function(line) {
		this.continenceManagement.catheterHistory.destroy(line);
	};

	this.updateData = function(self, data) {
		//this.catheters = 1;

		$('.catheter').hide();

		self.continenceManagement.id(data.ContinenceManagement.id);
		self.continenceManagement.versions(data.ContinenceManagement.versions);
		self.continenceManagement
				.newlyIncontinent(data.ContinenceManagement.newlyIncontinent);
		self.continenceManagement
				.hasContinencePlan(data.ContinenceManagement.hasContinencePlan);
		self.continenceManagement
				.inappropriateForContinencePlan(data.ContinenceManagement.inappropriateForContinencePlan);
		self.continenceManagement
				.continencePlanDate(data.ContinenceManagement.continencePlanDate);
		self.continenceManagement
				.continencePlanTime(data.ContinenceManagement.continencePlanTime);
		self.continenceManagement
				.noContinencePlanReason(data.ContinenceManagement.noContinencePlanReason);
		self.continenceManagement
				.noContinencePlanReasonOther(data.ContinenceManagement.noContinencePlanReasonOther);
		self.continenceManagement
				.priorCatheter(data.ContinenceManagement.priorCatheter);
		self.continenceManagement
				.catheterisedSinceAdmission(data.ContinenceManagement.catheterisedSinceAdmission);

		self.continenceManagement.catheterHistory([]);
		if (self.loaded) {
			this.loadCatheterHistory(data);
			this.catheterHistoryCache = null;
		} else {
			this.catheterHistoryCache = data;
		}
	};

	this.loadCatheterHistory = function(data) {
		if (data.ContinenceManagement.catheterHistory.length > 0) {
			for (i = 0; i < data.ContinenceManagement.catheterHistory.length; i++) {
				var catheter = new cimss.model.admission_assessment.Catheter(
						self.catheters);
				catheter.id = data.ContinenceManagement.catheterHistory[i].id;
				catheter.insertDate = data.ContinenceManagement.catheterHistory[i].insertDate;
				catheter.insertTime = data.ContinenceManagement.catheterHistory[i].insertTime;
				catheter.removalDate = data.ContinenceManagement.catheterHistory[i].removalDate;
				catheter.removalTime = data.ContinenceManagement.catheterHistory[i].removalTime;
				catheter.reason = data.ContinenceManagement.catheterHistory[i].reason;
				catheter.reasonOther = data.ContinenceManagement.catheterHistory[i].reasonOther;
				if (data.ContinenceManagement.catheterHistory[i]._destroy === true) {
					catheter._destroy = true;
					self.continenceManagement.catheterHistory.push(catheter);
				} else {
					self.addACatheter(catheter);
				}
			}
		}

	};

	var self = this;
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$
					.ajax({
						url : getUniqueUrl("/stroke/continenceManagement/getContinenceManagementPage/" + $('#careActivityId').val()),
						success : function(response) {
							$('#continence-section').append(response);
						},
						async : false
					});
			this.loadCatheterHistory(this.catheterHistoryCache);
			continenceViewModel.tracker = new changeTracker(continenceViewModel);
			ko.applyBindings(continenceViewModel, document
					.getElementById("continenceForm"));

			defaultUISetup('#continenceForm');
		}
		self.loaded = true;
	};

	this.reset = function() {
		$('button', '#continenceForm').button("disable");
		$('button', '#catheterHistory').button("enable");
		$(':checked', '#continenceForm').button("refresh");
		refreshRadioGroupAsButtons('#continenceForm');
		setCheckboxesAsButtons('#continenceForm');
		self.catheterChanged("");
		$('.containPanel', '#continenceForm').removeClass('changed');

		self.tracker = new changeTracker(self);
		$('button', '#continenceForm').button("disable");
		$('button', '#catheterHistory').button("enable");
	};

	$
			.ajax({
				url : getUniqueUrl("/stroke/continenceManagement/getContinenceManagement/"+ $('#careActivityId').val()),
				success : function(data) {
					self.continenceManagement = new cimss.model.admission_assessment.ContinenceManagement();
					self.updateData(self, data);
					setRadioGroupAsButtons('#catheterHistory');
					self.infoMessage("");
					$('button', '#catheterHistory').button("enable");
				},
				async : false
			});

	this.undo = function() {
		$
				.ajax({
					url : getUniqueUrl("/stroke/continenceManagement/getContinenceManagement/"+ $('#careActivityId').val()),
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
		var values = [];

		$('.catheter:visible').each(function(index, domEle) {
			$(':text', domEle).each(function(index) {
				//alert(index + ': ' + $(this).text())
				values[index] = $(this).val();
			});
			var catheter = new cimss.model.admission_assessment.Catheter(0);
			catheter.id = values[0];
			catheter.insertDate = values[1];
			catheter.insertTime = values[2];
			catheter.removalDate = values[3];
			catheter.removalTime = values[4];
			catheter.reason = $('.reason', domEle).val();
			catheter.reasonOther = values[5];
			self.continenceManagement.catheterHistory.unshift(catheter);
		});

		self.continenceManagement.versions().careActivity = dataStatusViewModel
				.dataStatus().versions.careActivity;
		$
				.ajax(
						getUniqueUrl("/stroke/continenceManagement/updateContinenceManagement/" + $('#careActivityId').val()),
						{
							data : ko
									.toJSON({
										ContinenceManagement : self.continenceManagement
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
								$('.message', '#continenceForm').show();
								if (result.ErrorsAsList.length === 0) {
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
									dataStatusViewModel.dataStatus().versions.careActivity = result.ContinenceManagement.versions.careActivity;
								}
								$('.message', '#continenceForm').fadeOut(2000);
							},
							async : false
						});

	};
};


continenceViewModel = new cimss.mvvm.admission_assessment.ContinenceViewModel();

continenceViewModel.totalCatheters = ko.dependentObservable(function() {
	return ko.utils.arrayFilter(continenceViewModel.continenceManagement
			.catheterHistory(), function(catheter) {
		return !catheter._destroy;
	});
}, continenceViewModel);
