<h3 class="headspace bottomspace">Swallowing assessment</h3>

<div class="vspace1">
	<label>
	Has the patient received a formal swallowing assessment by a Speech and Language Therapist or another 
	professional trained in dysphagia assessment (if swallow screen not completed within 72 hrs. cannot pass swallow screen)?
    </label>
</div>

<div class="therapy">
	<table>

		<tbody class="therapy">
			<tr>
				<td><strong>Within 72 hours</strong> <small>(BEFORE  <span data-bind="text: threeDaysLater"></span>)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="swallowAsmt72Y" name="swallowAsmt72" value="true"  data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs, event:{ click: toggleSwallowingAsmtPerformedAffirmation72HrsOn}"/>
						<label for="swallowAsmt72Y">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="swallowAsmt72N" name="swallowAsmt72" value="false" data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs, event:{ change: resetSwallowingDateTime}"/><label for="swallowAsmt72N">N</label>
					</span>
				</td>
				
				<td>
					<span data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs() == 'true'">
						<span data-bind="inError:'speechAndLanguageTherapyManagement.swallowingAssessmentDate therapyManagement.speechAndLanguageTherapyManagement.swallowingAssessmentDate'">						
							Date <input data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentDate, event: {change:checkSwallowingAsmtTime}" style="width:106px" class="standardDatePicker "/> 
						</span>
					|
						<span class="value " 
							data-bind="inError:'speechAndLanguageTherapyManagement.swallowingAssessmentTime therapyManagement.speechAndLanguageTherapyManagement.swallowingAssessmentTime'">
							Time <g:textField style="width:50px;" class="time"
								data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentTime, event: {change:checkSwallowingAsmtTime}"
								name="swallowingAssessmentTime" value="" />
						</span>
					</span>
					
					<span data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs() == 'false'">
						Reason
						<select id="swallowing72HrNoAssessmentReason" data-bind="options: noSwallowingReasonOptions, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.speechAndLanguageTherapyManagement().swallowing72HrNoAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>
			<tr data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformedIn72Hrs() == 'false'">
				<td><strong>After 72 hours</strong> <small>(BEFORE DISCHARGE)</small></td>
				<td>
					<span class="toggleButtonSet">
						<input class="toggleButton toggleButtonY" type="radio" id="swallowAsmtY" name="swallowAsmt" value="true" data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformed"/><label for="swallowAsmtY">Y</label>
						<input class="toggleButton toggleButtonN" type="radio" id="swallowAsmtN" name="swallowAsmt" value="false" data-bind="checked: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformed"/><label for="swallowAsmtN">N</label>
					</span>
				</td>				
				<td>
				<span data-bind=", visible: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformed() == 'true'">
					<span data-bind="inError:'speechAndLanguageTherapyManagement.swallowingAssessmentDate therapyManagement.speechAndLanguageTherapyManagement.swallowingAssessmentDate'">
							Date <input data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentDate, event: {change:checkSwallowingAsmtTime2}" style="width:106px" class="standardDatePicker "/> 
					</span>
					|
					<span class="value  " 
						data-bind="inError:'speechAndLanguageTherapyManagement.swallowingAssessmentTime therapyManagement.speechAndLanguageTherapyManagement.swallowingAssessmentTime'">
						Time <g:textField style="width:50px;" class="time"
							data-bind="value: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentTime, event: {change:checkSwallowingAsmtTime2}"
							name="swallowingAssessmentTime" value="" />
					</span>					
				</span>
					<span data-bind="visible: therapyManagement.speechAndLanguageTherapyManagement().swallowingAssessmentPerformed() == 'false'">
						Reason
						<select id="swallowingNoAssessmentReason" data-bind="options: noSwallowingReasonOptions2, optionsText: 'description', optionsValue: 'value', optionsCaption: 'Please state why ...', value:therapyManagement.speechAndLanguageTherapyManagement().swallowingNoAssessmentReason">
						</select>					
					</span>
				</td>
			</tr>			
		</tbody>
	</table>
</div>



