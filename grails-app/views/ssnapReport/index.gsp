<html>
<head>
  	<title>The Calderdale And Huddersfield NHS Foundation Trust Stroke System</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<link rel='stylesheet' href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}" type="text/css"/>
	<%--link rel='stylesheet' href="${resource(dir:'css/jquery/redmond', file:'jquery-ui.css')}" type="text/css"/--%> 
	<r:require modules="datatables"/>
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>	

	<style>
	
	  .navbar-inner {
	    background-color: rgb(15, 77, 146);
	    background-image: none;
	  }
	  
	  .navbar .nav .active > a, .navbar .nav .active > a:hover {
	    color: rgb(255, 255, 255);
	    text-decoration: none;
	    background-color: rgb(15, 77, 146);
	  }
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }

		.ui-widget-header {
		    border: 1px solid #4281C9;
		    background: #4281C9;
		    color: rgb(255, 255, 255);
		    font-weight: bold;
		}
		.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
    		border: 1px solid rgb(204, 204, 204);
    		background: white;
    		font-weight: normal;
    		color: black;
		}
		.ui-datepicker th {  
    		text-transform: uppercase;  
    		font-size: 8pt;  
    		padding: 5px 0;  
    		color: #666666;
    		background: white; 
    	}
		.ui-datepicker-calendar .ui-state-active {
			background: #F0F8FF;
		}
		.ui-datepicker-calendar .ui-state-hover {  
    		background: #f7f7f7;  
		} 
		div.dataTables_length label {
			float: left;
			text-align: left;
		}
		
		div.dataTables_length select {
			width: 75px;
		}
		
		div.dataTables_filter label {
			float: right;
		}
		
		div.dataTables_info {
			padding-top: 8px;
		}
		
		div.dataTables_paginate {
			float: right;
			margin: 0;
		}
		
		table.table {
			clear: both;
			margin-bottom: 6px !important;
			max-width: none !important;
		}
		
		table.table thead .sorting,
		table.table thead .sorting_asc,
		table.table thead .sorting_desc,
		table.table thead .sorting_asc_disabled,
		table.table thead .sorting_desc_disabled {
			cursor: pointer;
			*cursor: hand;
		}
		
		table.table thead .sorting { background: url('images/sort_both.png') no-repeat center right; }
		table.table thead .sorting_asc { background: url('images/sort_asc.png') no-repeat center right; }
		table.table thead .sorting_desc { background: url('images/sort_desc.png') no-repeat center right; }
		
		table.table thead .sorting_asc_disabled { background: url('images/sort_asc_disabled.png') no-repeat center right; }
		table.table thead .sorting_desc_disabled { background: url('images/sort_desc_disabled.png') no-repeat center right; }
		
		table.dataTable th:active {
			outline: none;
		}
		
		/* Scrolling */
		div.dataTables_scrollHead table {
			margin-bottom: 0 !important;
			border-bottom-left-radius: 0;
			border-bottom-right-radius: 0;
		}
		
		div.dataTables_scrollHead table thead tr:last-child th:first-child,
		div.dataTables_scrollHead table thead tr:last-child td:first-child {
			border-bottom-left-radius: 0 !important;
			border-bottom-right-radius: 0 !important;
		}
		
		div.dataTables_scrollBody table {
			border-top: none;
			margin-bottom: 0 !important;
		}
		
		div.dataTables_scrollBody tbody tr:first-child th,
		div.dataTables_scrollBody tbody tr:first-child td {
			border-top: none;
		}
		
		div.dataTables_scrollFoot table {
			border-top: none;
		}
		
		
		
		
		/*
		 * TableTools styles
		 */
		.table tbody tr.active td,
		.table tbody tr.active th {
			background-color: #08C;
			color: white;
		}
		
		.table tbody tr.active:hover td,
		.table tbody tr.active:hover th {
			background-color: #0075b0 !important;
		}
		
		.table-striped tbody tr.active:nth-child(odd) td,
		.table-striped tbody tr.active:nth-child(odd) th {
			background-color: #017ebc;
		}
		
		table.DTTT_selectable tbody tr {
			cursor: pointer;
			*cursor: hand;
		}
		
		div.DTTT .btn {
			color: #333 !important;
			font-size: 12px;
		}
		
		div.DTTT .btn:hover {
			text-decoration: none !important;
		}
		
		
		ul.DTTT_dropdown.dropdown-menu a {
			color: #333 !important; /* needed only when demo_page.css is included */
		}
		
		ul.DTTT_dropdown.dropdown-menu li:hover a {
			background-color: #0088cc;
			color: white !important;
		}
		
		/* TableTools information display */
		div.DTTT_print_info.modal {
			height: 150px;
			margin-top: -75px;
			text-align: center;
		}
		
		div.DTTT_print_info h6 {
			font-weight: normal;
			font-size: 28px;
			line-height: 28px;
			margin: 1em;
		}
		
		div.DTTT_print_info p {
			font-size: 14px;
			line-height: 20px;
		}
		
		
		
		/*
		 * FixedColumns styles
		 */
		div.DTFC_LeftHeadWrapper table,
		div.DTFC_LeftFootWrapper table,
		table.DTFC_Cloned tr.even {
			background-color: white;
		}
		
		div.DTFC_LeftHeadWrapper table {
			margin-bottom: 0 !important;
			border-top-right-radius: 0 !important;
			border-bottom-left-radius: 0 !important;
			border-bottom-right-radius: 0 !important;
		}
		
		div.DTFC_LeftHeadWrapper table thead tr:last-child th:first-child,
		div.DTFC_LeftHeadWrapper table thead tr:last-child td:first-child {
			border-bottom-left-radius: 0 !important;
			border-bottom-right-radius: 0 !important;
		}
		
		div.DTFC_LeftBodyWrapper table {
			border-top: none;
			margin-bottom: 0 !important;
		}
		
		div.DTFC_LeftBodyWrapper tbody tr:first-child th,
		div.DTFC_LeftBodyWrapper tbody tr:first-child td {
			border-top: none;
		}
		
		div.DTFC_LeftFootWrapper table {
			border-top: none;
		}
      
    </style>	
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
          <a class="brand" href="#">CIMSS Reporting</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
		  	<sec:ifNotLoggedIn>				
					<li>Please sign in...</li>				
			</sec:ifNotLoggedIn>
			              
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
    
	<div class="container">
			<%--sec:ifLoggedIn --%>
				<div  class="well" style="background-color: rgb(244, 244, 255)">
				<g:form action="makeReport" class="form-inline">

				
					<div class="row-fluid">
					<div class="control-group span3">						
							<label for="reportName" class="control-label" >Report name</label>
							<div class="controls">
							<input id="reportName" name="reportName"  type="text" size="30"/>
							</div>
					</div>
					<div class="control-group span2">						
							<label for="reportType" class="control-label" >Report Type</label> 
							<div class="controls">
							<select id="reportType" name="reportType" class="span2" >
								<option  value="SSNAP72">SSNAP-72</option>
								<option  value="SSNAP">SSNAP</option>
							</select>
							</div> 
					</div>	
					

					
					
					<div class="control-group span2 offset2">						
							<label for="startDate" class="control-label" >Start date</label>
							<div class="controls"> 
							<input id="startDate" name="startDate"  class="standardDatePicker input-mini" type="text" size="10"/>
							</div> 
					</div>
					<div class="control-group span2">						
							<label for="endDate" class="control-label" >End date</label>
							<div class="controls"> 
							<input id="endDate" name="endDate" class="standardDatePicker input-mini" type="text" size="10"/>
							</div> 
					</div>	
				
					<div class="control-group span1">
					    <label class="control-label" ></label>
						<div class="controls">			
							<button type="submit" class="btn btn-primary btn-mini">Go</button>
						</div>				
					</div>
				
					</div>
				</g:form>
				</div>
				<table id="dataTablesList">
				<thead>
					<tr>

						<th>Report name</th>						
						<th>Report Type</th>
						<th>Version</th>
						<th>Creation Date</th>
						<th>Start Date</th>
						<%-- th>SSNAP Uploaded</th>
						<th>Upload Status</th>
						<th>Upload Time</th--%>	
						<th>DELETE</th>						
					</tr>
				</thead>
				<tbody>
				
					<g:each in="${ list}" var="report">
						<tr>
							<td> ${report.reportName} </td>
														
							<td>${report.reportType}</td>
							<td>${report.version }</td>
					   		<td><g:link action="getCsv" params="[reportId:report.id]">
					   		   <g:formatDate date="${report.creationDate}" type="datetime" style="SHORT" timeStyle="SHORT"/></g:link>
					   	    </td>
		
							<td>
					   		   <g:formatDate date="${report.startDate}" type="datetime" style="SHORT" timeStyle="SHORT"/>
					   		</td>
					   		
					   		<%-- td>${report.sentToSsnap }</td>
					   		<td>${report.ssnapUploadStatus }</td>
					   		<td>${report.ssnapUploadTime }</td --%>
					   		<td><g:link action="deleteReport" params="[reportId:report.id]">Delete</g:link></td>
					   </tr>
					</g:each>
				
				</tbody>
				</table>
			<%-- /sec:ifLoggedIn --%>
		</div>
<g:javascript>
$(document).ready(function() {
   $('.standardDatePicker').datepicker({
		dateFormat : 'yy-mm-dd',
		maxDate : new Date(),
		showOn : 'button',
		buttonImage : '/stroke/images/calendar-sharepoint.gif',
		buttonImageOnly : true
	});
	
   	
 /*
  *     $("#dataTablesList").dataTable(
  *      {"oLanguage": {"sLengthMenu": "_MENU_ records per page"}}
  * 	   );
  */	
   	
/* Set the defaults for DataTables initialisation */
$.extend( true, $.fn.dataTable.defaults, {
	"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
	"sPaginationType": "bootstrap",
	"oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	}
} );


/* Default class modification */
$.extend( $.fn.dataTableExt.oStdClasses, {
	"sWrapper": "dataTables_wrapper form-inline"
} );


/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{
	return {
		"iStart":         oSettings._iDisplayStart,
		"iEnd":           oSettings.fnDisplayEnd(),
		"iLength":        oSettings._iDisplayLength,
		"iTotal":         oSettings.fnRecordsTotal(),
		"iFilteredTotal": oSettings.fnRecordsDisplay(),
		"iPage":          oSettings._iDisplayLength === -1 ?
			0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
		"iTotalPages":    oSettings._iDisplayLength === -1 ?
			0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
	};
};


/* Bootstrap style pagination control */
$.extend( $.fn.dataTableExt.oPagination, {
	"bootstrap": {
		"fnInit": function( oSettings, nPaging, fnDraw ) {
			var oLang = oSettings.oLanguage.oPaginate;
			var fnClickHandler = function ( e ) {
				e.preventDefault();
				if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
					fnDraw( oSettings );
				}
			};

			$(nPaging).addClass('pagination').append(
				'<ul>'+
					'<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
					'<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
				'</ul>'
			);
			var els = $('a', nPaging);
			$(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
			$(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
		},

		"fnUpdate": function ( oSettings, fnDraw ) {
			var iListLength = 5;
			var oPaging = oSettings.oInstance.fnPagingInfo();
			var an = oSettings.aanFeatures.p;
			var i, ien, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

			if ( oPaging.iTotalPages < iListLength) {
				iStart = 1;
				iEnd = oPaging.iTotalPages;
			}
			else if ( oPaging.iPage <= iHalf ) {
				iStart = 1;
				iEnd = iListLength;
			} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
				iStart = oPaging.iTotalPages - iListLength + 1;
				iEnd = oPaging.iTotalPages;
			} else {
				iStart = oPaging.iPage - iHalf + 1;
				iEnd = iStart + iListLength - 1;
			}

			for ( i=0, ien=an.length ; i<ien ; i++ ) {
				// Remove the middle elements
				$('li:gt(0)', an[i]).filter(':not(:last)').remove();

				// Add the new list items and their event handlers
				for ( j=iStart ; j<=iEnd ; j++ ) {
					sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
					$('<li '+sClass+'><a href="#">'+j+'</a></li>')
						.insertBefore( $('li:last', an[i])[0] )
						.bind('click', function (e) {
							e.preventDefault();
							oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
							fnDraw( oSettings );
						} );
				}

				// Add / remove disabled classes from the static elements
				if ( oPaging.iPage === 0 ) {
					$('li:first', an[i]).addClass('disabled');
				} else {
					$('li:first', an[i]).removeClass('disabled');
				}

				if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
					$('li:last', an[i]).addClass('disabled');
				} else {
					$('li:last', an[i]).removeClass('disabled');
				}
			}
		}
	}
} );


/*
 * TableTools Bootstrap compatibility
 * Required TableTools 2.1+
 */
if ( $.fn.DataTable.TableTools ) {
	// Set the classes that TableTools uses to something suitable for Bootstrap
	$.extend( true, $.fn.DataTable.TableTools.classes, {
		"container": "DTTT btn-group",
		"buttons": {
			"normal": "btn",
			"disabled": "disabled"
		},
		"collection": {
			"container": "DTTT_dropdown dropdown-menu",
			"buttons": {
				"normal": "",
				"disabled": "disabled"
			}
		},
		"print": {
			"info": "DTTT_print_info modal"
		},
		"select": {
			"row": "active"
		}
	} );

	// Have the collection use a bootstrap compatible dropdown
	$.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
		"collection": {
			"container": "ul",
			"button": "li",
			"liner": "a"
		}
	} );
}

});
/* Table initialisation */
$(document).ready(function() {
	$('#dataTablesList').dataTable( {
		"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
		"sPaginationType": "bootstrap",
		"oLanguage": {
			"sLengthMenu": "_MENU_ records per page"
		}
	} );
} );   	
	</g:javascript>
	</body>
</html>