/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.leeds.lihs.auecr.ssnapexporter.dao.Dao;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;

/**
 *
 * @author AjasinA
 */
@Service
public class RowChecker {

	@Autowired
	private Dao dao;
	
    public void checkRows(final SsnapMetadata metadata) throws SQLException {    	
        metadata.setExpectedRowCount(getExpectedRowCount());
        metadata.setActualRowCount(getActualRowCount());
    }

    private  int getExpectedRowCount() throws SQLException {       
        return this.dao.getHsiCount();
    }

    private int getActualRowCount() throws SQLException {
    	return this.dao.getSsnapReportColumnCount();
    }

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
}
