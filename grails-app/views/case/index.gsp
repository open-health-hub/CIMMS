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
    			<h3>CIMSS</h3>
    		</div>			
			<g:if test="${flash.message}">
			<div class="alert alert-success">${flash.message}</div>
			</g:if>
			
			
			
			
			<br>
			<br>
				<ul class="thumbnails">

					
					<li class=" span4" style="margin-left: 230px;">
						<div class="thumbnail">
						<g:link action="lookupCase">
							<img height="200px" width="200px" src="${resource(dir:'images',file:'folder-98462_640.png')}" alt="Lookup">
							<img height="50px" width="50px" src="${resource(dir:'images',file:'magnifying-glass-97635_640.png')}" alt="Lookup">
						</g:link>
						<h3>Look-up existing case</h3>
						<p>Continue with a case that is already in CIMSS</p>
						</div>
					</li>

					<li class="span4 ">
						<div class="thumbnail">
						<g:link action="newCase">
							<img height="200px" width="200px" src="${resource(dir:'images',file:'folder-98462_640.png')}" alt="New case">
							<img height="50px" width="50px" src="${resource(dir:'images',file:'add-159647_640.png')}" alt="New case">							
						</g:link>
						<h3>Create new case</h3>
						<p>Create a new case in  CIMSS</p>
						</div>
					</li>
				</ul>			
			
			

			<%-- /sec:ifLoggedIn --%>
		</div>
		
		<script type="text/javascript" src="${resource(dir:'js/application', file:'cimss_admin.js')}" >
		</script>
	</body>
</html>