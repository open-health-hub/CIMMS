<div class="pull-left sidebar" >
    
    <div class="sidebar-head">	    
		<h4>Imaging</h4>
    </div>
    
    <tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="imaging"/>
</div>
<div id="imagingForm" class="imagingView data-entry-narrow">
	<g:render template="/templates/sectionHead"></g:render>		
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	
	<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
	   <div class="warnings" data-bind="visible: hasWarnings()">
         <ul>
            <li data-bind="text: warningMessage">
         </ul>
       </div>
	   <div data-bind="html: errorsAsList"></div>	
	   <div id="imaging">

			<div class="page-header"><h1>Imaging</h1></div>	
			
			<div class="question-block">
				<div class="question-alt">
					<label>Did the patient have a brain scan after the stroke?</label>
				</div>
			
				<div class="answer-block">
					<div class="answer"><input type="radio" name="scanPostStroke" data-bind="checkedWithToggle: imaging.scanPostStroke" value="yes" id="scanPostStrokeYes"><label for="scanPostStrokeYes">Yes</label></div>
					<div class="answer"><input type="radio" name="scanPostStroke" data-bind="checkedWithToggle: imaging.scanPostStroke" value="no" id="scanPostStrokeNo"><label for="scanPostStrokeNo">No</label></div>
					<div class="answer"><input type="radio" name="scanPostStroke" data-bind="checkedWithToggle: imaging.scanPostStroke" value="unknown" id="scanPostStrokeUnknown"><label for="scanPostStrokeUnknown">Unknown</label></div>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
			
			<div id="scanDeatils" data-bind="visible: imaging.scanPostStroke() == 'yes' ">
			
				<div class="question-block">
					<div class="question-alt" >
						<label>When was the imaging requested?</label>
					</div>
					<div class="answer-block">
					
						<div class="answer" >
				  			<label>Date</label>
				  		</div>
				 		<div class="answer value "  data-bind="inError:'imaging.scan.requestDate'">
							<g:textField   data-bind="value: imaging.scan().requestDate"  class="standardDatePicker date"  name="requestDate"  
									value="" />
						</div>	
					
						<div class="answer">
							Time
						</div>	
						<div class="answer value "  data-bind="inError:'imaging.scan.requestTime'">
							<g:textField  data-bind="value: imaging.scan().requestTime"  class="time" name="requestTime" 
								 value=""   />
						</div>
					</div>
				</div>
				<div class="clearfix spacer-1"></div>
					
				<div class="question-block">			
					<div class="question-alt" >
							<label>When was the image completed?</label>
					</div>
					
					<div class="answer-block">
						
							<div class="answer" >
					  			<label>Date</label>
					  		</div>
			  		
			 				<div class=" answer value  " data-bind="inError:'imaging.scan.takenDate'">
								<g:textField   data-bind="value: imaging.scan().takenDate"  class="standardDatePicker date"  name="takenDate"  
								value="" />
							</div>
								
							<div class=" answer ">
								<label>Time</label>
							</div>
								
							<div class=" answer value  "  data-bind="inError:'imaging.scan.takenTime'">
								<g:textField  data-bind="value: imaging.scan().takenTime"  class="time" name="takenTime" 
							 		value=""   />
							</div>
							
			                <g:if test="${grailsApplication.config.trust.name.equals('anhst')}">
								<div class="answer" >
				                    <input type="checkbox" data-bind="uniqueId:true, checkBoxAsButton: imaging.scan().takenOverride" />
				                    <label data-bind="uniqueFor:true"  >Override Warning</label>
				                </div>
							</g:if>
				</div>				
			</div>				
			
			
			<div class="clearfix spacer-1"></div>
					
			<div class="question-block">	
				<div class="question-alt">
					<label>What type of image was taken?</label>
				</div>
				<div class="answer-block">	
					<g:buttonRadioGroup  class="answer"
			 									name="imageType"
												labels="['CT','MRI']" 
												values="['CT','MRI']"
												spans="[3,3 ]"
												bind="imaging.scan()"
												value="" >
														${it.radio} 
														${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
					
			<div class="question-block">	
				<div class="question-alt">
					<label>What was the diagnosis?</label>
				</div>
				<div class="answer-block" >				
					<g:buttonRadioGroup  class="answer"
					 									name="imageDiagnosisCategory"
														labels="['Stroke','TIA', 'Other']" 
														values="['Stroke','TIA', 'Other']"
														spans="[8,8,8]"
														spaceEvery="3"
														bind="imaging.scan()"
														value="" >
																${it.radio} 
																${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>
			
			<div data-bind="visible: imaging.scan().imageDiagnosisCategory()=='Stroke'" >
			
			<div class="clearfix spacer-1"></div>
			
			<div class="question-block">	
				<div class="question-alt">					
					<label>What was the type of stroke?</label>
				</div>
				<div class="answer-block" >					
					<g:buttonRadioGroup  class="answer" 
			 									name="imageDiagnosisType"
												labels="['Infarction','Primary Intercerebral Haemorrhage']" 
												values="['Infarct','Intercerebral_Haemorrhage']"
												spans="[8,8]"
												spaceEvery="3"
												bind="imaging.scan()"												
												value="" >
														${it.radio} 
														${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>
			<!--  
			<div class=" " data-bind="visible: imaging.scan().imageDiagnosisType()=='Other', inError:'imaging.scan.diagnosisTypeOther'">
				<p>Please specify: </p>
				<g:textField name="imageDiagnosisTypeOtherText" data-bind="value: imaging.scan().imageDiagnosisTypeOther"  />
			</div>
				
			-->	
			</div>
			
			
			
			</div>
			
			
			<div data-bind="visible: imaging.scanPostStroke() == 'no' ">
			
			<div class="clearfix spacer-1"></div>
			
			<div class="question-block">	
				<div class="question-alt">					
					<label>Why did the patient not have a scan?</label>
				</div>
				<div class="answer-block" >					
					<g:buttonRadioGroup  class="answer-single-column"  
			 									name="noScanReason"
												labels="['Patient refused/ unable to co-operate','Palliative care' , 'Scan not routinely available', 'Not clinically indicated']" 
												values="['refused','palliative', 'notRoutine', 'notIndicated']"
												spans="[8,8,8,8]"
												spaceEvery="2"
												bind="imaging"
												value="" >
														${it.radio} 
														${it.label}
					</g:buttonRadioGroup>
			
				</div>
			
			</div>
				
		</div>
	</div>

</div>
<!-- 
	<hr />
<h2>Debug</h2>

<div data-bind="text: ko.toJSON(imagingViewModel)"></div>

 -->
 
	
</div>
