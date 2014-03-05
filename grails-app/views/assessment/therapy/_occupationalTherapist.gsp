<div class="page-header">
	<h1>Occupational Therapy</h1>
</div>

<div class=" span-30 prepend-top append-bottom" >
	<h5>Has an occupational therapy assessment been performed?</h5>
</div>
<div class="span-1"  style="min-height:10px;">
	<p></p>
</div>

<div class="span-27 prepend-3">
	<table>

		<tbody class="therapy">
			<tr>
				<td><strong>Within 72 hours</strong> <small>(BEFORE  <span data-bind="text: threeDaysLater"></span>)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="occTherapyAsmt72Y" name="occTherapyAsmt72" value="true"  data-bind="checked: therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs"/><label for="occTherapyAsmt72Y">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="occTherapyAsmt72N" name="occTherapyAsmt72" value="false" data-bind="checked: therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs"/><label for="occTherapyAsmt72N">N</label>
					</span>
				</td>
				
				<td>
					<span data-bind="visible: therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs() == 'true'">
						<span data-bind="inError:'occupationalTherapyManagement.assessmentDate therapyManagement.occupationalTherapyManagement.assessmentDate'">						
							Date <input data-bind="value: therapyManagement.occupationalTherapyManagement().assessmentDate, event: {change:checkAsmtTime}" style="width:106px" class="standardDatePicker "/> 
						</span>
					|
						<span class="value " 
							data-bind="inError:'occupationalTherapyManagement.assessmentTime therapyManagement.occupationalTherapyManagement.assessmentTime'">
							Time <g:textField style="width:50px;" class="time"
								data-bind="value: therapyManagement.occupationalTherapyManagement().assessmentTime, event: {change:checkAsmtTime}"
								name="assessmentTime" value="" />
						</span>
					</span>
					
					<span data-bind="visible: therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs() == 'false'">
						Reason
						<select id="otTherapyNo72HrAssessmentReason" data-bind="options: noAssessmentReasonOptions, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.occupationalTherapyManagement().otTherapyNo72HrAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>
			<tr data-bind="visible: therapyManagement.occupationalTherapyManagement().assessmentPerformedIn72Hrs() == 'false'">
				<td><strong>After 72 hours</strong> <small>(BEFORE DISCHARGE)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="occTherapyAsmtY" name="occTherapyAsmt" value="true" data-bind="checked: therapyManagement.occupationalTherapyManagement().assessmentPerformed"/><label for="occTherapyAsmtY">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="occTherapyAsmtN" name="occTherapyAsmt" value="false" data-bind="checked: therapyManagement.occupationalTherapyManagement().assessmentPerformed"/><label for="occTherapyAsmtN">N</label>
					</span>
				</td>				
				<td>
				<span data-bind=", visible: therapyManagement.occupationalTherapyManagement().assessmentPerformed() == 'true'">
					<span data-bind="inError:'occupationalTherapyManagement.assessmentDate therapyManagement.occupationalTherapyManagement.assessmentDate'">
							Date <input data-bind="value: therapyManagement.occupationalTherapyManagement().assessmentDate, event: {change:checkAsmtTime2}" style="width:106px" class="standardDatePicker "/> 
					</span>
					|
					<span class="value  " 
						data-bind="inError:'occupationalTherapyManagement.assessmentTime therapyManagement.occupationalTherapyManagement.assessmentTime'">
						Time <g:textField style="width:50px;" class="time"
							data-bind="value: therapyManagement.occupationalTherapyManagement().assessmentTime,  event: {change:checkAsmtTime2}"
							name="assessmentTime" value="" />
					</span>					
				</span>
					<span data-bind="visible: therapyManagement.occupationalTherapyManagement().assessmentPerformed() == 'false'">
						Reason
						<select id="otTherapyNoAssessmentReason" data-bind="options: noAssessmentReasonOptions, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.occupationalTherapyManagement().otTherapyNoAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>			
		</tbody>
	</table>
</div>
<%--

--%>


<div class="clearfix"></div>

<div>
	<div class="page-header"><h2>Mood assessment</h2></div>

	<div class="question-block">
		<div class="question-alt">
			<label>Has a mood assessment been performed using a validated tool?</label>
		</div>
		<div class="answer-block">
			<g:buttonRadioGroup class="answer" 
			 									name="moodAssessmentPerformed"
												labels="['Yes','No' ]" 
												values="['true','false']"
												spans="[3,3]"
												bind="therapyManagement.occupationalTherapyManagement()"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
		</div>	
	</div>

	<div class="clearfix"></div>
			
	<div id="moodAssessmentDateTime" data-bind="visible: therapyManagement.occupationalTherapyManagement().moodAssessmentPerformed()=='true'">
		<div class="question-block">
			<div class="answer-block">
				<div class="form-inline">
		  			<label for="moodAssessmentDate">Date</label>
		  				  		
			 		<div class="value" data-bind="inError:'occupationalTherapyManagement.moodAssessmentDate therapyManagement.occupationalTherapyManagement.moodAssessmentDate'">
						<g:textField   data-bind="value: therapyManagement.occupationalTherapyManagement().moodAssessmentDate"  class="standardDatePicker "  name="moodAssessmentDate" id="moodAssessmentDate"  
								value="" />
					</div>
				</div>
			</div>
		</div>	
	</div>

	<div class="clearfix"></div>

	<div id="noMoodAssessmentReasons" data-bind="visible:  therapyManagement.occupationalTherapyManagement().moodAssessmentPerformed() == 'false' ">
	
		<div class="question-block">	
	    	<div class="question-alt"  data-bind="inError:'therapyManagement.occupationalTherapyManagement.noMoodAssessmentReasonType'">
	        	<label>What was the reason?</label>
	    	</div>
	   
	 		<div class="answer-block">
				<div class="answer">
		        	<input  type="radio"  
		                data-bind="checkedWithToggle: therapyManagement.occupationalTherapyManagement().noMoodAssessmentReason" 
		                name="noMoodAssessmentReason" 
		                id="noMoodAssessmentReasonUnwell" 
		                value="unwell" />
		        	<label 
		               for="noMoodAssessmentReasonUnwell" >Patient Unwell</label>
		    	</div>
		    	
		    	<div class="answer">
		        	<input  type="radio"  
		                data-bind="checkedWithToggle: therapyManagement.occupationalTherapyManagement().noMoodAssessmentReason" 
		                name="noMoodAssessmentReason" 
		                id="noMoodAssessmentReasonRefused" 
		                value="refused" />
		        	<label  
		               for="noMoodAssessmentReasonRefused" >Patient Refused</label>
		    	</div>
		    
		   		<div class="answer">
		        	<input  type="radio"  
		                data-bind="checkedWithToggle: therapyManagement.occupationalTherapyManagement().noMoodAssessmentReason" 
		                name="noMoodAssessmentReason" 
		                id="noMoodAssessmentReasonOrganisational" 
		                value="organisational" />
		        	<label  
		               for="noMoodAssessmentReasonOrganisational" >Organisational reasons</label>
		    	</div>
		    	    
		    	<div class="answer">
		        	<input  type="radio"  
		                data-bind="checkedWithToggle: therapyManagement.occupationalTherapyManagement().noMoodAssessmentReason" 
		                name="noMoodAssessmentReason" 
		                id="noMoodAssessmentReasonUnknown" 
		                value="unknown" />
		        	<label  
		               for="noMoodAssessmentReasonUnknown" >Unknown</label>
		    	</div>  
	    	</div>
	    </div>
	</div>
</div>

