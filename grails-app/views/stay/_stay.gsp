
	<g:formRemote id="stayForm" class="validatedForm" name="stayForm"  
								asynchronous="false"  url="[controller:'stay',action:'update']" 
								update="stayForm"  before="" after="onPage()" >
		<g:hiddenField name="id" value="${patientInstance?.id}" />
		<g:hiddenField name="version" value="${patientInstance?.version}" />	
		<g:hiddenField name="hospitalStayId" value="${hospitalStayInstance?.id}" />
		<g:hiddenField name="wardStayId" value="${wardStayInstance?.id}" />
		
		<p>Hospital stay details - admissions, ward movements, consultant episodes, discharge</p>
<br/>

	


	<g:if test="${flash.message}">
		<div class="message">
			${flash.message}
		</div>
	</g:if>
 	<g:hasErrors>
		<div class="errors">
			<g:renderErrors  as="list" /></div>
	</g:hasErrors>
	 
		<table>
			<tbody>
				<tr class="props">
					<td valign="top" class="name">
						<label for="admissionDate">
							<g:message code="hospitalStay.startDate.label" default="Hospital admission date" />
						</label>
					</td>
					
					<td valign="top" class="value ${hasErrors(bean: hospitalStayInstance, field: 'startDate', 'errors')}">
						
						<g:textField class="datepicker_current date"  name="admissionDate"  value="${formatDate(format:'dd/MM/yyyy',date:hospitalStayInstance?.startDate)}"    />
					
					</td>
					<td valign="top" class="name">
						<label for="admissionTime">
							<g:message code="hospitalStay.startTime.label" default="Hospital admission time" />
						</label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: hospitalStayInstance, field: 'startTime', 'errors')}" >
						<g:textField class="time" name="admissionTime" value="${g.displayTime(time:hospitalStayInstance?.startTime)}"  />
					</td>
				</tr>
				<tr  class="props">
					<td>
						<label for="arrivalLocation">
							<g:message code="stay.arrival.location.label" default="Location on arrival in hospital" />
						</label>	
					</td>
					<td>

						<g:select name="arrivalLocation" from="${admissionPoints}" optionKey="id" optionValue="description" noSelection="['': '__ Select an option __']" 
											value="${hospitalStayInstance?.admissionPoint?.id}"/>
					</td>
					<td>
						<label for="arrivalLocationOther">
							<g:message code="stay.arrival.location.other.label" default="Please specify .. " />
						</label>	
					</td>
					<td>
						<g:textField  name="arrivalLocationOther"  value="${hospitalStayInstance?.otherForAdmissionPoint}"   />
					</td>
				</tr>
				<tr class="props">
					<td valign="top" class="name">
						<label for="wardArrivalDate">
							<g:message code="patient.startDate.label" default="Date arrived on ASU/Stroke ward" />
						</label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: wardStayInstance, field: 'startDate', 'errors')}">
						
						<g:textField   class="datepicker_current date"  name="wardArrivalDate"  value="${formatDate(format:'dd/MM/yyyy',date:wardStayInstance?.startDate)}"    />
					
					</td>
					<td valign="top" class="name">
						<label for="strokeWardArrivalTime">
							<g:message code="hospitalStay.endTime.label" default="Time arrived on ASU/Stroke Ward" />
						</label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: wardStayInstance, field: 'startTime', 'errors')}" >
						<g:textField class="time" name="strokeWardArrivalTime" value="${g.displayTime(time:wardStayInstance?.startTime)}" />
					</td>
				
				</tr>	
				<tr class="props">
					<td valign="top" class="name">
						<label for="dischargeDate">
							<g:message code="patient.endDate.label" default="Hospital discharge date" />
						</label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: hospitalStayInstance, field: 'endDate', 'errors')}">
					
						<g:textField class="datepicker_current date" name="dischargeDate"  value="${formatDate(format:'dd/MM/yyyy',date:hospitalStayInstance?.endDate)}"    />
					
					</td>
					<td valign="top" class="name">
						<label for="dischargeTime">
							<g:message code="hospitalStay.endTime.label" default="Hospital discharge time" />
						</label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: hospitalStayInstance, field: 'endTime', 'errors')}" >
						<g:textField class="time" name="dischargeTime" value="${g.displayTime(time:hospitalStayInstance?.endTime)}"  /> 
					</td>
				
				</tr>	
			
			</tbody>
		</table>
		
		<div class="buttons">
				<g:actionSubmit class="save" action="updateStay" 	value="${message(code: 'default.button.update.label', default: 'Update')}" />
	
		</span>
		</div>
	</g:formRemote>
