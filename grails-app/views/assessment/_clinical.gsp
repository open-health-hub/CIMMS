<div id="clinicalAssessmentForm">
	<g:render template="/templates/sectionHead"></g:render>		
		
	<div class="container " data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>	
		<div id="clinical">
			<div class=" span-30" >
				<h5>Glasgow Coma Score</h5>
			</div>
			<div id="glasgowComaScoreDateTime" >	
					<div class=" span-1  prepend-5 " >
		  			Date
		  		</div>
		 		<div class="span-6 value "  data-bind="inError :'glasgowComaScore.dateAssessed clinicalAssessment.glasgowComaScore.dateAssessed'">
					<g:textField   data-bind="value: clinicalAssessment.glasgowComaScore().dateAssessed" style="width:106px" class="standardDatePicker "  name="dateOfGlasgowComaScore"  
							value="" />
				</div>	
				<div class="span-2 ">
					Time
				</div>	
				<div class="span-3 value " style="vertical-align:2px;"  data-bind="inError :'glasgowComaScore.timeAssessed clinicalAssessment.glasgowComaScore.timeAssessed'">
					<g:textField  data-bind="value: clinicalAssessment.glasgowComaScore().timeAssessed" style="width:50px;" class="time" name="timeOfGlasgowComaScore" 
						 value=""   />
				</div>
			</div>
			<div class="span-30-empty-line"></div>
			<div class="span-2 prepend-4">
				<h5>Motor</h5>
			</div>
			<div class="span-6" >
				<div data-bind="numberSelector: clinicalAssessment.glasgowComaScore().motorScore, symbols:6, min:1">
				</div>
			</div>
			<div class="span-16" style="min-height:35px" >
				<span><p data-bind="text: clinicalAssessment.glasgowComaScore().motorScore() ? '(' + clinicalAssessment.glasgowComaScore().motorScore() + ')' : ''" ></p></span>
			</div>
			<div class="span-2 prepend-4">
				<h5>Eyes</h5>
			</div>
			<div class="span-6" >
				<div data-bind="numberSelector: clinicalAssessment.glasgowComaScore().eyeScore, symbols:4, min:1">
				</div>
			</div>
			<div class="span-16" style="min-height:35px" >
				<span><p data-bind="text: clinicalAssessment.glasgowComaScore().eyeScore() ? '(' + clinicalAssessment.glasgowComaScore().eyeScore() + ')' : ''" ></p></span>
			</div>
			<div class="span-2 prepend-4">
				<h5>Verbal</h5>
			</div>
			<div class="span-6" >
				<div data-bind="numberSelector: clinicalAssessment.glasgowComaScore().verbalScore, symbols:5, min:1">
				</div>
			</div>
			<div class="span-16" style="min-height:35px" >
				<span><p data-bind="text: clinicalAssessment.glasgowComaScore().verbalScore() ? '(' + clinicalAssessment.glasgowComaScore().verbalScore() + ')' : ''" ></p></span>
			</div>
			<div class="span-2 prepend-4">
				<h5>Total</h5>
			</div>
			<div class="span-16" style="min-height:50px" >
				<span><p data-bind="text: theGlasgowScoreTotal" ></p></span>
			</div>
			
			
	
					
			<div class="span-30">			
				<h4>Did the patient present with ..</h4>
			</div>
			<div class="span-30-empty-line"></div>	
			<div class="prepend-1 span-29 ">
				<h5>  Facial weakness?</h5>
			</div>
			<div class="span-4 prepend-2">
				<h5>Facial Palsy</h5>
			</div>
			  
			<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px; min-height:38px; vertical-align:top;" 
			 									name="facialPalsy"
												labels="['Normal','Minor','Partial', 'Complete' ]" 
												values="['normal','minor','partial','complete']"
												spans="[5,5,5,5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
			
			<div class="prepend-2 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Sensory loss</h5>
			</div>

			<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" 
			 									name="faceSensoryLoss"
												labels="['None','Mild-to-moderate' ,'Severe', 'Unable to assess']" 
												values="['none','mild', 'severe','unable']"
												spans="[5,5,5,5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
			<div class="span-30" style="min-height:1px;">
			</div>	
			<div class="span-4 prepend-2" >
				<h5 style="min-height:25px;padding-top:15px">Side affected</h5>
			</div>
			<div class="span-5" data-bind="visible: !clinicalAssessment.neitherFaceAffected()" >
				<g:checkBox name="leftFaceAffected" data-bind="checkBoxAsButton: clinicalAssessment.leftFaceAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="leftFaceAffected">Left</label>
		</div>
			<div class="span-5" data-bind="visible: !clinicalAssessment.neitherFaceAffected()" >
				<g:checkBox name="rightFaceAffected" data-bind="checkBoxAsButton: clinicalAssessment.rightFaceAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="rightFaceAffected">Right</label>
		</div>
		
		<div class="span-5">
				<g:checkBox name="neitherFaceAffected" data-bind="checkBoxAsButton: clinicalAssessment.neitherFaceAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="neitherFaceAffected">Neither</label>
		</div>
			
				<div class="span-30" style="min-height:15px;">
			</div>	
			
			
			
			
			 
			
			<div class="prepend-1 span-29 ">
				<h5>  Arm Weakness?</h5>
			</div>	  
			<div class="span-4 prepend-2">
				<h5>Scale For Muscle Strength</h5>
			</div>
			<div class="span-6" >
				<div data-bind="numberSelector: clinicalAssessment.armMrcScale, symbols:6, min:0">
				</div>
			</div>
			<div class="span-12" style="min-height:50px" >
				<span><p data-bind="text: isNumber(clinicalAssessment.armMrcScale())? '(' + clinicalAssessment.armMrcScale() + ')' : ''" ></p></span>
			</div>
			
			<div class="span-30" style="min-height:1px;">
			</div>	
			
			<div class="prepend-2 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">  sensory loss</h5>
			</div>
			
				<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" 
			 									name="armSensoryLoss"
												labels="['None','Mild-to-moderate' ,'Severe', 'Unable to assess']" 
												values="['none','mild', 'severe','unable']"
												spans="[5,5,5,5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>

	
			<div class="span-30" style="min-height:1px;">
			</div>
			
			
			<div class="span-4 prepend-2">
				<h5 style="min-height:25px;padding-top:15px">Side affected</h5>
			</div>
			<div class="span-5" data-bind="visible: !clinicalAssessment.neitherArmAffected()" >
				<g:checkBox name="leftArmAffected" data-bind="checkBoxAsButton: clinicalAssessment.leftArmAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="leftArmAffected">Left</label>
		</div>
			<div class="span-5" data-bind="visible: !clinicalAssessment.neitherArmAffected()" >
				<g:checkBox name="rightArmAffected" data-bind="checkBoxAsButton: clinicalAssessment.rightArmAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="rightArmAffected">Right</label>
		</div>
		
		<div class="span-5">
				<g:checkBox name="neitherArmAffected" data-bind="checkBoxAsButton: clinicalAssessment.neitherArmAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="neitherArmAffected">Neither</label>
		</div>
			
			
	<div class="span-30" style="min-height:1px;">
			</div>	
			
			<div class="prepend-2 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">  Dominant hand</h5>
			</div>
			
				<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" 
			 									name="dominantHand"
												labels="['Left','Right' ,'Unknown']" 
												values="['left','right', 'unknown']"
												spans="[5,5,5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>		
			
				<div class="span-30" style="min-height:15px;">
			</div>	
			
			<div class="span-30" style="min-height:1px;">
			</div>	
			
			
			
			<div class="prepend-1 span-23 ">
				<h5>Leg Weakness?</h5>
			</div>
			<div class="span-4 prepend-2">
				<h5>Scale For Muscle Strength</h5>
			</div>
			<div class="span-6" >
				<div data-bind="numberSelector: clinicalAssessment.legMrcScale, symbols:6, min:0">
				</div>
			</div>
			<div class="span-12" style="min-height:50px" >
				<span><p data-bind="text: isNumber(clinicalAssessment.legMrcScale()) ? '(' + clinicalAssessment.legMrcScale() + ')' : ''" ></p></span>
			</div>
			
			<div class="span-30" style="min-height:1px;">
			</div>	
			
			
			
			<div class="prepend-2 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">  sensory loss</h5>
			</div>
			
			<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" 
			 									name="legSensoryLoss"
												labels="['None','Mild-to-moderate' ,'Severe', 'Unable to assess']" 
												values="['none','mild', 'severe','unable']"
												spans="[5,5,5,5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
				  
			
			
				
		<div class="span-30" style="min-height:1px;">
			</div>	
			
			<div class="span-4 prepend-2">
				<h5 style="min-height:25px;padding-top:15px">Side affected</h5>
			</div>
			<div class="span-5" data-bind="visible: !clinicalAssessment.neitherLegAffected()" >
				<g:checkBox name="leftLegAffected" data-bind="checkBoxAsButton: clinicalAssessment.leftLegAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="leftLegAffected">Left</label>
		</div>
			<div class="span-5" data-bind="visible: !clinicalAssessment.neitherLegAffected()" >
				<g:checkBox name="rightLegAffected" data-bind="checkBoxAsButton: clinicalAssessment.rightLegAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="rightLegAffected">Right</label>
		</div>
		
		<div class="span-5">
				<g:checkBox name="neitherLegAffected" data-bind="checkBoxAsButton: clinicalAssessment.neitherLegAffected"    ></g:checkBox>
				<label style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" for="neitherLegAffected">Neither</label>
		</div>
			
				<div class="span-30" style="min-height:15px;">
			</div>	
			
	
	<div class="prepend-1 span-23 ">
				<h5>Loss Of Consciousness?</h5>
			</div>
			
			
			
			<div class="prepend-2 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">  Stimulation</h5>
			</div>
			
			<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" 
			 									name="locStimulation"
												labels="['Keenly responsive','Arousal' ,'Repeated stimulation only', 'Unresponsive']" 
												values="['keen','arousal', 'repeated','unresponsive']"
												spans="[5,5,5,5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
				  
		<div class="span-30" style="min-height:15px;"/>	
		<div class="prepend-2 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">  Answers questions</h5>
			</div>
			
			<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" 
			 									name="locQuestions"
												labels="['Both correct','One correct' ,'Neither']" 
												values="['both','one', 'neither']"
												spans="[5,5,5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
			<div class="span-30" style="min-height:15px;"/>	
			
			
			<div class="prepend-2 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">  Perform tasks</h5>
			</div>
			
			<g:buttonRadioGroup style="width:125px;margin-right:5px;margin-top:10px;min-height:38px; vertical-align:top;" 
			 									name="locTasks"
												labels="['Both correct','One correct' ,'Neither']" 
												values="['both','one', 'neither']"
												spans="[5,5,5]"												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
	
	
	
	
	<div class="span-30" style="min-height:1px;">
			</div>
	
	
	
	
	
	
			
			
			<div class="prepend-1 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Best Gaze</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px;min-height:40px; vertical-align:top;" 
			 									name="bestGaze"
												labels="['Partial gaze palsy ','Forced deviation' ]" 
												values="['partial','forced']"
												spans="[6,6]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
		<div class="span-30" style="min-height:1px;">
			</div>			
			
			
			
			
		
				
			<div class="prepend-1 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Dysarthria</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px;min-height:40px; vertical-align:top;" 
			 									name="dysarthria"
												labels="['Normal','Mild-to-moderate' , 'Severe', 'Unable to assess']" 
												values="['normal','mild', 'severe', 'unable']"
												spans="[6,6,6,6]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
		<div class="span-30" style="min-height:1px;">
			</div>		
			<div class="prepend-1 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Aphasia</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px;min-height:40px; vertical-align:top;" 
			 									name="aphasia"
												labels="['Normal','Mild-to-moderate' , 'Severe',  'Global']" 
												values="['normal','mild', 'severe', 'global']"
												spans="[6,6,6,6]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
		
			
			<div class="span-30" style="min-height:1px;">
			<div class="prepend-1 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Hemianopia</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px;min-height:40px; vertical-align:top;" 
			 									name="hemianopia"
												labels="['No loss','Partial' , 'Complete', 'Bilateral']" 
												values="['none','partial', 'complete', 'bilateral']"
												spans="[6,6,6,6]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
		
			<div class="span-30" style="min-height:1px;">
		
			<div class="prepend-1 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Inattention</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px;min-height:40px; vertical-align:top;" 
			 									name="inattention"
												labels="['No abnormality','Single Sense Hemi-inattention' , 'Profound Hemi-inattention']" 
												values="['normal','single', 'profound']"
												spans="[6,6,6]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
			<div class="span-30" style="min-height:1px;">
			
			<div class="prepend-1 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Limb ataxia</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px;min-height:40px; vertical-align:top;" 
			 									name="limbAtaxia"
												labels="['Absent','Single limb' , 'Two Limbs', 'Unable to assess']" 
												values="['yes','single', 'two', 'unable']"
												spans="[6,6,6,6]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
			
				<div class="span-30" style="min-height:1px;">
			
		
			
			<div class="prepend-1 span-4 ">
				<h5 style="min-height:25px;padding-top:15px">Other signs at presentation</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px;min-height:40px; vertical-align:top;" 
			 									name="other"
												labels="['Yes','No' ]" 
												values="['yes','no']"
												spans="[6,6 ]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
			<div class="span-10" style="min-height:60px;">
			</div>
			<div class="span-29" style="max-height:1px;"></div>
			<div id="otherTextSection" data-bind="visible: clinicalAssessment.other()=='yes'">
			<div class="prepend-2 span-4">
				<h5>please specify</h5>
			</div>	
			<div class="span-16" data-bind="inError:'clinicalAssessment.otherText'">
						<g:textField style="width:400px" data-bind="value: clinicalAssessment.otherText" name="otherText" ></g:textField>
			
			</div>
			</div>

			 <div class=" span-24 prepend-top" >
				<h5>Was the patient able to walk without assistance at presentation?</h5>
			</div>
			<div class=" span-6" style="min-height:50px"><p></p></div>	
			<g:buttonRadioGroup  style="width:150px;margin-right:5px;margin-top:10px; vertical-align:top;" 
 									name="walkAtPresentation"
									labels="['Yes','No']" 
									values="['true','false']"
									spans="[6,6]"
									bind="clinicalAssessment"
									>
											${it.radio} 
											${it.label}
			</g:buttonRadioGroup>	 
			<div class="span-10 prepend-top" style="min-height: 45px;">
	<p></p>
</div>
			<div id="ableToWalkAtPresentation" data-bind="visible: clinicalAssessment.walkAtPresentation() == 'false' ">
				<div class="span-23 prepend-7 append-bottom ">
					<h5>Were they able to do so before the stroke?</h5>
				</div>
				<div class="span-7	" style="min-height: 45px;">
					<p></p>
				</div>
				<g:buttonRadioGroup  style="width:150px;margin-right:5px;margin-top:10px; vertical-align:top;" 
 									name="mobilePreStroke"
									labels="['Yes','No']" 
									values="['true','false']"
									spans="[6,6]"
									bind="clinicalAssessment"
									>
											${it.radio} 
											${it.label}
			</g:buttonRadioGroup>	 
		</div>	
		
<div class=" span-29 prepend-top append-bottom">
	<h5>Has the patient received a swallowing screen?</h5>
</div>
<div class="span-6" style="min-height: 45px;">
	<p></p>
</div>
<g:buttonRadioGroup
	style="width:150px;margin-right:5px; vertical-align:top;"
	name="swallowScreenPerformed" labels="['Yes' , 'No']"
	bind="clinicalAssessment"
	values="['true','false']" spans="[6,6]" value="">
	${it.radio}
	${it.label}
</g:buttonRadioGroup>
<div class="span-10" style="min-height: 45px;"></div>
<div id="swallowScreenDateTime" class="span-30" data-bind="visible: clinicalAssessment.swallowScreenPerformed() == 'true', inError :'clinicalAssessment.swallowScreenPerformed'  ">
	<div class=" span-1  prepend-7 prepend-top">Date</div>
	<div class="span-6 value prepend-top " data-bind="inError : 'clinicalAssessment.swallowScreenDate'">
		<g:textField style="width:106px" class="standardDatePicker " data-bind="value: clinicalAssessment.swallowScreenDate"
			name="swallowScreenDate" value="" />
	</div>
	<div class="span-2 prepend-top">Time</div>
	<div class="span-3 value prepend-top " style="vertical-align: 2px;" data-bind="inError : 'clinicalAssessment.swallowScreenTime'">
		<g:textField style="width:50px;" class="time" data-bind="value: clinicalAssessment.swallowScreenTime"
			name="swallowScreenTime" value="" />
	</div>
</div>
<div id="noSwallowScreenReasons"  class="span-30" data-bind="visible: clinicalAssessment.swallowScreenPerformed() == 'false' , inError :'clinicalAssessment.swallowScreenPerformed'">
	<div class="span-23 prepend-7 append-bottom " inError="clinicalAssessment.swallowScreenPerformed">
		<h5>What was the reason?</h5>
	</div>
	<div class="span-7	" style="min-height: 45px;">
		<p></p>
	</div>
	<g:buttonRadioGroup
		style="min-height:66px;width:110px;margin-right:5px; vertical-align:top;"
		name="noSwallowScreenPerformedReason"
		labels="['Impaired level of consciousness' , 'Palliative care', 'Patient refused' ,'Unknown']"
		values="['impaired', 'palliative', 'refused', 'unknown']"
		bind="clinicalAssessment"	
		spans="[5,5,5,5]" value="">
		${it.radio}
		${it.label}
	</g:buttonRadioGroup>
</div>

		
			
			
			<div class="span-30-empty-line"></div>	
	
			<div class="span-24 append-bottom">
				<h5>What was the Oxfordshire Community Stroke Project (OCSP) classification ?</h5>
			</div>	
			<div class="span-6"  style="min-height:50px;">
				<p></p>
			</div>  
			<g:buttonRadioGroup style="width:100px;margin-right:5px;margin-top:0px; vertical-align:top;" 
			 									name="classification"
												labels="['TACS','PACS','LACS', 'POCS']" 
												values="['TACS','PACS', 'LACS', 'POCS']"
												spans="[5,5,5, 5]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
				<div class="span-24-empty-line"></div>	
			<div class="span-6 ">
				<h5>Was the patient independent in everyday activities before the stroke</h5>
			</div>	  
			<g:buttonRadioGroup style="width:150px;margin-right:5px;margin-top:10px; vertical-align:top;" 
			 									name="independent"
												labels="['Yes','No' , 'Unable to assess']" 
												values="['yes','no', 'unable']"
												spans="[6,6,6]"
												bind="clinicalAssessment"
												value="" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			
			<div class="span-24-empty-line"></div>	
			
				
		</div>
	</div>
	<!-- 
	<hr />
<h2>Debug</h2>

<div data-bind="text: ko.toJSON(clinicalAssessmentViewModel)"></div>

 -->	
</div>

