<html>
<head>
  	<title>The Calderdale And Huddersfield NHS Foundation Trust Stroke System</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<link rel='stylesheet' href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}" type="text/css"/>
	<link rel='stylesheet' href="${resource(dir:'css', file:'cimss_admin.css')}" type="text/css"/> 
	<r:require modules="datatables"/>
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>	

	
</head>
 <body>  	
  	
  	
  	
	<tmpl:navBar/>
    
	<div class="container">
			<%--sec:ifLoggedIn --%>

		<div class="page-header">
    		<h3>Therapist <small>enrollment</small></h3>
    	</div>

		<g:if test="${flash.error_message}">
			<div class="alert alert-error">
			${flash.error_message}
				<g:hasErrors bean="${therapist}">
  					<ul>
   						<g:eachError var="err" bean="${therapist}">
       					<li>${err}</li>
   						</g:eachError>
  					</ul>
				</g:hasErrors>
			</div>
		</g:if>
		<g:if test="${flash.message}">
			<div class="alert alert-success">${flash.message}</div>
		</g:if>
			    
	    <g:form action="newTherapist" class="form-horizontal">
	    		 
			    <div class="control-group">
			    	<label class="control-label" for="firstName">First Name</label>
			    	<div class="controls">
			    		<input type="text" id="firstName" name="firstName"  placeholder="First Name" value="${therapist.firstName}">
			    	</div>			  
			    </div>
			    
			    <div class="control-group">
			    	<label class="control-label" for="surname">Surname</label>
			    	<div class="controls">
			    		<input type="text" id="surname" name="surname" placeholder="Surname" value="${therapist.surname}">
			    	</div>			  
			    </div>

			    <div class="control-group">
			    	<label class="control-label" for="username">Username</label>
			    	<div class="controls">
			    		<input type="text" id="username" name="username" placeholder="User name" value="${therapist.username}">
			    	</div>			  
			    </div>

				<div class="control-group">
			    	<label class="control-label" for="username">Password</label>
			    	<div class="controls">
			    		<input type="password" id="password" name="password" placeholder="Password" value="${therapist.password}">
			    	</div>			  
			    </div>
			    
				<div class="control-group">
			    	<label class="control-label" for="accountLocked">Account locked</label>
			    	<div class="controls">
			    	 	<label class="checkbox">
			    	 		<g:checkBox name="accountLocked" value="${therapist.accountLocked}" />							
						</label>			    		
			    	</div>			  
			    </div>
			    
			    <div class="control-group">
			    	<div class="controls">			    
			    		<button type="submit" class="btn btn-primary">Enrol</button>
			    	</div>
			    </div>
    	</g:form>
    			
			<%-- /sec:ifLoggedIn --%>
		</div>
		
		<script type="text/javascript" src="${resource(dir:'js/application', file:'cimss_admin.js')}" >
		</script>
	</body>
</html>