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
    			<h3>Therapists <small>therapist accounts</small></h3>
    		</div>			
			<g:if test="${flash.message}">
			<div class="alert alert-success">${flash.message}</div>
			</g:if>
			
			
			<div class="row">
				<div class="span12">
					<p>
					Currently enrolled therapists
					
					<g:link action="newTherapistBegin" class="btn btn-primary btn-small">
						Enrol new Therapist
					</g:link>
					</p>
				</div>
			</div>
			
			<table id="dataTablesList">
				<thead>
					<tr>

						<th>Therapist name</th>						
						<th>Therapist login</th>
						<th>Role</th>
					</tr>
				</thead>
				<tbody>
				
					<g:each in="${therapistList}" var="th">
						<tr>
							<td> ${th.user.firstName} </td>
							<td> <g:link action="showTherapist" params="[therapistId:th.user.id]">${th.user.username} </g:link></td>
							<td> ${th.role.authority}</td>
					   </tr>
					</g:each>
				
				</tbody>
				</table>
			<%-- /sec:ifLoggedIn --%>
		</div>
		
		<script type="text/javascript" src="${resource(dir:'js/application', file:'cimss_admin.js')}" >
		</script>
	</body>
</html>