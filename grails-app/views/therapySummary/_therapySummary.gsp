<div id="therapySummaryForm">
	<g:render template="/templates/sectionHead"></g:render>		
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	
	<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
	<div data-bind="html: errorsAsList"></div> 
	 
	 <div class="page-header">
	 	<h1>Therapy Summary</h1>
	 </div>
	 
	 <div class="question-block">
	 
	 <div class="question-alt">
	   <label>Was the patient considered to require any of the following therapies at any point during this admission?</label>
	 </div>

	 <div class="answer-block">           
            <div class="">
                <%-- label class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" --%>
                        <div class="answer">
                            <span class="theLabel span-6" 
                            data-bind="css: {selected:therapySummary.requiredTherapies().physiotherapy() ==='true' || therapySummary.requiredTherapies().physiotherapy() ==='false' }"
                                >Physiotherapy</span>
                        <%-- /div>
                       <div class="transparent" --%>
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().physiotherapy" 
                                   name="physiotherapyRequired" 
                                   id="physiotherapyRequiredYes"
                                   value="true" /> 
                            <label class="radio-label" for="physiotherapyRequiredYes">Yes</label>
        
        
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().physiotherapy"
                                   name="physiotherapyRequired" 
                                   id="physiotherapyRequiredNo"
                                   value="false" />
                            <label class="radio-label" for="physiotherapyRequiredNo">No</label>
                        </div>
                <%-- /label--%>
            </div>
            
            
             <div class="">
                <%-- label class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" --%>
                        <div class="answer" >
                            <span class="theLabel span-6" 
                            data-bind="css: {selected:therapySummary.requiredTherapies().occupational() ==='true' || therapySummary.requiredTherapies().occupational() ==='false' }"
                                >Occupational Therapy</span>
                        <%-- /div>
                       <div class="transparent"  --%>
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().occupational" 
                                   name="occupationalRequired" 
                                   id="occupationalRequiredYes"
                                   value="true" /> 
                            <label class="radio-label" for="occupationalRequiredYes">Yes</label>
        
        
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().occupational"
                                   name="occupationalRequired" 
                                   id="occupationalRequiredNo"
                                   value="false" />
                            <label class="radio-label" for="occupationalRequiredNo">No</label>
                        </div>
                <%--/label --%>
            </div>
            
            
              <div class="">
                <%--label class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" --%>
                        <div class="answer">
                            <span class="theLabel span-6" 
                            data-bind="css: {selected:therapySummary.requiredTherapies().salt() ==='true' || therapySummary.requiredTherapies().salt() ==='false' }"
                                >Speech And Language Therapy</span>
                        <%--/div>
                       <div class="transparent"   --%>
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().salt" 
                                   name="saltRequired" 
                                   id="saltRequiredYes"
                                   value="true" /> 
                            <label class="radio-label" for="saltRequiredYes">Yes</label>
        
        
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().salt"
                                   name="saltRequired" 
                                   id="saltRequiredNo"
                                   value="false" />
                            <label class="radio-label" for="saltRequiredNo">No</label>
                        </div>
                <%-- /label  --%>
            </div>
            
               <div class="">
                <%-- label class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" --%>
                        <div >
                            <span class="theLabel span-6" 
                            data-bind="css: {selected:therapySummary.requiredTherapies().psychology() ==='true' || therapySummary.requiredTherapies().psychology() ==='false' }"
                                >Psychology</span>
                        <%--/div>
                       <div class="transparent"  --%>
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().psychology" 
                                   name="psychologyRequired" 
                                   id="psychologyRequiredYes"
                                   value="true" /> 
                            <label class="radio-label" for="psychologyRequiredYes">Yes</label>
        
        
                            <input type="radio" 
                                   data-bind="checkedWithToggle: therapySummary.requiredTherapies().psychology"
                                   name="psychologyRequired" 
                                   id="psychologyRequiredNo"
                                   value="false" />
                            <label class="radio-label" for="psychologyRequiredNo">No</label>
                        </div>
                <%-- /label  --%>
            </div>
             
           </div>
	</div>  
                
                
	<div class="clearfix"></div>





		<div class="question-block form-horizontal">

			<div class="question-alt">
				<label>On how many days did the patient receive this therapy
					across their total stay in this hospital/team?</label>
			</div>

			<div class="answer-block-compact">

				<div class="control-group" data-bind="inError: 'therapyManagement.physiotherapyManagement.daysOfTherapy'">
					<label for="daysOfPhysiotherapy" class="control-label">Physiotherapy</label>

					<div class="controls">
						<input style="width: 50px;" id="daysOfPhysiotherapy"
							class="daysOfTherapy" type="text"
							data-bind="value:therapySummary.daysOfTherapy().physiotherapy">
					</div>
				</div>

				<div class="control-group" data-bind="inError: 'therapyManagement.physiotherapyManagement.daysOfOccupational'">
					<label for="daysOfOccupational" class="control-label">Occupational
						Therapy</label>

					<div class="controls">
						<input style="width: 50px;" class="daysOfTherapy"
							id="daysOfOccupational" type="text"
							data-bind="value:therapySummary.daysOfTherapy().occupational">
					</div>
				</div>

				<div class="control-group" data-bind="inError: 'therapyManagement.physiotherapyManagement.daysOfSalt'">
					<label for="daysOfSalt" class="control-label">Speech &amp;
						Language Therapy</label>

					<div class="controls">
						<input style="width: 50px;" class="daysOfTherapy" id="daysOfSalt"
							type="text" data-bind="value:therapySummary.daysOfTherapy().salt">
					</div>
				</div>

				<div class="control-group" data-bind="inError: 'therapyManagement.physiotherapyManagement.daysOfPsychology'">
					<label for="daysOfPsychology" class="control-label">Psychology</label>

					<div class="controls">
						<input style="width: 50px;" class="daysOfTherapy"
							id="daysOfPsychology" type="text"
							data-bind="value:therapySummary.daysOfTherapy().psychology">
					</div>
				</div>

			</div>
		</div>

		<div class="clearfix"></div>

		<div class="question-block">
		    
			<div class="question-alt">
		       <label>How many minutes in total did the patient receive during their stay/team?</label>
		    </div>
	
			<div class="answer-block-compact form-horizontal">	
			
				<div class="control-group" data-bind="inError: 'therapyManagement.physiotherapyManagement.minutesOfTherapy '">
					<label for="minutesOfPhysiotherapy" class="control-label">Physiotherapy</label>
		         
					<div class="controls">
						<input style="width:50px;"  class="minutesOfTherapy"  id="minutesOfPhysiotherapy" type="text" data-bind="value:therapySummary.minutesOfTherapy().physiotherapy">						
					</div> 
		    	</div>
		    	
				<div class="control-group">
					<label for="minutesOfOccupational" class="control-label">Occupational Therapy</label>
		         
					<div class="controls">
			                <input style="width:50px;"  class="minutesOfTherapy"  id="minutesOfOccupational" type="text" data-bind="value:therapySummary.minutesOfTherapy().occupational">			               
					</div> 			
				</div>			
				
			  
				<div class="control-group">
					<label for="minutesOfSalt" class="control-label">Speech &amp; Language Therapy</label>
				 
					<div class="controls">
						<input style="width:50px;" class="minutesOfTherapy" id="minutesOfSalt" type="text" data-bind="value:therapySummary.minutesOfTherapy().salt">						
					</div> 
				</div>
				
			
				
				<div class="control-group">
					<label for="minutesOfPsychology" class="control-label">Psychology</label>
				
					<div class="controls">
						<input style="width:50px;"  class="minutesOfTherapy" id="minutesOfPsychology" type="text" data-bind="value:therapySummary.minutesOfTherapy().psychology">						
					</div>
				</div> 
			</div>
		</div>
	</div>
	

<!-- 
	<hr />
<h2>Debug</h2>

<div data-bind="text: ko.toJSON(clinicalSummaryViewModel)"></div>

 -->
 
	
</div>
