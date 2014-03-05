<div class="page-header">
	<h1>Discharge function assessments</h1>
</div>
 
 <div class="question-block" data-bind="visible: dischargeAssessment.dischargedTo() != 'intermediateCare' && dischargeAssessment.dischargedTo() != 'died' && dischargeAssessment.dischargedTo() != 'otherHospital'">
	 <div class="question-alt">
		<label>Barthel Score</label>
	</div>

	<div 
		id="dischargeBarthelKnown" 
		class="answer-block"  
		data-bind="visible: dischargeAssessment.assessments().barthelNotKnown() != true, 
            inError:'therapyManagement.assessmentManagement.barthelAssessments.manualTotal therapyManagement.assessmentManagement.barthel.bowels therapyManagement.assessmentManagement.barthel.bladder therapyManagement.assessmentManagement.barthel.grooming therapyManagement.assessmentManagement.barthel.toilet therapyManagement.assessmentManagement.barthel.feeding therapyManagement.assessmentManagement.barthel.transfer therapyManagement.assessmentManagement.barthel.mobility therapyManagement.assessmentManagement.barthel.dressing therapyManagement.assessmentManagement.barthel.stairs therapyManagement.assessmentManagement.barthel.bathing' ">
	
		<g:textField class="answer" name="dischargeManualBarthelScore" 
			 data-bind="value: dischargeAssessment.assessments().barthel().manualTotal, visible: dischargeAssessment.assessments().barthel().hasDetail() != true"  />
			 
		<g:textField class="answer" name="dischargeCalculatedBarthelScore" disabled="true" data-bind="value: theBarthelScore
																								, visible: dischargeAssessment.assessments().barthel().hasDetail() === true"  />
		<g:if test="${grailsApplication.config.ssnap.level == 'standard'}">
			<div class="answer">
				<g:checkBox  data-bind="checkBoxAsButton: dischargeAssessment.assessments().barthelNotKnown" 
							 name="dischargeBarthelIsUnknown"
							 value="" />
				<label  for="dischargeBarthelIsUnknown">Not known</label>
			</div>
		</g:if>
		<p>
		</p>
		<div id="editDischargeBarthelLink" class=" " 
			data-bind="visible: dischargeAssessment.assessments().barthelNotKnown() != true ">
			<p>click <strong><a id="editDischargeBarthelScore"  data-bind="click: editBarthel " >here</a></strong> to enter details</p>
		</div>
	</div>
 </div>

<div class="clearfix"></div>

<div class="question-block">
	<div class="question-alt"><label>Modified Rankin Score</label></div>

	<div class="answer-block">
		<div id="dischargeModifiedRankinKnown" class="answer" data-bind="inError:'therapyManagement.assessmentManagement.modifiedRankinAssessments.modifiedRankinScore' ">
				<g:textField name="dischargeModifiedRankinScore" data-bind="value: dischargeAssessment.assessments().modifiedRankin().modifiedRankinScore"  />
		</div>
		<p></p>
		<div id="editDischargeModifiedRankinLink" class="answer" >
			<p>click <strong><a id="editDischargeModifiedRankinScore" data-bind="click: editModifiedRankin " >here</a></strong> to enter details</p>
		</div>
	</div>
</div>

<div class="clearfix"></div>
<br>

<%--div class=" ">
	<g:checkBox
		data-bind="checkBoxAsButton: dischargeAssessment.assessments().modifiedRankinNotKnown" 
		name="dischargeModifiedRankinUnknown"
		value="" />
	<label  for="dischargeModifiedRankinUnknown">Not known</label>
</div--%>


<div data-bind="visible: dischargeAssessment.dischargedTo() != 'otherHospital' && dischargeAssessment.dischargedTo() != 'died' ">
	<div class="question-block" >
		<div class="question-alt"><label>Is there documented evidence that the patient is in AF on discharge?</label></div>
		<div class="answer-block">
			<g:buttonRadioGroup
				class="answer"	
				name="inAfOnDischarge" labels="['Yes','No']"
				values="['Yes','No']" spans="[5,5 ]"
				bind="dischargeAssessment">
				${it.radio}
				${it.label}
			</g:buttonRadioGroup>
		</div>
	</div>	
	
	<div class="clearfix"></div>
	
	<div data-bind="visible: dischargeAssessment.inAfOnDischarge() == 'Yes'"  >
		<div class="question-block" >
			<div class="question-alt"><label>Was the patient taking anticoagulation on discharge or discharged with a plan to start anticoagulation within the next month?(not anti-platelet agent)</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup
					class="answer"	
					name="onAnticoagulantAtDischarge" labels="['Yes','No','No But']"
					values="['Yes','No','NoBut']" spans="[5,5,5 ]"
					bind="dischargeAssessment">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>				
	</div>
</div>














<g:render template="/discharge/assessment/dialog/barthel" 
								model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>

<g:render template="/discharge/assessment/dialog/modifiedRankin" 
								model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>




