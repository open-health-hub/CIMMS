<div id="sensorAndMotorFeatureForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>
		<div id="sensorAndMotorFeature" style="display:inline-block: height:100%">
			<div class="page-header">
				<h1>Sensory/Motor Features</h1>
			</div>
			
			<p>Did the patient present with ..</p>
			
			
			<h3 class="headspace">Facial weakness</h3>
			
			
			<div class="question-block clearfix">
				<div class="question-alt">
					<label>Facial Palsy</label>	
				</div>
				<div class="answer-block">

					<div class="answer">
						<input name="facialPalsy" id="facialPalsyNormal" value="normal" type="radio" data-bind="checkedWithToggle: clinicalAssessment.facialPalsy">
						<label for="facialPalsyNormal">Normal</label>
					</div>
					<div class="answer">
						<input name="facialPalsy" id="facialPalsyMinor" value="minor" type="radio" data-bind="checkedWithToggle: clinicalAssessment.facialPalsy">
						<label for="facialPalsyMinor">Minor</label>
					</div>
					<div class="answer">
						<input name="facialPalsy" id="facialPalsyPartial" value="partial" type="radio" data-bind="checkedWithToggle: clinicalAssessment.facialPalsy">
						<label for="facialPalsyPartial">Partial</label>
					</div>
					<div class="answer">
						<input name="facialPalsy" id="facialPalsyComplete" value="complete" type="radio" data-bind="checkedWithToggle: clinicalAssessment.facialPalsy">
						<label for="facialPalsyComplete">Complete</label>
					</div>
					<div class="answer">
						<input name="facialPalsy" id="facialPalsyUnknown" value="unknown" type="radio" data-bind="checkedWithToggle: clinicalAssessment.facialPalsy">
						<label for="facialPalsyUnknown">Not known</label>
					</div>
					
					<%-- 
						g:buttonRadioGroup
						style="width:125px;margin-left:54px;margin-right:5px;margin-top:10px; min-height:38px; vertical-align:top;"
						name="facialPalsy"
						labels="['Normal','Minor','Partial', 'Complete', 'Not known' ]"
						values="['normal','minor','partial','complete', 'unknown']" spans="[5,5,5,5,5]"
						bind="clinicalAssessment" value="">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup --%>
			
				</div>

			</div>

			
			
			<h3 class="headspace">Arm weakness</h3>
						
			<div class="question-block clearfix">
				<div class="question-alt"><label>Left Arm  - Scale For Muscle Strength</label> </div>
				<div class="answer-block">
							
		            <div class="control-group">
		                
		                <div class="controls toggleButtonSet">
						
	
	            		    <label for="sense_leftarm_score_na">N/A</label>
							<input class="toggleButton toggleButtonY" type="radio" data-bind="checked: clinicalAssessment.leftArmMrcScale" name="sense_leftarm_score" id="sense_leftarm_score_na" value="-1"> 
	                		
		                	<label for="sense_leftarm_score_0">0</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftArmMrcScale" name="sense_leftarm_score" id="sense_leftarm_score_0" value="0"> 
							
		                	<label for="sense_leftarm_score_1">1</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftArmMrcScale" name="sense_leftarm_score" id="sense_leftarm_score_1" value="1"> 
							
		                	<label for="sense_leftarm_score_2">2</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftArmMrcScale" name="sense_leftarm_score" id="sense_leftarm_score_2" value="2">
							
							
		                	<label for="sense_leftarm_score_3">3</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftArmMrcScale" name="sense_leftarm_score" id="sense_leftarm_score_3" value="3">
							
		                	<label for="sense_leftarm_score_4">4</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftArmMrcScale" name="sense_leftarm_score" id="sense_leftarm_score_4" value="4"> 
						</div> <!--  controls -->							
	                </div> <!--  control-group -->
            	</div>
            
			</div>
          
          
            <div class="question-block clearfix">
            	<div class="question-alt"><label>Right Arm  - Scale For Muscle Strength </label></div>
            	<div class="answer-block">
            	<div class="control-group">
                
                <div class="controls toggleButtonSet">

                     
            		    <label for="sense_rightarm_score_na">N/A</label>
						<input class="toggleButton" type="radio" data-bind="checked: clinicalAssessment.rightArmMrcScale" name="sense_rightarm_score" id="sense_rightarm_score_na" value="-1"> 
                		
	                	<label for="sense_rightarm_score_0">0</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightArmMrcScale" name="sense_rightarm_score" id="sense_rightarm_score_0" value="0">
						
	                	<label for="sense_rightarm_score_1">1</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightArmMrcScale" name="sense_rightarm_score" id="sense_rightarm_score_1" value="1">
						
	                	<label for="sense_rightarm_score_2">2</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightArmMrcScale" name="sense_rightarm_score" id="sense_rightarm_score_2" value="2">
						
	                	<label for="sense_rightarm_score_3">3</label>
							<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightArmMrcScale" name="sense_rightarm_score" id="sense_rightarm_score_3" value="3">
						
	                	<label for="sense_rightarm_score_4">4</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightArmMrcScale" name="sense_rightarm_score" id="sense_rightarm_score_4" value="4">
                     
            	</div><!--  controls -->
            </div> <!--  control-group -->
			</div> <!--  answer-block -->
		</div> <!--  question-block -->
		
		
		
		<div class="question-block clearfix">
			<div class="question-alt"><label>Dominant hand</label></div>
			<div class="answer-block">
				<div class="control-group">
                
				<div class="controls">

				<div class="answer">
				<input name="dominantHand" id="dominantHandLeft" value="left" type="radio" data-bind="checkedWithToggle: clinicalAssessment.dominantHand">
				<label for="dominantHandLeft">Left</label>
				</div>
				
				<div class="answer">
				<input name="dominantHand" id="dominantHandRight" value="right" type="radio" data-bind="checkedWithToggle: clinicalAssessment.dominantHand">
				<label for="dominantHandRight">Right</label>
				</div>
				
				<div class="answer">
				<input name="dominantHand" id="dominantHandUnknown" value="unknown" type="radio" data-bind="checkedWithToggle: clinicalAssessment.dominantHand">
				<label for="dominantHandUnknown">Unknown</label>
				</div>
						
			

				</div><!--  controls -->
			</div><!-- control-group -->
            
			</div> <!--  answer-block -->
		</div> <!--  question-block -->
		
	
	
	<h3 class="headspace">Leg weakness</h3>

	<div class="question-block clearfix" >
	
			<div class="question-alt"><label>Left Leg  - Scale For Muscle Strength </label></div>
			<div class="answer-block">
			<div class="control-group">
				
			
			<div class="controls toggleButtonSet">
				
				
            		    <label for="sense_leftleg_score_na">N/A</label>
						<input class="toggleButton" type="radio" data-bind="checked: clinicalAssessment.leftLegMrcScale" name="sense_leftleg_score" id="sense_leftleg_score_na" value="-1"> 
                		
	                	<label for="sense_leftleg_score_0">0</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftLegMrcScale" name="sense_leftleg_score" id="sense_leftleg_score_0" value="0"> 
						
	                	<label for="sense_leftleg_score_1">1</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftLegMrcScale" name="sense_leftleg_score" id="sense_leftleg_score_1" value="1">
						
	                	<label for="sense_leftleg_score_2">2</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftLegMrcScale" name="sense_leftleg_score" id="sense_leftleg_score_2" value="2">
						
	                	<label for="sense_leftleg_score_3">3</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftLegMrcScale" name="sense_leftleg_score" id="sense_leftleg_score_3" value="3">
						
	                	<label for="sense_leftleg_score_4">4</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.leftLegMrcScale" name="sense_leftleg_score" id="sense_leftleg_score_4" value="4">							
					</div>
				</div>			
			
			</div> <!--  answer-block -->
		</div> <!--  question-block -->

        
		<div class="question-block clearfix" >
			<div class="question-alt"><label>Right Leg  - Scale For Muscle Strength </label></div>
		 
		 	<div class="answer-block">
        	<div class="control-group">
			           
            <div class="controls toggleButtonSet">

            

            		    <label for="sense_rightleg_score_na">N/A</label>
						<input class="toggleButton" type="radio" data-bind="checked: clinicalAssessment.rightLegMrcScale" name="sense_rightleg_score" id="sense_rightleg_score_na" value="-1"> 
                		
	                	<label for="sense_rightleg_score_0">0</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightLegMrcScale" name="sense_rightleg_score" id="sense_rightleg_score_0" value="0">
						
	                	<label for="sense_rightleg_score_1">1</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightLegMrcScale" name="sense_rightleg_score" id="sense_rightleg_score_1" value="1">
						
	                	<label for="sense_rightleg_score_2">2</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightLegMrcScale" name="sense_rightleg_score" id="sense_rightleg_score_2" value="2">
						
	                	<label for="sense_rightleg_score_3">3</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightLegMrcScale" name="sense_rightleg_score" id="sense_rightleg_score_3" value="3">
						
	                	<label for="sense_rightleg_score_4">4</label>
						<input class="toggleButton"  type="radio" data-bind="checked: clinicalAssessment.rightLegMrcScale" name="sense_rightleg_score" id="sense_rightleg_score_4" value="4">						                
            </div>
         </div>
         </div>
      </div>
 

 	
    <h3 class="headspace">Sensory loss</h3>          
    
    <div class="question-block clearfix">
		   <div class="question-alt"><label>Level</label></div>
		   
           <div class="answer-block">
           
	            <g:buttonRadioGroup
	                class="answer"    
	                name="sensoryLoss"
	                labels="['Normal','Mild-to-moderate' ,'Severe', 'Not known']"
	                values="['none','mild', 'severe', 'unknown']" spans="[5,5,5,5]"
	                bind="clinicalAssessment" value="">
	                ${it.radio}
	                ${it.label}
	            </g:buttonRadioGroup>
				
			</div>
		</div>
					
            
       <div class="question-block clearfix">
                <div class="question-alt"><label>Side affected</label></div>
                
            	<div class="answer-block">
            	<div class="answer"
                	data-bind="visible: !clinicalAssessment.neitherSideAffected()">
	                <g:checkBox name="leftSideAffected" id="leftSideAffected"
	                    data-bind="checkBoxAsButton: clinicalAssessment.leftSideAffected"></g:checkBox>
                	<label for="leftSideAffected">Left</label>
            	</div>
            	
            	<div class="answer" 
                	data-bind="visible: !clinicalAssessment.neitherSideAffected()">
                	<g:checkBox name="rightSideAffected" id="rightSideAffected"
                    	data-bind="checkBoxAsButton: clinicalAssessment.rightSideAffected"></g:checkBox>
                	<label for="rightSideAffected">Right</label>
            	</div>

            	<div class="answer">
                	<g:checkBox name="neitherSideAffected" id="neitherSideAffected"
                    	data-bind="checkBoxAsButton: clinicalAssessment.neitherSideAffected"></g:checkBox>
                	<label for="neitherSideAffected">Neither</label>
            	</div>
			</div>
		</div>

			
			
			<div class="question-block clearfix">
				<div class="question-alt"><label>Was the patient able to walk without assistance at
					presentation?</label></div>
			
			
				<div class="answer-block">
					<g:buttonRadioGroup
						class="answer"					
						name="walkAtPresentation" labels="['Yes','No']"
						values="['true','false']" spans="[6,6]" bind="clinicalAssessment">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
	            </div>
            </div>
            
            
			<div id="ableToWalkAtPresentation"
				data-bind="visible: clinicalAssessment.walkAtPresentation() == 'false' ">
				
				<div class="question-block clearfix">
				<div class="question-alt"><label>Were they able to do so before the stroke?</label>
				</div>
				
				<div class="answer-block">
					<g:buttonRadioGroup
						class="answer"
						name="mobilePreStroke" labels="['Yes','No']"
						values="['true','false']" spans="[6,6]" bind="clinicalAssessment">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>
				</div>
				</div>
			</div>
		

		</div>
	</div>
	<g:render template="/templates/sectionFoot"></g:render>
</div>
