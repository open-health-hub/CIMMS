<div class="pull-left sidebar" >
    
    <div class="sidebar-head">	    
		<h4>Treatments</h4>
    </div>
    
    <tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="treatments"/>
</div>

<div id="treatmentForm" class="data-entry-narrow">
	<g:render template="/templates/sectionHead"></g:render>
	<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>	
		
		
		
		<div id="treatment">
			<div class="page-header"><h1>Treatments</h1></div>	
			<%-- 
			<div class="push-10 span-1 prepend-top">
				<button  class="add"  data-bind="click: addTreatment, jqueryui: 'button'" >Add</button>
			</div>
			 --%>
			   
			
			<table>
			<thead>
				<tr>
					<th>Treatment</th>
					<th>Date started</th>
					<th>Time started</th>  
					<th>Date finished</th>
					<th>Time finished</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody  
					data-bind="template: {name:'compulsoryTemplate', foreach: treatments.compulsory() }">
				
			</tbody>
			
			</table>
			<div class="clearfix spacer-1"></div>	
			
				
				<%-- 
				<div class="span-30" 
					data-bind="template: {name:'additionalTemplate', foreach: treatments.additional() }">
				</div>
				--%>
				
			<div class="span-30-empty-line"></div>		
			
			<div class=" question-block ">
				<div class=" question-alt ">
					<label>Which, if any,  other major interventions has the patient had?</label>
				</div>
				<div class="answer-block">
					<div class="answer-single-column">
						<g:checkBox name="respiratoryIntervention"  data-bind="checkBoxAsButton: treatments.respiratory"  ></g:checkBox>
						<label for="respiratoryIntervention">Respiratory</label>
					</div>
					<div class="answer-single-column">
						<g:checkBox name="icu" data-bind="checkBoxAsButton: treatments.icu"></g:checkBox>
						<label for="icu">ICU</label>
					</div>
					<div class="answer-single-column">
						<g:checkBox name="neuroSurgery" data-bind="checkBoxAsButton: treatments.neuro"/>
						<label for="neuroSurgery">Neurosurgery</label>
					</div>
					<div class="answer-single-column">
						<g:checkBox name="otherTreatmentSpeciality" data-bind="checkBoxAsButton: treatments.otherSpeciality" />
						<label for="otherTreatmentSpeciality">Other</label>
					</div>
				</div>
			</div>
			
			<div data-bind="visible: treatments.otherSpeciality" >

			<div class=" question-block ">
				<div class=" question-alt ">
					<label>If other, please specify:</label>
				</div>
				<div class=" answer-block ">
					<div class="answer"  data-bind="inError:'otherInterventionText'">
						<g:textField  data-bind="value: treatments.otherText" name="otherSpecialityText"   />
					</div>
				</div>
			</div>
			
			</div>		
			
			
			
			
		</div>
	</div>
	

	
 <!--  
 		<hr />
		<h2>Debug</h2>
		<div data-bind="text: ko.toJSON(treatmentViewModel)"></div>
 -->
	
	
	<script type="text/x-jquery-tmpl" id="compulsoryTemplate">
    	
				<tr>
				<td>
					<span data-bind="text:type"></span>
				</td>
			
			
			 	<td>
		 			<span class="value" data-bind="visible: !contraindicated()" >
						<input type="text" class="standardDatePicker date"   
								data-bind="value: startDate ,   uniqueName: true " />
					</span>
				</td>	
	
				<td>	
					<span class="value" data-bind="visible: !contraindicated()">
					<input type="text" class="time" 
						 data-bind="value: startTime,  uniqueName: true "     />
					</span>
				</td>
	
	
	
			 	<td>			 	
	 				<span class="value" data-bind="visible: !contraindicated()" >
						<input type="text" class="standardDatePicker date"   
							data-bind="value: endDate ,   uniqueName: true " />
					</span>
				</td>

				<td>	
					<span class="value" data-bind="visible: !contraindicated()">
						<input type="text" class="time" 
						 data-bind="value: endTime,  uniqueName: true "     />
					</span>
				</td>


			
		
			<td >
				<input type="checkbox" data-bind="uniqueId:true, checkBoxAsButton: contraindicated" />
				<label data-bind="uniqueFor:true">Contra-indicated</label>
			</td>
			
			
			</tr>
		</script>
		
	
<script type="text/x-jquery-tmpl" id="additionalTemplate">
<div class="span-19" style="border:1px solid #ddd;padding:1px;">
		
			<div class=" span-4" style="vertical-align:2px;">
				<select  style="max-width:150px" data-bind="options: treatmentViewModel.treatmentTypes 
														,	 optionsCaption: 'select..'
														,	 optionsText: 'description'
														,	 optionsValue: 'description'  
														,  value: type"></select>
			</div>
			<div class="span-1   " >
		 		Date
		 	</div>
 			<div class="span-3 value " >
				<input type="text" style="width:72px" class="standardDatePicker"   
						data-bind="value: startDate ,uniqueName: true " />
			</div>	
			<div class="span-1 ">
				Time
			</div>	
			<div class="span-2 value " style="vertical-align:2px;">
				<input type="text" style="width:40px;" class="time" 
					 data-bind="value: startTime ,   uniqueName: true "     />
			</div>



		<div class="span-1   " >
		 		Date
		 	</div>
 			<div class="span-10 value " >
				<input type="text" style="width:72px" class="standardDatePicker"   
						data-bind="value: endDate ,  uniqueName: true " />
			</div>	
			<div class="span-1 ">
				Time
			</div>	
			<div class="span-2 value " style="vertical-align:2px;">
				<input type="text" style="width:40px;" class="time" 
					 data-bind="value: endTime,  uniqueName: true "     />
			</div>
	
			</div>

			<div class=" span-2 push-1 remove-icon"  style=" min-height: 45px;">
				<button class="delete"  data-bind="jqueryui: 'button' , click: function() { treatmentViewModel.removeLine($data) }" >Delete</button>
		 	</div>
			
			

		</script>
		

	
</div>