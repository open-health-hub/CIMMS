  	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">Therapists</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><g:link controller="therapistAdmin" action="index">Therapists</g:link></li>
              
		  	<sec:ifNotLoggedIn>				
					<li><a href="#">Please sign in...</a></li>				
			</sec:ifNotLoggedIn>
			<sec:ifLoggedIn>				
					<li><g:link controller="logout" action="index">Logout</g:link></li>				
			</sec:ifLoggedIn>             
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>