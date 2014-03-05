
<form action='/someServerSideHandler' id="nutritionForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel " data-bind="css : {changed: tracker().somethingHasChanged }">
        <div class="warnings" data-bind="visible: hasWarnings()">
            <ul>
                <li data-bind="text: warningMessage">
            </ul>
        </div>
	
		<div data-bind="html: errorsAsList"></div>		 
		<div id="nutrition">
			<div class="page-header">				
				<h1>Nutrition Management</h1>
            </div>
            
		 	<div  data-bind="inError:'nutritionManagement.dateScreened nutritionManagement.timeScreened nutritionManagement.mustScore'">
		 
		 	<div class="question-block">
				<div class="question-alt" >
					<label>When was the MUST screening performed?</label>
				</div>
								
				<div id="nutritionScreeningDateTime" class="answer-block" data-bind="visible: nutritionManagement.unableToScreen() != true ">
				
					<div class="answer"	>
						<label for="nutritionDate" >Date</label>
				     	<input type="text"  id="nutritionDate"  name="nutritionDate" data-bind="value: nutritionManagement.dateScreened" style="width:106px" class="required standardDatePicker" />
				   </div>
			   	
			   
			   		<div class="answer">
						<label for="nutritionTime" >Time</label>
						<g:textField  data-bind="value: nutritionManagement.timeScreened " style="width:50px;" class="required time" name="nutritionTime" 
						value=""   />
					</div>
					
			   
					<div class="answer">
						<label for="mustScore">MUST Score</label>
						<input class="required" id="mustScore" type="text" size="3" maxlength="3" style="width:4em;" data-bind="value: nutritionManagement.mustScore">
					</div>
			   </div>
			   
			   	<div data-bind="css: {'none': nutritionManagement.unableToScreen() === true}">
                    <g:checkBox data-bind="checkBoxAsButton: nutritionManagement.unableToScreen " name="unableToScreen"  />
                    <label for="unableToScreen">Not Screened</label>
            	</div> 
			</div>
			

			
			</div>

            



	 		
	 		<div  data-bind="inError:'nutritionManagement.dietitianReferralDate nutritionManagement.dietitianReferralTime'">
		 
			<div id="contactDietitian" >

			<div class="question-block">			
				<div class="question-alt" >
					<label>When was the patient referred to the dietitian?</label>
				</div>
				<div id="dieticianDateTime" class="answer-block" data-bind="visible: nutritionManagement.dietitianNotSeen() !== true ">
				
					<div class="answer" >
			  			<label for="dietitianReferralDate">Date</label>			 		
						<g:textField style="width:106px" class="standardDatePicker " data-bind="value: nutritionManagement.dietitianReferralDate"  name="dietitianReferralDate"  
								id="dietitianReferralDate" value="" />
					</div>
						
					<div class="answer" >
			  			<label for="dietitianReferralTime">Time</label>
						<span style="vertical-align:2px;"  data-bind="inError:'nutritionManagement.dietitianReferralTime'">
							<g:textField  style="width:50px;" class="time" data-bind="value: nutritionManagement.dietitianReferralTime"  name="dietitianReferralTime"   
							 	value=""   />
						</span>
					</div>
				</div>
				<div class="" data-bind="css: {'none': nutritionManagement.dietitianNotSeen() === true}">
                    <g:checkBox data-bind="checkBoxAsButton: nutritionManagement.dietitianNotSeen " name="dietitianNotSeen"  />
                    <label for="dietitianNotSeen">Not Seen</label>
            	</div> 
			</div>


		</div>
		</div>
			
			</div>
		
			<!-- Debug for view model
			<hr />
				<h2>Debug</h2>
				<div data-bind="text: ko.toJSON(nutritionManagementViewModel)"></div>
	 		-->  
	 			 
		
	</div>
</form>