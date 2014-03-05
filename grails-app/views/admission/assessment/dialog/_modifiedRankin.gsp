<div id="preMorbidModifiedRankinData" 
	 data-bind='jqueryui: { widget: "dialog", 
             				options: { 	modal: true,
										resizable:false,
										autoOpen: false,
										width:980,
										minHeight: 500,
										zIndex: 1050,
										title: "Modified Rankin Scale (Pre Morbid)",
										buttons: {
													"Ok": function(){
														if(preMorbidAssessmentViewModel.admissionDetails().preMorbidAssessment().assessments().modifiedRankin().modifiedRankinScore()=="null"){
															preMorbidAssessmentViewModel.admissionDetails().preMorbidAssessment().assessments().modifiedRankin().modifiedRankinScore("");
														}
														$("#dischargeModifiedRankinScore").attr("disabled", false);
														$( this ).dialog( "close" );
													}
										} 
					} } ' >

	<div class="containPanel ">
		<div class="question-block">
		<div class="question-alt">
			<label>Select the option which best describes the present state of the patient</label>
		</div>
		<div class="answer-block">
		<g:buttonRadioGroup class="preMorbidModifiedRankinScore answer-single-column" 
							 
			 				name="modifiedRankinScore"
			 				idStem="preMorbid"
							labels="['No Symptoms At All','No Significant disability despite symptoms)','Slight disability', 'Moderate disability', 'Moderately severe disability','Severe disability','Dead' ]" 
							values="['0','1', '2','3','4','5','6']"
							
							bind="admissionDetails().preMorbidAssessment().assessments().modifiedRankin()"
							value="" >
								${it.radio} 
								${it.label}
			</g:buttonRadioGroup>
		</div>
		</div>
		
		<!-- 
		<hr />
		<h2>Debug</h2>
		<div data-bind="text: ko.toJSON(dischargeAssessmentViewModel.dischargeAssessment.assessments().modifiedRankin())"></div>
		 -->
	</div>
</div>
