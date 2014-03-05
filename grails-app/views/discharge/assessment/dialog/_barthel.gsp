<div id="DischargeBarthelData" 
	 data-bind='jqueryui: { widget: "dialog", 
       						options:{ 	modal: true,
       									resizable:false,
       									autoOpen: false,
       									width:980,
       									zIndex: 1050,
       									title: "Barthel Activities Of Daily living Scale (Discharge)",
										buttons: {
												"Ok": updateBarthelTotal,
												"Clear": cancelBarthel
										} 
									}	 
							} '>





	<div class="containPanel ">
	
		<div class="question-block">
			<div class="question-alt"><label>Bowels</label></div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer barthelScore"  
			 									name="bowels"
			 									idStem="discharge"
												labels="['Incontinent','Occasional Accident','Continent' ]" 
												values="['0','1','2']"
												spans="[6,6,6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Incontinent','Occasional Accident','Continent' ]" 
												values="['0','1', '2']"
												spans="[6,6,6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Needs Help','Independent' ]" 
												values="['0','1']"
												spans="[6,6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Dependent','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Dependent','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Unable','Major Help', 'Minor Help' , 'Independent']" 
												values="['0','1', '2', '3']"
												spans="[6,6, 6,6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Immobile','Wheelchair independent', 'Walks With Help' , 'Independent']" 
												values="['0','1', '2', '3']"
												spans="[6,6, 6,6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Dependent','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Unable','Needs Some Help', 'Independent' ]" 
												values="['0','1', '2']"
												spans="[6,6, 6]"
												bind="dischargeAssessment.assessments().barthel()"
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
			 									idStem="discharge"
												labels="['Dependent','Independent' ]" 
												values="['0','1']"
												spans="[6,6]"
												bind="dischargeAssessment.assessments().barthel()"
												value="" >
														${it.radio} 
														${it.label}
				</g:buttonRadioGroup>
			</div>					
		</div>	
				
			
		
		<!--  
		<hr />
		<h2>Debug</h2>
		<div data-bind="text: ko.toJSON(dischargeAssessmentViewModel)"></div>
		-->
	</div>

</div>
