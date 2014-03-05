
<h3 class="headspace bottomspace">Communication assessment</h3>


<div class=" vspace1">
	<label>
		Has the patient received a formal communication assessment 
		by a speech & language therapist?
	</label>
</div>



<div class="therapy">
	<table>

		<tbody class="therapy">
			<tr>
				<td><strong>Within 72 hours</strong> <small>(BEFORE  <span data-bind="text: threeDaysLater"></span>)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="commsAsmt72Y" name="commsAsmt72" value="true"  data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs"/><label for="commsAsmt72Y">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="commsAsmt72N" name="commsAsmt72" value="false" data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs, event: { change: resetCommsDateTime } "/><label for="commsAsmt72N">N</label>
					</span>
				</td>
				
				<td>
					<span data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs() == 'true'">
						<span data-bind="inError:'speechAndLanguageTherapyManagement.communicationAssessmentDate therapyManagement.speechAndLanguageTherapyManagement.communicationAssessmentDate'">						
							Date <input data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentDate, event: {change:checkCommsAsmtTime}" style="width:106px" class="standardDatePicker "/> 
						</span>
					|
						<span class="value " 
							data-bind="inError:'speechAndLanguageTherapyManagement.communicationAssessmentTime therapyManagement.speechAndLanguageTherapyManagement.communicationAssessmentTime'">
							Time <g:textField style="width:50px;" class="time"
								data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentTime, event: {change:checkCommsAsmtTime}"
								name="communicationAssessmentTime" value="" />
						</span>
					</span>
					
					<span data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs() == 'false'">
						Reason
						<select id="__noCommsReason_01" data-bind="options: noCommunicationReasonOptions, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.speechAndLanguageTherapyManagement().communication72HrNoAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>
			<tr data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformedIn72Hrs() == 'false'">
				<td><strong>After 72 hours</strong> <small>(BEFORE DISCHARGE)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="commsAsmtY" name="commsAsmt" value="true" data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformed"/><label for="commsAsmtY">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="commsAsmtN" name="commsAsmt" value="false" data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformed"/><label for="commsAsmtN">N</label>
					</span>
				</td>				
				<td>
				<span data-bind=", visible: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformed() == 'true'">
					<span data-bind="inError:'speechAndLanguageTherapyManagement.communicationAssessmentDate therapyManagement.speechAndLanguageTherapyManagement.communicationAssessmentDate'">
							Date <input data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentDate, event: {change:checkCommsAsmtTime2}" style="width:106px" class="standardDatePicker "/> 
					</span>
					|
					<span class="value  " 
						data-bind="inError:'speechAndLanguageTherapyManagement.communicationAssessmentTime therapyManagement.speechAndLanguageTherapyManagement.communicationAssessmentTime'">
						Time <g:textField style="width:50px;" class="time"
							data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentTime, event: {change:checkCommsAsmtTime2}"
							name="communicationAssessmentTime" value="" />
					</span>					
				</span>
					<span data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().communicationAssessmentPerformed() == 'false'">
						Reason
						<select id="__noCommsReason_02" data-bind="options: noCommunicationReasonOptions, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.speechAndLanguageTherapyManagement().communicationNoAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>			
		</tbody>
	</table>
</div>

