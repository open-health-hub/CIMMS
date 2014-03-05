

<div id="dischargeForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>


		<div id="discharge">
			<div class="page-header"><h1>Pre-discharge</h1>	</div>
			
			<div  data-bind="visible: discharge.dischargedTo() != 'died' && discharge.dischargedTo() != 'intermediateCare' && discharge.dischargedTo() != 'otherHospital', inError :'fitForDischargeDate'">
			
				<div class="question-block">
				<div class="question-alt">
					<label>Date patient considered by the multi-disciplinary team to no longer require in-patient rehabilitation?</label>
				</div>
				<div class="answer-block">				
					<div class="answer">
						<input type="text" id="fitForDischargeDate"
							name="fitForDischargeDate"
							data-bind="value: discharge.fitForDischargeDate"
							class="required standardDatePicker input-small" 
							size="10" >
					</div>
				</div>
			</div>
			
			</div>
			
			<div class="clearfix spacer-1"></div>


			<div class="question-block" data-bind="visible: discharge.dischargedTo() != 'died' && discharge.dischargedTo() != 'otherHospital'">
				<div class="question-alt">
					<label>Was the patient referred to a hospital social worker?</label>
				</div>
				<div class="answer-block">
				
				<g:buttonRadioGroup				
					name="socialWorkerReferral" labels="['Yes','No', 'Not Applicable']"
					values="['Yes','No', 'NotApplicable']" spans="[5,5,5 ]"
					bind="discharge"
					class="answer">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>

			<div class="" id="dateReferredToSocialWorker"
				data-bind="visible: discharge.socialWorkerReferral()=='Yes', inError :'socialWorkerReferralDate'">
				<div class="question-block">
					<div class="question-alt">
						<label>Date the patient was referred to a social worker</label>
					</div>
					<div class="answer-block">
						<div data-bind="visible: !discharge.socialWorkerReferralUnknown()">
						<div class="answer">
							<input type="text" id="socialWorkerReferralDate"
								name="socialWorkerReferralDate"
								data-bind="value: discharge.socialWorkerReferralDate"
						    	class="required standardDatePicker input-small"
						    	size="10" />
						</div>
						</div>
						<div class="answer">
							<g:checkBox name="socialWorkerReferralUnknown"
								data-bind="checkBoxAsButton: discharge.socialWorkerReferralUnknown" />
							<label 
							for="socialWorkerReferralUnknown">Not Required</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
			
			<div data-bind="visible: discharge.socialWorkerReferral()=='Yes'">
				<div class="question-block">
					<div class="question-alt">
						<label>Hospital social worker assessment done</label>
					</div>
				
					<div class="answer-block">
						<g:buttonRadioGroup					
							name="socialWorkerAssessment" labels="['Yes','No']"
							values="['Yes','No']" spans="[5,5 ]" bind="discharge" 
							class="answer">
							${it.radio}
							${it.label}
						</g:buttonRadioGroup>
					</div>
				</div>
				

				<div class="" id="dateAssessedBySocialWorker"
					data-bind="visible: discharge.socialWorkerAssessment()=='Yes' , inError :'socialWorkerAssessmentDate'">
					<div class="question-alt">
						<label>Date assessed by social worker</label>
					</div>
					
					<div class="answer-block">
					<div
						data-bind="visible: !discharge.socialWorkerAssessmentUnknown()">
							<div class="answer">	
								<input type="text" id="socialWorkerAssessmentDate"
									name="socialWorkerAssessmentDate"
									data-bind="value: discharge.socialWorkerAssessmentDate"	
									class="required standardDatePicker input-small" size="10"/>							 
							</div>
					</div>
					<div class="answer">
						<g:checkBox name="socialWorkerAssessmentUnknown"
							data-bind="checkBoxAsButton: discharge.socialWorkerAssessmentUnknown" />
						<label 
							for="socialWorkerAssessmentUnknown">Unknown</label>
					</div>
				</div>				
			</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
			
			<div data-bind="visible: discharge.dischargedTo() != 'otherHospital' && discharge.dischargedTo() != 'died'">
			
				<div class="question-block">
					<div class="question-alt">
						<label>Is there documented evidence of joint care planning between
							health &amp; social care for post discharge management?</label>
					</div>
				
					<div class="answer-block">
						<g:buttonRadioGroup				
							name="documentedEvidence" labels="['Yes','No', 'Not Applicable']"
							values="['Yes','No', 'NotApplicable']" spans="[5,5,5 ]"
							bind="discharge"
							class="answer">
							${it.radio}
							${it.label}
						</g:buttonRadioGroup>
					</div>
				</div>
			</div>

			<div class="clearfix spacer-1"></div>

			<div data-bind="visible: discharge.dischargedTo() != 'otherHospital' && discharge.dischargedTo() != 'died'">
				<div class="question-block">			
					<div class="question-alt">
						<label>Is the named person for the patient / carer to contact post
							discharge documented?</label>
					</div>
	
					<div class="answer-block">
						<g:buttonRadioGroup
							name="documentationPostDischarge" labels="['Yes','No']"
							values="['true','false']" spans="[5,5 ]" bind="discharge" class="answer">
							${it.radio}
							${it.label}
						</g:buttonRadioGroup>
					</div>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>


			<div data-bind="visible: discharge.dischargedTo() != 'otherHospital' && discharge.dischargedTo() != 'died'">
				<div class="question-block">
		            <div class="question-alt">
		            	<label>Did they require support on discharge? </label>
		            </div>
		            <div class="answer-block">
			           <g:buttonRadioGroup                
			                name="supportOnDischargeNeeded" labels="['Yes','No']"
			                values="['Yes','No']" spans="[5,5 ]"
			                bind="discharge" class="answer">
			                ${it.radio}
			                ${it.label}
			            </g:buttonRadioGroup>
			       </div>
			   </div>
            </div>
            
            <div class="clearfix spacer-1"></div>

			<div data-bind="visible :discharge.supportOnDischargeNeeded()=='Yes'">
				<div class="question-block">
					<div class="question-alt">
						<label>What support did they receive?
						<br><i>(Note: cannot select only palliative care)</i></label>
					</div>
					<div class="answer-block">
					<div data-bind="visible:!discharge.postDischargeSupport().patientRefused()">						
						<div class="answer-single-column">
							<g:checkBox name="informalCarers"
								data-bind="checkBoxAsButton: discharge.postDischargeSupport().informalCarers, click: checkDischargeInfo"></g:checkBox>
							<label								
								for="informalCarers">Informal Carers</label>
						</div>		
									
						<div class="answer-single-column">
							<g:checkBox name="palliativeCare"
								data-bind="checkBoxAsButton: discharge.postDischargeSupport().palliativeCare"></g:checkBox>
							<label								
								for="palliativeCare">Palliative Care</label>
						</div>
						
						
						<div class="answer-single-column">
                            <g:checkBox name="socialServicesUnavailable"
                                data-bind="checkBoxAsButton:discharge.postDischargeSupport().socialServicesUnavailable,click:clearDischarge"></g:checkBox>
                            <label                                
                                for="socialServicesUnavailable">Paid Carers Unavailable</label>
                        </div>
						
						
						<div data-bind="visible:!discharge.postDischargeSupport().socialServicesUnavailable()" >
							<div class="answer-single-column" >					   
	                            <g:checkBox name="socialServices"
	                                data-bind="checkBoxAsButton: discharge.postDischargeSupport().socialServices, click: checkDischargeInfo"></g:checkBox>
	                            <label                                
	                                for="socialServices">Paid Carers</label>
	                        </div>
                        </div>
					</div>
					
					
					<div class="answer-single-column" data-bind="css: { 'oldprepend-20' :discharge.postDischargeSupport().patientRefused() }">
						<g:checkBox name="patientRefused"
							data-bind="checkBoxAsButton:discharge.postDischargeSupport().patientRefused,click:clearDischarge"></g:checkBox>
						<label							
							for="patientRefused">NONE: Patient Refused</label>
					</div>
				</div>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
			
			<div data-bind="visible : discharge.supportOnDischargeNeeded()=='Yes'">
				<div class="question-block">
                    <div class="question-alt">
                        <label>How many visits are to be provided by paid carers each week?</label>
                    </div>
                    <div class="answer-block">
	                    <div class="answer"
	                        data-bind="visible:!discharge.numberOfSocialServiceVisitsUnknown()">
	                        <input id="numberOfSocialServiceVisits" type="text"                            
	                            data-bind="value:discharge.numberOfSocialServiceVisits" class="input-mini" size="3"/>
	                    </div>
	                    <div class="answer" >
	                        <g:checkBox id="numberOfSocialServiceVisitsUnknown" name="numberOfSocialServiceVisitsUnknown"
	                            data-bind="checkBoxAsButton: discharge.numberOfSocialServiceVisitsUnknown" />
	                        <label for="numberOfSocialServiceVisitsUnknown">Not
	                            Known</label>
	                    </div>
                    </div>
               </div>
            </div>
            
			
			<div class="clearfix spacer-1"></div>
			
			<div data-bind="visible: discharge.dischargedTo() != 'otherHospital' && discharge.dischargedTo() != 'died'">
			
				<div class="question-block">
					<div class="question-alt">    	
						<label>Was the patient discharged with an ESD multi-disciplinary team?</label>
					</div>				
				    
					 <div class="answer-block">
						<div class="answer-single-column">
					        <input  type="radio"  
					                data-bind="checkedWithToggle: discharge.postDischargeSupport().esdType" 
					                name="esdSupport" 
					                id="esdSupportSpecialist" 
					                value="specialist" />
					        <label  
					               for="esdSupportSpecialist" >Stroke/neurology specific ESD</label>
					    </div>
					    <div class="answer-single-column">
					        <input  type="radio"  
					                data-bind="checkedWithToggle: discharge.postDischargeSupport().esdType" 
					                name="esdSupport" 
					                id="esdSupportNonSpecialist" 
					                value="nonSpecialist" />
					        <label  
					               for="esdSupportNonSpecialist" >Non specialist ESD</label>
					    </div>
					    
					    <div class="answer-single-column">
					        <input  type="radio"  
					                data-bind="checkedWithToggle: discharge.postDischargeSupport().esdType" 
					                name="esdSupport" 
					                id="esdSupportNoESD" 
					                value="noESD" />
					        <label  
					               for="esdSupportNoESD" >No ESD Support</label>
					    </div>
					</div>
			    </div>
				
				<div class="clearfix spacer-1"></div>
					
				<div 
                	data-bind="visible: discharge.postDischargeSupport().esdType()==='specialist'">
                	<div id="referredToEsdDischarge"
                    	data-bind="inError :'postDischargeCare.esdReferralDate'">
                    	
                    	<div class="question-block">
                    	<div class="question-alt">
                        	<label>Date referred to ESD</label>
                    	</div>
                    
                    	<div class="answer-block">
		                    <div
		                        data-bind="visible: !discharge.esdReferralDateUnknown()">
		                        <div class="answer">
		                            <input type="text" id="esdReferralDate"
		                                name="esdReferralDate"
		                                data-bind="value: discharge.esdReferralDate"
		                                 class="required standardDatePicker date" />
		                        </div>
		                    </div>
		                    <div class="answer">
		                        <g:checkBox id="esdReferralDateUnknown" name="esdReferralDateUnknown"
		                            data-bind="checkBoxAsButton: discharge.esdReferralDateUnknown" />
		                        <label 
		                            for="esdReferralDateUnknown">Unknown</label>
		                    </div>
	                    </div>
	                    </div>
	                </div>
				</div>
				</div>
				
				<div class="clearfix spacer-1"></div>
				
				
				<div data-bind="visible: discharge.dischargedTo() != 'otherHospital' && discharge.dischargedTo() != 'died'">
					<div class="question-block">
						<div class="question-alt">   	
							<label>Was the patient discharged with a multi-disciplinary community rehabilitation team?</label>			
						</div>
						<div class="answer-block">			
			                <div class="answer-single-column">
			                    <input  type="radio"  
			                            data-bind="checkedWithToggle: discharge.postDischargeSupport().rehabilitationType" 
			                            name="rehabilitationType" 
			                            id="rehabilitationTypeSpecific" 
			                            value="specific" />
			                    <label  
			                           for="rehabilitationTypeSpecific" >Stroke/neurology specific rehabilitation</label>
			                </div>
			                <div class="answer-single-column">
			                    <input  type="radio"  
			                            data-bind="checkedWithToggle: discharge.postDischargeSupport().rehabilitationType" 
			                            name="rehabilitationType" 
			                            id="rehabilitationTypeGeneral" 
			                            value="general" />
			                    <label  
			                           for="rehabilitationTypeGeneral" >Non specialist rehabilitation</label>
			                </div>
			                
			                <div class="answer-single-column">
			                    <input  type="radio"  
			                            data-bind="checkedWithToggle: discharge.postDischargeSupport().rehabilitationType" 
			                            name="rehabilitationType" 
			                            id="rehabilitationTypeNo" 
			                            value="noRehabilitation" />
			                    <label  
			                           for="rehabilitationTypeNo" >No rehabilitation</label>
			                </div>
		                </div>
					</div>
				
			</div>
		
		<!--  
		<hr />
				<h2>Debug</h2>
				<div data-bind="text: ko.toJSON(preDischargeViewModel)"></div>
		
		-->
	</div>
	</div>
</div>