
<div class="page-header">
	<h1>Physiotherapy</h1>
</div>

<div class="vspace1">
	<label>Has the patient been assessed by a physiotherapist?</label>
</div>



<div class="therapy">
	<table>

		<tbody class="therapy">
			<tr>
				<td><strong>Within 72 hours</strong> <small>(BEFORE  <span data-bind="text: threeDaysLater"></span>)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="physioTherapyAsmt72Y" name="physioTherapyAsmt72" value="true"  data-bind="checked: therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs"/><label for="physioTherapyAsmt72Y">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="physioTherapyAsmt72N" name="physioTherapyAsmt72" value="false" data-bind="checked: therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs"/><label for="physioTherapyAsmt72N">N</label>
					</span>
				</td>
				
				<td>
					<span data-bind="visible: therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs() == 'true'">
						<span data-bind="inError:'physiotherapyManagement.physioAssessmentDate therapyManagement.physiotherapyManagement.physioAssessmentDate'">						
							Date <input data-bind="value: therapyManagement.physiotherapyManagement().physioAssessmentDate, event: {change:checkAsmtTime}" style="width:106px" class="standardDatePicker "/> 
						</span>
					|
						<span class="value " 
							data-bind="inError:'physiotherapyManagement.physioAssessmentTime therapyManagement.physiotherapyManagement.physioAssessmentTime'">
							Time <g:textField style="width:50px;" class="time"
								data-bind="value: therapyManagement.physiotherapyManagement().physioAssessmentTime, event: {change:checkAsmtTime}"
								name="assessmentTime" value="" />
						</span>
					</span>
					
					<span data-bind="visible: therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs() == 'false'">
						Reason
						<select id="physioNo72HrAssessmentReason" data-bind="options: noAssessmentReasonOptions, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.physiotherapyManagement().physioNo72HrAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>
			<tr data-bind="visible: therapyManagement.physiotherapyManagement().physioAssessmentPerformedIn72Hrs() == 'false'">
				<td><strong>After 72 hours</strong> <small>(BEFORE DISCHARGE)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="physioTherapyAsmtY" name="physioTherapyAsmt" value="true" data-bind="checked: therapyManagement.physiotherapyManagement().physioAssessmentPerformed"/><label for="physioTherapyAsmtY">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="physioTherapyAsmtN" name="physioTherapyAsmt" value="false" data-bind="checked: therapyManagement.physiotherapyManagement().physioAssessmentPerformed"/><label for="physioTherapyAsmtN">N</label>
					</span>
				</td>				
				<td>
				<span data-bind="visible: therapyManagement.physiotherapyManagement().physioAssessmentPerformed() == 'true'">
					<span data-bind="inError:'physiotherapyManagement.physioAssessmentDate therapyManagement.physiotherapyManagement.physioAssessmentDate'">
							Date <input data-bind="value: therapyManagement.physiotherapyManagement().physioAssessmentDate, event: {change:checkAsmtTime2}" style="width:106px" class="standardDatePicker "/> 
					</span>
					|
					<span class="value  " 
						data-bind="inError:'physiotherapyManagement.physioAssessmentTime therapyManagement.physiotherapyManagement.physioAssessmentTime'">
						Time <g:textField style="width:50px;" class="time"
							data-bind="value: therapyManagement.physiotherapyManagement().physioAssessmentTime, event: {change:checkAsmtTime2}"
							name="assessmentTime" value="" />
					</span>					
				</span>
					<span data-bind="visible: therapyManagement.physiotherapyManagement().physioAssessmentPerformed() == 'false'">
						Reason
						<select id="physioNoAssessmentReason" data-bind="options: noAssessmentReasonOptions, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.physiotherapyManagement().physioNoAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>			
		</tbody>
	</table>
</div>

