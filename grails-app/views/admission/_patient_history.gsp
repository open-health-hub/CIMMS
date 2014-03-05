<div id="patientHistoryForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>
		<div id="patientHistory">

			<div class="page-header"><h1>Patient history</h1></div>	



			<div class="question-block clearfix">
				<div class="question-alt">
					<label>Was the patient transferred from another hospital?</label>
				</div>
			
				<div class="answer-block">
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().arrival().transferredFromAnotherHospital"
							name="transferredFromAnotherHospital" id="transferredFromAnotherHospitalYes" value="true" /> <label
							for="transferredFromAnotherHospitalYes">Yes</label>
					</div>		
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().arrival().transferredFromAnotherHospital"
							name="transferredFromAnotherHospital" id="transferredFromAnotherHospitalNo" value="false" /> <label
							for="transferredFromAnotherHospitalNo">No</label>
					</div>
				</div>
			</div>

			<div class="question-block clearfix">
				<div class="question-alt">
					<label>Date and Time the patient arrived at this hospital</label>
				</div>

				<div class="answer-block">

                    <div class="answer">
                        <g:textField  class="standardDatePicker date"
                                      name="thisHospitalArrivalDate"
                                      data-bind="inError :'medicalHistory.hospitalArrivalDate', value: admissionDetails().arrival().thisHospitalArrivalDate"
                        />
                    </div>

                    <div class="answer">
                        <g:textField class="time" name="thisHospitalArrivalTime"
                                     data-bind="inError :'medicalHistory.hospitalArrivalTime', value: admissionDetails().arrival().thisHospitalArrivalTime" />
                    </div>

				</div>
			</div>
			
			<div class="question-block clearfix">
				<div class="question-alt">
					<label>Did the patient arrive by ambulance?<br><i>( Note: if patient transferred to another hospital <b>within the same Trust</b>, and was an inpatient at time of stroke, enter “No” )</i></label>
				</div>
			
				<div class="answer-block">
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().arrival().arriveByAmbulance"
							name="arriveByAmbulance" id="arriveByAmbulanceYes" value="true" /> <label
							
							for="arriveByAmbulanceYes">Yes</label>
					</div>		
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().arrival().arriveByAmbulance"
							name="arriveByAmbulance" id="arriveByAmbulanceNo" value="false" /> <label
							
							for="arriveByAmbulanceNo">No</label>
					</div>
				</div>
			</div>
			
			<div data-bind="visible: admissionDetails().arrival().arriveByAmbulance() === 'true'">
			
				<div class="question-block clearfix" >		
					<div class="question-alt">
						<label>Ambulance Trust</label>
					</div>
					<div class="answer-block">
						<div class="answer">
							<select
								data-bind="options: patientHistoryViewModel.knownAmbulanceTrusts, optionsCaption:'Choose an ambulance trust ...', optionsText:'description', optionsValue:'code', value: admissionDetails().arrival().ambulanceTrust" ></select>
						</div>
					</div>
				</div>

				<div class="question-block clearfix" >		
					<div class="question-alt">
						<label>Computer Aided Despatch (CAD) no.</label>
					</div>
					<div class="answer-block">
						<div class="answer">
							<input type="text" size="10" maxlength="10" data-bind="value:admissionDetails().arrival().cadNumber" style="width: 8em;">
						</div>
						<div class="answer">
							<input type="radio"
								data-bind="checkedWithToggle: admissionDetails().arrival().cadNumberUnknown"
								name="cadNumberUnknown" id="cadNumberUnknown" value="true" />
							<label								
								for="cadNumberUnknown">Unknown</label>			
						</div>
					</div>
				</div>
			</div>
			
			<div class="question-block clearfix">
				<div class="question-alt">
					<label>Does the patient wish to opt out from receiving an Outcomes Questionnaire at 4-months post stroke?</label>
				</div>
				<div class="answer-block">
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().arrival().outcomeQuestionnairOptOut"
							name="outcomeQuestionnairOptOut" id="outcomeQuestionnairOptOutYes" value="true" /> <label							
							for="outcomeQuestionnairOptOutYes">Yes</label>
					</div>
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().arrival().outcomeQuestionnairOptOut"
							name="outcomeQuestionnairOptOut" id="outcomeQuestionnairOptOutNo" value="false" /> 
							<label for="outcomeQuestionnairOptOutNo">No N/A</label>
					</div>
				</div>
			</div>


			<div class="question-block clearfix">		
				<div class="question-alt">
					<label>Has the patient had a previous stroke?</label>
				</div>
				<div class="answer-block">
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().existing().previousStroke"
							name="previousStroke" id="previousStrokeYes" value="yes" /> 
							<label for="previousStrokeYes">Yes</label>
					</div>
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().existing().previousStroke"
							name="previousStroke" id="previousStrokeNo" value="no" /> 
							<label for="previousStrokeNo">No</label>
					</div>
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().existing().previousStroke"
							name="previousStroke" id="previousStrokeUnknown" value="unknown" />
						<label for="previousStrokeUnknown">Unknown</label>
					</div>
				</div>
			</div>

			<div class="question-block clearfix">
				<div class="question-alt">
					<label>Has the patient had a previous TIA?</label>
				</div>
				<div class="answer-block">			
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().existing().previousTIA"
							name="previousTIA" id="previousTiaYesWithinMonth"
							value="yesWithinMonth" /> 
							<label for="previousTiaYesWithinMonth">Yes (&lt; month)</label>
					</div>
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().existing().previousTIA"
							name="previousTIA" id="previousTiaYes" value="yes" /> 
							<label 
							for="previousTiaYes">Yes (&gt; month)</label>
					</div>
		
		
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().existing().previousTIA"
							name="previousTIA" id="previousTiaNo" value="no" /> 
							<label for="previousTiaNo">No</label>
					</div>
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().existing().previousTIA"
							name="previousTIA" id="previousTiaUnknown" value="unknown" /> 
							<label for="previousTiaUnknown">Unknown</label>
					</div>
				</div>
			</div>

			<div
				data-bind="visible: admissionDetails().existing().previousTIA() == 'yesWithinMonth' ">
				<div class="question-block clearfix">
					<div class="question-alt">
						<label>Was the patient assessed in a vascular clinic?</label>
					</div>
					<div class="answer-block">
						<div class="answer">
							<input type="radio"
								data-bind="checkedWithToggle: admissionDetails().existing().assessedInVascularClinic"
								name="assessedInVascularClinic" id="assessedInVascularClinicYes"
								value="true" /> 
								<label for="assessedInVascularClinicYes">Yes</label>
						</div>
						<div class="answer">
							<input type="radio"
								data-bind="checkedWithToggle: admissionDetails().existing().assessedInVascularClinic"
								name="assessedInVascularClinic" id="assessedInVascularClinicNo"
								value="false" /> 
								<label for="assessedInVascularClinicNo">No</label>
						</div>
					</div>
				</div>
			</div>
	
			<div class="clearfix spacer-1"></div>
			
			<div class="page-header">
				<h3 >Co-morbidity</h3>
			</div>

			<div class="row row-wide">
			<div class="span2 pull-left">
	            
	                    <div >
	                        <span data-bind="css: {selected :admissionDetails().existing().diabetes() ==='true' || admissionDetails().existing().diabetes() ==='false' }">Diabetes</span>
	                    </div>
	                   <div  class="pull-left answer vspace1">
	                        <input type="radio" 
	                               data-bind="checkedWithToggle: admissionDetails().existing().diabetes" 
	                               name="diabetes" 
	                               id="diabetesYes"
	                               value="true" /> 
	                        <label  for="diabetesYes">Yes</label>
	    				</div>
	    				<div class="pull-left answer vspace1">
	                        <input type="radio" 
	                               data-bind="checkedWithToggle: admissionDetails().existing().diabetes"
	                               name="diabetes" 
	                               id="diabetesNo"
	                               value="false" />
	                        <label  for="diabetesNo">No</label>
	                    </div>
	           
            </div>
            

			<div class="span2 pull-left">
	            
	                    <div >
                            <span class="theLabel" data-bind="css: {selected :admissionDetails().existing().atrialFibrillation() ==='true' || admissionDetails().existing().atrialFibrillation() ==='false' }">Atrial Fibrillation</span>
                        </div>
                       <div  class="pull-left answer vspace1">
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().atrialFibrillation" 
                                   name="atrialFibrillation" 
                                   id="atrialFibrillationYes"
                                   value="true" /> 
                            <label class="radio-label" for="atrialFibrillationYes">Yes</label>
        
        				</div>
        				<div  class="pull-left answer vspace1">
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().atrialFibrillation"
                                   name="atrialFibrillation" 
                                   id="atrialFibrillationNo"
                                   value="false" />
                            <label class="radio-label" for="atrialFibrillationNo">No</label>
                        </div>
                	
            </div>
		
			<div class="span2 pull-left">
	            
                <div >
                       <span class="theLabel" data-bind="css: {selected :admissionDetails().existing().myocardialInfarction() ==='true' || admissionDetails().existing().myocardialInfarction() ==='false' }">Myocardial Infarction</span>
                   </div>
                  <div class="pull-left answer vspace1"  >
                       <input type="radio" 
                              data-bind="checkedWithToggle: admissionDetails().existing().myocardialInfarction" 
                              name="myocardialInfarction" 
                              id="myocardialInfarctionYes"
                              value="true" /> 
                       <label class="radio-label" for="myocardialInfarctionYes">Yes</label>
   				</div>
   				<div class="pull-left answer vspace1"  >
   
                       <input type="radio" 
                              data-bind="checkedWithToggle: admissionDetails().existing().myocardialInfarction"
                              name="myocardialInfarction" 
                              id="myocardialInfarctionNo"
                              value="false" />
                       <label class="radio-label" for="myocardialInfarctionNo">No</label>
                   </div>
               
            </div>
			
			<div class="span2 pull-left">
                
                 <div >
                     <span class="theLabel"
                            data-bind="css: {selected :admissionDetails().existing().hyperlipidaemia() ==='true' || admissionDetails().existing().hyperlipidaemia() ==='false' }"
                         >Hyperlipidaemia</span>
                 </div>
                <div class="pull-left answer vspace1"  >
                     <input type="radio" 
                            data-bind="checkedWithToggle: admissionDetails().existing().hyperlipidaemia" 
                            name="hyperlipidaemia" 
                            id="hyperlipidaemiaYes"
                            value="true" /> 
                     <label class="radio-label" for="hyperlipidaemiaYes">Yes</label>
 				</div>
 				<div class="pull-left answer vspace1"  >
                     <input type="radio" 
                            data-bind="checkedWithToggle: admissionDetails().existing().hyperlipidaemia"
                            name="hyperlipidaemia" 
                            id="hyperlipidaemiaNo"
                            value="false" />
                     <label class="radio-label" for="hyperlipidaemiaNo">No</label>
                 </div>
                
            </div>
			</div>

            
            <div class="row row-wide">
            <div class="span2 pull-left">
                
                        <div >
                            <span class="theLabel"
                            data-bind="css: {selected :admissionDetails().existing().hypertension() ==='true' || admissionDetails().existing().hypertension() ==='false' }"
                                >Hypertension</span>
                        </div>
                       <div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().hypertension" 
                                   name="hypertension" 
                                   id="hypertensionYes"
                                   value="true" /> 
                            <label class="radio-label" for="hypertensionYes">Yes</label>
        				</div>
        				<div class="pull-left answer vspace1">
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().hypertension"
                                   name="hypertension" 
                                   id="hypertensionNo"
                                   value="false" />
                            <label class="radio-label" for="hypertensionNo">No</label>
                        </div>
                
            </div>
    			
  
            
            <div class="span2 pull-left" >
                        <div >
                            <span class="theLabel"
                            data-bind="css: {selected :admissionDetails().existing().valvularHeartDisease() ==='true' || admissionDetails().existing().valvularHeartDisease() ==='false' }"
                                >Valvular Heart Disease</span>
                        </div>
                       <div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().valvularHeartDisease" 
                                   name="valvularHeartDisease" 
                                   id="valvularHeartDiseaseYes"
                                   value="true" /> 
                            <label class="radio-label" for="valvularHeartDiseaseYes">Yes</label>
        
        				</div>
        				<div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().valvularHeartDisease"
                                   name="valvularHeartDisease" 
                                   id="valvularHeartDiseaseNo"
                                   value="false" />
                            <label class="radio-label" for="valvularHeartDiseaseNo">No</label>
                        </div>
                
            </div>
    			

            
            <div class="span2 pull-left" >
                        <div >
                            <span class="theLabel"
                            data-bind="css: {selected :admissionDetails().existing().ischaemicHeartDisease() ==='true' || admissionDetails().existing().ischaemicHeartDisease() ==='false' }"
                                >Ischaemic Heart Disease</span>
                        </div>
                       <div class="pull-left answer vspace1">
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().ischaemicHeartDisease" 
                                   name="ischaemicHeartDisease" 
                                   id="ischaemicHeartDiseaseYes"
                                   value="true" />
                            <label class="radio-label" for="ischaemicHeartDiseaseYes">Yes</label>
        				</div>
        				<div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().ischaemicHeartDisease"
                                   name="ischaemicHeartDisease" 
                                   id="ischaemicHeartDiseaseNo"
                                   value="false" />
                            <label class="radio-label" for="ischaemicHeartDiseaseNo">No</label>
                        </div>                
            </div>
    			
    	
			
            
            <div class="span2 pull-left" >
                        <div >
                            <span class="theLabel" 
                                  data-bind="css: {selected :admissionDetails().existing().congestiveHeartFailure() ==='true' || admissionDetails().existing().congestiveHeartFailure() ==='false' }"
                                >Congestive Heart Failure</span>
                        </div>
                       <div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().congestiveHeartFailure" 
                                   name="congestiveHeartFailure" 
                                   id="congestiveHeartFailureYes"
                                   value="true" /> 
                            <label class="radio-label" for="congestiveHeartFailureYes">Yes</label>
        				</div>
        				<div class="pull-left answer vspace1"  >
        
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().existing().congestiveHeartFailure"
                                   name="congestiveHeartFailure" 
                                   id="congestiveHeartFailureNo"
                                   value="false" />
                            <label class="radio-label" for="congestiveHeartFailureNo">No</label>
                        </div>
                
            </div>
    			
    </div>
         
         			
			
	
			
			<div class="page-header"><h3>Risk Factors</h3></div>
			
			<div class="question-block clearfix">
				<div class="question-alt">Was/is the patient a smoker</div>

				<div class="answer-block">
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().riskFactors().smoker"
							name="smoker" id="smokercurrent" value="current" /> 
							<label for="smokercurrent">Current</label>
					</div>
					<div class=" answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().riskFactors().smoker"
							name="smoker" id="smokernon" value="non" /> <label for="smokernon">Non-smoker</label>
					</div>
		
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().riskFactors().smoker"
							name="smoker" id="smokerprevious" value="previous" /> <label for="smokerprevious">Previous</label>
					</div>
				</div>
			</div>

			<div class="question-block clearfix">
				<div class="question-alt">
					Alcohol consumption (units per week)
				</div>
				<div class="answer-block">
					<div class="answer">
						<input class="required input-mini" id="riskFactorAlcohol" type="text"
							data-bind="value:admissionDetails().riskFactors().alcohol" size="2" width="2">
						
					</div>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
		
			<div class="page-header">
				<h3>Medication on admission</h3>
			</div>
			
			
            <div class="row row-wide">
            <div class="span2 pull-left">
                
                        <div >
                            <span class="theLabel" 
                                    data-bind="css: {selected :admissionDetails().medication().lipidLowering() ==='true' || admissionDetails().medication().lipidLowering() ==='false' }"
                                        >Lipid lowering</span>
                        </div>
                        
                       <div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().medication().lipidLowering" 
                                   name="lipidLowering" 
                                   id="lipidLoweringYes"
                                   value="true" /> 
                            <label class="radio-label" for="lipidLoweringYes">Yes</label>
        				</div>
        				<div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().medication().lipidLowering"
                                   name="lipidLowering" 
                                   id="lipidLoweringNo"
                                   value="false" />
                            <label class="radio-label" for="lipidLoweringNo">No</label>
                        </div>
                
            </div>
            
            <div class="span2 pull-left">
                
                        <div >
                            <span class="theLabel" 
                                    data-bind="css: {selected :admissionDetails().medication().warfarin() ==='true' || admissionDetails().medication().warfarin() ==='false' }"
                                        >Anticoagulation</span>
                        </div>
                       <div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().medication().warfarin" 
                                   name="warfarin" 
                                   id="warfarinYes"
                                   value="true" /> 
                            <label class="radio-label" for="warfarinYes">Yes</label>
        				</div>
        				<div class="pull-left answer vspace1">
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().medication().warfarin"
                                   name="warfarin" 
                                   id="warfarinNo"
                                   value="false" />
                            <label class="radio-label" for="warfarinNo">No</label>
                        </div>
                
            </div>
            
            <div class="span2 pull-left">
                
                        <div >
                            <span class="theLabel" 
                                    data-bind="css: {selected :admissionDetails().medication().antiplatelet() ==='true' || admissionDetails().medication().antiplatelet() ==='false' }"
                                        >Antiplatelet</span>
                        </div>
                       <div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().medication().antiplatelet" 
                                   name="antiplatelet" 
                                   id="antiplateletYes"
                                   value="true" /> 
                            <label class="radio-label" for="antiplateletYes">Yes</label>
        				</div>
        				<div class="pull-left answer vspace1"  >
                            <input type="radio" 
                                   data-bind="checkedWithToggle: admissionDetails().medication().antiplatelet"
                                   name="antiplatelet" 
                                   id="antiplateletNo"
                                   value="false" />
                            <label class="radio-label" for="antiplateletNo">No</label>
                        </div>
                
            </div>
            </div>

			<div class="clearfix spacer-1"></div>

			<div class="question-block">
				<div class="question-alt">
					<label>Did the patient live alone prior to admission?</label>
				</div>
				
				<div class="answer-block">
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().lifeStyle().livedAlone"
							name="livedAlone" id="livedAloneYes" value="true" /> 
							<label for="livedAloneYes">Yes</label>
					</div>
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().lifeStyle().livedAlone"
							name="livedAlone" id="livedAloneNo" value="false" /> 
							<label for="livedAloneNo">No</label>
					</div>
		
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().lifeStyle().livedAlone"
							name="livedAlone" id="livedAloneUnknown" value="unknown" /> 
							<label for="livedAloneUnknown">Unknown</label>
					</div>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
			
			<div class="question-block">
				<div class="question-alt">
					<label>Was the patient independent in everyday activities before
						the stroke?</label>
				</div>

				<div class="answer-block">
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().independent"
							name="independent" id="independentYes" value="yes" /> 
							<label for="independentYes">Yes</label>
					</div>
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().independent"
							name="independent" id="independentNo" value="no" /> 
							<label			
							for="independentNo">No</label>
					</div>
		
					<div class="answer">
						<input type="radio"
							data-bind="checkedWithToggle: admissionDetails().independent"
							name="independent" id="independentUnable" value="unable" /> 
							<label for="independentUnable">Unable</label>
					</div>
	
				</div>
			</div>
		</div>
	</div>
	<g:render template="/templates/sectionFoot"></g:render>
</div>