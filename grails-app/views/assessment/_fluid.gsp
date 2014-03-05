<div id="fluidForm">
	<g:render template="/templates/sectionHead"></g:render>
	  <g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
		<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
			<div data-bind="html: errorsAsList"></div>
			<div id="fluid">					
			 <div class="prepend-top span-18" >
			 	<h5>Did the patient receive more than 1 litre of fluid during the specified 24 hour period? </h5>			 	
			 </div>
			 
			 <!-- TODO move to a dependantObservable -->	
			 <div class="prepend-top span-11">
			  <span><p data-bind="text:'Time since admission is ' + fluidManagement.periodSinceAdmission()"></p></span>
			 
			 </div>
			 <div class="span-30"  data-bind="inError:'fluidManagement.litrePlusAt24 fluidManagement.inadequateAt24ReasonOther fluidManagement.inadequateAt24FluidReasonType'">
			  <div class="prepend-1 span-6 ">
			 	<p>First 24 hours since admission</p>
			 </div>	
			 <!-- TODO move to a template -->	
			 <g:buttonRadioGroup style="width:100px;margin-right:5px;margin-top:10px; vertical-align:top;" 
			 									name="litrePlusAt24"
												labels="['Yes','No','Unknown' ]" 
												values="['yes','no', 'unknown']"
												bind="fluidManagement"
												spans="[4,4,4]"
												>
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			<div id="inadequateAt24" data-bind="visible : fluidManagement.litrePlusAt24() == 'no'" >
				<div class="span-30-empty-line"></div>	
					<div class="prepend-7  span-4"><p>Reason not given</p></div>
				<div class="span-10" >
						<select data-bind="options: inadequateFluidReasons, value:fluidManagement.inadequateAt24Reason   , optionsCaption:'Choose a reason:'"></select>
				</div>
				<div id="inadequateAt24Other" data-bind="visible : fluidManagement.inadequateAt24Reason() == 'Other' || fluidManagement.inadequateAt24Reason() == 'Nasogastric tube not used - other' ">
					<div class="span-3"><p>Details</p></div>
					<div class="span-4" >
							<input type="text" data-bind="value: fluidManagement.inadequateAt24ReasonOther">
					</div>
				</div>	
			</div>
			</div>
			<div class="span-30-empty-line"></div>	
			
			<div class="span-30" data-bind="visible: fluidManagement.hoursSinceAdmission() > 24, inError:'fluidManagement.litrePlusAt48 fluidManagement.inadequateAt48ReasonOther fluidManagement.inadequateAt48FluidReasonType'" > 
			<div class="prepend-1  span-6 ">
				<p>Second 24 hours since admission</p>
			</div>	  
			<g:buttonRadioGroup style="width:100px;margin-right:5px;margin-top:10px; vertical-align:top;" 
			 									name="litrePlusAt48"
												labels="['Yes','No','Unknown' ]" 
												bind="fluidManagement"
												values="['yes','no', 'unknown']"
												spans="[4,4,4]"
												>
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>
			<div id="inadequateAt48" data-bind="visible : fluidManagement.litrePlusAt48() == 'no'" >
				<div class="span-30-empty-line"></div>	
					<div class="prepend-7  span-4"><p>Reason not given</p></div>
				<div class="span-10" >
						<select data-bind="options: inadequateFluidReasons, value:fluidManagement.inadequateAt48Reason   , optionsCaption:'Choose a reason:'"></select>
				</div>
				<div id="inadequateAt48Other" data-bind="visible : fluidManagement.inadequateAt48Reason() == 'Other' || fluidManagement.inadequateAt48Reason() == 'Nasogastric tube not used - other' ">
					<div class="span-3"><p>Details</p></div>
					<div class="span-4" >
							<input type="text" data-bind="value: fluidManagement.inadequateAt48ReasonOther">
					</div>
				</div>	
			</div>
			</div>
			<div class="span-30" data-bind="visible: fluidManagement.hoursSinceAdmission() > 48 , inError:'fluidManagement.litrePlusAt72 fluidManagement.inadequateAt72ReasonOther fluidManagement.inadequateAt72FluidReasonType'" > 	
			<div class="prepend-1 span-6 ">
				<p>Third 24 hours since admission</p>
			</div>	
			<g:buttonRadioGroup style="width:100px;margin-right:5px;margin-top:10px; vertical-align:top;" 
			 									name="litrePlusAt72"
												labels="['Yes','No','Unknown' ]" 
												bind="fluidManagement"
												values="['yes','no', 'unknown']"
												spans="[4,4,4]"
												>
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>	
			<div id="inadequateAt72" data-bind="visible : fluidManagement.litrePlusAt72() == 'no'" >
				<div class="span-30-empty-line"></div>	
					<div class="prepend-7  span-4"><p>Reason not given</p></div>
				<div class="span-10" >
						<select data-bind="options: inadequateFluidReasons, value:fluidManagement.inadequateAt72Reason   , optionsCaption:'Choose a reason:'"></select>
				</div>
				<div id="inadequateAt72Other" data-bind="visible : fluidManagement.inadequateAt72Reason() == 'Other' || fluidManagement.inadequateAt72Reason() == 'Nasogastric tube not used - other' ">
					<div class="span-3"><p>Details</p></div>
					<div class="span-4" >
							<input type="text" data-bind="value: fluidManagement.inadequateAt72ReasonOther">
					</div>
				</div>	
			</div>	
			<div class="span-24-empty-line"></div>	
			</div>
	 <!-- 
	<hr />
<h2>Debug</h2>

<div data-bind="text: ko.toJSON(fluidManagementViewModel)"></div>

 			 -->
		</div>
	</div>
</div>