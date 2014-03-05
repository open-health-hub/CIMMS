/**
 * 
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProvider;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapRow;

/**
 * @author AjasinA
 *
 */
@Repository
public class DaoImpl implements Dao {
	
	private JdbcTemplate jdbcTemplate;

	public SsnapMetadata getColumnInfo(SsnapMetadataProvider metadataProvider) {
		return this.jdbcTemplate.queryForObject( "SELECT TOP  1 * from dbo.SsnapExport", new SsnapMetadataRowMapper(metadataProvider));	
	}

	public List<SsnapRow> getAllRows(SsnapMetadataProvider metadataProvider) {
		return this.jdbcTemplate.query( "SELECT * from dbo.SsnapExport", new SsnapRowMapper(metadataProvider));	
	}
	
	public List<SsnapRow> getRowsByHsi(String hsi, SsnapMetadataProvider metadataProvider) {
		return this.jdbcTemplate.query( "SELECT * from dbo.SsnapExport WHERE hsi = ?", new Object[]{hsi}, new SsnapRowMapper(metadataProvider));	
	}
	
    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public int getSsnapReportColumnCount() {		
		return this.jdbcTemplate.queryForInt("SELECT count(*) FROM ssnapExport");
	}

	@Override
	public int getHsiCount() {
		return  this.jdbcTemplate.queryForInt("SELECT count(*) FROM ssnapExport");
	}    
}
