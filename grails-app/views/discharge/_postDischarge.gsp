<div id="postDischargeForm">

	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class=""
		data-bind="css : {changed: tracker().somethingHasChanged }">
		
		<div data-bind="html: errorsAsList"></div>
		
		<div class="page-header">
			<h1>On Discharge</h1>
		</div>
			
			
		<g:if
			test="${grailsApplication.config.ssnap.level.equals('comprehensive')}">

			
			<h5>SSNAP inclusion consent</h5>
			
			
			<div class="question-block">
			
				<div class="question-alt">
					<label>Inclusion consent</label>
				</div>
				
				<div class="question-help-text">
					It is not a requirement that the patient provides explicit
						consent for their patient identifiable details to be included in
						SSNAP at this stage. However, where efforts have been made to seek
						consent from the patient, please state if the patient gave consent
						for their identifiable information to be included in SSNAP?
				</div>
				
				<div class="answer-block">				
					<g:buttonRadioGroup 				
						name="ssnapParticipationConsent" labels="['Yes','No','Not asked' ]"
						values="['yes','no','not_asked']" spans="[6,6,6]"
						bind="postDischarge" value="" class="answer">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
				</div>
				
			</div>
		</g:if>
		
		<div class="clearfix spacer-1"></div>
		
		<div id="postDischargePalliativeCareForm"
			data-bind="visible: postDischarge.palliativeCare72() != 'yes'">
		
			<div class="page-header">
				<h3>Palliative care <small>(by discharge)</small></h3>
			</div>

			<div class="question-block">					
				<div class="question-alt">
					<label>Has it been decided by discharge that the patient is for
						palliative care?</label>
				</div>
				
				
				<div class="answer-block">
					<g:buttonRadioGroup 				
						name="palliativeCare" labels="['Yes','No' ]" values="['yes','no']"
						spans="[6,6]" bind="postDischarge" value="" idStem="pd_"
						onEvent="change: setGlobalFlag('palliativeCareRefresh',true)" class="answer">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>
						
			<div data-bind="visible: postDischarge.palliativeCare() === 'yes'">
				<div data-bind="inError:'postDischarge.palliativeCareDate'">
					<div class="question-block">
					<div class="question-alt">
						<label>Date started palliative care</label>
					</div>
					<div class="answer-block">
						<div class="answer">
						<input type="text" class="standardDatePicker input-small" size="10"
							data-bind="value: postDischarge.palliativeCareDate,
		                                      jqueryui: { widget:'datepicker',
		                                                 options:{dateFormat:'dd/mm/yy',
		                                                          maxDate: new Date,
		                                                          showOn: 'button',
		                                                          buttonImage: '/stroke/images/calendar-sharepoint.gif',
		                                                          buttonImageOnly: true } }, 
		                                      uniqueName: true " />
						</div>
					</div>
				</div>
				</div>
				<div class="question-block">
					<div class="question-alt">
						<label>Is the patient on an end of life pathway?</label>
					</div>
					<div class="answer-block">
					<g:buttonRadioGroup class="answer"					
						name="endOfLifePathway" labels="['Yes','No' ]"
						values="['yes','no']" spans="[7,7]" bind="postDischarge" value=""
						idStem="pd_">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
					</div>
				</div>
			</div>
		</div>

		<!--  -->

		<div class="clearfix spacer-1"></div>
		
		<div class="page-header">
			<h3>Discharged to</h3>
		</div>

		<div class="question-alt">
			<label>The patient was discharged to</label>
		</div>
		
		<div class="answer-block">
		
		<div class="answer-single-column">		
			<input type="radio"
				data-bind="checkedWithToggle: postDischarge.dischargedTo, event: {change: setGlobalFlag('predischargeRefresh',true)}"
				name="dischargedTo" id="dischargedToIntermediateCare"
				value="intermediateCare" /> <label				
				for="dischargedToIntermediateCare">Transferred to an
				ESD/community team</label>
		</div>
		
		
		
		<div class="answer-single-column">
			<input type="radio"
				data-bind="checkedWithToggle: postDischarge.dischargedTo"
				name="dischargedTo" id="dischargedToHome" value="home" /> <label				
				for="dischargedToHome">Private or council owned home</label>
		</div>

		<div class="answer-single-column">
			<input type="radio"
				data-bind="checkedWithToggle: postDischarge.dischargedTo"
				name="dischargedTo" id="dischargedToResidentialCareHome"
				value="residentialCareHome" /> <label				
				for="dischargedToResidentialCareHome">Residential Care Home</label>
		</div>

		<div class="answer-single-column">
			<input type="radio"
				data-bind="checkedWithToggle: postDischarge.dischargedTo"
				name="dischargedTo" id="dischargedToNursingCareHome"
				value="nursingCareHome" /> <label				
				for="dischargedToNursingCareHome">Nursing Care home</label>
		</div>

		<div class="answer-single-column">
			<input type="radio"
				data-bind="checkedWithToggle: postDischarge.dischargedTo"
				name="dischargedTo" id="dischargedToOtherHospital"
				value="otherHospital" /> <label				
				for="dischargedToOtherHospital">Transferred to another
				in-patient care team/hospital</label>
		</div>

		<div class="answer-single-column">
			<input type="radio"
				data-bind="checkedWithToggle: postDischarge.dischargedTo"
				name="dischargedTo" id="dischargedToSomewhere" value="somewhere" />
			<label				
				for="dischargedToSomewhere">Somewhere else</label>
		</div>

		<div class="answer-single-column">
			<input type="radio"
				data-bind="checkedWithToggle: postDischarge.dischargedTo"
				name="dischargedTo" id="dischargedToDied" value="died" />
			<label				
				for="dischargedToDied">Died</label>
		</div>
		</div>
		
		<div class="indented"
			data-bind="visible: postDischarge.dischargedTo() == 'died'">
			
			<div class="clearfix spacer-1"></div>
			
			<div class="control-group">
				<div class="control-label"><label>Date of death</label></div>
				<div class="controls">				
					<input name="dateOfDeath" id="dateOfDeath" class="date required standardDatePicker" data-bind="inError: 'patient.dateOfDeath', value: postDischarge.deathDate">
				</div>				
			</div>
			
			<div class="clearfix spacer-1"></div>
			
			<div class="control-group" data-bind="visible: postDischarge.didNotStayInStrokeUnit() != 'true'">
				<div class="control-label"><label>Did the patient die in the stroke unit?</label></div>
				<div class="controls" data-bind="inError: 'medicalHistory.strokeUnitDeath'">				
			
				<g:buttonRadioGroup				
					name="strokeUnitDeath" labels="['Yes','No']"
					values="['true','false']" spans="[5,5 ]" bind="postDischarge">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
				</div>			
			</div>
		</div>
	
		<div 
			data-bind="visible: postDischarge.dischargedTo() == 'otherHospital'">
			
			<div class="clearfix spacer-1"></div>
			
			<div class="question-block">
				<div class="question-alt">
					<label>Was this under a stroke specialist?</label>
				</div>
				<div class="answer-block">
					<g:buttonRadioGroup				
						name="strokeSpecialist" labels="['Yes','No']"
						values="['true','false']" spans="[5,5 ]" bind="postDischarge" class="answer">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>
		</div>

		<div class="clearfix spacer-1"></div>
		
		<div 
				data-bind="visible: postDischarge.dischargedTo() == 'otherHospital'">
			<div class="question-block">
				<div class="question-alt">
					<label>What team/hospital was the patient transferred to?</label>
				</div>
				<div class="answer-block">
					<div class="answer">
						<input width="3" name="specialistIntermediateCareSiteCode"
							data-bind="value: postDischarge.newCareTeam" />
					</div>
				</div>
			</div>
		</div>

		<div class="clearfix spacer-1"></div>
		
		<div 
			data-bind="visible: postDischarge.dischargedTo() == 'intermediateCare'">
			<div class="question-block">
				<div class="question-alt">
					<label>What team was the patient transferred to?</label>
				</div>
				<div class="answer-block">
					<div class="answer">
						<input width="4" name="esdCommunitySiteCode"
							data-bind="value: postDischarge.newCareTeam" />
					</div>
				</div>
			</div>
		</div>

		


		
		<div 
			data-bind="visible: postDischarge.dischargedTo() == 'home'">
			<div class="clearfix spacer-1"></div>
			<div class="question-block ">
			<div class="question-alt">
				<label>Will the patient be living alone post discharge?</label>
			</div>
			
			<div class="answer-block">
				<g:buttonRadioGroup				
					name="alonePostDischarge" labels="['Yes','No','Unknown']"
					values="['true','false','unknown']" spans="[5,5,5]"
					bind="postDischarge" class="answer">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>

			<div class="clearfix spacer-1"></div>
			<div class="question-block ">
				<div class="question-alt">
					<label>Is this sheltered / warden controlled accommodation?</label>
				</div>
				<div class="answer-block">
					<g:buttonRadioGroup					
						name="shelteredAccommodation" labels="['Yes','No']"
						values="['true','false']" spans="[5,5 ]" bind="postDischarge" class="answer">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>

		</div>
		
		<div 
			data-bind="visible: postDischarge.dischargedTo() == 'residentialCareHome' || postDischarge.dischargedTo() == 'nursingCareHome' ">
			<div class="clearfix spacer-1"></div>
			<div class="question-block">
				<div class="question-alt">
					<label>Was the patient previously a resident?</label>
				</div>
				
				<div class="answer-block">
					<g:buttonRadioGroup
						name="patientPreviouslyResident" labels="['Yes','No']"
						values="['true','false']" spans="[5,5 ]" bind="postDischarge" class="answer"> 
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>
			
			<div 
				data-bind="visible: postDischarge.patientPreviouslyResident()=='false' ">
				<div class="clearfix spacer-1"></div>
				<div class="question-block">
				<div class="question-alt">
					<label>Is the new arrangement temporary or permanent?</label>
				</div>
				<div class="answer-block">
					<g:buttonRadioGroup					
						name="temporaryOrPermanent" labels="['Temporary','Permanent']"
						values="['temporary','perm']" spans="[5,5 ]" bind="postDischarge" class="answer">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
				</div>
				</div>
			</div>


		</div>

		<div class="clearfix spacer-1"></div>
		
		<%-- 
		DischargedTo=<span data-bind="text: postDischarge.dischargedTo()"></span>
		<br>StrokeUnitDeath=<span data-bind="text: postDischarge.strokeUnitDeath()"></span>
		<br>DidNotStayInSU=<span data-bind="text:   postDischarge.didNotStayInStrokeUnit()"></span>
		<br>MyExpr1<span data-bind="text: !(postDischarge.dischargedTo() === 'died' && postDischarge.strokeUnitDeath() ==  'true')"></span>
		<br>MrExpr2<span data-bind="text: postDischarge.didNotStayInStrokeUnit() == 'true'"></span>
		--%>
		
		<div 
			data-bind="visible: ((postDischarge.didNotStayInStrokeUnit() != 'true' && postDischarge.dischargedTo() !== 'died')||(postDischarge.dischargedTo() === 'died' && postDischarge.strokeUnitDeath() !=  'true' && postDischarge.didNotStayInStrokeUnit() != 'true'))">
			
			<div class="question-block" >
				<div class="question-alt">
					<label>Date and time discharged from Stroke Unit</label>
				</div>
	
				<div class="answer-block">
														
					<div class="answer"
						data-bind="inError :'medicalHistory.strokeWardDischargeDate'">
						<label for="strokeUnitDischargeDate">Date</label>
						<g:textField  class="standardDatePicker date"
						name="strokeUnitDischargeDate"
						id="strokeUnitDischargeDate"
						data-bind="value: postDischarge.strokeWardDischargeDate" />						
					</div>
							
					<div class="answer"
						data-bind="inError :'medicalHistory.strokeWardAdmissionTime'">
						<label for="strokeUnitDischargeTime">Time</label>
						<g:textField class="time" name="strokeUnitDischargeTime" id="strokeUnitDischargeTime"
							data-bind="value: postDischarge.strokeWardDischargeTime" />
					</div>
				</div>			
			</div>

         </div>
         
         <div data-bind="visible: (postDischarge.dischargedTo() != 'died' && postDischarge.strokeUnitDeath() != 'true')">
            <div class="clearfix spacer-1"></div>
			<div class="question-block">
				<div class="question-alt">
					<label>Date and time discharged from Hospital</label>
				</div>
	
				<div class="answer-block">
														
					<div class="answer"
						data-bind="inError :'medicalHistory.hospitalDischargeDate'">
						<label for="hospitalDischargeDate">Date</label>
						<g:textField  class="standardDatePicker date"
						name="hospitalDischargeDate"
						id="hospitalDischargeDate"
						data-bind="value: postDischarge.hospitalDischargeDate" />
						
					</div>
							
					<div class="answer"
						data-bind="inError :'medicalHistory.hospitalDischargeTime'">
						<label for="hospitalDischarge">Time</label>
						<g:textField class="time" name="hospitalDischarge" id="hospitalDischarge"
							data-bind="value: postDischarge.hospitalDischargeTime" />
					</div>
				</div>			
			</div>
		</div>
		<%-- 
		<hr />
		<h2>Debug</h2>
		<div data-bind="text: ko.toJSON(postDischargeViewModel)"></div>
 		 --%>

	</div>
	<g:render template="/templates/sectionFoot"></g:render>
</div>