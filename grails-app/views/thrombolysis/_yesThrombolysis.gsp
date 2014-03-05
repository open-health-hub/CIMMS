<div id="thrombolysisDetail" data-bind="visible: thrombolysisManagement.givenThrombolysis() === 'yes'  ">
	<div class="clearfix"	></div>
	<div class="question-block">
		<div class="question-alt">
			<label>Date and time the patient was thrombolysed <small>(Must be within 7 hours of admission or onset if onset occurred in hospital)</small></label>
	 	</div>		
	   	<div class="answer-block" >	   
		   	<div class="answer"  data-bind="css: { errors: $.inArray( 'thrombolysis.thrombolysisDate', fieldsInError() ) >= 0}" >
		   	    <label for="thrombolysisDate">Date</label>
				<g:textField 
				     id="thrombolysisDate" 
					 class="standardDatePicker date" 
					 data-bind="value: thrombolysisManagement.thrombolysis().thrombolysisDate"  name="dateThrombolysed"  value="${careActivityInstance?.thrombolysis?.thrombolysisDate?.format('dd/MM/yyyy')}" />
	 		</div>
	 		<div class="answer" data-bind="css: { errors: $.inArray( 'thrombolysis.thrombolysisTime', fieldsInError() ) >= 0}" >
	 		    <label for="thrombolysisTime">Time</label>
	 			<g:textField 
	 			    id="thrombolysisTime"
	 				class="time" 
	 				name="timeThrombolysed"  
	 				data-bind="value: thrombolysisManagement.thrombolysis().thrombolysisTime" value="${careActivityInstance?.thrombolysis?.thrombolysedTimeAsString()}"    />
	 		</div>
		</div> 	
 	</div>
 	
 	<div class="clearfix"	></div>
 	<div class="question-block">
	 	<div class="question-alt" data-bind="css: { errors: $.inArray( 'thrombolysis.decisionMakerLocation', fieldsInError() ) >= 0}" >
	 		<label>Was the clinician who made the decision to thrombolyse the patient:</label>
	 	</div>
	 	<div class="answer-block">	
	 		<g:buttonRadioGroup  
	 									name="decisionMakerLocation"
										labels="['Present in person','Available via video link' ,'Available via phone with access to imaging']" 
										values="['person','link', 'phone']"
										class="answer"
										bind="thrombolysisManagement.thrombolysis()"									
										value="${careActivityInstance?.thrombolysis?.decisionMakerLocation}" >
												${it.radio} 
												${it.label}
		</g:buttonRadioGroup>	
	 	</div>
	</div>	
 	<div class="clearfix"	></div>
 	<div class="question-block">
		<div class="question-alt" data-bind="css: { errors: $.inArray( 'thrombolysis.complications', fieldsInError() ) >= 0}">
	 		<label>Did the patient have any complications from the thrombolysis?</label>
	 	</div>
	 	<div class="answer-block">	
	 		<g:buttonRadioGroup  
	 									name="complications"
										labels="['Yes','No' ]" 
										values="['true','false']"
										bind="thrombolysisManagement.thrombolysis()"
										class="answer"
										value="${careActivityInstance?.thrombolysis?.complications?.toString()}" >
												${it.radio} 
												${it.label}
			</g:buttonRadioGroup>
		</div>	
	</div>
	
	 <div class="clearfix spacer-1"></div>
	  
	<div id="hadComplications" data-bind="visible: thrombolysisManagement.thrombolysis().complications() === 'true'  ">
	
		<div class="question-block"> 
			<div class=" question-alt ${inError(errorList:errorList, field:'thrombolysis.complicationType')}">
		 		<label>Which of the following?</label>
		 	</div>
		 	
		 	<div class="answer-block" >	
		 	
			 	<div class="answer-single-column">
			        <g:checkBox name="complicationTypeHaemorrhage" 
			                    data-bind="checkBoxAsButton: thrombolysisManagement.thrombolysis().complicationTypeHaemorrhage" 
			                    value=""/>
			        <label  for="complicationTypeHaemorrhage">Symptomatic Brain haemorrhage</label>
			    </div>
			 	
			 	<div class="answer-single-column">
			        <g:checkBox name="complicationTypeOedema" 
			                    data-bind="checkBoxAsButton: thrombolysisManagement.thrombolysis().complicationTypeOedema" 
			                    value=""/>
			        <label  for="complicationTypeOedema">Angio oedema</label>
			    </div>
			 	
			 	<div class="answer-single-column">
			        <g:checkBox name="complicationTypeBleed" 
			                    data-bind="checkBoxAsButton: thrombolysisManagement.thrombolysis().complicationTypeBleed" 
			                    value=""/>
			        <label  for="complicationTypeBleed">Extra-cranial bleed</label>
			    </div>
			    
			    <div class="answer-single-column">
			        <g:checkBox name="complicationTypeOther" 
			                    data-bind="checkBoxAsButton: thrombolysisManagement.thrombolysis().complicationTypeOther" 
			                    value=""/>
			        <label for="complicationTypeOther">Other</label>
			    </div>
		 	
		 	</div>
	 	</div>
 	
	</div>
	
	<!--  HAS COMPLICATIONS  -->
	
	<div class="clearfix spacer-1"></div>
	
	<div id="complicationTypeOtherText"  
	       data-bind="css: { errors: $.inArray( 'thrombolysis.complicationTypeOther', fieldsInError() ) >= 0},visible:thrombolysisManagement.thrombolysis().complications() === 'true'  && thrombolysisManagement.thrombolysis().complicationTypeOther() ===true ">
	       
        <div class="question-block complicationTypeOtherText" >
            <div class="question-alt"><label>If other, please specify:</label></div>
            <div class="answer-block">
            	<g:textField class="answer" data-bind="value: thrombolysisManagement.thrombolysis().complicationTypeOtherText" name="complicationTypeOtherTexts"  />
            </div>
        </div>
        
    </div>
	

	<div class="clearfix spacer-1"></div>
	
	<div class="question-block">
		<div class="question-alt" data-bind="css: { errors: $.inArray( 'thrombolysis.followUpScan', fieldsInError() ) >= 0}">
	 		<label>Did the patient have a follow up scan?</label>
	 	</div>
 	
	 	<div class="answer-block">	
		 	<g:buttonRadioGroup  class="answer"
		 									name="followUpScan"
											labels="['Yes','No' ]" 
											values="['true','false']"
											spans="[3,3]"
											bind="thrombolysisManagement.thrombolysis()"
											value="${careActivityInstance?.thrombolysis?.followUpScan}" >
													${it.radio} 
													${it.label}
			</g:buttonRadioGroup>
		
	    </div>
    </div>
    
    <div class="clearfix spacer-1"></div>
    
	<div id="hadFollowUp" class=" form-horizontal question-block " data-bind="visible: thrombolysisManagement.thrombolysis().followUpScan() === 'true', inError :'thrombolysis.followUpScanDate thrombolysis.followUpScanTime' " >
		<div class="control-group">
			<div class="control-label"><label>Date</label></div>
	 	
		  	<div class="controls">
		 		<g:textField  size="10" class="standardDatePicker date"  data-bind="value: thrombolysisManagement.thrombolysis().followUpScanDate"  name="dateFollowUpScan"  value="${careActivityInstance?.thrombolysis?.followUpScanDate?.format('dd/MM/yyyy')}"    />
		 	</div>	
	 	</div>
	 	
		<div class="control-group">
			<div class="control-label"><label>Time</label></div>
	 		<div class="controls">
	 			<g:textField  class="time" data-bind="value: thrombolysisManagement.thrombolysis().followUpScanTime" name="timeFollowUpScan"  value="${careActivityInstance?.thrombolysis?.followUpScanTimeAsString()}"    />
	 		</div>
	 	</div>
 	</div>	
 	
 	<div class="clearfix spacer-1"></div>
 	
 	<div  class=" question-block " data-bind="inError :'thrombolysis.followUpScanDate thrombolysis.nihssScoreAt24Hours' " >
	 	<div class=" question-alt ">
	 		<label>What was the patient's NIHSS score at 24 hours?</label>
	 	</div>
 	
 		<div class="answer-block"  >
			<g:textField  class="answer  input-small" name="nihssScoreAt24Hours" size="5" 
					data-bind="value: thrombolysisManagement.thrombolysis().nihssScoreAt24Hours, visible:!thrombolysisManagement.thrombolysis().nihssScoreAt24HoursUnknown()" />
		

			<div class="answer " data-bind="css: { 'highlight' : thrombolysisManagement.thrombolysis().nihssScoreAt24HoursUnknown()}">
		                <g:checkBox
		                    data-bind="checkBoxAsButton: thrombolysisManagement.thrombolysis().nihssScoreAt24HoursUnknown"
		                    name="nihssScoreAt24HoursUnknown" />
		                <label for="nihssScoreAt24HoursUnknown">Unknown</label>
		    </div>
		</div>
	</div>	
		<div class="clearfix spacer-1"></div>
        <%-- 
        <hr />
        <h2>Debug</h2>
        <div data-bind="text: ko.toJSON(thrombolysisViewModel)"></div>
        --%>
	
 								  		
</div>



	