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
				CIMSS <small>New case</small>
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


			<div class="control-group">
				<label for="userId" class="control-label">User ID</label>
				<div class="controls">
					<input id="userId" name="userId" 
						type="text" size="12" disabled="true" value="<sec:username/>"/>
				</div>
			</div>














			<div class="control-group">
				<label for="surname" class="control-label"> <abbr
					title="Patient surname" class="initialism">Surname</abbr>
				</label>
				<div class="controls">
					<input id="surname" name="surname"  type="text"
						size="10" />
				</div>
			</div>

			<div class="control-group">
				<label for="forename" class="control-label"> <abbr
					title="Patient forename" class="initialism">Forename</abbr>
				</label>
				<div class="controls">
					<input id="forename" name="forename"  type="text"
						size="10" />
				</div>
			</div>

			<div class="control-group">
			    <label for="genderChoice" class="control-label">
			    	<abbr
						title="Gender M or F" class="initialism">Gender</abbr>
				</label>
				
				<div class="controls">
					<label class="radio" id="genderChoice"> 				
						<input id="genderMale" name="gender" value="M" type="radio"/>
						Male						 
					</label>
					<label class="radio"> 				
						<input id="genderFemale" name="gender" value="F" type="radio"/>		
						Female				 
					</label>
				</div>
			</div>
			<div class="control-group">
				<label for="ethnicity" class="control-label"> <abbr
					title="Race" class="initialism">Ethnicity</abbr>
				</label> 
				
				<div class="controls">
				<select id="ethnicity" name="ethnicity">

					<optgroup label="Great Britain">
						<option value="A">British</option>
						<option value="B">Irish</option>
						<option value="C">Any other White background</option>
						<option value="D">White and Black Caribbean</option>
						<option value="E">White and Black African</option>
						<option value="F">White and Asian</option>
						<option value="G">Any other mixed background</option>
						<option value="H">Indian</option>
						<option value="J">Pakistani</option>
						<option value="K">Bangladeshi</option>
						<option value="L">Any other Asian background</option>
						<option value="M">Caribbean</option>
						<option value="N">African</option>
						<option value="P">Any other Black background</option>
						<option value="R">Chinese</option>
						<option value="S">Any other ethnic group</option>
						<option value="Z">Not stated</option>
						<option value="99">Not known</option>
					</optgroup>
				</select>
				</div>
			</div>

			<div class="control-group">
				<label for="postcode" class="control-label"> <abbr
					title="postcodeIn " class="initialism">Postcode</abbr>
				</label>
				<div class="controls">
					<input id="postcode" name="postcode"  type="text"
						size="10" />
				</div>
			</div>

			<div class="control-group">
				<label for="dateOfBirth" class="control-label"> <abbr
					title="dateOfBirth - format dd-mm-yyyy" class="initialism">Birth
						date</abbr>
				</label>
				<div class="controls">
					<input id="dateOfBirth" name="dateOfBirth" 
						type="text" size="10" />
				</div>
			</div>



			<div class="control-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary">Create case</button>
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