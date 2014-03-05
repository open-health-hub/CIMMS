<div id="observationForm">
	<g:render template="/templates/sectionHead"></g:render>
	<div class="container " data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>	
		<div id="observation">
			<div class="span-12 prepend-top" ><h3>Observations</h3></div>	
				<div class="span-24-empty-line"></div>
			<div class="span-24" >
			<p> Functionality to add/edit/delete observations is currently in development </p>
			</div>
			<div class="span-24-empty-line"></div>
			<!--  	
			<div class="push-10 span-1 prepend-top">
				<button  class="add"  data-bind="click: addObservation, jqueryui: 'button'" >Add</button>
			</div>
			
			
			<div class="span-24-empty-line"></div>
			
			
			  		 
				<div class="span-24" 
					data-bind="template: {name:'observationTemplate', foreach: observations() }">
				</div>
				
			-->	
				
			<div class="span-24-empty-line"></div>		
		</div>
	</div>

	 
   <!-- 
 		<hr />
		<h2>Debug</h2>
		<div data-bind="text: ko.toJSON(observationViewModel)"></div>
		 -->
		
  <script type="text/x-jquery-tmpl" id="Condition compared to yesterday">
		<fieldset style="border:none" >
			<select data-bind="visible:false, value:theValue, refreshjqueryui: 'selectToUISlider',uniqueId:true , uniqueName:true">
				<option value="Worse">Worse than presentation</option>
				<option value="Same">Same as at presentation</option>
				<option value="Better" >Better than at presentation</option>
				<option value="Recovered">Fully recovered</option>
			</select>
		</fieldset>
 	</script>
 	
 	
	<script type="text/x-jquery-tmpl" id="Neurological State">
			<input type='text' data-bind='visible:false, value:theValue'/>
	</script>
	<!--  slide: function(evt,ui) { quantity(ui.value); } -->
	<script type="text/x-jquery-tmpl" id="Conscious Level">
		<fieldset style="border:none">
			<select  data-bind="visible:false, value:theValue, refreshjqueryui: 'selectToUISlider',uniqueId:true , uniqueName:true">
				<option value="Fully">fully conscious</option>
				<option value="Drowsy">Drowsy</option>
				<option value="Semi" >Semi-conscious</option>
				<option value="Unconscious">Unconscious</option>
			</select>
			
		</fieldset>
	</script>
	
	<script type="text/x-jquery-tmpl" id="Oxygen saturation">
    <div class="  span-4" style="margin-left:25px; vertical-align:2px;">
			<input data-bind='checkedWithToggle: theValue, uniqueId:true, uniqueName:true'   type='radio' value="Yes"/>
    	<label style="width: 125px; margin-right: 5px; margin-top: 1px; vertical-align: top;" data-bind="uniqueFor:true" >&lt; 95%</label>
		</div>
		<div class=" span-4" style="vertical-align:2px;">
    	<input data-bind='checkedWithToggle: theValue, uniqueId:true, uniqueNameForRadio:true'    type='radio' value="No"/>
			<label style="width: 125px; margin-right: 5px; margin-top: 1px; vertical-align: top;" data-bind="uniqueFor:true" >&gt; 95%</label>
		</div>
	</script>
	
	
	
	
	<script type="text/x-jquery-tmpl" id="default">
	</script>



	
	<script type="text/x-jquery-tmpl" id="observationTemplate">
		<div class="span-21" style="border:none; padding:1px;">
			<div class=" span-4" style="vertical-align:2px;">
				<select  style="max-width:150px" data-bind="options: observationViewModel.observationTypes 
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
						data-bind="value: dateMade ,  uniqueName: true " />
			</div>	
			<div class="span-1 ">
				Time
			</div>	
			<div class="span-2 value " style="vertical-align:2px;">
				<input type="text" style="width:40px;" class="time" 
					 data-bind="value: timeMade,  uniqueName: true "     />
			</div>

			<div class="span-9" data-bind='template: { afterRender: observationViewModel.postRenderLogic,  name: function(){ return observationViewModel.templateToUse(type(), $data); } }'>
				
			</div>



	
	</div>

			<div class=" span-2  remove-icon"  style=" min-height: 45px; margin-left: 20px" >
				<button class="delete"  data-bind="jqueryui: 'button' , click: function() { observationViewModel.removeLine($data) }" >Delete</button>
		 	</div>
			
			

		</script>
		

	
	</div>