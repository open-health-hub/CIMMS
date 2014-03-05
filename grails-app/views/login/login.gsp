<html>
<head>
  	<title>CIMSS Login</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<link rel='stylesheet' href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}" type="text/css"/>
	
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>	

	<style type="text/css">
	 body {
 		background-color: rgb(15, 77, 146);
 		padding-top: 40px;
        padding-bottom: 40px;
	 }
	 .login-panel {
	 	max-width: 300px;
	    padding: 19px 29px 29px;
	    margin: 0 auto 20px;
	    background-color: #fff;
	    border: 1px solid #e5e5e5;
	 }
	 
	</style>
</head>
 <body> 

	<div class="container">

		<div class="login-panel">			
			<div class="page-header">
    			<h3>CIMSS <small> log-in</small></h3>
    		</div>	
    				
			<g:if test="${params.error}">
				<div class="alert alert-error">
					<strong>Login failed.</strong> Please try again
				</div>
			</g:if>
		
			<form method="POST" action="${resource(file: 'j_spring_security_check')}">
						
		 			<div class="control-group">
						<label class="control-label" for="j_username">User name</label>
						<div class="controls">										
							<input name="j_username" id="j_username">
						</div>
					</div>
								    
		 			<div class="control-group">
						<label class="control-label" for="j_password">Password</label>
						<div class="controls">										
							<input type="password" name="j_password" id="j_password">
						</div>
					</div>	
					
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-primary button-ok">Sign-in</button>
						</div>
					</div>				
			</form>
		</div>
	</div>
</body>
</html>							