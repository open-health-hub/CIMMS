<div id="strokeOnsetForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>
		<div id="strokeOnset" class="clearfix">

		<div class="page-header"><h1>Stroke Onset</h1></div>

		<div class="question-block" data-bind="visible:  admissionDetails().arriveByAmbulance() != 'true' ">
			<div class="question-alt">
				<label>Was the patient already an in-patient at the time of stroke?
				<br><i>(Note: if arrived by ambulance = "Yes", must be "No")</i></label>
			</div>
			<div class="answer-block">
			<div class="answer">
				<label
					class="positiveAnswer"
					for="inpatientAtOnsetY">Yes</label>
					<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().inpatientAtOnset, click: resetOnsetTimeUnknown"
					name="inpatientAtOnset" id="inpatientAtOnsetY" value="true" /> 
			</div>
			<div class="answer">
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().inpatientAtOnset"
					name="inpatientAtOnset" id="inpatientAtOnsetN" value="false" /> <label
					class="negativeAnswer"
					for="inpatientAtOnsetN">No</label>
			</div>
			</div>
		</div>
		
		<div class="question-block">
			<div class="question-alt">
				<label>Did stroke onset occur during sleep?</label>
			</div>
			<div class="answer-block">
				<div class="answer">
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().onset().duringSleep"
					name="onsetDuringSleep" id="onsetDuringSleepYes" value="true" /> <label
					class="positiveAnswer"
					for="onsetDuringSleepYes">Yes</label>
				</div>
			
				<div class="answer">
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().onset().duringSleep"
					name="onsetDuringSleep" id="onsetDuringSleepNo" value="false" /> <label
					class="negativeAnswer"
					for="onsetDuringSleepNo">No</label>
				</div>
			</div>
			</div>
			
			<div class="question-block">
				<div class="question-alt">
				<label>Date of onset</label>
				</div>
				<div class="question-alt">
				<label>Onset Date</label>
			
				<div class="answer"
				data-bind="visible: !admissionDetails().onset().onsetDateUnknown() , inError :'medicalHistory.onsetDate medicalHistory.onsetDateEstimated'">
				<input class="standardDatePicker date"
					name="onsetDate2" id="onsetDate2"
					data-bind="value: admissionDetails().onset().onsetDate" />
				</div>


			<div class="answer">
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().onset().onsetDateEstimated "
					name="onsetDateEstimate" 
					id="onsetDateEstimated" 
					value="true" />
				<label  for="onsetDateEstimated">Estimated</label>
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().onset().onsetDateEstimated "
					name="onsetDateEstimate" 
					id="onsetDatePrecise" 
					value="false" />
				<label  for="onsetDatePrecise">Precise</label>
				
			</div>
			</div>
			</div>

			
			
			
										
		
	
		<div class="question-block">

			<div class="question-alt">
				<label>Time of onset</label> 
			</div>
			<div class="answer-block">
				<label>Onset Time</label>
				
					
				
				<div class="answer"
					data-bind="visible: !admissionDetails().onset().onsetTimeUnknown() , inError :'medicalHistory.onsetTime medicalHistory.onsetTimeEstimated'">
					<g:textField class="time" name="onsetTime"
						data-bind="value: admissionDetails().onset().onsetTime" />
				</div>
	
				
					
				<div class="answer"
					data-bind="visible: !admissionDetails().onset().onsetTimeUnknown() && admissionDetails().onset().onsetDateEstimated() != 'true'">
	
					<input type="radio"
						data-bind="checkedWithToggle: admissionDetails().onset().onsetTimeEstimated"
						name="onsetTimeEstimated" 
						value="true" 
						id="onsetTimeEstimatedTrue"/>
					<label for="onsetTimeEstimatedTrue">Estimated</label>
	
					
					<input type="radio"
						data-bind="checkedWithToggle: admissionDetails().onset().onsetTimeEstimated"
						name="onsetTimeEstimated" 
						value="false"
						id="onsetTimeEstimatedFalse"/>
					<label for="onsetTimeEstimatedFalse">Precise</label>
	
				</div>
	
				
				<div  class="answer" data-bind="visible: admissionDetails().inpatientAtOnset() != 'true'">
					<g:checkBox
						data-bind="checkBoxAsButton: admissionDetails().onset().onsetTimeUnknown"
						name="onsetTimeUnknown" />
					<label  for="onsetTimeUnknown">Unknown</label>
				</div>

			</div>
		</div>

		<div class="clearfix"></div>
	
		<div class="question-block">
			<div class="question-alt">
				<label>First ward patient was admitted to</label>
			</div>
			<div class="answer-block">
				<div class="answer">
					<input type="radio"
						data-bind="checkedWithToggle: admissionDetails().admissionWard"
						name="firstWardOnAdmission" 
						id="firstWardOnAdmissionMAC" 
						value="MAC" />
					<label  for="firstWardOnAdmissionMAC">MAU/AAU/CDU</label>
				</div>
				<div class="answer">
					<input type="radio"
						data-bind="checkedWithToggle: admissionDetails().admissionWard, event: { change: toggleDidNotStayInSU }"
						name="firstWardOnAdmission" 
						id="firstWardOnAdmissionSU" 
						value="SU" />
					<label  for="firstWardOnAdmissionSU">Stroke Unit</label>
				</div>
				<div class="answer">
					<input type="radio"
						data-bind="checkedWithToggle: admissionDetails().admissionWard"
						name="firstWardOnAdmission" 
						id="firstWardOnAdmissionICH" 
						value="ICH" />
					<label  for="firstWardOnAdmissionICH">ITU/CCU/HDU</label>					
				</div>
				<div class="answer">
					<input type="radio"
						data-bind="checkedWithToggle: admissionDetails().admissionWard"
						name="firstWardOnAdmission" 
						id="firstWardOnAdmissionOther" 
						value="O" />
					<label  for="firstWardOnAdmissionOther">Other</label>					
				</div>
				
			</div>
		</div>


		<div class="clearfix"></div>	
		
		<div class="question-block">
			<div class="question-alt">
				<label>Date and time arrived on Stroke Unit</label>
			</div>

			<div class="answer-block">
				
			
				
				<div class="answer"
					data-bind="visible: admissionDetails().onset().didNotStayInStrokeWard() != true, inError :'medicalHistory.strokeWardAdmissionDate'">
					<label for="strokeUnitAdmissionDate">Date</label>
					<g:textField  class="standardDatePicker date"
					name="strokeUnitAdmissionDate"
					id="strokeUnitAdmissionDate"
					data-bind="value: admissionDetails().onset().strokeWardAdmissionDate" />
					
				</div>

					
				<div class="answer"
					data-bind="visible: admissionDetails().onset().didNotStayInStrokeWard() != true, inError :'medicalHistory.strokeWardAdmissionTime'">
					<label for="strokeUnitAdmissionTime">Time</label>
					<g:textField class="time" name="strokeUnitAdmissionTime" id="strokeUnitAdmissionTime"
						data-bind="value: admissionDetails().onset().strokeWardAdmissionTime" />
				</div>
	
				
				<div class="answer" data-bind="visible: admissionDetails().admissionWard() != 'SU'">
					<input type="checkbox"
						data-bind="checkBoxAsButton: admissionDetails().onset().didNotStayInStrokeWard"
						name="didNotStayInStrokeUnit" 
						id="didNotStayInStrokeUnit" 
						 />
					<label  for="didNotStayInStrokeUnit">Did not stay in SU</label>
					
				</div>
			</div>


		</div>


		
		<div class="clearfix"></div>	
		
		<div class="question-block">
			<div class="question-alt">
				At the point of the decision to admit was a stroke bed
					available?
				</div>
			
			<div class="answer-block">
			<div class="answer">
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().strokeBedAvailable"
					name="strokeBedAvailable" id="strokeBedAvailableYes" value="true" />
				<label
					class="positiveanswer"
					for="strokeBedAvailableYes">Yes</label>
			</div>
			<div class="answer">
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().strokeBedAvailable"
					name="strokeBedAvailable" id="strokeBedAvailableNo" value="false" />
				<label
					class="negativeAnswer"
					for="strokeBedAvailableNo">No</label>
			</div>
			<div class="answer">
				<input type="radio"
					data-bind="checkedWithToggle: admissionDetails().strokeBedAvailable"
					name="strokeBedAvailable" id="strokeBedAvailableUnknown"
					value="unknown" /> <label
					class="negativeAnswer"
					for="strokeBedAvailableUnknown">Unknown</label>
			</div>
			</div>
		</div>
	</div>

	<div>

			<div class="page-title"
				data-bind="inError: 'evaluations.dateEvaluated evaluations.timeEvaluated'">				
				<h2>First assessed by</h2>
			</div>



			<div>
				

				<div id="template"
					data-bind="template: { foreach: admissionDetails().firstSeenByList  } ">

					
					<div class="question-block">
					<div class="question-alt">
						<label data-bind="text:display"> </label>
					</div>
					
					<div class="answer-block">
						<div data-bind='visible: !notSeen()'>
						
							<div class="answer">
							<div class="control-label">Date</div>
							<div class="value controls">
								<input type="text" class="date"
									class="standardDatePicker"
									data-bind="attr:{'id':id()+'_date'}, value: firstSeenDate,  jqueryui: { widget:'datepicker'
                                                                                                        , options:{dateFormat:'dd/mm/yy'
                                                                                                            , maxDate: new Date
                                                                                                                        , showOn: 'button'
                                                                                                                        , buttonImage: '/stroke/images/calendar-sharepoint.gif'
                                                                                                                        , buttonImageOnly: true } } ,  uniqueName: true, uniqueId: false " />
							</div>
							</div>
							
							<div class="answer">
								<div class="control-label">Time</div> 
								<div class="value controls">
									<input type="text" class="time"
										data-bind="attr:{'id':id()+'_time'}, value: firstSeenTime, uniqueName: true " />
								</div>
							</div>
						</div>
					
						<div class="answer">
					
							<label class="checkbox" data-bind="attr:{'for':id()+'_notSeen'}">
							
								<input type="checkbox" class="simpleCheckBox"
									data-bind="checked: notSeen, attr:{'id':id()+'_notSeen'}" /> Not seen
							</label>
						</div>
					</div>
				  </div>
				  <div class="clearfix"></div>
				</div>
				<!-- template end -->
			</div>
		</div>
	</div>
	<g:render template="/templates/sectionFoot"></g:render>
</div>

