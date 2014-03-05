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
    			<h3>Therapists <small> choose therapists</small></h3>
    		</div>			
			<g:if test="${flash.message}">
				<div class="alert alert-success">${flash.message}</div>
			</g:if>
			<g:if test="${flash.error_message}">
				<div class="alert alert-error">
				${flash.error_message}
				</div>
			</g:if>		

						
			<g:form action="chooseTherapists" class="form-horizontal">
				
				<div class="row">
				<div class="span6">
	 			<div class="control-group">
					<label class="control-label" for="therapists">Therapists</label>
					<div class="controls">
									
						<select id="therapists" name="therapists" multiple>
						<g:each in="${therapistList}" var="th">
							
							<option value="${th.user.id}">${th.user.firstName} ${th.user.surname}</option>
											
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
			</g:form>
			

			<%-- /sec:ifLoggedIn --%>
		</div>
		
	</body>
</html>