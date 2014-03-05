<div class="page-header">
	<h1>Post Stroke Assessments</h1>
</div>
 <div class="form-container">
    <form class="form-horizontal">
        
    <div class="control-group">    
	
		<label class="control-label" for="baselineManualBarthelScore">Barthel Score</label>
		<div class="controls">
		
		
			<div id="barthelKnown"   data-bind="visible: therapyManagement.assessments().barthelNotKnown() != true, 
                inError:'therapyManagement.assessmentManagement.barthelAssessments.manualTotal therapyManagement.baselineAssessmentManagement.barthel.bowels therapyManagement.baselineAssessmentManagement.barthel.bladder therapyManagement.baselineAssessmentManagement.barthel.grooming therapyManagement.baselineAssessmentManagement.barthel.toilet therapyManagement.baselineAssessmentManagement.barthel.feeding therapyManagement.baselineAssessmentManagement.barthel.transfer therapyManagement.baselineAssessmentManagement.barthel.mobility therapyManagement.baselineAssessmentManagement.barthel.dressing therapyManagement.baselineAssessmentManagement.barthel.stairs therapyManagement.baselineAssessmentManagement.barthel.bathing' ">
			
				<input class="input-mini" name="baselineManualBarthelScore" id="baselineManualBarthelScore"
					data-bind="value: therapyManagement.assessments().barthel().manualTotal, visible: therapyManagement.assessments().barthel().hasDetail() != true"  />
				<input class="input-mini" name="baselineCalculatedBarthelScore" id="baselineCalculatedBarthelScore" 
						disabled="true" data-bind="value: theBarthelScore, visible: therapyManagement.assessments().barthel().hasDetail() === true"  />
			</div>

			<div>
				<label for="barthelIsUnknown">Not known</label>
				<g:checkBox  data-bind="checkBoxAsButton: therapyManagement.assessments().barthelNotKnown" 
					name="barthelIsUnknown" 
					id="barthelIsUnknown"
					value="" />
			</div>		
		
			
			<span id="editBarthelLink" class="help-block" data-bind="visible: therapyManagement.assessments().barthelNotKnown() != true ">
				<p>Click <strong><a id="editBarthelScore"  data-bind="click: editBarthel " >here</a></strong> to enter details</p>
			</span>
		</div>
	</div>






	<div id="modifiedRankinKnown"  data-bind="inError:'therapyManagement.assessmentManagement.modifiedRankinAssessments.modifiedRankinScore' ">
		<div class="control-group">
			<label class="control-label" for="baselineModifiedRankinScore">Modified Rankin Score</label>
	
			<div class="controls">
                    <input 
						class="input-mini" 			
						name="baselineModifiedRankinScore" 
						id="baselineModifiedRankinScore" 
						data-bind="value: therapyManagement.assessments().modifiedRankin().modifiedRankinScore"  />
					<span class="help-block" id="editModifiedRankinLink">
						<p>Click <strong><a id="editModifiedRankinScore" data-bind="click: editModifiedRankin " >here</a></strong> to enter details</p>
					</span>
			</div>
		</div>
	
	<%--div class="span-5 ">
		<g:checkBox name="modifiedRankinUnknown" data-bind="checkBoxAsButton: therapyManagement.assessments().modifiedRankinNotKnown" name="modifiedRankinUnknown"
			value="" />
		<label style="font-size: 10px" for="modifiedRankinUnknown">Not known</label>
	</div--%>
	


	</div>
	</form>
</div>
	
	<div id="baselineBarthelData" data-bind='jqueryui: { widget: "dialog", 
                            								options: { 	modal: true,
																												resizable:false,
																												autoOpen: false,
																												width:980,
																												zIndex: 1050,
																												title: "Barthel Activities Of Daily living Scale",
																												buttons: {
																														"Ok": updateBarthelTotal,
																														"Clear": cancelBarthel
																												} 
																											} } '>





	<div class="containPanel ">
		<div class="question-block">
		<div class="question-alt"><label>Bowels</label></div>
		<div class="answer-block">
			<g:buttonRadioGroup class="answer barthelScore"  
			 									name="bowels"
												labels="['Incontinent','Occasional Accident','Continent' ]" 
												values="['0','1','2']"
												spans="[6,6,6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>			
			</div>
			
			<div class="question-block">			
			<div class="question-alt"><label>Bladder</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"  
			 									name="bladder"
												labels="['Incontinent','Occasional Accident','Continent' ]" 
												values="['0','1', '2']"
												spans="[6,6,6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
			
			<div class="question-block">
			<div class="question-alt"><label>Grooming</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"   
			 									name="grooming"
												labels="['Needs Help','Independent' ]" 
												values="['0','1']"
												spans="[6,6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
			
			<div class="question-block">
			<div class="question-alt"><label>Toilet Use</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"   
			 									name="toilet"
												labels="['Dependent','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
						
			<div class="question-block">
			<div class="question-alt"><label>Feeding</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup  class="answer barthelScore"  
			 									name="feeding"
												labels="['Dependent','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
			
			<div class="question-block">
			<div class="question-alt"><label>Transfer (bed-chair)</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup  class="answer barthelScore"  
			 									name="transfer"
												labels="['Unable','Major Help', 'Minor Help' , 'Independent']" 
												values="['0','1', '2', '3']"
												spans="[6,6, 6,6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
			
			<div class="question-block">
			<div class="question-alt"><label>Mobility</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"   
			 									name="mobility"
												labels="['Immobile','Wheelchair independent', 'Walks With Help' , 'Independent']" 
												values="['0','1', '2', '3']"
												spans="[6,6, 6,6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
			
			<div class="question-block">	
			<div class="question-alt"><label>Dressing</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"   
			 									name="dressing"
												labels="['Dependent','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
			
			<div class="question-block">	
			<div class="question-alt"><label>Stairs</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"   
			 									name="stairs"
												labels="['Unable','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
					
			<div class="question-block">
			<div class="question-alt"><label>Bathing</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"  
			 									name="bathing"
												labels="['Dependent','Independent' ]" 
												values="['0','1']"
												spans="[6,6]"
												bind="therapyManagement.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>
			</div>
		
	</div>
</div>
<div id="baselineModifiedRankinData" 
	 data-bind='jqueryui: { widget: "dialog", 
             				options: { 	modal: true,
										resizable:false,
										autoOpen: false,
										width:980,
										minHeight: 550,
										height: 550,
										zIndex: 1050,
										title: "Modified Rankin Scale",
										buttons: {
													"Ok": function(){
														if(baselineAssessmentViewModel.therapyManagement.assessments().modifiedRankin().modifiedRankinScore()=="null"){
															baselineAssessmentViewModel.therapyManagement.assessments().modifiedRankin().modifiedRankinScore("");
														}
														$("#baselinemodifiedRankinScore").attr("disabled", false);
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
			<g:buttonRadioGroup class="modifiedRankinScore answer-single-column"
			 				name="modifiedRankinScore"
			 				idStem="baseline"
							labels="['No Symptoms At All','No Significant disability despite symptoms)','Slight disability', 'Moderate disability', 'Moderately severe disability','Severe disability','Dead' ]" 
							values="['0','1', '2','3','4','5','6']"
							spans="[20,20,20,20,20,20,20]"
							bind="baselineAssessmentViewModel.therapyManagement.assessments().modifiedRankin()"
							value="" >
								${it.radio} 
								${it.label}
			</g:buttonRadioGroup>
		</div>
		</div>
	</div>
</div>

