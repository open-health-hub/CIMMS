/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapField;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapFieldValue;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapMetadata;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapRow;
import uk.ac.leeds.lihs.auecr.ssnapexporter.validation.RuleValidator;

/**
 *
 * @author AjasinA
 */

public class RowDataSource {

	
    public static List<SsnapRow> fetchRow(String hsi, Connection conn, SsnapMetadata metadata) throws SQLException {

        List<SsnapRow> rows = new ArrayList<SsnapRow>();

        String qry = "SELECT * from dbo.SsnapExport WHERE hsi = '" + hsi + "'";
        System.out.println("qry  = " +qry);
        
        
         Statement st = conn.createStatement();               
         ResultSet rs = st.executeQuery(qry); 

         processRows(rows, 1, metadata, rs);

        return rows;
    }

    public static List<SsnapRow> fetchRow(int numRows, Connection conn, SsnapMetadata metadata) throws SQLException {

        List<SsnapRow> rows = new ArrayList<SsnapRow>();

        
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT distinct TOP " + numRows + " * from dbo.SsnapExport");

            processRows(rows, numRows, metadata, rs);

        
        return rows;
    }

    private static void processRows(List<SsnapRow> rows, int numRows, SsnapMetadata metadata, ResultSet rs) throws SQLException {
        
        Map<String,Object> ruleValidationContext = RuleValidator.makeValidationContext();
        
        for (int i = 0; rs.next() && i < numRows; i++) {
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
            RuleValidator.validate(row, ruleValidationContext);
            rows.add(row);
            
        }
               
    }



}
