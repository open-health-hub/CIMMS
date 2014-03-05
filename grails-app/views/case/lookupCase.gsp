<html>
<head>
<title>The Calderdale And Huddersfield NHS Foundation Trust
	Stroke System</title>
<meta name="layout" content="reportTemplate" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel='stylesheet'
	href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}"
	type="text/css" />
<link rel='stylesheet'
	href="${resource(dir:'css', file:'cimss_admin.css')}" type="text/css" />
<r:require modules="datatables" />
<r:require modules="bootstrap" />
<r:require modules="jquery-ui-dev" />


</head>
<body>



	<tmpl:navBar />

	<div class="container">
		<%--sec:ifLoggedIn --%>

		<div class="page-header">
			<h3>
				CIMSS <small>Look-up case</small>
			</h3>
		</div>

		<g:if test="${flash.error_message}">
			<div class="alert alert-error">
				${flash.error_message}
				<%-- g:hasErrors bean="${case}">
  					<ul>
   						<g:eachError var="err" bean="${case}">
       					<li>${err}</li>
   						</g:eachError>
  					</ul>
				</g:hasErrors--%>
			</div>
		</g:if>
		
		<g:if test="${flash.message}">
			<div class="alert alert-success">
				${flash.message}
			</div>
		</g:if>

		<g:form url="[action:'edit', controller:'patient']" class="form-horizontal">


			<div class="control-group">
				<label for="hsi" class="control-label">Hosp. Stay Id (HSI)</label>
				<div class="controls">
					<input id="hsi" name="hsi" type="text" size="5"
						 />
				</div>
			</div>

			<div class="control-group">
				<label for="hospitalNumber" class="control-label">Hosp. Nr</label>
				<div class="controls">
					<input id="hospitalNumber" name="hospitalNumber" 
						 type="text" size="7" />
				</div>
			</div>


			
				
			<input id="userId" name="userId" 
						type="hidden"  value="<sec:username/>"/>
				



			<div class="control-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary">Lookup case</button>
				</div>
			</div>


		</g:form>

		<%-- /sec:ifLoggedIn --%>
	</div>

	<script type="text/javascript"
		src="${resource(dir:'js/application', file:'cimss_admin.js')}">
		
	</script>
</body>
</html>