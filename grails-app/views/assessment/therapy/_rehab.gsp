<div class="page-header">
	<h1>MDT Rehabilitation Goals</h1>
</div>

<div class="question-block">
	<div class="question-alt">
		<label>Has the patient had agreed and documented MDT rehabilitation goals?</label>
	</div>

	<div class="answer-block">
		<g:buttonRadioGroup
			class="answer"
			name="rehabGoalsSet" labels="['Yes' , 'No']" values="['true','false']"
			bind="therapyManagement"
			spans="[3,3]" value="">
			${it.radio}
			${it.label}
		</g:buttonRadioGroup>
	</div>
</div>
		

<div class="question-block" id="rehabGoalsDateTime" data-bind="visible: therapyManagement.rehabGoalsSet() == 'true' ">
	
	<div class="answer-block">
		<div class="answer" data-bind="inError:'therapyManagement.rehabGoalsSetDate'">
		<label for="rehabGoalsDate">Date</label>
			  <g:textField style="width:106px" class="standardDatePicker " data-bind="value: therapyManagement.rehabGoalsSetDate"
				name="rehabGoalsDate" id="rehabGoalsDate"  value="" />
		</div>
	</div>
	
</div>

<div class="question-block" id="noRehabGoalsReasons" data-bind="visible: therapyManagement.rehabGoalsSet() === 'false'    ">
	<div class="question-alt" data-bind="inError:'therapyManagement.rehabGoalsNotSetReasonType'">
		<label>What was the reason?</label>
	</div>
	<div class="answer-block">
	<div class="answer">
		<input  type="radio"  
	            data-bind="checkedWithToggle: therapyManagement.rehabGoalsNotSetReason" 
	            name="rehabGoalsNotSetReason" 
	            id="rehabGoalsNotSetReasonUnwell" 
	            value="unwell" />
	    <label style="width:210px;margin-right:5px;margin-top:10px; vertical-align:top;" 
	           for="rehabGoalsNotSetReasonUnwell" >Patient Unwell</label>
	</div>
	<div class="answer">
		<input  type="radio"  
	            data-bind="checkedWithToggle: therapyManagement.rehabGoalsNotSetReason" 
	            name="rehabGoalsNotSetReason" 
	            id="rehabGoalsNotSetReasonRefused" 
	            value="refused" />
	    <label style="width:210px;margin-right:5px;margin-top:10px; vertical-align:top;" 
	           for="rehabGoalsNotSetReasonRefused" >Patient Refused</label>
    </div>
    
    <div class="answer">
        <input  type="radio"  
                data-bind="checkedWithToggle: therapyManagement.rehabGoalsNotSetReason" 
                name="rehabGoalsNotSetReason" 
                id="rehabGoalsNotSetReasonNoproblem" 
                value="noproblem" />
        <label style="width:210px;margin-right:5px;margin-top:10px; vertical-align:top;" 
               for="rehabGoalsNotSetReasonNoproblem" >No Impairments</label>
    </div>
    
    <div class="clearfix vspace1"></div>
        
    <div class="answer">
        <input  type="radio"  
                data-bind="checkedWithToggle: therapyManagement.rehabGoalsNotSetReason" 
                name="rehabGoalsNotSetReason" 
                id="rehabGoalsNotSetReasonNoPotential" 
                value="nopotential" />
        <label style="width:210px;margin-right:5px;margin-top:10px; vertical-align:top;" 
               for="rehabGoalsNotSetReasonNoPotential" >No Rehabilitation Potential</label>
    </div>
    
    
    <div class="answer">
        <input  type="radio"  
                data-bind="checkedWithToggle: therapyManagement.rehabGoalsNotSetReason" 
                name="rehabGoalsNotSetReason" 
                id="rehabGoalsNotSetReasonOrganisational" 
                value="organisational" />
        <label style="width:210px;margin-right:5px;margin-top:10px; vertical-align:top;" 
               for="rehabGoalsNotSetReasonOrganisational" >Organisational reasons</label>
    </div>
    
    
    <div class="answer">
        <input  type="radio"  
                data-bind="checkedWithToggle: therapyManagement.rehabGoalsNotSetReason" 
                name="rehabGoalsNotSetReason" 
                id="rehabGoalsNotSetReasonUnknown" 
                value="unknown" />
        <label style="width:210px;margin-right:5px;margin-top:10px; vertical-align:top;" 
               for="rehabGoalsNotSetReasonUnknown" >Unknown</label>
    </div>
    </div>
 </div>
    
   
<!--  end of Cognitive -->
