 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>SSNAP Export Checker</title>
	<meta name="layout" content="reportTemplate" />
	<link rel='stylesheet' href="${resource(dir:'css', file:'cimss_admin.css')}" type="text/css"/> 
	
	<r:require modules="datatables"/>
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>
	

        
    </head>
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <g:link class="brand" action="index">SSNAP Export Preview</g:link>
                    <div class="nav-collapse collapse">
                        <ul class="nav">
                            <li class="active"><g:link class="brand" action="index">Home</g:link></li>
                            <li><a href="#about">About</a></li>
                            <li><a href="mailto:Vicky.Padgett@Bthft.nhs.uk">Contact</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <div class="container">

            <div class="page-header">
                <h1>SSNAP Check</h1>
                <small>Check results</small>
            </div>

            <div class="tabbable"> 
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#statsTab" data-toggle="tab">Search result</a></li>
                    <li><a href="#searchTab" data-toggle="tab">Search</a></li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane active" id="statsTab">

                        
                        <g:if test="${ RowList.size() == 0}">
                            <div class="alert">
                                <strong>Warning:</strong> No records wee found
                            </div>
                        </g:if>
                        <g:else>
                            <div class="alert alert-success">
                                <strong>${ RowList.size() }</strong> record(s) found
                            </div>                                
                        </g:else>
                        
                                
                        <table id="dataTablesList" class="table table-hover table-striped">
                            <thead>
                                <tr>
                                    
                                    <th>Field</th>
                                    <th>Value</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <g:each var="row" in="${RowList}">
                                <g:each var="fld" in="${Metadata.fieldList}">
	                                <tr >
	                                    <td>${fld.name}</td>
	                                    <td style="${row.ssnapValueMap[fld.name].fieldValid? '': 'background-color: rgb(242, 222, 222);'}">
	                                        ${row.ssnapValueMap[fld.name].value}
	                                    </td>                
	                                                                          
	                                </tr> 
                                </g:each>
                                </g:each>
                            </tbody>
                        </table>                        
                    </div>
                   
                    <div class="tab-pane" id="searchTab">


                        <g:form class="form-horizontal" action="search">
                              <div class="control-group">
                                  <label class="control-label" for="hsi">Hospital stay ID</label>
                                  <div class="controls">
                                  <input type="text" id="hsi" name="hsi" placeholder="HOSPITAL STAY ID">
                                  </div>
                              </div>

                              <div class="control-group">
                                  <div class="controls">
                                      <button type="submit" class="btn">Search</button>                                                
                                  </div>
                              </div>
                         </g:form>
                                                       
                    </div>


                    </div>                                    
            </div>
                                          





        </div> <!-- /container -->

        <script src="http://code.jquery.com/jquery.js"></script>
        <tmpl:/templates/dataTable_customization_js />
    </body>
</html>