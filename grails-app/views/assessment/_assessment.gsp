<div class="pull-left sidebar">
 
     <div class="sidebar-head">	    
		<h4>Admission Assessment</h4>
    </div>
    
	<tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="assessment"/>
    
    <ul class="nav nav-list iconised-list">    
	    
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-leaf"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a href="#" id="gcs_link" onclick="$('#tabs').tabs('select',1); $('#accordion').accordion('option','active',0) ;">Glasgow Coma Score</a>
	    	</span>
	    </li>
	    
	    
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class=" icon-user"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a href="#" id="__sensory_moter_01" onclick="$('#tabs').tabs('select',1); $('#accordion').accordion('activate',1) ;">Sensory/Motor Features</a>
	    	</span>
	    </li>
	    	    	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-star"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" id="__other_features_01" onclick="$('#tabs').tabs('select',1); $('#accordion').accordion('activate',2) ;">Other Features</a>
	    	</span>
	    </li>
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-plus"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" id="__swallow_screening_01" onclick="$('#tabs').tabs('select',1); $('#accordion').accordion('activate',3) ;">Swallow Screening</a>
	    	</span>
	    </li>

	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-map-marker"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" id="__continence_01" onclick="$('#tabs').tabs('select',1); $('#accordion').accordion('activate',4) ;">Continence</a>
	    	</span>
	    </li>

	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-th-list"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" id="__nutrition_01" onclick="$('#tabs').tabs('select',1); $('#accordion').accordion('activate',5) ;">Nutrition Management</a>
	    	</span>
	    </li>
	    
    </ul>
</div>

<div id="accordion" class="data-entry-narrow">
    <h3><a href="#">Glasgow Coma Score</a></h3>
    <div id='gcs-section'></div>
    
    <h3><a href="#">Sensory/Motor Features</a></h3>
    <div id='nihss-sensory-section'></div>
    
    <h3><a href="#">Other Features</a></h3>
    <div id='nihss-other-section'></div>
    
    <h3><a href="#">Swallow Screening</a></h3>
    <div id='swallow-screen-section'></div>
    
    <h3><a href="#">Continence</a></h3>
    <div id='continence-section'></div>
    
    <h3><a href="#">Nutrition Management</a></h3>
    <div id='nutrition-section'></div>
    
</div>
