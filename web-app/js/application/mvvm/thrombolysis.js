var thrombolysisViewModel;

function Thrombolysis() {
	this.id = ko.observable("");
	this.admissionDate = ko.observable();
	this.admissionTime = ko.observable();
	this.thrombolysisDate = ko.observable("");
	this.thrombolysisTime = ko.observable("");
	this.doorToNeedleTime = ko.observable("");
	this.decisionMakerGrade = ko.observable("");
	this.decisionMakerLocation = ko.observable("");
	this.decisionMakerSpeciality = ko.observable("");
	this.decisionMakerSpecialityOther = ko.observable("");
	this.complications = ko.observable("");
	this.complicationType = ko.observable("");
	this.complicationTypeHaemorrhage = ko.observable("");
	this.complicationTypeBleed = ko.observable("");
	this.complicationTypeOedema = ko.observable("");
	this.complicationTypeOther = ko.observable("");
	this.complicationTypeOtherText = ko.observable();
	this.followUpScan = ko.observable("");
	this.followUpScanDate = ko.observable("");
	this.followUpScanTime = ko.observable("");
	this.nihssScoreAt24Hours = ko.observable("");
	this.nihssScoreAt24HoursUnknown = ko.observable();
}

function NotThrombolysed() {
	this.noThrombolysisNone = ko.observable(false);
	//this.noThrombolysisHaemorhagic = ko.observable(false);
	this.noThrombolysisNotAvailable = ko.observable(false);
	this.noThrombolysisOutsideHours = ko.observable(false);
	this.noThrombolysisScanLate = ko.observable(false);
	this.noThrombolysisTooLate = ko.observable(false);
	this.noThrombolysisComorbidity = ko.observable(false);
	this.noThrombolysisAge = ko.observable(false);
	this.noThrombolysisMedication = ko.observable(false);
	this.noThrombolysisRefused = ko.observable(false);

	this.noThrombolysisSymptomsImproving = ko.observable(false);
	this.noThrombolysisTooMildOrTooSevere = ko.observable(false);
	this.noThrombolysisHaemorrhagicStroke = ko.observable(false);
	this.noThrombolysisOnsetTimeUnknown = ko.observable(false);
	

	this.noThrombolysisOther = ko.observable(false);
	this.noThrombolysisOtherText = ko.observable(false);
}

function ThrombolysisManagement(id, versions, givenThrombolysis,
		reasonsNotThrombolysed, thrombolysis) {
	this.id = ko.observable(id);
	this.versions = ko.observable(versions);
	this.givenThrombolysis = ko.observable(givenThrombolysis);
	this.reasonsNotThrombolysed = ko.observable(reasonsNotThrombolysed);
	this.thrombolysis = ko.observable(thrombolysis);
}

function ThrombolysisViewModel() {
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	this.reset = function() {
		$('button', '#thrombolysisForm').button("disable");
		$('.containPanel', '#thrombolysisForm').removeClass('changed');
		refreshRadioGroupAsButtons('#thrombolysisForm');
		self.tracker = new changeTracker(self);
	};

	this.loadThrombolysis = function(thrombolysis, data) {
		thrombolysis().id(data.id || null);
		thrombolysis().admissionDate(data.admissionDate);
		thrombolysis().admissionTime(data.admissionTime);
		thrombolysis().thrombolysisDate(data.thrombolysisDate || null);
		thrombolysis().thrombolysisTime(data.thrombolysisTime || null);
		thrombolysis().doorToNeedleTime(data.doorToNeedleTime || null);
		thrombolysis().decisionMakerGrade(data.decisionMakerGrade || null);
		thrombolysis()
				.decisionMakerLocation(data.decisionMakerLocation || null);
		thrombolysis().decisionMakerSpeciality(
				data.decisionMakerSpeciality || null);
		thrombolysis().decisionMakerSpecialityOther(
				data.decisionMakerSpecialityOther || null);
		if (data.complications === true || data.complications === "true") {
			thrombolysis().complications("true");
		} else if (data.complications === false || data.complications === "false") {
			thrombolysis().complications("false");
		} else {
			thrombolysis().complications(null);
		}
		
		
		thrombolysis().complicationTypeHaemorrhage(data.complicationTypeHaemorrhage);
		thrombolysis().complicationTypeOther(data.complicationTypeOther);
		thrombolysis().complicationTypeBleed(data.complicationTypeBleed);
		thrombolysis().complicationTypeOedema(data.complicationTypeOedema);
		
		
		
		
		
		thrombolysis().complicationType(data.complicationType || null);
		thrombolysis().complicationTypeOtherText(data.complicationTypeOtherText || null);
		if (data.followUpScan === "true") {
			thrombolysis().followUpScan("true");
		} else if (data.followUpScan === true) {
			thrombolysis().followUpScan("true");
		} else if (data.followUpScan === "false") {
			thrombolysis().followUpScan("false");
		} else if (data.followUpScan === false) {
			thrombolysis().followUpScan("false");
		} else {
			thrombolysis().followUpScan(null);
		}

		thrombolysis().followUpScanDate(data.followUpScanDate || null);
		thrombolysis().followUpScanTime(data.followUpScanTime || null);
		thrombolysis().nihssScoreAt24Hours(data.nihssScoreAt24Hours);
		thrombolysis().nihssScoreAt24HoursUnknown(data.nihssScoreAt24HoursUnknown);
	};

	this.loadReasonsNotThrombolysed = function(reasonsNotThrombolysed, reasons) {
		reasonsNotThrombolysed().noThrombolysisNone(
				reasons.noThrombolysisNone || false);

		reasonsNotThrombolysed().noThrombolysisNotAvailable(
				reasons.noThrombolysisNotAvailable || false);
		reasonsNotThrombolysed().noThrombolysisOutsideHours(
				reasons.noThrombolysisOutsideHours || false);
		reasonsNotThrombolysed().noThrombolysisScanLate(
				reasons.noThrombolysisScanLate || false);
		reasonsNotThrombolysed().noThrombolysisTooLate(
				reasons.noThrombolysisTooLate || false);
		reasonsNotThrombolysed().noThrombolysisComorbidity(
				reasons.noThrombolysisComorbidity || false);
		reasonsNotThrombolysed().noThrombolysisAge(
				reasons.noThrombolysisAge || false);
		reasonsNotThrombolysed().noThrombolysisMedication(
				reasons.noThrombolysisMedication || false);
		reasonsNotThrombolysed().noThrombolysisRefused(
				reasons.noThrombolysisRefused || false);
		reasonsNotThrombolysed().noThrombolysisOther(
				reasons.noThrombolysisOther || false);
		reasonsNotThrombolysed().noThrombolysisOtherText(
				reasons.noThrombolysisOtherText || "");

		reasonsNotThrombolysed().noThrombolysisSymptomsImproving(
				reasons.noThrombolysisSymptomsImproving || false);
		reasonsNotThrombolysed().noThrombolysisTooMildOrTooSevere(
				reasons.noThrombolysisTooMildOrTooSevere || false);
		
		reasonsNotThrombolysed().noThrombolysisHaemorrhagicStroke(
				reasons.noThrombolysisHaemorrhagicStroke || false);
		
		
		reasonsNotThrombolysed().noThrombolysisOnsetTimeUnknown(
				reasons.noThrombolysisOnsetTimeUnknown || false);

	};

	var self = this;
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/thrombolysis/getThrombolysisPage/" +
										$('#careActivityId').val()),
				success : function(response) {
					$('#thrombolysis-tab').append(response);
				},
				async : false
			});
			thrombolysisViewModel.tracker = new changeTracker(
					thrombolysisViewModel);
			ko.applyBindings(thrombolysisViewModel, document
					.getElementById("thrombolysisForm"));
			$("#thrombolysisForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			$("#thrombolysis-tab .dataStatus").each(function() {
				ko.cleanNode(this);
				ko.applyBindings(dataStatusViewModel, this);

			});			
			defaultUISetup('#thrombolysisForm');
		}
		else if ( getGlobalFlag('thrombolysisRefresh')==true ) {
			self.undo();
			setGlobalFlag('thrombolysisRefresh', false);
		}
		self.loaded = true;
	};
	$.ajax({
		url : getUniqueUrl("/stroke/thrombolysis/getThrombolysis/" + $('#careActivityId').val()),
		success : function(data) {
			self.thrombolysisManagement = new ThrombolysisManagement(
					data.CareActivity.id, data.CareActivity.versions,
					data.CareActivity.givenThrombolysis, new NotThrombolysed(),
					new Thrombolysis());
			self.loadThrombolysis(self.thrombolysisManagement.thrombolysis,
					data.CareActivity.thrombolysis);
			self.loadReasonsNotThrombolysed(
					self.thrombolysisManagement.reasonsNotThrombolysed,
					data.CareActivity.reasonsNotThrombolysed);
			
		},
		async : false
	});
	
	

	this.undo = function() {
		$
				.ajax({
					url : getUniqueUrl("/stroke/thrombolysis/getThrombolysis/"+ $('#careActivityId').val()),
					success : function(data) {
						self.thrombolysisManagement
								.givenThrombolysis(data.CareActivity.givenThrombolysis);
						self.thrombolysisManagement
								.versions(data.CareActivity.versions);
						self.loadThrombolysis(
								self.thrombolysisManagement.thrombolysis,
								data.CareActivity.thrombolysis);
						self
								.loadReasonsNotThrombolysed(
										self.thrombolysisManagement.reasonsNotThrombolysed,
										data.CareActivity.reasonsNotThrombolysed);
						self.fieldsInError(data.FieldsInError);
						self.errorsAsList(data.ErrorsAsList);
						self.infoMessage("");
						self.reset();
					},
					async : false
				});
	};

	this.save = function() {
		self.thrombolysisManagement.versions().careActivity = dataStatusViewModel
				.dataStatus().versions.careActivity;
		
				$.ajax({
					url : getUniqueUrl("/stroke/thrombolysis/updateThrombolysis/" + $('#careActivityId').val()),
					data : ko.toJSON({
						ThrombolysisManagement : self.thrombolysisManagement
					}),
					type : "post",
					contentType : "application/json",
					statusCode : {
						500 : function() {
							alert('There was a problem on the server. Please report to your system administrator');
						}
					},
					success : function(data) {
						self.thrombolysisManagement
								.versions(data.CareActivity.versions);
						self.thrombolysisManagement
								.givenThrombolysis(data.CareActivity.givenThrombolysis);
						self.loadThrombolysis(
								self.thrombolysisManagement.thrombolysis,
								data.CareActivity.thrombolysis);
						self
								.loadReasonsNotThrombolysed(
										self.thrombolysisManagement.reasonsNotThrombolysed,
										data.CareActivity.reasonsNotThrombolysed);
						self.fieldsInError(data.FieldsInError);
						self.errorsAsList(data.ErrorsAsList);
						self.infoMessage(data.InfoMessage);
						$('.message', '#thrombolysisForm').show();
						if (data.FieldsInError.length === 0) {
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
							dataStatusViewModel.dataStatus().versions.careActivity = data.CareActivity.versions.careActivity;
							refreshRadioGroupAsButtons('#thrombolysisForm');
						}
						$('.message', '#thrombolysisForm').fadeOut(2000);
					},
					async : false
				});

	};
	
	
	
	self.noBut = ko.computed(function() {
        return !(self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNone()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNotAvailable()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOutsideHours() || self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisScanLate());
				
    }, self);
	
	self.showNone = ko.computed(function() {
        return !(self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNotAvailable()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOutsideHours() || self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisScanLate());
				
    }, self);
	
	self.showNotAvailable = ko.computed(function() {
        return !(self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNone()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOutsideHours() || self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisScanLate());
				
    }, self);
	
	
	self.showOutsideHours = ko.computed(function() {
        return !(self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNone()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNotAvailable()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisScanLate());
				
    }, self);
	
	self.showScanLate = ko.computed(function() {
        return !(self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNone()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNotAvailable()|| self.thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOutsideHours() );
				
    }, self);
	
	self.sevenHoursLater = ko.observable(
			cimss.utils.sevenHoursHence(self.thrombolysisManagement.thrombolysis().admissionDate(),
			self.thrombolysisManagement.thrombolysis().admissionTime()));

}

thrombolysisViewModel = new ThrombolysisViewModel();
