package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap

import java.util.List;
import java.util.Map;

import uk.ac.leeds.lihs.auecr.ssnapexporter.controller.ExpectationFactory;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProviderImpl;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapRow;
import uk.ac.leeds.lihs.auecr.ssnapexporter.validation.RuleValidator;

class SsnapPreviewController {

	def columnChecker
	def rowChecker
	def dao
	
    def index = { }
	
	def search = {
		
		def hsi = params.hsi;
		SsnapMetadata metadata = ExpectationFactory.getExpectationMetadata();
		
		columnChecker.checkColumns(metadata)
		rowChecker.checkRows(metadata)
		List<SsnapRow> rowList = dao.getRowsByHsi(hsi,
				new SsnapMetadataProviderImpl(metadata))
				
		Map<String,Object> ruleValidationContext = RuleValidator.makeValidationContext()
		RuleValidator.validate(rowList, ruleValidationContext)
		
		render ([view:"search_results", model:[RowList: rowList, SearchedHsi:hsi, Metadata:metadata]])
	}
}
