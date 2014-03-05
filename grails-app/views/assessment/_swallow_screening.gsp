<div id="swallowScreeningForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>
		<div id="swallowScreening">

			<div class="page-header"><h1>Swallow screen</h1></div>

			<div class="question-block">
				<div class="question-alt">
					<label>Has the patient received a swallowing screen?</label>
				</div>
				<div class="answer-block">
					<g:buttonRadioGroup				
						name="swallowScreenPerformed" labels="['Yes' , 'No']"
						bind="clinicalAssessment" values="['true','false']" spans="[6,6]"
						value="" class="answer">
							${it.radio}
							${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>				
		
			<div class="clearfix"></div>
		
			<div class="question-block" data-bind="visible: clinicalAssessment.swallowScreenPerformed() == 'true', inError :'clinicalAssessment.swallowScreenPerformed'  ">
				<div class="answer-block">
					<div id="swallowScreenDateTime">
					<form class="form-inline">
						
						<div data-bind="inError : 'clinicalAssessment.swallowScreenDate'">
							<label for="swallowScreenDate">Date</label>	
							<g:textField class="standardDatePicker"
									data-bind="value: clinicalAssessment.swallowScreenDate"
									name="swallowScreenDate" value="" />
						</div>	
						
						<div data-bind="inError : 'clinicalAssessment.swallowScreenTime'">
							<label for="swallowScreenTime">Time</label>
							<g:textField  class="time"
									data-bind="value: clinicalAssessment.swallowScreenTime"
									name="swallowScreenTime" value="" />
						</div>
					</form>
					</div>					
				</div>
			</div>
				
		
			<div class="clearfix"></div>		
			
			
			<div id="noSwallowScreenReasonsAt4Hours"
				data-bind="visible: over4Hours() && clinicalAssessment.swallowScreenPerformed() =='true'">
				<div data-bind="inError:'therapyManagement.physiotherapyManagement.noAssessmentReasonType'">
					<div class="question-block">
						<div class="question-alt">				
							<label>What was the reason it was longer than 4 hours from
								onset/admission?</label>
						</div>
						<div class="answer-block">
							<div class="answer">
								<input type="radio"
									data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours"
									name="noSwallowScreenPerformedReasonAt4Hours"
									id="noSwallowScreenPerformedReasonAt4HoursUnwell" value="unwell" />
								<label									
									for="noSwallowScreenPerformedReasonAt4HoursUnwell">Patient
									unwell</label>
							</div>
							<div class="answer">
								<input type="radio"
									data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours"
									name="noSwallowScreenPerformedReasonAt4Hours"
									id="noSwallowScreenPerformedReasonAt4HoursRefused" value="refused" />
								<label						
									for="noSwallowScreenPerformedReasonAt4HoursRefused">Patient
									Refused</label>
							</div>
							<div class="answer">
								<input type="radio"
									data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours"
									name="noSwallowScreenPerformedReasonAt4Hours"
									id="noSwallowScreenPerformedReasonAt4HoursUknown"
									value="unknown" /> 
								<label					
									for="noSwallowScreenPerformedReasonAt4HoursUknown">Not known</label>
							</div>

							<div class="answer">
								<input type="radio"
									data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours"
									name="noSwallowScreenPerformedReasonAt4Hours"
									id="noSwallowScreenPerformedReasonAt4HoursOrganisational"
									value="organisational" /> 
								<label						
									for="noSwallowScreenPerformedReasonAt4HoursOrganisational">Organisational
									reasons</label>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="clearfix"></div>

			<div id="noSwallowScreenReasons"
				data-bind="visible: (clinicalAssessment.swallowScreenPerformed() == 'false' || (over72Hours() && clinicalAssessment.swallowScreenPerformed() =='true'))">
				<div 
					data-bind="inError:'therapyManagement.physiotherapyManagement.noAssessmentReasonType'">
					<div class="question-block">
					<div class="question-alt">
						<label
							data-bind="visible: clinicalAssessment.swallowScreenPerformed() === 'false'">What
							was the reason?</label>
						<label
							data-bind="visible:(clinicalAssessment.swallowScreenPerformed() === 'true' && over72Hours())">What
							was the reason it was longer than 72 hours from onset /admission?</label>
					</div>

					<div class="answer-block">
						<div class="answer">
							<input type="radio"
								data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReason"
								name="noSwallowScreenPerformedReason"
								id="noSwallowScreenPerformedReasonUnwell" value="unwell" /> 
							<label
								for="noSwallowScreenPerformedReasonUnwell">Patient Unwell</label>
						</div>
						<div class="answer">
							<input type="radio"
								data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReason"
								name="noSwallowScreenPerformedReason"
								id="noSwallowScreenPerformedReasonRefused" value="refused" /> 
							<label						
								for="noSwallowScreenPerformedReasonRefused">Patient
								Refused</label>
						</div>

						<div class="answer">
							<input type="radio"
								data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReason"
								name="noSwallowScreenPerformedReason"
								id="noSwallowScreenPerformedReasonUnknown" value="unknown" />
							<label						
								for="noSwallowScreenPerformedReasonUnknown">Not known</label>
						</div>

						<div class="answer">
							<input type="radio"
								data-bind="checkedWithToggle: clinicalAssessment.noSwallowScreenPerformedReason"
								name="noSwallowScreenPerformedReason"
								id="noSwallowScreenPerformedReasonOrganisational"
								value="organisational" /> 
							<label					
								for="noSwallowScreenPerformedReasonOrganisational">Organisational
									reasons</label>
						</div>



				</div>

			</div>

		</div>
	</div>
</div>


            <!-- 
			<hr />
			<h2>Debug</h2>
			<div data-bind="text: ko.toJSON(swallowScreeningViewModel)"></div>
            -->

		
	</div>
</div>