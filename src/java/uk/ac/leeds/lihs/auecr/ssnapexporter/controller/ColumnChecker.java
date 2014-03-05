package uk.ac.leeds.lihs.auecr.ssnapexporter.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.leeds.lihs.auecr.ssnapexporter.dao.Dao;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProvider;

/**
 *
 * @author AjasinA
 */
@Service
public class ColumnChecker {

	@Autowired
	private Dao dao;

	public void checkColumns(final SsnapMetadata metadata) throws SQLException {
    	dao.getColumnInfo(new SsnapMetadataProvider() {
			
			@Override
			public SsnapMetadata getMetadata() {				
				return metadata;
			}
		});
    }
	
    public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
}
