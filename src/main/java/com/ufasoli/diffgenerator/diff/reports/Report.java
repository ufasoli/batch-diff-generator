package com.ufasoli.diffgenerator.diff.reports;

import difflib.DiffRow;

import java.io.IOException;
import java.util.List;

/**
 *
 * User: ufasoli
 * Date: 21/04/13
 * Time: 19:17
 *
 */
public interface Report {

    /**
     * Generates the diff report based on the diffRows.
     * The report would usually be written in a file in the reportsFolder defined in the ApplicationConfig
     *
     * @see com.ufasoli.diffgenerator.util.ApplicationConfig#getReportsFolder()
     *
     * @param diffRows      the diff object
     * @param reportName    the name of the report
     * @param leftTitle     the title to be shown in the left column of the report
     * @param rightTitle    the title to be shown in the right column of the report
     * @throws IOException
     */
    public void generateReport(List<DiffRow> diffRows, String reportName, String leftTitle, String rightTitle) throws IOException;

}
