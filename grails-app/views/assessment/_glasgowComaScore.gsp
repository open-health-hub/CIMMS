<div id="glasgowComaScoreForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>
		<div id="glasgowComaScore">
			<div class="page-header">				
				<h1>Glasgow Coma Score</h1>
            </div>
            
            <form>
            <div id="glasgowComaScoreDateTime">
               <div class="control-group">    
	               <div class="control-label" >Date</div>
	               <div class="controls">
		                <div class="value"  data-bind="inError :'glasgowComaScore.dateAssessed clinicalAssessment.glasgowComaScore.dateAssessed'">
		                    <g:textField   data-bind="value: clinicalAssessment.glasgowComaScore().dateAssessed" style="width:106px" class="standardDatePicker "  name="dateOfGlasgowComaScore"  
		                            value="" />
		                </div>
	                </div>	                
                </div>
				<div class="control-group">
	               <div class="control-label" >Time</div>  
                   <div class="controls">
                   		<div class="value" data-bind="inError :'glasgowComaScore.timeAssessed clinicalAssessment.glasgowComaScore.timeAssessed'">
                    		<g:textField  data-bind="value: clinicalAssessment.glasgowComaScore().timeAssessed" style="width:50px;" 
                    			class="time" name="timeOfGlasgowComaScore" 
                         		value=""   />
                		</div>
            		</div>
            	</div>
            	
				<div class="question-block">
	               <div class="question-alt" ><label>Motor</label></div>
            	   <div class="answer-block toggleButtonSet">	
            		
                	<%-- span data-bind="numberSelector: clinicalAssessment.glasgowComaScore().motorScore, symbols:6, min:1">                	
            		</span>
            		<span data-bind="text: clinicalAssessment.glasgowComaScore().motorScore() ? '(' + clinicalAssessment.glasgowComaScore().motorScore() + ')' : ''" >
            		</span --%>
            		
            		    <label for="gcs_motorscore_na">N/A</label>
						<input class="toggleButton" type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().motorScore" name="gcs_motorscore" id="gcs_motorscore_na" value="">
                		
	                	<label for="gcs_motorscore_1">1</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().motorScore" name="gcs_motorscore" id="gcs_motorscore_1" value="1">
						
	                	<label for="gcs_motorscore_2">2</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().motorScore" name="gcs_motorscore" id="gcs_motorscore_2" value="2">
						
	                	<label for="gcs_motorscore_3">3</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().motorScore" name="gcs_motorscore" id="gcs_motorscore_3" value="3"> 
						
	                	<label for="gcs_motorscore_4">4</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().motorScore" name="gcs_motorscore" id="gcs_motorscore_4" value="4"> 
						
	                	<label for="gcs_motorscore_5"> 5</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().motorScore" name="gcs_motorscore" id="gcs_motorscore_5" value="5">
						
	                	<label for="gcs_motorscore_6">6</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().motorScore" name="gcs_motorscore" id="gcs_motorscore_6" value="6"> 
						
            	</div>
            	
            	<div class="clearfix"></div>
            	
            	<div class="question-block" >
                	
            	
            		<div class="question-alt"><label>Eyes</label></div>
            		<div class="answer-block toggleButtonSet" >
                		<%--
                		<span data-bind="numberSelector: clinicalAssessment.glasgowComaScore().eyeScore, symbols:4, min:1">
                		</span>
                		<span data-bind="text: clinicalAssessment.glasgowComaScore().eyeScore() ? '(' + clinicalAssessment.glasgowComaScore().eyeScore() + ')' : ''" ></span>
                		--%>
                		 
                		<label for="gcs_eyescore_na">N/A</label>
							<input class="toggleButton" type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().eyeScore" name="gcs_eyescore" id="gcs_eyescore_na" value=""> 
                		
	                	<label for="gcs_eyescore_1">1</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().eyeScore" name="gcs_eyescore" id="gcs_eyescore_1" value="1"> 
						
	                	<label for="gcs_eyescore_2">2</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().eyeScore" name="gcs_eyescore" id="gcs_eyescore_2" value="2"> 
						
	                	<label for="gcs_eyescore_3">3</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().eyeScore" name="gcs_eyescore" id="gcs_eyescore_3" value="3"> 
						
	                	<label for="gcs_eyescore_4">4</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().eyeScore" name="gcs_eyescore" id="gcs_eyescore_4" value="4"> 
                        
            		</div>
            		
            	</div>
            	
            	<div class="clearfix"></div>
            	
            	<div class="question-block">
                	<div class="question-alt"><label>Verbal</label></div>
            		<div class="answer-block">
            		
          		
	                	<%-- span data-bind="numberSelector: clinicalAssessment.glasgowComaScore().verbalScore, symbols:5, min:1">
	                    </span>
                  		<span data-bind="text: clinicalAssessment.glasgowComaScore().verbalScore() ? '(' + clinicalAssessment.glasgowComaScore().verbalScore() + ')' : ''" ></span--%>

						<div class="toggleButtonSet">
                		<label for="gcs_verbalscore_na">N/A</label>
							<input class="toggleButton" type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().verbalScore" name="gcs_verbalscore" id="gcs_verbalscore_na" value=""> 
                		
	                	<label for="gcs_verbalscore_1">1</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().verbalScore" name="gcs_verbalscore" id="gcs_verbalscore_1" value="1"> 
						
	                	<label for="gcs_verbalscore_2">2</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().verbalScore" name="gcs_verbalscore" id="gcs_verbalscore_2" value="2"> 
						
	                	<label for="gcs_verbalscore_3">3</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().verbalScore" name="gcs_verbalscore" id="gcs_verbalscore_3" value="3"> 
						
	                	<label for="gcs_verbalscore_4">4</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().verbalScore" name="gcs_verbalscore" id="gcs_verbalscore_4" value="4"> 
						
	                	<label for="gcs_verbalscore_5">5</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.glasgowComaScore().verbalScore" name="gcs_verbalscore" id="gcs_verbalscore_5" value="5"> 						
						</div>
						
            		</div>
            	</div>
            	
            	<div class="clearfix"></div>
            	
            	<div class="question-block">
                	<div class="question-alt"><label>Total</label></div>
		            <div class="answer-block" >
		            	<span class="answer badge" data-bind="text: theGlasgowScoreTotal" ></span>
	           		</div>
	        	</div>
                	
            	</div>
	</form>       
	</div>
	
	</div>
</div>