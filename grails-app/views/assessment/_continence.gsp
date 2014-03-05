<div id="continenceForm" >
	<g:render template="/templates/sectionHead"></g:render>	
 	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>	
		<div id="continence">
		
		<div class="page-header">
			<h1>Continence</h1>
		</div>
		
		<div data-bind="inError:'continenceManagement.newlyIncontinent'">
			<div class="question-block">
					
				<div class="question-alt">
			  	<label>Is the patient newly incontinent since their stroke?</label>
			  	</div>
			 	<div class="answer-block">	
			  		<g:buttonRadioGroup  
			  									name="newlyIncontinent"
													labels="['Yes','No' ]" 
													values="['true','false']"
													bind="continenceManagement"
													spans="[3,3]"
													value="">
															${it.radio} 
															${it.label}
					</g:buttonRadioGroup>
				</div>
			</div>
		</div>
		<div class="clearfix" ></div>	
			
			
			
		<div  data-bind="inError:'continenceManagement.hasContinencePlan'">		
			<div class="question-block">
					
				<div class="question-alt">
			  		<label>Has a continence plan been drawn up?</label>
		  		</div>
		 		<div class="answer-block">			 	
		  			<g:buttonRadioGroup 
		  									name="hasContinencePlan"
												labels="['Yes' , 'No']" 
												values="['true','false']"
												bind="continenceManagement"
												spans="[3,3]"
												value="" >
														${it.radio} 
														${it.label}
					</g:buttonRadioGroup>				
				</div>
			
			</div>
		</div>	
			<div class="clearfix" ></div>	
			<div id="continencePlanDateTime" data-bind="visible: continenceManagement.hasContinencePlan() == 'true' && !continenceManagement.inappropriateForContinencePlan() ">
				<div class="question-block">
					
				<div class="question-alt">
			  		<label>Date</label>
		  		</div>
		  		<div class="answer-block" data-bind="inError :'continenceManagement.continencePlanDate'">
			  		<g:textField class="standardDatePicker "  name="continencePlanDate"  
			  			data-bind="value: continenceManagement.continencePlanDate" />
		 		</div>	
		 		<%--  
	 			<div class="span-2 prepend-top">
	 				Time
	 			</div>	
	 			<div class="span-3 value prepend-top " style="vertical-align:2px;"   data-bind="inError :'continenceManagement.continencePlanTime'">
	 				<g:textField style="width:50px;" class="time" name="continencePlanTime" 
	 					 data-bind="value: continenceManagement.continencePlanTime"  />
	 			</div>
	 			--%>
			</div>
			</div>	
			<div data-bind="visible: continenceManagement.hasContinencePlan() == 'false' , inError:'continenceManagement.noContinencePlanReason continenceManagement.noContinencePlanReasonOther'">			
				<div id="noPlanReasons question-block">				
					<div class="question-alt"><label>What were the reasons?</label></div>
					<div class="answer-block">	
			  		<g:buttonRadioGroup style="width:150px;margin-right:5px; vertical-align:top;" 
			  									name="noContinencePlanReason"
													labels="['Continent' , 'Patient Refused', 'Organisational', 'Unknown']" 
													values="['continent' , 'refused', 'organisational', 'unknown']"
													spans="[6,6, 6,6]"
													bind="continenceManagement"
													value="" >
															${it.radio} 
															${it.label}
					</g:buttonRadioGroup>				
					</div>
				</div>
					
			</div> <!--  end of no plan reasons -->	
			
			<div class="clearfix"></div>
			
			<div  data-bind="inError:'continenceManagement.priorCatheter'">
			  <div class="question-block">		
	 		  <div class="question-alt" >
			  	<label>Did the patient have an in-dwelling catheter prior to admission?</label>
			  </div>
			  <div class="answer-block">	
			  <g:buttonRadioGroup  
			  									name="priorCatheter"
													labels="['Yes','No' ]" 
													values="['true','false']"
													spans="[3,3]"
													bind="continenceManagement"
													value="" >
															${it.radio} 
															${it.label}
				</g:buttonRadioGroup>	
				</div>	
			</div>	
			</div>
			
			<div  data-bind="inError:'continenceManagement.catheterisedSinceAdmission'">		
			<div class="question-block">
			<div class="question-alt">
		  		<label>Has the patient been catheterised since admission?</label>
		  	</div>
		  	<div class="answer-block">	
		  <g:buttonRadioGroup  
		  									name="catheterisedSinceAdmission"
												labels="['Yes','No' ]" 
												values="['true','false']"
												spans="[3,3]"
												bind="continenceManagement"
												value="${careActivityInstance?.continenceManagement?.catheterisedSinceAdmission}" >
														${it.radio} 
														${it.label}
			</g:buttonRadioGroup>	
			
			</div>
			</div>
			</div>				
		</div> <!-- End of continence -->
		
		<div class="clearfix"></div>
		
		<div id="catheterHistory"   data-bind="visible: continenceManagement.catheterisedSinceAdmission() == 'true'">
			<div class="" ><h3>Catheter History</h3></div>
			
			
			<div class="">
				<button  class="add"  data-bind="click: addCatheterHistory, jqueryui: 'button'" >Add</button>
			</div>
			

			<div id="catheters" >
				
			</div>
		
			<%-- 			
			<div class="span-30" 
				data-bind="template: {name:'catheterTemplate', foreach: continenceManagement.catheterHistory() }">
			</div>		
			 --%>
			 
		</div>
 			
 			
		<script type="text/x-template" id="catheterTemplate">
		
		
    		<div id="catheter{{position}}" class="catheter" >
    		
			<table class="table">
				<thead>
					<tr>
					<th>
			 			Catheter inserted
			 		</th>

			  		<th>
			 			Catheter removed
			 		</th>

					<th>
			 			Reason
			 		</th>

					<th>
						Delete
				 	</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						
						<td class="value" >
							<input type="text" value="{{id}}" style="display: none;"/>

			 				<div class="control-group">
								<div class="control-label">
									<label for="catheter{{position}}_ins_date">Date</label>
								</div>
								<div class="controls"> 		
									<input type="text" value="{{insertDate}}" style="width:75px" class="standardDatePicker" id="catheter{{position}}_ins_date"/>
								</div>	
			 				</div>

			 				<div class="control-group">
								<div class="control-label">
									<label for="catheter{{position}}_ins_time">Time</label>
								</div>
								<div class="controls"> 						
									<input type="text" value="{{insertTime}}" style="width:50px;" class="time" id="catheter{{position}}_ins_time"/>
								</div>
							</div>
						</td>

						<td class="value" >
			 				<div class="control-group">
								<div class="control-label">
			 						<label for="catheter{{position}}_del_date">Date</label>
								</div>
								<div class="controls"> 					 		
									<input type="text" value="{{removalDate}}" style="width:75px" class="standardDatePicker" id="catheter{{position}}_del_date"/>
								</div>
							</div>

			 				<div class="control-group">
								<div class="control-label">
									<label for="catheter{{position}}_del_time">Time</label>
								</div>
								<div class="controls"> 						
									<input type="text" value="{{removalTime}}" style="width:50px;" class="time" id="catheter{{position}}_del_time"/>
								</div>
							</div>
						</td>

						<td>
			 				<div class="control-group">
								<div class="control-label">						
									Reason
								</div>
								<div class="controls">					
									<select class="reason">
										<option value="">Choose a reason ...</option>
										<option value="retention">Retention</option>
										<option value="clinical">Clinical Reason</option>
										<option value="skin" >Skin</option>
										<option value="preexist">Pre-existing</option>
									</select>
								</div>
							</div>
							<span class="clinicalOther" >
			 				<div class="control-group">
								<div class="control-label">						
									<label for="catheter{{position}}_cl_rsn">Clinical Reasons</label>
								</div>
								<div class="controls">
									<input type="text" value="{{reasonOther}}" id="catheter{{position}}_cl_rsn">
								</div>
							</div> 					
							</span>

						</td>
						<td>
							<button class="delete" >Delete</button>							
						</td>
					</tr>


			</tbody>
			</table>
		</div>
		</script>

					

				
				
  	
	 
	
		
		
		<!--
 		<hr />
		<h2>Debug</h2>
		<div data-bind="text: ko.toJSON(continenceViewModel)"></div>
	  -->
 
		
	</div> <!-- End of container -->
</div> <!-- End of continenceForm -->
