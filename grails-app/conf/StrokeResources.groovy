// Define the resources for exposure via Resources plugin
modules = {
	
	'cimss-base' {
		dependsOn 'jquery', 'jquery-dev'
		resource url:[dir:'css/jquery/redmond/', file:'jquery-ui.css'], disposition: 'head'
//		resource url:[dir:'css/jquery/spoonface/css/custom-theme', file:'jquery-ui-1.10.3.custom.min.css'], disposition: 'head'
		resource url:[dir:'css', file:'main.css'], disposition: 'head'
		resource url:[dir:'css', file:'bihr-layout.css'], disposition: 'head'
		resource url:[dir:'css', file:'stroke.css'], disposition: 'head'
		resource url:[dir:'css', file:'stroke.ie.css'],
		wrapper: { s -> "<!--[if lt IE 8]>$s<![endif]-->" }		
	}
	
    'stroke' {
		dependsOn 'cimss-base', 'stroke-mvvm'					
   }
	
   'reporting' {
		dependsOn 'cimss-base'		
    }
   
/*   'flat-ui' {
	   dependsOn 'jquery', 'jquery-ui'
	   resource url:[dir: 'css/flat-ui/bootstrap/css', file: 'bootstrap.css'], disposition: 'head'
	   resource url:[dir: 'css/flat-ui/css', file: 'flat-ui.css'], disposition: 'head'
	   
	   resource url:[dir: 'css/flat-ui/js', file: 'html5shiv.js'], disposition: 'defer',  wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
//	   resource url:[dir: 'css/flat-ui/js', file: 'jquery-1.8.3.min.js']
	   /*resource url:[dir: 'css/flat-ui/js', file: 'jquery-ui-1.10.3.custom.min.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'jquery.ui.touch-punch.min.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'bootstrap.min.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'bootstrap-select.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'bootstrap-switch.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'flatui-checkbox.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'flatui-radio.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'jquery.tagsinput.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'jquery.placeholder.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'jquery.stacktable.js']
	   resource url:[dir: 'css/flat-ui/js', file: 'application.js']
   }
*/   
/*   'icheck' {
	   dependsOn 'jquery', 'jquery-ui'
	   resource url:[dir: 'css/icheck/skins/flat', file: 'blue.css'], disposition: 'head'
	   resource url:[dir: 'js/thirdparty/icheck', file: 'jquery.icheck.min.js']
	}
 */
    
	'datejs'{
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir: 'js/thirdparty/utilities/date', file: 'date.js'], disposition: 'defer'
	}
	
		
	'ie-fix'{
		resource url:[dir: 'js/thirdparty/ie', file: 'IE9.js'], disposition: 'defer'	
	}
	
	'stroke-edit' {
		resource url:[dir: 'js/application', file: 'patient.edit.js']
		resource url:[dir: 'js/application', file: 'ui.setup.js']
	}
	
	'spin'{
		resource url:[dir: 'js/thirdparty/spin', file: 'spin.js'], disposition: 'defer'
	}
	
	'mustache'{
		resource url:[dir: 'js/thirdparty/templates', file: 'mustache.js'], disposition: 'defer'
	}
	
	'slang'{
		resource url:[dir: 'js/thirdparty/utilities/slang', file: 'slang.js'], disposition: 'defer'
	}
	
	
	'knockout-2'{
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir: 'js/thirdparty/ko', file: 'knockout-2.0.0.debug.js'], disposition: 'defer'
	}	
	
	'jquery-validate'{
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir:'js/thirdparty/jquery', file:'jquery.validate.js']
		resource url:[dir:'js/thirdparty/jquery', file:'additional-methods.js']
	}
	
	'stroke-model' {
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir: 'js/application', file: 'cimss.js']
		
		//Onset Admission
		resource url:[dir: 'js/application/model/onset_admission', file: 'AdmissionDetails.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'Existing.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'FirstSeenBy.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'LifeStyle.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'Medication.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'Onset.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'Arrival.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'RiskFactors.js']
		resource url:[dir: 'js/application/model/onset_admission', file: 'PreMorbidAssessment.js']
		
		
		
		
		resource url:[dir: 'js/application/model/therapy', file: 'Assessments.js']
		resource url:[dir: 'js/application/model/therapy', file: 'Barthel.js']
		resource url:[dir: 'js/application/model/therapy', file: 'ModifiedRankin.js']
		resource url:[dir: 'js/application/model/therapy', file: 'OccupationalTherapyManagement.js']
		resource url:[dir: 'js/application/model/therapy', file: 'PhysiotherapyManagement.js']
		resource url:[dir: 'js/application/model/therapy', file: 'SpeechAndLanguageTherapyManagement.js']
		resource url:[dir: 'js/application/model/therapy', file: 'TherapyManagement.js']
		
		
		
		
		// Continence
		resource url:[dir: 'js/application/model/admission_assessment', file: 'ContinenceManagement.js']
		resource url:[dir: 'js/application/model/admission_assessment', file: 'Catheter.js']
		resource url:[dir: 'js/application/model/admission_assessment', file: 'Category.js']
		// Nutrition Management
		resource url:[dir: 'js/application/model/admission_assessment', file: 'NutritionManagement.js']
		//Fluid management
		resource url:[dir: 'js/application/model/admission_assessment', file: 'FluidManagement.js']
		
		
		// Therapy summary
		resource url:[dir: 'js/application/model/therapy_summary', file: 'TherapySummary.js']
		
		// Imaging
		resource url:[dir: 'js/application/model/imaging', file: 'Imaging.js']
		resource url:[dir: 'js/application/model/imaging', file: 'Scan.js']
		
		// Treatments
		resource url:[dir: 'js/application/model/treatment', file: 'Treatment.js']
		resource url:[dir: 'js/application/model/treatment', file: 'Treatments.js']
		resource url:[dir: 'js/application/model/treatment', file: 'TreatmentType.js']
		
		// Clinical Summary
		resource url:[dir: 'js/application/model/clinical_summary', file: 'ClinicalSummary.js']
		
			
		// Discharge
		resource url:[dir: 'js/application/model/discharge', file: 'Discharge.js']
		resource url:[dir: 'js/application/model/discharge', file: 'PostDischarge.js']
		resource url:[dir: 'js/application/model/discharge', file: 'PostDischargeSupport.js']
		resource url:[dir: 'js/application/model/discharge', file: 'PostDischargeTherapy.js']
		resource url:[dir: 'js/application/model/discharge', file: 'DischargeAssessment.js']
		
		
	}
	
	'stroke-mvvm' {
		dependsOn 'jquery', 'jquery-ui', 'stroke-json' , 'stroke-knockout' ,'stroke-model', 'stroke-edit'
		resource url:[dir: 'js/application/mvvm', file: 'dataStatus.js']
		//resource url:[dir: 'js/application/mvvm/onset_admission', file: 'admission.js']
		resource url:[dir: 'js/application/mvvm/onset_admission', file: 'patientHistory.js']
		resource url:[dir: 'js/application/mvvm/onset_admission', file: 'strokeOnset.js']
		resource url:[dir: 'js/application/mvvm/onset_admission', file: 'preMorbidAssessment.js']
		
		resource url:[dir: 'js/application/mvvm', file: 'clinical.js']
		
		resource url:[dir: 'js/application/mvvm/admission_assessment', file: 'glasgowComaScore.js']
		resource url:[dir: 'js/application/mvvm/admission_assessment', file: 'sensoryAndMotorFeatures.js']
		resource url:[dir: 'js/application/mvvm/admission_assessment', file: 'otherFeatures.js']
		resource url:[dir: 'js/application/mvvm/admission_assessment', file: 'swallowScreening.js']
		resource url:[dir: 'js/application/mvvm/admission_assessment', file: 'continence.js']
		resource url:[dir: 'js/application/mvvm/admission_assessment', file: 'nutrition.js']
		resource url:[dir: 'js/application/mvvm/admission_assessment', file: 'fluid.js']
		resource url:[dir: 'js/application/mvvm', file: 'therapy.js']
		resource url:[dir: 'js/application/mvvm/imaging', file: 'imaging.js']
		resource url:[dir: 'js/application/mvvm/treatment', file: 'treatment.js']
		
		resource url:[dir: 'js/application/mvvm', file: 'thrombolysis.js']
		
		
		resource url:[dir: 'js/application/mvvm/therapy', file: 'occupationalTherapy.js']
		resource url:[dir: 'js/application/mvvm/therapy', file: 'speechAndLanguageTherapy.js']
		resource url:[dir: 'js/application/mvvm/therapy', file: 'physiotherapy.js']
		resource url:[dir: 'js/application/mvvm/therapy', file: 'nurseLedTherapy.js']
		resource url:[dir: 'js/application/mvvm/therapy', file: 'baselineAssessments.js']
		resource url:[dir: 'js/application/mvvm/therapy', file: 'therapySummary.js']
		
		resource url:[dir: 'js/application/mvvm/clinical_summary', file: 'clinicalSummary.js']
		
		resource url:[dir: 'js/application/mvvm/discharge', file: 'preDischarge.js']
		resource url:[dir: 'js/application/mvvm/discharge', file: 'dischargeAssessment.js']
		resource url:[dir: 'js/application/mvvm/discharge', file: 'postDischarge.js']
		
	}
	
	'stroke-knockout'{
		resource url:[dir: 'js/thirdparty/ko', file: 'knockout-jquery-ui-widget.js'], disposition: 'defer'
		resource url:[dir: 'js/thirdparty/ko', file: 'utils.js'], disposition: 'defer'
		resource url:[dir: 'js/thirdparty/ko', file: 'bindings.js'], disposition: 'defer'
	}
	
	'stroke-json' {
		resource url:[dir: 'js/thirdparty/json', file: 'json2.js'], disposition: 'defer'
	}
	
	'jquery-template' {
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir: 'js/thirdparty/jquery', file: 'jquery.tmpl.js'], disposition: 'defer'
	}
	'datatables' {
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir: 'js/thirdparty/jquery', file:'jquery.dataTables.js']
	}
	'jquery-ui-timepicker-addon'{
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir:'css/jquery', file:'jquery-ui-timepicker-addon.css'], disposition: 'head'
		resource url:[dir: 'js/thirdparty/jquery', file:'jquery-ui-timepicker-addon.js'], disposition: 'defer'
		
	}
	
	'jquery-mask' {
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir: 'js/thirdparty/jquery', file: 'jquery.maskedinput-1.3.js'], disposition: 'defer'
		//resource url:[dir: 'js/jquery', file: 'jquery.meio.mask.js'], disposition: 'head'
	}
	
	
	'jquery-jdmenu'  {
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir:'css/jquery', file:'jquery.jdMenu.css'], disposition: 'head'
		resource url:[dir: 'js/thirdparty/jquery', file: 'jquery.jdMenu.js'], disposition: 'defer'
	}
	
	'jquery-timeentry' {
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir:'css/jquery', file:'jquery.timeentry.css'], disposition: 'head'
		resource url:[dir: 'js/thirdparty/jquery', file: 'jquery.timeentry.js'], disposition: 'defer'
	}
	
	'jquery-select-to-slider' {
		dependsOn 'jquery', 'jquery-ui'
		resource url:[dir:'css/jquery', file:'ui.slider.extras.css'], disposition: 'head'
		resource url:[dir: 'js/thirdparty/jquery', file: 'jquery.select.to.uislider.js'], disposition: 'defer'
	}
	
	
	'blueprint-bihr' {
		resource id:'main', url:[dir:'css/blueprint', file:'screen.css'],
			attrs:[media:'screen, projection']
		resource id:'ie', url:[dir:'css/blueprint', file:'ie.css'], attrs:[media:'screen,projection'],
			wrapper: { s -> "<!--[if lt IE 8]>$s<![endif]-->" }
	}

	'blueprint-print-bihr'  {
		dependsOn 'blueprint-bihr' 
		resource url:[dir:'css/blueprint', file:'print.css'], attrs:[media:'print']
	}

	'blueprint-buttons-bihr'  {
		dependsOn 'blueprint-bihr' 
		resource url:[dir:'css/blueprint/plugins/buttons', file:'screen.css'], attrs:[media:'screen, projection']
	}

	'blueprint-fancy-type-bihr'  {
		dependsOn 'blueprint-bihr' 
		resource url:[ dir:'css/blueprint/plugins/fancy-type', file:'screen.css'], attrs:[media:'screen, projection']
	}

	'blueprint-link-icons' {
		dependsOn 'blueprint-bihr' 
		resource url:[ dir:'css/blueprint/plugins/link-icons', file:'screen.css'], attrs:[media:'screen, projection']
	}

	'blueprint-rtl-bihr'  {
		dependsOn 'blueprint-bihr' 
		resource url:[dir:'css/blueprint/plugins/rtl', file:'screen.css'], attrs:[media:'screen, projection']
	}
	
	

   
}