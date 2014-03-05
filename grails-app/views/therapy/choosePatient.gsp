<html>
<head>
  	<title>CIMSS Therapy</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<link rel='stylesheet' href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}" type="text/css"/>
	<link rel='stylesheet' href="${resource(dir:'css', file:'cimss_admin.css')}" type="text/css"/> 
	
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>	

	
</head>
 <body>  	
  	
  	
  	
  	<tmpl:navBar/>
    
	<div class="container">
			<%--sec:ifLoggedIn --%>
			<div class="page-header">
    			<h3>Patient <small> choose therapy recipient</small></h3>
    		</div>			
			<g:if test="${flash.message}">
				<div class="alert alert-success">${flash.message}</div>
			</g:if>
			<g:if test="${flash.error_message}">
				<div class="alert alert-error">
				${flash.error_message}
				</div>
			</g:if>

						
			<g:form action="choosePatient" class="form-horizontal">
				
				<div class="row">
				<div class="span6">
	 			<div class="control-group">
					<label class="control-label" for="patient">Patient</label>
					<div class="controls">
									
						<select name="patient" id="patient">
						<g:each in="${patientList}" var="p">
							
							<option value="${p.id}">${p.forename} ${p.surname} 
								DOB: <g:formatDate format="yyyy-MM-dd" date="${p.dateOfBirth}"/></option>
											
						</g:each>
						</select>
					</div>
				</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-primary">Proceed</button>
						</div>
					</div>
				</div>
				</div>
			</g:form>
			

			<%-- /sec:ifLoggedIn --%>
		</div>
		
	</body>
</html>