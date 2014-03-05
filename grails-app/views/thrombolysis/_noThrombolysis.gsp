<!-- Only shown if no selected -->
<g:set var="reasonsNotThrombolysed" value="${careActivityInstance?.getReasonsNotThrombolysed()}" />
<div id="noThrombolysis" data-bind="visible: thrombolysisManagement.givenThrombolysis() === 'no'  ">

<div class="question-block">
	<div class=" question-alt ${inError(errorList:errorList, field:'thrombolysis')}">
	 	<label>What were the reasons for not giving thrombolysis?</label> 
	 </div>

	<div class="answer-block">
		 
	<div class="answer-single-column" data-bind="visible:showNone">
		<g:checkBox name="noThrombolysisNone" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNone"   ></g:checkBox>
		<label for="noThrombolysisNone">None</label>
	</div>
	
	<div class="answer-single-column" data-bind="visible:showNotAvailable">
		<g:checkBox name="noThrombolysisNotAvailable" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisNotAvailable" value="${reasonsNotThrombolysed?.noThrombolysisNotAvailable}"/>
		<label for="noThrombolysisNotAvailable">Not available at centre</label>
	</div>
	<div class="answer-single-column" data-bind="visible:showOutsideHours">
		<g:checkBox name="noThrombolysisOutsideHours" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOutsideHours" value="${reasonsNotThrombolysed?.noThrombolysisOutsideHours}"/>
		<label for="noThrombolysisOutsideHours">Arrived outside normal service hours</label>
	</div>
	 <div class="answer-single-column" data-bind="visible:showScanLate">
			<g:checkBox name="noThrombolysisScanLate" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisScanLate" value="${reasonsNotThrombolysed?.noThrombolysisScanLate}"/>
			<label for="noThrombolysisScanLate">Unable to scan in time</label>
	</div>
	
	<div data-bind="visible:noBut">
	<div class="answer-single-column">
			<g:checkBox name="noThrombolysisTooLate" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisTooLate" value="${reasonsNotThrombolysed?.noThrombolysisTooLate}"/>
			<label for="noThrombolysisTooLate">Arrived outside time window</label>
	</div>
	<div class="answer-single-column">
			<g:checkBox name="noThrombolysisComorbidity" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisComorbidity" value="${reasonsNotThrombolysed?.noThrombolysisComorbidity}"/>
			<label for="noThrombolysisComorbidity">Contra-indicated due to co-morbidity</label>
	</div>
	<div class="answer-single-column">
			<g:checkBox name="noThrombolysisAge" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisAge" value="${reasonsNotThrombolysed?.noThrombolysisAge}"/>
			<label for="noThrombolysisAge">Contra-indicated due to age</label>
	</div>
	<div class="answer-single-column">
			<g:checkBox name="noThrombolysisMedication" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisMedication" value="${reasonsNotThrombolysed?.noThrombolysisMedication}"/>
			<label for="noThrombolysisMedication">Contra-indicated due to medication</label>
	</div>
	<div class="answer-single-column">
		<g:checkBox name="noThrombolysisRefused" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisRefused" value="${reasonsNotThrombolysed?.noThrombolysisRefused}"/>
		<label for="noThrombolysisRefused">Patient or relative refusal</label>
	</div>  
	
	<div class="answer-single-column">
			<g:checkBox name="noThrombolysisSymptomsImproving" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisSymptomsImproving" value="${reasonsNotThrombolysed?.noThrombolysisSymptomsImproving}"/>
			<label for="noThrombolysisSymptomsImproving">Symptoms improving</label>
	</div>
	<div class="answer-single-column">
		<g:checkBox name="noThrombolysisTooMildOrTooSevere" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisTooMildOrTooSevere" value="${reasonsNotThrombolysed?.noThrombolysisTooMildOrTooSevere}"/>
		<label for="noThrombolysisTooMildOrTooSevere">Stroke too mild or too severe</label>
	</div>  
	
	
	<div class="answer-single-column">
        <g:checkBox name="noThrombolysisHaemorrhagicStroke" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisHaemorrhagicStroke" value="${reasonsNotThrombolysed?.noThrombolysisHaemorrhagicStroke}"/>
        <label for="noThrombolysisHaemorrhagicStroke">Haemorrhagic stroke</label>
    </div>  
    
	
	
	
	<div class="answer-single-column">
		<g:checkBox name="noThrombolysisOnsetTimeUnknown" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOnsetTimeUnknown" value="${reasonsNotThrombolysed?.noThrombolysisOnsetTimeUnknown}"/>
		<label for="noThrombolysisOnsetTimeUnknown">Symptoms onset time unknown</label>
	</div>  
	
	
	 <div class="answer-single-column">
	 	<g:checkBox name="noThrombolysisOther" data-bind="checkBoxAsButton: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOther" value="${reasonsNotThrombolysed?.noThrombolysisOther}"/>
	 	<label for="noThrombolysisOther">Other</label>
	</div>	
	
	<div class="answer-single-column" id="noThrombolysisOtherDetail" data-bind="visible: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOther()   ">
		<div class=" noThrombolysisOtherText" >
			<div class="control-group">
				<div class="control-label">If other, please specify</div>
				<div class="controls">
					<g:textField  data-bind="value: thrombolysisManagement.reasonsNotThrombolysed().noThrombolysisOtherText" name="noThrombolysisOtherText" value="${reasonsNotThrombolysed?.noThrombolysisOtherText}" />
				</div>
			</div>
		</div>
	</div>
	
	</div>
	</div>
</div>	
</div>