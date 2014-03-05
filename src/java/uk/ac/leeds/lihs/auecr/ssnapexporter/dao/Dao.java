/**
 * 
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.dao;

import java.util.List;

import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadataProvider;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapRow;

/**
 * @author AjasinA
 *
 */
public interface Dao {

	public int getSsnapReportColumnCount();
	public int getHsiCount();
	
	public SsnapMetadata getColumnInfo(SsnapMetadataProvider metadataProvider);
	public List<SsnapRow> getAllRows(SsnapMetadataProvider metadataProvider);
	public List<SsnapRow> getRowsByHsi(String hsi, SsnapMetadataProvider metadataProvider);

}
