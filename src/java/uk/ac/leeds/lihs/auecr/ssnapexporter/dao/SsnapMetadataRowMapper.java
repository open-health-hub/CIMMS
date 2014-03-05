/**
 * 
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapField;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProvider;

/**
 * @author AjasinA
 *
 */
public class SsnapMetadataRowMapper implements RowMapper<SsnapMetadata> {

	private SsnapMetadataProvider metadataProvider;
	
	public SsnapMetadataRowMapper(SsnapMetadataProvider metadataProvider) {
		super();
		this.metadataProvider = metadataProvider;
	}

	@Override
	public SsnapMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsMetadata = rs.getMetaData();
        int numColumns = rsMetadata.getColumnCount();
        
        SsnapMetadata metadata = this.metadataProvider.getMetadata();
        
        for ( int i = 1; i <= numColumns; i++) {
            String columnName = rsMetadata.getColumnLabel(i);
            SsnapField fld = metadata.getField(columnName);
            if ( fld != null) {
                fld.setFieldPresent(true);
            }
        }
        
		return metadata;
	}

}
