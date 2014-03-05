<!DOCTYPE html>
<html>
	<head>
		<title><g:layoutTitle default="Calderdale Stroke Team" /></title>
		<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
		<g:setProvider library="jquery"/>
		<g:javascript library='jquery' plugin='jquery' />
		<jqui:resources />
		<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.jdMenu.css')}"/>
		<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.jdMenu.slate.css')}"/>
		<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.alerts.css')}"/>
		<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.ui.tabs.css')}"/>
		<link rel="stylesheet" media="screen" href="${resource(dir:'css/redmond',file:'jquery-ui-1.8.10.custom.css')}"/>
		
		
		<link rel="shortcut icon"
			href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
		<g:layoutHead />
		<script type="text/javascript">
	 			function deselect(elementName) {
	         	var walkOutside = document.getElementsByName(elementName);
	            	for( var i=0; i < walkOutside.length; i++) {
	          			walkOutside[i].checked = false;
	    			}
	 			}

	 			function setMasks(){
	 				$(".date").mask("99/99/9999");
	 				$(".time").mask("99:99");
	 			}

	 			

	 			function setDatePickers(){
	 				  $( ".datepicker_with_years_and_months" ).datepicker({
							changeMonth: true,
							changeYear: true,
							yearRange: '-100:+0', 
							dateFormat:'dd/mm/yy',
							showOn: 'button',
							buttonImage: '../../images/calendar.gif',
							buttonImageOnly: true
						
						});

	 				 $( ".datepicker_current" ).datepicker({ 
	 					dateFormat:'dd/mm/yy',
	 					showOn: 'button',
	 					buttonImage: '../../images/calendar.gif',
	 					buttonImageOnly: true
	 				
	 				});
		 		}

		</script>	
	</head>
	<body>
		<div id="spinner" class="spinner" style="display:none;">
    	<img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
    </div>
    <div id="hd">
			<table border="0">
				<tr>
					<td>
						<a href="<g:createLinkTo dir="/"/>">
						 	<img id="chftLogo" class="logo" height="38px" width="315px" src="${resource(dir:'images',file:'CH_foundation_trust_logo.gif')}" alt="Calderdale And Huddersfield Foundation Trust" />
					    </a>
					</td>
					<td colspan="3">
						<h1>Stroke & TIA System</h1>
						
					</td>
					
					<td>
						<sec:ifLoggedIn>
								Logged in as <sec:username/> (<g:link controller='logout'>Logout</g:link>)
						</sec:ifLoggedIn>
						<sec:ifNotLoggedIn>
							<form method="POST" action="${resource(file: 'j_spring_security_check')}">
  								<table>
								    <tr>
								      <td>Username:</td><td><g:textField name="j_username"/></td>
								    </tr>
								    <tr>
								      <td>Password:</td><td><input name="j_password" type="password"/></td>
								    </tr>
  								</table>
  								<input type="submit" value="Sign in" />
							</form>

						</sec:ifNotLoggedIn>
					</td>
					
				</tr>
			</table>
		</div>
		
		<div>
			<div style="margin: 0px auto; width:500px;padding:15px;border:1px solid #eed;	background-color:#eee;">
				
					<h1 style="font-size:24px;">Data</h1>
					<div>
						<g:link controller="patient" action="edit">CIMSS data entry</g:link>
					</div>
					
					<%-- h1>Data</h1>
					<br><g:link controller="patient" action='add'>Add Patient</g:link>
					<br><g:link controller="patient" action='find'>Find Patients</g:link>
					
					<h1>Reports</h1>
					<br><g:link controller="wardReports" action='wardIndex'>Ward status</g:link>
					<br><g:link controller="statutoryReports" action='statutoryIndex'>Statutory reports</g:link>
					<br><g:link controller="export" action='exportIndex'>Exports</g:link--%>
			</div>
		</div>
		
		
		<g:javascript src='jquery/jquery.positionBy.js'/>
		<g:javascript src='jquery/jquery.bgiframe.js'/>
		<g:javascript src='jquery/jquery.jdMenu.js'/>
		<g:javascript src='jquery/jquery.alerts.js'/>
		<g:javascript src='jquery/jquery.ui.core.js'/>
		<g:javascript src='jquery/jquery.ui.widget.js'/>
		<g:javascript src='jquery/jquery.ui.tabs.js'/>
		<g:javascript src='jquery/jquery.maskedinput-1.2.2.js'/>
	
		
		<g:layoutBody />
	</body>
</html>

