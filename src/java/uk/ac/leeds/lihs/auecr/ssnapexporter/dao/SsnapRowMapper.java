/**
 * 
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapField;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapFieldValue;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProvider;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapRow;

/**
 * @author AjasinA
 *
 */
public class SsnapRowMapper implements RowMapper<SsnapRow>  {
	
	private SsnapMetadataProvider metadataProvider;
	
	public SsnapRowMapper(SsnapMetadataProvider metadataProvider) {
		this.metadataProvider = metadataProvider;
	}

	@Override
	public SsnapRow mapRow(ResultSet rs, int rowNum) throws SQLException {
		SsnapMetadata metadata = this.metadataProvider.getMetadata();

		SsnapRow row = new SsnapRow(metadata);
        for (SsnapField fld : metadata.getFieldList()) {
            String value = rs.getString(fld.getName());                                
            if ( value == null ) {
                value = "";
            }
            SsnapFieldValue valueField = new SsnapFieldValue();
            valueField.setValue(value);
            row.setFieldValue(fld.getName(), valueField);                
        }
        
		return row;
	}
}
