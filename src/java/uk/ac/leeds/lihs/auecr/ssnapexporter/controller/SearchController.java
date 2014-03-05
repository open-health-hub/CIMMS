package uk.ac.leeds.lihs.auecr.ssnapexporter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.ac.leeds.lihs.auecr.ssnapexporter.dao.Dao;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProvider;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProviderImpl;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapRow;
import uk.ac.leeds.lihs.auecr.ssnapexporter.validation.RuleValidator;

@Component
public class SearchController {

	@Autowired
	private ColumnChecker columnChecker;
	@Autowired 
	private RowChecker rowChecker;
	@Autowired
	private Dao dao;
	
	public void runSearch(String hsi,  Map modelMap) throws Exception  {
            

            final SsnapMetadata metadata = ExpectationFactory.getExpectationMetadata();
            
                            
            columnChecker.checkColumns(metadata);
            rowChecker.checkRows(metadata);
            List<SsnapRow> rowList = dao.getRowsByHsi(hsi, 
            		new SsnapMetadataProviderImpl(metadata));
					
            Map<String,Object> ruleValidationContext = RuleValidator.makeValidationContext();
            RuleValidator.validate(rowList, ruleValidationContext);
            
                modelMap.put("RowList", rowList);
                modelMap.put("SearchedHsi", hsi);
             
                modelMap.put("Metadata", metadata);
//            return new ModelAndView("search_results");
            
	}
}
