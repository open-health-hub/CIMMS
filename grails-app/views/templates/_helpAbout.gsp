<div id="helpAboutDialog" style="display:none">
	<div>
		<br>
		<g:if test="${grailsApplication.config.trust.name.equals('anhst')}">
		  <p>Airedale NHS Foundation Trust - Stroke module</p>
		</g:if>
		<g:if test="${grailsApplication.config.trust.name.equals('chft')}">
          <p>Calderdale and Huddersfield NHS Foundation Trust - Stroke module</p>
        </g:if>
		<br>
		<p>
		Version - ${grailsApplication.metadata['app.version']}
		</p>
		<br>
	</div>
	<div>
		<dl>
			<dt><strong>SSNAP Comprehensive Mode</strong> -- ${grailsApplication.config.ssnap.level}</dt>
			<dd>${grailsApplication.config.ssnap.level.equals('comprehensive')}</dd>
		</dl>
	</div>
	<div>	
		<br>
		<p>Developed in partnership with the CIMSS team</p>
		<br>
	</div>
	<div>		
		<br>
		<p>Please report issues to the CIMSS team</p>
		<br>
	</div>
	

	<div id="message" style="display:none">
		<br>
		<p id="patientDetails"></p>
	</div>
	
	<div>
	
		<br>
		Check Browser Mode. The mode affects how the web page is displayed
		
		<br>
		<p>
		<a href="#" onclick="fnCompatCheck();" style="color:#600; font-size:9px;">Check Mode</a>
		</p>
		
		<br>
		
		<p style="font-size:9px;">Compatibility check courtesy of Microsoft</p>
	</div>
	
	<SCRIPT type="text/javascript">		
		function fnCompatCheck(){		
		var sAppend, sCompat = document.compatMode;
		if (sCompat == "CSS1Compat")
			{
				sAppend = "Standards-compliant mode is switched on.  \nThis document renders only standards-compliant standards rules.";
			}
		else if (sCompat == "BackCompat")
			{
				sAppend = "Standards-compliant mode is not switched on.  \nThis document renders non-standards-compliant style rules compatible with earlier versions of IE.";
			}
		else
			{
				 alert("Error.  Neither value was returned.");
				 return;
			}		
			
			alert("Value of compatMode property is " + 
			document.compatMode + ".\n" + sAppend);
		}	
	</script>	
</div>