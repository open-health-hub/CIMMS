

<div  class="statusbar clearfix">

	<div  class="warnings tiaWarning"  data-bind="visible: (imaging.scan().imageDiagnosisCategory() == 'TIA' || imaging.scan().imageDiagnosisCategory() == 'Other')" >
		<ul>
			<li>If TIA or Other selected, there is not requirement to continue inputting onto CIMSS</li>
		</ul>
	</div>

	<g:if test="${grailsApplication.config.trust.name.equals('chft')}">
		<div class="dataStatus dataStatusIndicator" >   
		    <a href="#" class="statusButton" data-bind="click:chftStatusDetails, css:{ statusButton_flag: chftStatus() < 0,  statusButton: chftStatus() >= 0}" alt="CHFT" >CHFT</a>           
		</div>
	</g:if>

	<g:if test="${grailsApplication.config.trust.name.equals('anhst')}">
	   <div class="dataStatus dataStatusIndicator" >   
	        <a href="#" class="statusButton" data-bind="click:anhstStatusDetails, css:{ statusButton_flag: anhstStatus() < 0,  statusButton: anhstStatus() >= 0}" alt="ANHST" >ANHST</a>           
	    </div>
	</g:if>
	
	
	<div class="dataStatus dataStatusIndicator">
		<a href="#" class="statusButton" data-bind="click:ssnapStatusDetails, css:{ statusButton_flag: ssnapStatus() < 0,  statusButton: ssnapStatus() >= 0}"  alt="SSNAP">SSNAP</a>
	</div>
	
	
	 
	<div class="dataStatus dataStatusIndicator">
		<a href="#" class="statusButton"  data-bind="click:ssnap72StatusDetails, css:{ statusButton_flag: ssnap72Status() < 0,  statusButton: ssnap72Status() >= 0}"  alt="SSNAP">72 SSNAP</a>
	</div>
	
	
	<div class="dataStatus dataStatusIndicator">
		<a href="#" class="statusButton" data-bind="click:cimssStatusDetails,  css:{ statusButton_flag: cimssStatus() < 0,  statusButton: cimssStatus() >= 0}"  alt="CIMSS">CIMSS</a>
	</div>

</div>