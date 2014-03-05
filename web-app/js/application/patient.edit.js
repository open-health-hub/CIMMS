// sets up the page structure with tabs 
// calls the ui setup script
// sets the scroller function


function onPage() {
	$(function() {
		defaultUISetup('#admissionForm');

		if ($('#strokePatient').val() == '1') {
			$(".tiaWarning").hide();
		}
		$("#helpAboutDialog").dialog({
			autoOpen : false,
			height : 350,
			width : 550,
			modal : true
		});

		$("#helpAbout").click(function() {
			if ($("#helpAboutDialog").dialog("isOpen") === false) {
				$("#helpAboutDialog").dialog("open");
			} else {
				$("#helpAboutDialog").dialog("close");
			}
			// ($("#helpAboutDialog").dialog("isOpen") === false) ?
			// $("#helpAboutDialog").dialog("open") :
			// $("#helpAboutDialog").dialog("close");
		});

		$('#chftStatusDialogBox').dialog({
			autoOpen : false,
			height : 350,
			width : 550,
			modal : false
		});
		
		$('#anhstStatusDialogBox').dialog({
			autoOpen : false,
			height : 350,
			width : 550,
			modal : false
		});

		$("#cimssStatusDialogBox").dialog({
			autoOpen : false,
			height : 350,
			width : 550,
			modal : false
		});

		$("#ssnapStatusDialogBox").dialog({
			autoOpen : false,
			height : 350,
			width : 550,
			modal : false
		});
		$("#ssnap72StatusDialogBox").dialog({
			autoOpen : false,
			height : 350,
			width : 550,
			modal : false
		});
		
		$("#exportData").click(
				function() {
					$.ajax({
						url : getUniqueUrl("/stroke/patient/export/" + $('#careActivityId').val()),
						success : function(response) {
							$('#message').show();
							$('#patientDetails').text(response);

						},
						async : false
					});
				});
	});
}



$(function() {
	
	$("#admissionAccordion").accordion({
		animated : false,
		collapsible : true,
		autoHeight : false,
		active : 0,
		changestart : function(event, ui) {
			var title = ui.newHeader.find("a").text();
			if (title === "Patient History") {
				patientHistoryViewModel.load();
			} else if (title === "Stroke Onset") {
				strokeOnsetViewModel.load();
			} else if (title === "Pre-morbid Assessments") {
				preMorbidAssessmentViewModel.load();
			} 
		}
	});
	
	patientHistoryViewModel.load();

	$("#tabs").tabs({
		selected : 0,
		select : function(event, ui) {
			if (ui.index === 1) {
				$("#accordion").accordion({
					animated : false,
					collapsible : true,
					autoHeight : false,
					active : false,
					changestart : function(event, ui) {
						var title = ui.newHeader.find("a").text();
						if (title === "Glasgow Coma Score") {
							glasgowComaScoreViewModel.load();
						} else if (title === "Sensory/Motor Features") {
							sensoryAndMotorFeatureViewModel.load();
						} else if (title === "Other Features") {
							otherFeatureViewModel.load();
						} else if (title === "Swallow Screening") {
							swallowScreeningViewModel.load();
						}  else if (title === "Continence") {
							continenceViewModel.load();
						} else if (title === "Nutrition Management") {
							nutritionManagementViewModel.load();
						}
					}
				});
			} else if (ui.index === 2) {
				$("#therapyAccordion").accordion({
					animated : false,
					collapsible : true,
					autoHeight : false,
					active : false,
					changestart : function(event, ui) {
						var title = ui.newHeader.find("a").text();
						if (title === "Occupational Therapy") {
							occupationalTherapyViewModel.load();
						} else if (title === "Speech and Language Therapy") {
							speechAndLanguageTherapyViewModel.load();
						} else if (title === "Physiotherapy") {
							physiotherapyViewModel.load();
						} else if (title === "MDT Rehabilitation Goals") {
							nurseLedTherapyViewModel.load();
						}  else if (title === "Post Stroke Assessments") {
							baselineAssessmentViewModel.load();
						} else if (title === "Therapy Summary") {
							therapySummaryViewModel.load();
						}
					}
				});
				
			} else if (ui.index === 3) {
				imagingViewModel.load();
			} else if (ui.index === 5) {
				treatmentViewModel.load();
			} else if (ui.index === 4) {
				thrombolysisViewModel.load();
			} else if (ui.index === 6) {
				clinicalSummaryViewModel.load();
			}else if (ui.index === 7) {
				
				$("#dischargeAccordion").accordion({
					animated : false,
					collapsible : true,
					autoHeight : false,
					active : false,
					changestart : function(event, ui) {
						
						var title = ui.newHeader.find("a").text();
						
						if (title === "Pre Discharge") {
							preDischargeViewModel.load();
						} else if (title === "Assessments") {
							dischargeAssessmentViewModel.load();
						} else if (title === "On Discharge") {							
							postDischargeViewModel.load();								
						}
					}
				});
			}
			else if (ui.index === 8) {				
				window.location="/stroke/ssnapReport";
			}

		} 
	

	});

	/* -- Alternative rendering - Menus instead of tabs -- */
	
	$("#accordion").accordion({
		animated : false,
		collapsible : false,
		autoHeight : false,
		active : false,
		changestart : function(event, ui) {
			var title = ui.newHeader.find("a").text();
			if (title === "Glasgow Coma Score") {
				glasgowComaScoreViewModel.load();
			} else if (title === "Sensory/Motor Features") {
				sensoryAndMotorFeatureViewModel.load();
			} else if (title === "Other Features") {
				otherFeatureViewModel.load();
			} else if (title === "Swallow Screening") {
				swallowScreeningViewModel.load();
			}  else if (title === "Continence") {
				continenceViewModel.load();
			} else if (title === "Nutrition Management") {
				nutritionManagementViewModel.load();
			}
		}
	});

	$("#therapyAccordion").accordion({
		animated : false,
		collapsible : false,
		autoHeight : false,
		active : false,
		changestart : function(event, ui) {
			var title = ui.newHeader.find("a").text();
			if (title === "Occupational Therapy") {
				occupationalTherapyViewModel.load();
			} else if (title === "Speech and Language Therapy") {
				speechAndLanguageTherapyViewModel.load();
			} else if (title === "Physiotherapy") {
				physiotherapyViewModel.load();
			} else if (title === "MDT Rehabilitation Goals") {
				nurseLedTherapyViewModel.load();
			}  else if (title === "Post Stroke Assessments") {
				baselineAssessmentViewModel.load();
			} else if (title === "Therapy Summary") {
				therapySummaryViewModel.load();
			}
		}
	});
	
	$('a[href="#therapy-tab"]').bind('click', function(){occupationalTherapyViewModel.load();});
	$('a[href="#imaging-tab"]').bind('click', function(){imagingViewModel.load();});
	$('a[href="#treatments-tab"]').bind('click', function(){treatmentViewModel.load();});
	$('a[href="#thrombolysis-tab"]').bind('click', function(){thrombolysisViewModel.load();});
	$('a[href="#clinical-summary-tab"]').bind('click', function(){clinicalSummaryViewModel.load();});
	
	$("#dischargeAccordion").accordion({
		animated : false,
		collapsible : false,
		autoHeight : false,
		active : false,
		changestart : function(event, ui) {
			
			var title = ui.newHeader.find("a").text();
			
			if (title === "Pre Discharge") {
				preDischargeViewModel.load();
			} else if (title === "Assessments") {
				dischargeAssessmentViewModel.load();
			} else if (title === "On Discharge") {							
				postDischargeViewModel.load();								
			}
		}
	});
	$('#admission-tab').tab('show');
					

	$('#admission-assessment-toggle').on('shown',function(e) {
		$('#gcs_link').click();
	});
	$('#discharge-toggle').on('shown',function(e) {
		$('#on_discharge_link').click();
	});
	$('#therapy-toggle').on('shown',function(e) {
		$('#occupational_therapy_link').click();
	});

	

	onPage();

/*	$(window).scroll(function() {
		$('.scroller').css('top', $(document).scrollTop());
	});
*/
	$("#loading").hide();
	/*$("#tabs").show(10);*/
});
