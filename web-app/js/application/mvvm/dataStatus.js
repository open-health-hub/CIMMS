function CHFTDataStatus() {

	this.mustEvaluationAfter48hrs = ko.observable();
	this.inError = ko.observable();
	this.issues = [];
	this.message = function() {
		if (this.inError()) {
			return "Data for this patient does not meet all CHFT standards";
		} else {
			return "Data for this patient meets all CHFT standards";
		}
	};

}

function ANHSTDataStatus() {

	this.imageAfter24hrs = ko.observable();
	this.inError = ko.observable();
	this.issues = [];
	this.message = function() {
		if (this.inError()) {
			return "Data for this patient does not meet all ANHST standards";
		} else {
			return "Data for this patient meets all ANHST standards";
		}
	};

}

function Versions() {
	this.careActivity = null;
}

function SsnapExtract() {

	this.previousStroke = ko.observable();
	this.inError = ko.observable();
	this.issues = [];
	this.message = function() {
		if (this.inError()) {
			return "Data for this patient does not have all the SSNAP data items";
		} else {
			return "Data for this patient is SSNAP compliant";
		}
	};

	this.getIssuesOfType = function(type) {
		var list = [];

		for (i = 0; i < this.issues.length; i++) {

			if (this.issues[i].section === type) {
				list.push(this.issues[i]);
			}
		}

		return list.reverse();

	};

}

function Ssnap72HrExtract() {

	this.previousStroke = ko.observable();
	this.inError = ko.observable();
	this.issues = [];
	this.message = function() {
		if (this.inError()) {
			return "Data for this patient does not have all the SSNAP-72 data items";
		} else {
			return "Data for this patient is SSNAP-72 compliant";
		}
	};

	this.getIssuesOfType = function(type) {
		var list = [];

		
		for (i = 0; i < this.issues.length; i++) {
			
			if (this.issues[i].section === type) {
				list.push(this.issues[i]);
			}
		}

		return list.reverse();

	};

}
function CimssExtract() {
	this.previousStroke = ko.observable();
	this.strokeBedAvailableAtPresentation = ko.observable();

	this.GCSAtPresentation = ko.observable();
	this.GCSDate = ko.observable();
	this.GCSTime = ko.observable();

	this.sensoryLossInFace = ko.observable();

	this.powerLeg = ko.observable();
	this.powerArm = ko.observable();

	this.armSideAffected = ko.observable();
	this.legSideAffected = ko.observable();

	this.imagingDate = ko.observable();
	this.imagingTime = ko.observable();
	this.typeOfStroke = ko.observable();
	this.thrombolysisGiven = ko.observable();
	this.independentInADLPriorToAdmission = ko.observable();

	this.ableToWalkWithoutAssistanceOnPresentation = ko.observable();

	this.swallowingAssessmentPerformed = ko.observable();

	this.newIncontinence = ko.observable();

	this.inError = ko.observable();
	this.issues = [];
	this.message = function() {
		if (this.inError()) {
			return "Data for this patient does not have all the CIMSS data items";
		} else {
			return "Data for this patient is CIMSS compliant";
		}
	};

	this.getIssuesOfType = function(type) {
		var list = [];

		for (i = 0; i < this.issues.length; i++) {

			if (this.issues[i].section === type) {
				list.push(this.issues[i]);
			}
		}

		return list.reverse();

	};

}

function PatientInfo() {

	this.careStart = ko.observable();
	this.dob  = ko.observable();
	this.onset = ko.observable();
	this.inpatientAtOnset  = ko.observable();
	this.arrival  = ko.observable();

}

function DataStatus() {
	this.versions = new Versions();
	this.chftDataStatus = ko.observable(new CHFTDataStatus());
	this.anhstDataStatus = ko.observable(new ANHSTDataStatus());
	this.cimssExtract = ko.observable(new CimssExtract());
	this.ssnapExtract = ko.observable(new SsnapExtract());
	this.ssnap72HrExtract = ko.observable(new Ssnap72HrExtract());
	this.patientInfo = ko.observable(new PatientInfo());
}

function DataStatusViewModel() {
	this.dataStatus = ko.observable(new DataStatus());
	var self = this;

	self.chftStatusDetails = function() {

		if (self.dataStatus().chftDataStatus().issues.length > 0) {
			$('#chftIssues').show();
			$('#chftIssueList').empty();
			for (i = 0; i < self.dataStatus().chftDataStatus().issues.length; i++) {
				var rendered = Mustache.to_html($('#issueTemplate').html(), {
					issueText : self.dataStatus().chftDataStatus().issues[i]
				});
				$('#chftIssueList').prepend(rendered);
			}
		} else {
			$('#chftIssues').hide();
		}

		if(($('#chftStatusDialogBox').dialog("isOpen") === false)){
			$('#chftStatusDialogBox').dialog('open');
		}else{
			$('#chftStatusDialogBox').dialog('close');
		}
					
	};
	
	self.anhstStatusDetails = function() {

		if (self.dataStatus().anhstDataStatus().issues.length > 0) {
			$('#anhstIssues').show();
			$('#anhstIssueList').empty();
			for (i = 0; i < self.dataStatus().anhstDataStatus().issues.length; i++) {
				var rendered = Mustache.to_html($('#issueTemplate').html(), {
					issueText : self.dataStatus().anhstDataStatus().issues[i]
				});
				$('#anhstIssueList').prepend(rendered);
			}
		} else {
			$('#anhstIssues').hide();
		}

		if(($('#anhstStatusDialogBox').dialog("isOpen") === false)){
			$('#anhstStatusDialogBox').dialog('open');
		}else{
			$('#anhstStatusDialogBox').dialog('close');
		}
					
	};

	

	self.hasDischargeIssues = function() {
		var theIssues = self.dataStatus().cimssExtract().getIssuesOfType("Discharge : Pre discharge");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
					"Discharge : Discharge assessments");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
						"Discharge : On discharge");
			}
		}
		return theIssues.length > 0;

	};

	self.hasSsnap72DischargeIssues = function() {
		var theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
				"Discharge : Pre discharge");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
					"Discharge : Discharge assessments");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
						"Discharge : On discharge");
			}
		}
		return theIssues.length > 0;

	};
	self.hasSsnapDischargeIssues = function() {
		var theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
				"Discharge : Pre discharge");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
					"Discharge : Discharge assessments");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
						"Discharge : On discharge");
			}
		}
		return theIssues.length > 0;

	};

	self.hasSsnapAdmissionIssues = function() {
		var theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Admission Assessment : Nutrition");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Admission Assessment : Continence");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Admission Assessment : Clinical Assessment");
			}
		}
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
					"Admission Assessment : Sensory/Motor Features");
			
		}
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
					"Admission Assessment : Other Features");
			
		}
		
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
					"Admission Assessment : Swallow Screening");
			
		}
		
		return theIssues.length > 0;

	};
	
	self.hasSsnap72AdmissionIssues = function() {
		var theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Admission Assessment : Nutrition");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Admission Assessment : Continence");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Admission Assessment : Clinical Assessment");
			}
		}
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
					"Admission Assessment : Sensory/Motor Features");
			
		}
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
					"Admission Assessment : Other Features");
			
		}
		
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
					"Admission Assessment : Swallow Screening");
			
		}
		
		return theIssues.length > 0;

	};
	
	self.hasTherapyIssues = function() {
		var theIssues = self.dataStatus().cimssExtract().getIssuesOfType("Therapy : Occupational Therapy");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().cimssExtract().getIssuesOfType("Therapy : Speech And Language Therapy");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().cimssExtract().getIssuesOfType("Therapy : Physiotherapy");
				if (theIssues.length === 0) {
					theIssues = self.dataStatus().cimssExtract().getIssuesOfType("Therapy : MDT Goals");
					if (theIssues.length === 0) {
						theIssues = self.dataStatus().cimssExtract().getIssuesOfType("Therapy : Baseline Assessments");
						
					}
				}
			}
		}
		return theIssues.length > 0;

	};
	
	
	self.hasSsnapTherapyIssues = function() {
		var theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Therapy : Occupational Therapy");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Therapy : Speech And Language Therapy");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Therapy : Physiotherapy");
				if (theIssues.length === 0) {
					theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Therapy : MDT Goals");
					if (theIssues.length === 0) {
						theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Therapy : Baseline Assessments");
						if (theIssues.length === 0) {
							theIssues = self.dataStatus().ssnapExtract().getIssuesOfType("Therapy : Therapy Summary");
						}
					}
				}
			}
		}
		return theIssues.length > 0;

	};
	
	self.hasSsnap72TherapyIssues = function() {
		var theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Therapy : Occupational Therapy");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Therapy : Speech And Language Therapy");
			if (theIssues.length === 0) {
				theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Therapy : Physiotherapy");
				if (theIssues.length === 0) {
					theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Therapy : MDT Goals");
					if (theIssues.length === 0) {
						theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Therapy : Baseline Assessments");
						if (theIssues.length === 0) {
							theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType("Therapy : Therapy Summary");
						}
					}
				}
			}
		}
		return theIssues.length > 0;

	};
	
	self.hasOnsetIssues = function() {
		var theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
				"Onset/Admission : Patient History");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
					"Onset/Admission : Stroke Onset");		
		}
		
		return theIssues.length > 0;

	};
	
	self.hasSsnapOnsetIssues = function() {
		var theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
				"Onset/Admission : Patient History");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
					"Onset/Admission : Stroke Onset");
			
		}
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(
					"Onset/Admission : Pre-Morbid Assessments");
			
		}
		return theIssues.length > 0;

	};
	
	self.hasSsnap72OnsetIssues = function() {
		var theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
				"Onset/Admission : Patient History");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
					"Onset/Admission : Stroke Onset");
			
		}
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(
					"Onset/Admission : Pre-Morbid Assessments");
			
		}
		return theIssues.length > 0;

	};
	

	self.hasAdmissionIssues = function() {
		var theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
				"Admission Assessment : Swallow Screening");
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
					"Admission Assessment : Continence");
			
		}
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
					"Admission Assessment : Glasgow Coma Score");
			
		}
		
		if (theIssues.length === 0) {
			theIssues = self.dataStatus().cimssExtract().getIssuesOfType(
					"Admission Assessment : Sensory/Motor Features");
			
		}
		
		return theIssues.length > 0;

	};

	self.processIssueType = function(type, section, list) {
		var theIssues = self.dataStatus().cimssExtract().getIssuesOfType(type);
		if (theIssues.length > 0) {
			$(section).show();
			$(list).empty();
			for (i = 0; i < theIssues.length; i++) {
				var rendered = Mustache.to_html($('#issueTemplate').html(), {
					issueText : theIssues[i].message
				});
				$(list).prepend(rendered);
			}
		} else {
			$(section).hide();
		}
	};

	self.processSsnap72IssueType = function(type, section, list) {
		var theIssues = self.dataStatus().ssnap72HrExtract().getIssuesOfType(type);
		
		if (theIssues.length > 0) {
			$(section).show();
			$(list).empty();
			for (i = 0; i < theIssues.length; i++) {
				var rendered = Mustache.to_html($('#issueTemplate').html(), {
					issueText : theIssues[i].message
				});
				$(list).prepend(rendered);
			}
		} else {
			$(section).hide();
		}
	};
	
	self.processSsnapIssueType = function(type, section, list) {
		var theIssues = self.dataStatus().ssnapExtract().getIssuesOfType(type);
		if (theIssues.length > 0) {
			$(section).show();
			$(list).empty();
			for (i = 0; i < theIssues.length; i++) {
				var rendered = Mustache.to_html($('#issueTemplate').html(), {
					issueText : theIssues[i].message
				});
				$(list).prepend(rendered);
			}
		} else {
			$(section).hide();
		}
	};

	self.setTab = function(tabindex) {
		$('#tabs').tabs('select', tabindex);
	};

	self.cimssStatusDetails = function() {
		
		
		if (this.hasOnsetIssues()) {
			$('#onsetIssues').show();
			this.processIssueType("Onset/Admission : Patient History", '#patientHistoryIssue', '#patientHistoryIssueList');
			this.processIssueType("Onset/Admission : Stroke Onset", '#strokeOnsetIssue', '#strokeOnsetIssueList');
			
		} else {
			$('#onsetIssues').hide();
		}
		
		
		
		
		
		
		
		
		if (this.hasAdmissionIssues()) {
			$('#admissionIssues').show();
			this.processIssueType("Admission Assessment : Glasgow Coma Score", '#gcsIssue','#gcsIssueList');
			this.processIssueType("Admission Assessment : Sensory/Motor Features", '#sensoryFeatureIssue','#sensoryFeatureIssueList');
			this.processIssueType("Admission Assessment : Swallow Screening", '#swallowScreeningIssue','#swallowScreeningIssueList');
			this.processIssueType("Admission Assessment : Continence",'#continenceIssues', '#continenceIssuesList');
		} else {
			$('#admissionIssues').hide();
		}
		
		if (this.hasTherapyIssues()) {
			$('#therapyIssues').show();
			this.processIssueType("Therapy : Occupational Therapy",
					'#occupationalTherapyIssues',
					'#occupationalTherapyIssuesList');
			this.processIssueType("Therapy : Speech And Language Therapy",
					'#speechAndLanguageTherapyIssues',
					'#speechAndLanguageTherapyIssuesList');
			this.processIssueType("Therapy : Physiotherapy",
					'#physiotherapyIssues',
					'#physiotherapyIssuesList');
			this.processIssueType("Therapy : MDT Goals",
					'#nurseLedTherapyIssues',
					'#nurseLedTherapyIssuesList');
			this.processIssueType("Therapy : Baseline Assessments",
					'#baselineAssessmentsIssues',
					'#baselineAssessmentsIssuesList');
			this.processIssueType("Therapy : Therapy Summary",
					'#therapySummaryIssues',
					'#therapySummaryIssuesList');
		} else {
			$('#therapyIssues').hide();
		}
		
		
		
		
		this.processIssueType("Imaging", '#imagingIssues', '#imagingIssueList');
		
		this.processIssueType("Clinical Summary", '#clinicalSummaryIssue', '#clinicalSummaryIssueList');
		
		this.processIssueType("Treatments", '#treatmentIssues',
				'#treatmentIssueList');
		this.processIssueType("Thrombolysis", '#thrombolysisIssues',
				'#thrombolysisIssueList');
		
		
		if (this.hasDischargeIssues()) {
			$('#theDischargeIssues').show();
			this.processIssueType("Discharge : Pre discharge",
					'#preDischargeIssues', '#preDischargeIssueList');
			this.processIssueType("Discharge : Discharge assessments",
					'#dischargeAssessmentIssues',
					'#dischargeAssessmentIssuesList');
			this.processIssueType("Discharge : On discharge",
					'#postDischargeIssues', '#postDischargeIssuesList');
		} else {
			$('#theDischargeIssues').hide();
		}

		if(($('#cimssStatusDialogBox').dialog("isOpen") === false) ){
			$('#cimssStatusDialogBox').dialog('open')	;	
		}else{
			$('#cimssStatusDialogBox').dialog('close');
		}		
	};

	self.ssnapStatusDetails = function() {
		if (this.hasSsnapOnsetIssues()) {
			$('#ssnapOnsetIssues').show();
			this.processSsnapIssueType("Onset/Admission : Patient History", '#ssnapPatientHistoryIssue', '#ssnapPatientHistoryIssueList');
			this.processSsnapIssueType("Onset/Admission : Stroke Onset", '#ssnapStrokeOnsetIssue', '#ssnapStrokeOnsetIssueList');
			this.processSsnapIssueType("Onset/Admission : Pre-Morbid Assessments", '#ssnapPreMorbidAssessmentIssue', '#ssnapPreMorbidAssessmentIssueList');
		
		} else {
			$('#ssnapOnsetIssues').hide();
		}
		
		

		if (this.hasSsnapAdmissionIssues()) {
			$('#ssnapAdmissionIssues').show();
			this.processSsnapIssueType("Admission Assessment : Sensory/Motor Features", '#ssnapSensoryFeatureIssue','#ssnapSensoryFeatureIssueList');
			this.processSsnapIssueType("Admission Assessment : Other Features", '#ssnapOtherFeatureIssue','#ssnapOtherFeatureIssueList');
			this.processSsnapIssueType("Admission Assessment : Swallow Screening", '#ssnapSwallowScreeningIssue','#ssnapSwallowScreeningIssueList');
			
			
			
			this.processSsnapIssueType(
					"Admission Assessment : Clinical Assessment",
					'#ssnapClinicalAssessmentIssues',
					'#ssnapClinicalAssessmentIssuesList');
			this.processSsnapIssueType("Admission Assessment : Continence",
					'#ssnapContinenceManagementIssues',
					'#ssnapContinenceManagementIssuesList');
			this.processSsnapIssueType("Admission Assessment : Nutrition",
					'#ssnapNutritionManagementIssues',
					'#ssnapNutritionManagementIssuesList');
		} else {
			$('#ssnapAdmissionIssues').hide();
		}
		if (this.hasSsnapTherapyIssues()) {
			$('#ssnapTherapyIssues').show();
			this.processSsnapIssueType("Therapy : Occupational Therapy",
					'#ssnapOccupationalTherapyIssues',
					'#ssnapOccupationalTherapyIssuesList');
			this.processSsnapIssueType("Therapy : Speech And Language Therapy",
					'#ssnapSpeechAndLanguageTherapyIssues',
					'#ssnapSpeechAndLanguageTherapyIssuesList');
			this.processSsnapIssueType("Therapy : Physiotherapy",
					'#ssnapPhysiotherapyIssues',
					'#ssnapPhysiotherapyIssuesList');
			this.processSsnapIssueType("Therapy : MDT Goals",
					'#ssnapNurseLedTherapyIssues',
					'#ssnapNurseLedTherapyIssuesList');
			this.processSsnapIssueType("Therapy : Baseline Assessments",
					'#ssnapBaselineAssessmentsIssues',
					'#ssnapBaselineAssessmentsIssuesList');
			this.processSsnapIssueType("Therapy : Therapy Summary",
					'#ssnapTherapySummaryIssues',
					'#ssnapTherapySummaryIssuesList');
		} else {
			$('#ssnapTherapyIssues').hide();
		}
		
		
		
		
		
		
		this.processSsnapIssueType("Imaging", '#ssnapImagingIssues',
				'#ssnapImagingIssueList');
		this.processSsnapIssueType("Thrombolysis", '#ssnapThrombolysisIssues',
				'#ssnapThrombolysisIssueList');

		this.processSsnapIssueType("Clinical summary", '#ssnapClinicalSummaryIssues',
		'#ssnapClinicalSummaryIssuesList');
		
		
		
		if (this.hasSsnapDischargeIssues()) {
			$('#ssnapDischargeIssues').show();
			this.processSsnapIssueType("Discharge : Pre discharge",
					'#ssnapPreDischargeIssues', '#ssnapPreDischargeIssueList');
			this.processSsnapIssueType("Discharge : Discharge assessments",
					'#ssnapDischargeAssessmentIssues',
					'#ssnapDischargeAssessmentIssuesList');
			this.processSsnapIssueType("Discharge : On discharge",
					'#ssnapPostDischargeIssues',
					'#ssnapPostDischargeIssuesList');
		} else {
			$('#ssnapDischargeIssues').hide();
		}

		// this.processSsnapIssueType("Discharge",'#ssnapDischargeIssues',
		// '#ssnapDischargeIssueList' );

		if(($('#ssnapStatusDialogBox').dialog("isOpen") === false)) {
			$('#ssnapStatusDialogBox').dialog('open');
		}else{
			$('#ssnapStatusDialogBox').dialog('close');
		} 

	} ;

	self.ssnap72StatusDetails = function() {
		if (this.hasSsnap72OnsetIssues()) {
			$('#ssnap72OnsetIssues').show();
			this.processSsnap72IssueType("Onset/Admission : Patient History", '#ssnap72PatientHistoryIssue', '#ssnap72PatientHistoryIssueList');
			this.processSsnap72IssueType("Onset/Admission : Stroke Onset", '#ssnap72StrokeOnsetIssue', '#ssnap72StrokeOnsetIssueList');
			this.processSsnap72IssueType("Onset/Admission : Pre-Morbid Assessments", '#ssnap72PreMorbidAssessmentIssue', '#ssnap72PreMorbidAssessmentIssueList');
		
		} else {
			$('#ssnap72OnsetIssues').hide();
		}
		
		

		if (this.hasSsnap72AdmissionIssues()) {
			$('#ssnap72AdmissionIssues').show();
			this.processSsnap72IssueType("Admission Assessment : Sensory/Motor Features", '#ssnap72SensoryFeatureIssue','#ssnap72SensoryFeatureIssueList');
			this.processSsnap72IssueType("Admission Assessment : Other Features", '#ssnap72OtherFeatureIssue','#ssnap72OtherFeatureIssueList');
			this.processSsnap72IssueType("Admission Assessment : Swallow Screening", '#ssnap72SwallowScreeningIssue','#ssnap72SwallowScreeningIssueList');
			
			
			
			this.processSsnap72IssueType(
					"Admission Assessment : Clinical Assessment",
					'#ssnap72ClinicalAssessmentIssues',
					'#ssnap72ClinicalAssessmentIssuesList');
			this.processSsnap72IssueType("Admission Assessment : Continence",
					'#ssnap72ContinenceManagementIssues',
					'#ssnap72ContinenceManagementIssuesList');
			this.processSsnap72IssueType("Admission Assessment : Nutrition",
					'#ssnap72NutritionManagementIssues',
					'#ssnap72NutritionManagementIssuesList');
		} else {
			$('#ssnap72AdmissionIssues').hide();
		}
		if (this.hasSsnap72TherapyIssues()) {
			$('#ssnap72TherapyIssues').show();
			this.processSsnap72IssueType("Therapy : Occupational Therapy",
					'#ssnap72OccupationalTherapyIssues',
					'#ssnap72OccupationalTherapyIssuesList');
			this.processSsnap72IssueType("Therapy : Speech And Language Therapy",
					'#ssnap72SpeechAndLanguageTherapyIssues',
					'#ssnap72SpeechAndLanguageTherapyIssuesList');
			this.processSsnap72IssueType("Therapy : Physiotherapy",
					'#ssnap72PhysiotherapyIssues',
					'#ssnap72PhysiotherapyIssuesList');
			this.processSsnap72IssueType("Therapy : MDT Goals",
					'#ssnap72NurseLedTherapyIssues',
					'#ssnap72NurseLedTherapyIssuesList');
			this.processSsnap72IssueType("Therapy : Baseline Assessments",
					'#ssnap72BaselineAssessmentsIssues',
					'#ssnap72BaselineAssessmentsIssuesList');
			this.processSsnap72IssueType("Therapy : Therapy Summary",
					'#ssnap72TherapySummaryIssues',
					'#ssnap72TherapySummaryIssuesList');
		} else {
			$('#ssnap72TherapyIssues').hide();
		}
		
		
		
		
		
		
		this.processSsnap72IssueType("Imaging", '#ssnap72ImagingIssues',
				'#ssnap72ImagingIssueList');
		this.processSsnap72IssueType("Thrombolysis", '#ssnap72ThrombolysisIssues',
				'#ssnap72ThrombolysisIssueList');

		this.processSsnap72IssueType("Clinical summary", '#ssnap72ClinicalSummaryIssues',
		'#ssnap72ClinicalSummaryIssuesList');
		
		
		
		if (this.hasSsnap72DischargeIssues()) {
			$('#ssnap72DischargeIssues').show();
			this.processSsnap72IssueType("Discharge : Pre discharge",
					'#ssnap72PreDischargeIssues', '#ssnap72PreDischargeIssueList');
			this.processSsnap72IssueType("Discharge : Discharge assessments",
					'#ssnap72DischargeAssessmentIssues',
					'#ssnap72DischargeAssessmentIssuesList');
			this.processSsnap72IssueType("Discharge : On discharge",
					'#ssnap72PostDischargeIssues',
					'#ssnap72PostDischargeIssuesList');
		} else {
			$('#ssnap72DischargeIssues').hide();
		}

		// this.processSsnapIssueType("Discharge",'#ssnap72DischargeIssues',
		// '#ssnap72DischargeIssueList' );

		if(($('#ssnap72StatusDialogBox').dialog("isOpen") === false)) {
			$('#ssnap72StatusDialogBox').dialog('open');
		}else{
			$('#ssnap72StatusDialogBox').dialog('close');
		} 

	} ;

	
	self.loadChftData = function(data) {
		self.dataStatus().chftDataStatus().mustEvaluationAfter48hrs(data.chftDataStatus.lateMustScreen);
		self.dataStatus().chftDataStatus().inError(data.chftDataStatus.inError);
		self.dataStatus().chftDataStatus().issues = [];
		for (i = 0; i < data.chftDataStatus.errorList.length; i++) {
			self.dataStatus().chftDataStatus().issues
					.push(data.chftDataStatus.errorList[i]);
		}
	};
	
	
	self.loadAnhstData = function(data) {
		self.dataStatus().anhstDataStatus().imageAfter24hrs(data.anhstDataStatus.lateImage);
		self.dataStatus().anhstDataStatus().inError(data.anhstDataStatus.inError);
		self.dataStatus().anhstDataStatus().issues = [];
		for (i = 0; i < data.anhstDataStatus.errorList.length; i++) {
			self.dataStatus().anhstDataStatus().issues
					.push(data.anhstDataStatus.errorList[i]);
		}
	};

	self.loadCimssData = function(data) {
		self.dataStatus().cimssExtract().previousStroke(
				data.cimssExtract.previousStroke);
		self.dataStatus().cimssExtract().strokeBedAvailableAtPresentation(
				data.cimssExtract.strokeBedAvailableAtPresentation);

		self.dataStatus().cimssExtract().GCSAtPresentation(
				data.cimssExtract.GCSAtPresentation);
		self.dataStatus().cimssExtract().GCSDate(data.cimssExtract.GCSDate);
		self.dataStatus().cimssExtract().GCSTime(data.cimssExtract.GCSTime);

		self.dataStatus().cimssExtract().sensoryLossInFace(
				data.cimssExtract.sensoryLossInFace);

		self.dataStatus().cimssExtract().powerArm(data.cimssExtract.powerArm);
		self.dataStatus().cimssExtract().powerLeg(data.cimssExtract.powerLeg);
		self.dataStatus().cimssExtract().armSideAffected(
				data.cimssExtract.armSideAffected);
		self.dataStatus().cimssExtract().legSideAffected(
				data.cimssExtract.legSideAffected);

		self
				.dataStatus()
				.cimssExtract()
				.ableToWalkWithoutAssistanceOnPresentation(
						data.cimssExtract.ableToWalkWithoutAssistanceOnPresentation);
		self.dataStatus().cimssExtract().swallowingAssessmentPerformed(
				data.cimssExtract.swallowingAssessmentPerformed);

		self.dataStatus().cimssExtract().typeOfStroke(
				data.cimssExtract.typeOfStroke);
		self.dataStatus().cimssExtract().independentInADLPriorToAdmission(
				data.cimssExtract.independentInADLPriorToAdmission);

		self.dataStatus().cimssExtract().newIncontinence(
				data.cimssExtract.newIncontinence);

		self.dataStatus().cimssExtract().imagingDate(
				data.cimssExtract.imagingDate);
		self.dataStatus().cimssExtract().imagingTime(
				data.cimssExtract.imagingTime);

		self.dataStatus().cimssExtract().inError(data.cimssExtract.inError);
		self.dataStatus().cimssExtract().issues = [];
		for (i = 0; i < data.cimssExtract.errorList.length; i++) {
			self.dataStatus().cimssExtract().issues
					.push(data.cimssExtract.errorList[i]);
		}

	};

	self.loadSsnapData = function(data) {
		self.dataStatus().ssnapExtract().previousStroke(
				data.ssnapExtract.previousStroke);
		self.dataStatus().ssnapExtract().inError(data.ssnapExtract.inError);
		self.dataStatus().ssnapExtract().issues = [];
		for (i = 0; i < data.ssnapExtract.errorList.length; i++) {
			self.dataStatus().ssnapExtract().issues
					.push(data.ssnapExtract.errorList[i]);
		}
	};

	self.loadSsnap72HrData = function(data) {
		self.dataStatus().ssnap72HrExtract().previousStroke(
				data.ssnap72HrExtract.previousStroke);
		self.dataStatus().ssnap72HrExtract().inError(data.ssnap72HrExtract.inError);
		self.dataStatus().ssnap72HrExtract().issues = [];
		for (i = 0; i < data.ssnap72HrExtract.errorList.length; i++) {
			self.dataStatus().ssnap72HrExtract().issues
					.push(data.ssnap72HrExtract.errorList[i]);
		}
	};
	
	self.loadPatientInfoData = function(data) {
		self.dataStatus().patientInfo().onset(data.patientInfo.onset);
		self.dataStatus().patientInfo().careStart(data.patientInfo.careStart);
		self.dataStatus().patientInfo().dob(data.patientInfo.dob);
		
		self.dataStatus().patientInfo().inpatientAtOnset(data.patientInfo.inpatientAtOnset);
		self.dataStatus().patientInfo().arrival(data.patientInfo.arrival);
		
		
	};
	
	self.loadData = function(data) {
		self.dataStatus().versions.careActivity = data.versions.careActivity;
		self.loadChftData(data);
		self.loadAnhstData(data);
		self.loadCimssData(data);
		self.loadSsnapData(data);
		self.loadSsnap72HrData(data);
		self.loadPatientInfoData(data);
	};

	$.ajax({
		url : getUniqueUrl("/stroke/dataStatus/getDataStatus/"+ $('#careActivityId').val()),
		success : function(result) {
			self.loadData(result);
		},
		async : false
	});

	self.chftHasIssues = function() {
		if (self.dataStatus().chftDataStatus().inError() === true) {
			return -1;
		}
		return 1;
	};
	
	self.anhstHasIssues = function() {
		if (self.dataStatus().anhstDataStatus().inError() === true) {
			return -1;
		}
		return 1;
	};
	

	self.cimssHasIssues = function() {

		if (self.dataStatus().cimssExtract().inError() === true) {
			return -1;
		}
		return 1;
	};

	self.ssnapHasIssues = function() {
		if (self.dataStatus().ssnapExtract().inError() === true) {
			return -1;
		}
		return 1;
	};

	self.ssnap72HasIssues = function() {
		if (self.dataStatus().ssnap72HrExtract().inError() === true) {
			return -1;
		}
		return 1;
	};
	
	self.update = function() {
		$.ajax({
			url : getUniqueUrl("/stroke/dataStatus/getDataStatus/"+ $('#careActivityId').val()),
			success : function(result) {
				self.loadData(result);

			},
			async : false
		});

	};
}

var dataStatusViewModel = new DataStatusViewModel();

dataStatusViewModel.ssnapStatus = ko.dependentObservable(function() {
	return this.ssnapHasIssues();
}, dataStatusViewModel);

dataStatusViewModel.ssnap72Status = ko.dependentObservable(function() {
	return this.ssnap72HasIssues();
}, dataStatusViewModel);

dataStatusViewModel.chftStatus = ko.dependentObservable(function() {
	return this.chftHasIssues();
}, dataStatusViewModel);

dataStatusViewModel.anhstStatus = ko.dependentObservable(function() {
	return this.anhstHasIssues();
}, dataStatusViewModel);

dataStatusViewModel.cimssStatus = ko.dependentObservable(function() {
	return this.cimssHasIssues();
}, dataStatusViewModel);

$(".dataStatus").each(function() {
	ko.applyBindings(dataStatusViewModel, this);

});
