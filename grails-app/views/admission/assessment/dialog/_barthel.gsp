<div id="preMorbidBarthelData" 
	 data-bind='jqueryui: { widget: "dialog", 
       						options:{ 	modal: true,
       									resizable:false,
       									autoOpen: false,
       									width:980,
       									zIndex: 1050,
       									title: "Barthel Activities Of Daily living Scale (Pre Morbid)",
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
        <g:buttonRadioGroup class="barthelScore"  
                                                name="bowels"
                                                idStem="preMorbid"
                                                labels="['Incontinent','Occasional Accident','Continent' ]" 
                                                values="['0','1','2']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            </g:buttonRadioGroup>
            </div>
            </div>
            
            
            <div class="question-block">
        	<div class="question-alt"><label>Bladder</label></div>
        	<div class="answer-block">
        		<g:buttonRadioGroup class="barthelScore"  
                                                name="bladder"
                                                idStem="preMorbid"
                                                labels="['Incontinent','Occasional Accident','Continent' ]" 
                                                values="['0','1', '2']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            	</g:buttonRadioGroup>
            </div>
            </div>
            
            <div class="question-block">
        	<div class="question-alt"><label>Grooming</label></div>
        	<div class="answer-block">
        		<g:buttonRadioGroup class="barthelScore"   
                                                name="grooming"
                                                idStem="preMorbid"
                                                labels="['Needs Help','Independent' ]" 
                                                values="['0','1']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            	</g:buttonRadioGroup>
            </div>
            </div>
            
            <div class="question-block">
        	<div class="question-alt"><label>Toilet Use</label></div>
            <div class="answer-block">
        		<g:buttonRadioGroup class="barthelScore"   
                                                name="toilet"
                                                idStem="preMorbid"
                                                labels="['Dependent','Needs Some Help', 'Independent' ]" 
                                                values="['0','1', '2']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            </g:buttonRadioGroup>
            </div>
            </div>
            
            
            <div class="question-block">
        	<div class="question-alt"><label>Feeding</label></div>
            <div class="answer-block">
        		<g:buttonRadioGroup  class="barthelScore"  
                                                name="feeding"
                                                idStem="preMorbid"
                                                labels="['Dependent','Needs Some Help', 'Independent' ]" 
                                                values="['0','1', '2']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            	</g:buttonRadioGroup>
            </div>
            </div>
                   
            <div class="question-block">
        	<div class="question-alt"><label>Transfer (bed-chair)</label></div>
            <div class="answer-block">
        		<g:buttonRadioGroup  class="barthelScore"  
                                                name="transfer"
                                                idStem="preMorbid"
                                                labels="['Unable','Major Help', 'Minor Help' , 'Independent']" 
                                                values="['0','1', '2', '3']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            	</g:buttonRadioGroup>
            </div>
            </div>       
            
            <div class="question-block">
        	<div class="question-alt"><label>Mobility</label></div>
            <div class="answer-block">
        		<g:buttonRadioGroup class="barthelScore"   
                                                name="mobility"
                                                idStem="preMorbid"
                                                labels="['Immobile','Wheelchair independent', 'Walks With Help' , 'Independent']" 
                                                values="['0','1', '2', '3']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
	            </g:buttonRadioGroup>
	        </div>
	        </div>
	           
            <div class="question-block">
        	<div class="question-alt"><label>Dressing</label></div>
            <div class="answer-block">
        		<g:buttonRadioGroup class="barthelScore"   
                                                name="dressing"
                                                idStem="preMorbid"
                                                labels="['Dependent','Needs Some Help', 'Independent' ]" 
                                                values="['0','1', '2']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            	</g:buttonRadioGroup>
            </div>
            </div>
                   
            <div class="question-block">
        	<div class="question-alt"><label>Stairs</label></div>
            <div class="answer-block">
        		<g:buttonRadioGroup class="barthelScore"   
                                                name="stairs"
                                                idStem="preMorbid"
                                                labels="['Unable','Needs Some Help', 'Independent' ]" 
                                                values="['0','1', '2']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
                                                value="" >
                                                        ${it.radio} 
                                                        ${it.label}
            	</g:buttonRadioGroup>
            </div>
            </div>
                   
            <div class="question-block">
        	<div class="question-alt"><label>Bathing</label></div>
            <div class="answer-block">
        		<g:buttonRadioGroup class="barthelScore"  
                                                name="bathing"
                                                idStem="preMorbid"
                                                labels="['Dependent','Independent' ]" 
                                                values="['0','1']"
                                                class="answer"
                                                bind="admissionDetails().preMorbidAssessment().assessments().barthel()"
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
