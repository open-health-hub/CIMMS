
	<div id="chftStatusDialogBox" title="CHFT Compliance" style="display:none">
		<div class="issue-list-container">
			<br>
			<p data-bind="text:dataStatus().chftDataStatus().message()"></p>
			<br>
			<div id="chftIssues" >
				<div class="issue-list">
				<h2 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',5) ;}">NUTRITION MANAGEMENT</h2>
				<ul id="chftIssueList"></ul>
				</div>
			</div>
		</div>	
	</div>
	
	
	<div id="anhstStatusDialogBox" title="ANHST Compliance" style="display:none">
        <div class="issue-list-container">
            <br>
            <p data-bind="text:dataStatus().anhstDataStatus().message()"></p>
            <br>
            <div id="anhstIssues" >
            	<div class="issue-list">
	                <h2 data-bind="click: function(data, event) { $('#tabs').tabs('select',3);}">IMAGING</h2>
	                <ul id="anhstIssueList"></ul>
                </div>
            </div>
        </div>  
    </div>
	
	
	
	<div id="ssnap72StatusDialogBox" title="SSNAP (72-hour) Compliance"  style="display:none">	
		<div class="issue-list-container">
			<br>
			<p data-bind="text:dataStatus().ssnap72HrExtract().message()"></p>
			<br>
			<div class="issue-list">
				
				<div id="ssnap72OnsetIssues" class="issue-list-block">
                    <h2 data-bind="click: function(data, event) { $('#tabs').tabs('select',0);}" class="issue-list-title">ONSET/ADMISSION</h2>
                        
                        <div id="ssnap72PatientHistoryIssue" class="issue-list-subtitle">                            
                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',0) ;}">Patient History</h3>
                                <ul id="ssnap72PatientHistoryIssueList"></ul>                            
                        </div>
                        
                        <div id="ssnap72StrokeOnsetIssue" class="issue-list-subtitle">                            
                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',1) ;}">Stroke Onset</h3>
                                <ul id="ssnap72StrokeOnsetIssueList"></ul>
                        </div>
                        
                
                        <div id="ssnap72PreMorbidAssessmentIssue" class="issue-list-subtitle">                           
                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',2) ;}">Pre-Morbid Assessments</h3>
                                <ul id="ssnap72PreMorbidAssessmentIssueList"></ul>
                        </div>
                        
                </div>
				<div id="ssnap72AdmissionIssues" class="issue-list-block">
				
						<h2 data-bind="click: function(data, event) { $('#tabs').tabs('select',1);}" class="issue-list-title">ADMISSION ASSESSMENTS</h2>
						
						    
						<div id="ssnap72SensoryFeatureIssue" class="issue-list-subtitle">                                
                                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',1) ;}">Sensory/Motor Features</h3>
                                    <ul id="ssnap72SensoryFeatureIssueList"></ul>                                
						</div>
                            
                            
                        <div id="ssnap72OtherFeatureIssue" class="issue-list-subtitle">                                
                                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',2) ;}">Other Features</h3>
                                    <ul id="ssnap72OtherFeatureIssueList"></ul>
						</div>
                            
						<div id="ssnap72SwallowScreeningIssue" class="issue-list-subtitle">                                
                                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',3) ;}">Swallow Screening</h3>
                                    <ul id="ssnap72SwallowScreeningIssueList"></ul>                                
						</div>
						
					
						<div id="ssnap72ClinicalAssessmentIssues" class="issue-list-subtitle">								
									<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',3) ;}">Clinical Assessments</h3>
									<ul id="ssnap72ClinicalAssessmentIssuesList"></ul>
						</div>
						
						
						<div id="ssnap72ContinenceManagementIssues" class="issue-list-subtitle">								
									<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',4) ;}">Continence Management</h3>
									<ul id="ssnap72ContinenceManagementIssuesList"></ul>
						</div>
							
	
						<div id="ssnap72NutritionManagementIssues" class="issue-list-subtitle">								
									<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',5) ;}">Nutrition Management</h3>
									<ul id="ssnap72NutritionManagementIssuesList"></ul>
						</div>							
					</div>							
				</div>
				
                <div id="ssnap72TherapyIssues">	
                
				   <h2 data-bind="click: function(data, event) { $('#tabs').tabs('select',2);}">THERAPY</h2>
				   
				   <div id="ssnap72OccupationalTherapyIssues">                       
                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',0) ;}">Occupational Therapy</h3>
                           <ul id="ssnap72OccupationalTherapyIssuesList"></ul>
                   </div>
                   
                   <div id="ssnap72SpeechAndLanguageTherapyIssues">                       
                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',1) ;}">Speech And Language Therapy</h3>
                           <ul id="ssnap72SpeechAndLanguageTherapyIssuesList"></ul>
                   </div>
                   
                   <div id="ssnap72PhysiotherapyIssues">                       
                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',2) ;}">Physiotherapy</h3>
                           <ul id="ssnap72PhysiotherapyIssuesList"></ul>                       
                   </div>
                   
                   <div id="ssnap72NurseLedTherapyIssues">
                       
                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',3) ;}">MDT Goals</h3>
                           <ul id="ssnap72NurseLedTherapyIssuesList"></ul>
                       
                   </div>
                   
                   <div id="ssnap72BaselineAssessmentsIssues">
                       
                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',4) ;}">Post Stroke Assessments</h3>
                           <ul id="ssnap72BaselineAssessmentsIssuesList"></ul>
                   </div>
                   
                   
		           <div id="ssnap72TherapySummaryIssues">
	                       <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',5) ;}">Therapy Summary</h3>
	                       <ul id="ssnap72TherapySummaryIssuesList"></ul>
	               </div>
                   
				</div>   
				 
				<div id="ssnap72ImagingIssues">					
						<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',3); }">IMAGING</h3>
						<ul id="ssnap72ImagingIssueList"></ul>					
				</div>
				
				<div id="ssnap72ThrombolysisIssues">					
						<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',5); }">THROMBOLYSIS</h3>
						<ul id="ssnap72ThrombolysisIssueList"></ul>					
				</div>
				
				<div id="ssnap72ClinicalSummaryIssues">					
                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',6); }">CLINICAL SUMMARY</h3>
                    <ul id="ssnap72ClinicalSummaryIssuesList"></ul>                    
                </div>
                
				<div id="ssnap72DischargeIssues">
				
					<h2 data-bind="click: function(data, event) { $('#tabs').tabs('select',7);}">DISCHARGE</h2>
					
						<div id="ssnap72PreDischargeIssues">							
								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',0) ;}">Pre discharge</h3>
								<ul id="ssnap72PreDischargeIssueList"></ul>
						</div>
						
						<div id="ssnap72DischargeAssessmentIssues">
							<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',1) ;}">Discharge assessments</h3>
							<ul id="ssnap72DischargeAssessmentIssuesList"></ul>
						</div>
						
						<div id="ssnap72PostDischargeIssues">							
							<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',2) ;}">On discharge</h3>
							<ul id="ssnap72PostDischargeIssuesList"></ul>
						</div>
					</div>
		</div>
	</div>			


	<div id="ssnapStatusDialogBox" title="SSNAP Compliance"  style="display:none">	
		<div >
			<br>
			<p data-bind="text:dataStatus().ssnapExtract().message()"></p>
			<br>
			
				
				<div id="ssnapOnsetIssues" >
                    <h2 data-bind="click: function(data, event) { $('#tabs').tabs('select',0);}">ONSET/ADMISSION</h2>
                        <div id="ssnapPatientHistoryIssue">
                            
                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',0) ;}">Patient History</h3>
                                <ul id="ssnapPatientHistoryIssueList"></ul>
                            
                        </div>
                        
                        <div id="ssnapStrokeOnsetIssue">
                            
                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',1) ;}">Stroke Onset</h3>
                                <ul id="ssnapStrokeOnsetIssueList"></ul>
                            
                        </div>
                
                        <div id="ssnapPreMorbidAssessmentIssue">
                            
                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',2) ;}">Pre-Morbid Assessments</h3>
                                <ul id="ssnapPreMorbidAssessmentIssueList"></ul>
                            
                        </div>
                
                </div>
				<div id="ssnapAdmissionIssues">
						<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1);}">ADMISSION ASSESSMENTS</h3>
						
						    
						     <div id="ssnapSensoryFeatureIssue">                                
                                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',1) ;}">Sensory/Motor Features</h3>
                                    <ul id="ssnapSensoryFeatureIssueList"></ul>
                            </div>
                            
                            
                            <div id="ssnapOtherFeatureIssue">
                                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',2) ;}">Other Features</h3>
                                    <ul id="ssnapOtherFeatureIssueList"></ul>
                            </div>
                            
                            
                            <div id="ssnapSwallowScreeningIssue">
                                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',3) ;}">Swallow Screening</h3>
                                    <ul id="ssnapSwallowScreeningIssueList"></ul>
                            </div>
						
					
							<div id="ssnapClinicalAssessmentIssues">
									<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',3) ;}">Clinical Assessments</h3>
									<ul id="ssnapClinicalAssessmentIssuesList"></ul>
							</div>
						
						
							<div id="ssnapContinenceManagementIssues">
									<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',4) ;}">Continence Management</h3>
									<ul id="ssnapContinenceManagementIssuesList"></ul>
							</div>
	
							<div id="ssnapNutritionManagementIssues">
									<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',5) ;}">Nutrition Management</h3>
									<ul id="ssnapNutritionManagementIssuesList"></ul>
							</div>
							
							
							
					</div>
                <div id="ssnapTherapyIssues">	
				   <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2);}">THERAPY</h3>
				   <div id="ssnapOccupationalTherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',0) ;}">Occupational Therapy</h3>
                           <ul id="ssnapOccupationalTherapyIssuesList"></ul>

                   </div>
                   <div id="ssnapSpeechAndLanguageTherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',1) ;}">Speech And Language Therapy</h3>
                           <ul id="ssnapSpeechAndLanguageTherapyIssuesList"></ul>

                   </div>
                   <div id="ssnapPhysiotherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',2) ;}">Physiotherapy</h3>
                           <ul id="ssnapPhysiotherapyIssuesList"></ul>

                   </div>
                   <div id="ssnapNurseLedTherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',3) ;}">MDT Goals</h3>
                           <ul id="ssnapNurseLedTherapyIssuesList"></ul>

                   </div>
                   <div id="ssnapBaselineAssessmentsIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',4) ;}">Post Stroke Assessments</h3>
                           <ul id="ssnapBaselineAssessmentsIssuesList"></ul>

                   </div>
                   
                   
                   
		           <div id="ssnapTherapySummaryIssues">

	                       <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',5) ;}">Therapy Summary</h3>
	                       <ul id="ssnapTherapySummaryIssuesList"></ul>

                   </div>
				</div>    
				<div id="ssnapImagingIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',3); }">IMAGING</h3>
					<ul id="ssnapImagingIssueList"></ul>
				</div>
				<div id="ssnapThrombolysisIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',5); }">THROMBOLYSIS</h3>
					<ul id="ssnapThrombolysisIssueList"></ul>
				</div>
				<div id="ssnapClinicalSummaryIssues">
                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',6); }">CLINICAL SUMMARY</h3>
                    <ul id="ssnapClinicalSummaryIssuesList"></ul>
                </div>
				<div id="ssnapDischargeIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7);}">DISCHARGE</h3>
						<div id="ssnapPreDischargeIssues">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',0) ;}">Pre discharge</h3>
								<ul id="ssnapPreDischargeIssueList"></ul>

						</div>
						<div id="ssnapDischargeAssessmentIssues">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',1) ;}">Discharge assessments</h3>
								<ul id="ssnapDischargeAssessmentIssuesList"></ul>

						</div>
						<div id="ssnapPostDischargeIssues">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',2) ;}">On discharge</h3>
								<ul id="ssnapPostDischargeIssuesList"></ul>

						</div>
				</div>
				
				
				
				
					
		</div>
	</div>		


	
	<div id="cimssStatusDialogBox"  title="CIMSS Compliance" style="display:none">
		<div >
			<br>
			<p data-bind="text:dataStatus().cimssExtract().message()"></p>
			<br>
			
				<div id="onsetIssues" >
				    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0);}">ONSET/ADMISSION</h3>
                        <div id="patientHistoryIssue">

                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',0) ;}">Patient History</h3>
                                <ul id="patientHistoryIssueList"></ul>

                        </div>
                        <div id="strokeOnsetIssue">

                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',0); $('#admissionAccordion').accordion('activate',1) ;}">Stroke Onset</h3>
                                <ul id="strokeOnsetIssueList"></ul>

                        </div>
				
				
				
				</div>
				
				<div id="admissionIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1);}">ADMISSION ASSESSMENTS</h3>
					   <div id="gcsIssue">

                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',0) ;}">Glasgow Coma Score</h3>
                                <ul id="gcsIssueList"></ul>

                        </div>
					   <div id="sensoryFeatureIssue">

                                <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',1) ;}">Sensory/Motor Features</h3>
                                <ul id="sensoryFeatureIssueList"></ul>

                        </div>
					
					
						<div id="swallowScreeningIssue">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',3) ;}">Swallow Screening</h3>
								<ul id="swallowScreeningIssueList"></ul>

						</div>
						
						
						
						
						<div id="continenceIssues">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',1); $('#accordion').accordion('activate',4) ;}">Continence</h3>
								<ul id="continenceIssuesList"></ul>

						</div>
						
				</div>
				
				<div id="therapyIssues">   
                   <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2);}">THERAPY</h3>
                   <div id="occupationalTherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',0) ;}">Occupational Therapy</h3>
                           <ul id="occupationalTherapyIssuesList"></ul>

                   </div>
                   <div id="speechAndLanguageTherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',1) ;}">Speech And Language Therapy</h3>
                           <ul id="speechAndLanguageTherapyIssuesList"></ul>

                   </div>
                   <div id="physiotherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',2) ;}">Physiotherapy</h3>
                           <ul id="physiotherapyIssuesList"></ul>

                   </div>
                   <div id="nurseLedTherapyIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',3) ;}">MDT Goals</h3>
                           <ul id="nurseLedTherapyIssuesList"></ul>

                   </div>
                   <div id="baselineAssessmentsIssues">

                           <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',4) ;}">Post Stroke Assessments</h3>
                           <ul id="baselineAssessmentsIssuesList"></ul>

                   </div>
                   
                   
              </div>     
				
				<div id="imagingIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',3); }">IMAGING</h3>
					<ul id="imagingIssueList"></ul>
				</div>
					
				<div id="treatmentIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',4); }">TREATMENTS</h3>
						<ul id="treatmentIssueList"></ul>
				</div>
				
				<div id="thrombolysisIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',5); }">THROMBOLYSIS</h3>
						<ul id="thrombolysisIssueList"></ul>
				</div>
					
				<div id="clinicalSummaryIssue">
                    <h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',6); }">CLINICAL SUMMARY</h3>
                        <ul id="clinicalSummaryIssueList"></ul>
                </div>
					
								
				<div id="theDischargeIssues">
					<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7);}">DISCHARGE</h3>
						<div id="preDischargeIssues">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',0) ;}">Pre discharge</h3>
								<ul id="preDischargeIssueList"></ul>

						</div>
						<div id="dischargeAssessmentIssues">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',1) ;}">Discharge assessments</h3>
								<ul id="dischargeAssessmentIssuesList"></ul>

						</div>
						<div id="postDischargeIssues">

								<h3 data-bind="click: function(data, event) { $('#tabs').tabs('select',7); $('#dischargeAccordion').accordion('activate',2) ;}">On discharge</h3>
								<ul id="postDischargeIssuesList"></ul>

						</div>
				</div>
				
				
		</div>
		
		
		
	</div>
		
<script type="text/x-template" id="issueTemplate">
	<li>{{issueText}}</li>
</script>
