<html>
<head>
  	<title>CIMSS Therapy</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />

		
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
    			<h3>Therapy <small> choose type of therapy</small></h3>
    		</div>			
			<g:if test="${flash.message}">
				<div class="alert alert-success">${flash.message}</div>
			</g:if>
			<g:if test="${flash.error_message}">
				<div class="alert alert-error">
				${flash.error_message}
				</div>
			</g:if>
			
			<div class="row">
				<div class="span12">
					    <ul class="nav nav-list">
    						<li class="nav-header">Therapy types</li>
    						<li class="active"><g:link action="chooseTherapy" params="[therapy: 'Physiotherapy']">Physiotherapy</g:link></li>
    						<li><g:link action="chooseTherapy" params="[therapy: 'Occupational']">Occupational therapy</g:link></li>
    						<li><g:link action="chooseTherapy" params="[therapy: 'Speech-and-language']">Speech and language therapy</g:link></li>
    						<li><g:link action="chooseTherapy" params="[therapy: 'Psychotherapy']">Psychology</g:link></li>
    						<li><g:link action="chooseTherapy" params="[therapy: 'Nurse-led']">Nurse-led therapy</g:link></li>
        				</ul>
				</div>
			</div>
			

			<%-- /sec:ifLoggedIn --%>
		</div>
		
	</body>
</html>