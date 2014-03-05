

<!--  FOOTER BEGIN -->
<div id="footer" class="footer">
	<div class="container">


		<div class="row">
			<div class="span7">
				<h3 class="footer-title">CIMSS</h3>

				<div>

					<g:if test="${grailsApplication.config.trust.name.equals('anhst')}">
						<p>Airedale NHS Foundation Trust - Stroke module</p>
					</g:if>
					<g:if test="${grailsApplication.config.trust.name.equals('chft')}">
						<p>Calderdale and Huddersfield NHS Foundation Trust - Stroke
							module </p>
					</g:if>

				</div>

				<div>
					<p>Version  - ${grailsApplication.metadata['app.version']}</p>
				</div>
				
				<div>

					<p>
						CIMSS is running in
						<g:if
							test="${grailsApplication.config.ssnap.level.equals('comprehensive')}">
							<strong>SSNAP Comprehensive Mode</strong>
						</g:if>
						<g:else>
							<strong>SSNAP Standard Mode</strong>
						</g:else>
					</p>
				</div>

				<div>
					<p>
						<br> <br> Developed in partnership with the <strong>CIMSS</strong>
						team
					</p>
				</div>
				<div>
					<p>
						Please report issues to the <strong>CIMSS</strong> team
					</p>
				</div>



			</div>

			<div class="span5">
				<div class="footer-banner">
					<h3 class="footer-title">More Information</h3>
					<ul>
						<li>Royal College of Physicians</li>
						<li>About Stroke</li>
						<li>Technical queries</li>
					</ul>
					BIHR - 2013
				</div>
			</div>
		</div>
	</div>
	<!--  CREDIT TO: http://martinbean.co.uk Martin Bean  and http://ryanfait.com/sticky-footer/ Ryan Fait. -->

</div>
<!--  FOOTER BEGIN -->