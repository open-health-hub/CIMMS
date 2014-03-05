<html>
<head>
  	<title>CIMSS Therapy</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<link rel='stylesheet' href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}" type="text/css"/>
	<link rel='stylesheet' href="${resource(dir:'css', file:'cimss_admin.css')}" type="text/css"/> 
	
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>	

	<style>
		#clock {
		font-size: 52px; 
		color: #e2e6e9; 
		font-weight: bold; 
		background-color: #097312; 
		border: 1px solid #576675; 
		padding: 8px 12px; 
		text-shadow: 2px 2px #333;}
	</style>
</head>
 <body >  	
  	
  	
  	
	<tmpl:navBar/>
    
	<div class="container">
			<%--sec:ifLoggedIn --%>
			<div class="page-header">
    			<h3>Therapy <small> session summary</small></h3>
    		</div>			
			<g:if test="${flash.message}">
				<div class="alert alert-success">${flash.message}</div>
			</g:if>
			<g:if test="${flash.error_message}">
				<div class="alert alert-error">
				${flash.error_message}
				</div>
			</g:if>						
			
			<form>	
			<div class="row">
				<div class="span6">
		 				<div class="control-group">					
							<div class="controls">							
		                    	<label class="control-label" for="therapists">Therapists</label>	                
								<select id="therapists" name="therapists" multiple>
								<g:each in="${therapySession.therapistList}" var="th">
									
									<option value="${th.user.id}"> ${th.user.firstName} ${th.user.surname}</option>
													
								</g:each>
								</select>	                    	                    
							</div>
						</div>
				</div>
				<div class="span6">
		 			<div class="control-group">
						<label class="control-label" for="patient">Patient</label>
						<div class="controls">
										
							<input name="patient" id="patient" value="${therapySession.patient.forename} ${therapySession.patient.surname}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="hsi">Hospital Stay Id</label>
						<div class="controls">										
							<input name="patient" id="hsi" value="${therapySession.careActivity.hospitalStayId}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="therapyType">Therapy type</label>
						<div class="controls">										
							<input name="patient" id="therapyType" value="${therapySession.therapyType}">
						</div>
					</div>
							
					<div class="control-group">
						<label class="control-label" for="duration">Therapy duration (minutes)</label>
						<div class="controls">										
							<input name="patient" id="duration" value="${therapySession.durationMinutes}">
						</div>
					</div>

				</div>
			</div>
			
			<div class="row">
				<div class="span6">
					<div class="control-group">
						<div class="controls">
							<g:link action="showTherapies" class="btn btn-primary">Done</g:link>
						</div>
					</div>
				</div>
			</div>
			<form>
			<%-- /sec:ifLoggedIn --%>
		</div>
		
	</body>
</html>