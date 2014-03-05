var imagingViewModel;

cimss.namespace("mvvm.imaging").ImagingViewModel = function() {
	this.imaging = ko.observable();
	this.errorsAsList = ko.observable();
	this.infoMessage = ko.observable("");
	this.fieldsInError = ko.observableArray([ 0 ]);
	
	this.warningMessage = ko.observable("");

	this.fieldInError = function(field) {
		return $.inArray(field, this.fieldsInError()) >= 0;
	};

	var self = this;
	var loaded = false;
	this.load = function() {
		if (!self.loaded) {
			$.ajax({
				url : getUniqueUrl("/stroke/imaging/getImagingPage/"+ $('#careActivityId').val()),
				success : function(response) {
					$('#imaging-tab').append(response);
				},
				async : false
			});
			imagingViewModel.tracker = new changeTracker(imagingViewModel);
			ko.applyBindings(imagingViewModel, document
					.getElementById("imagingForm"));
			$(".tiaWarning").each(function() {
				ko.applyBindings(imagingViewModel, this);				
			});
			$("#imagingForm .extractStatus").each(function() {
				ko.applyBindings(dataStatusViewModel, this);

			});
			$("#imaging-tab .dataStatus").each(function() {
				ko.cleanNode(this);
				ko.applyBindings(dataStatusViewModel, this);

			});

			defaultUISetup('#imagingForm');
		}
		self.loaded = true;
	};

	this.updateData = function(self, data) {
		self.imaging.id(data.Imaging.id);
		self.imaging.versions(data.Imaging.versions);
		self.imaging.scanPostStroke(data.Imaging.scanPostStroke);
		self.imaging.noScanReason(data.Imaging.noScanReason);
		self.updateScan(self.imaging.scan(), data.Imaging.scan);
	};

	this.updateScan = function(scan, data) {
		scan.id(data.id);
		scan.admissionDate(data.admissionDate);
		scan.admissionTime(data.admissionTime);
		scan.requestDate(data.requestDate);
		scan.requestTime(data.requestTime);
		scan.takenDate(data.takenDate);
		scan.takenTime(data.takenTime);
		scan.takenOverLimit(data.takenOverLimit);
		scan.takenOverride(data.takenOverride);
		scan.imageType(data.imageType);
		scan.imageDiagnosisType(data.imageDiagnosisType);
		scan.imageDiagnosisTypeOther(data.imageDiagnosisTypeOther);
		scan.imageDiagnosisCategory(data.imageDiagnosisCategory);
	};

	this.reset = function() {
		$('button', '#imagingForm').button("disable");
		$('.containPanel', '#imagingForm').removeClass('changed');
		refreshRadioGroupAsButtons('#imagingForm');
		self.tracker = new changeTracker(self);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/imaging/getImaging/"+ $('#careActivityId').val()),
		success : function(data) {
			self.imaging = new cimss.model.imaging.Imaging();
			self.updateData(self, data);
			self.infoMessage("");
		},
		async : false
	});

	this.undo = function() {
		var request = $.ajax({
			url : getUniqueUrl("/stroke/imaging/getImaging/"+ $('#careActivityId').val()),
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
	
	self.hasWarnings = ko.computed(
			function() {
				
				var imageTakenDate , admissionDate;
				self.warningMessage("");
				if(self.imaging.scan().admissionTime()){
					if(self.imaging.scan().takenDate()){
						if(self.imaging.scan().takenTime()){
							imageTakenDate = Date.parseExact(self.imaging.scan().takenDate() +  ' ' + self.imaging.scan().takenTime(), "dd/MM/yyyy HH:mm");
							admissionDate = Date.parseExact(self.imaging.scan().admissionDate() +  ' ' + self.imaging.scan().admissionTime(), "dd/MM/yyyy HH:mm");
							
							if(imageTakenDate !== null && admissionDate !==null){
								admissionDate.day();
								if (admissionDate.compareTo(imageTakenDate) === -1) {
									self.warningMessage("The scan was taken more than 24 hours after admission");
									return true;
								} else {
									self.warningMessage("");
									return false;
								}
							}else{
								self.warningMessage("");
								return false;
							}
							
						}
						
						
					}
	
				}else{
					if(self.imaging.scan().takenDate()){
						
							imageTakenDate = Date.parse(self.imaging.scan().takenDate(), "d/M/yyyy");
							admissionDate = Date.parse(self.imaging.scan().admissionDate(), "d/M/yyyy");
							if(imageTakenDate !== null && admissionDate !==null){
								admissionDate.day();
								if (admissionDate.compareTo(imageTakenDate) === -1) {
									self.warningMessage("The scan was taken more than 24 hours after admission");
									return true;
								} else {
									self.warningMessage("");
									return false;
								}
								
							}else{
								self.warningMessage("");
								return false;
							}						
					}
					
				}
				
				return false;
				
			}, self);

	this.save = function() {
		self.imaging.versions().careActivity = dataStatusViewModel.dataStatus().versions.careActivity;
		var request = $
				.ajax(
						getUniqueUrl("/stroke/imaging/updateImaging/"+ $('#careActivityId').val()),
						{
							data : ko.toJSON({
								Imaging : self.imaging
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
								$('.message', '#imagingForm').show();
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
									if ($('#anhstStatusDialogBox').dialog("isOpen") === true) {
										$('#anhstStatusDialogBox').dialog('close');
										dataStatusViewModel.anhstStatusDetails();
									}
									if (self.imaging.scan().imageDiagnosisType() === 'Intercerebral_Haemorrhage') {
										setGlobalFlag('thrombolysisRefresh',true);
									}
								} else {
									dataStatusViewModel.dataStatus().versions.careActivity = result.Imaging.versions.careActivity;
								}
								$('.message', '#imagingForm').fadeOut(2000);

							},
							async : false
						});

		request.onreadystatechange = null;
		request.abort = null;
		request = null;
		
		
		
		
	};
};

imagingViewModel = new cimss.mvvm.imaging.ImagingViewModel();
