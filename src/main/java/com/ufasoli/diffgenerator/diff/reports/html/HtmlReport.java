package com.ufasoli.diffgenerator.diff.reports.html;

import com.ufasoli.diffgenerator.diff.reports.Report;
import difflib.DiffRow;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ufasoli
 * Date: 21/04/13
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class HtmlReport  implements Report{

    @Override
    public void generateReport(String targetFolder, List<DiffRow> diffs) throws IOException {


        if(targetFolder == null )    {

        FileWriter fWriter = new FileWriter(targetFolder+ "reportName" + ".html");

        StringBuilder htmlHead = new StringBuilder(
                "<!DOCTYPE html><html><head><meta charset='utf-8'></head><body><div id='comparaison'><pre style=' white-space: pre-wrap; word-wrap: break-word;'> ");

        htmlHead.append(
                String.format("<h3>Legend</h3><ul><li>changes : <span style='background-color: %s; border: thin solid black;'>&nbsp;</span></li><li> deleted  : <span style='background-color: %s; border: thin solid black;'>&nbsp;</span></li><li>Equal : <span style='background-color: %s; border: thin solid black;'>&nbsp;</span></li><li>inserted : <span style='background-color: %s; border: thin solid black;'>&nbsp;</span></li></ul>", new Object[] {
                        "#FF9933", "#FCD8D9", "#E0FCD0", "#0099CC" }));

        StringBuilder htmlDiffbody = new StringBuilder(
                "<table style='border-collapse: separate; border-spacing: 2px; border-color: gray;' >");
        htmlDiffbody.append(String.format(
                "<tr><th>Line</th><th>%s</th><th>Line</th><th>%s</th></tr>", new Object[] { "this.url1", "this.url2" }));
        htmlDiffbody.append("<tr>");
        int line = 1;
        int changes = 0;
        int deleted = 0;
        int inserted = 0;
        int equal = 0;

        for (DiffRow dr : diffs)
        {
            htmlDiffbody.append("<tr>");
            String leftCss = "gray";
            String rightCss = "gray";

            if (dr.getTag() == DiffRow.Tag.CHANGE) {
                leftCss = " border : 1px solid #FF6633; background-color: #FF9933";
                rightCss = leftCss;
                changes++;
            }
            else if (dr.getTag() == DiffRow.Tag.DELETE)
            {
                leftCss = "border: 1px solid #9A2328; background-color: #FCD8D9";
                rightCss = leftCss;
                deleted++;
            } else if (dr.getTag() == DiffRow.Tag.EQUAL)
            {
                leftCss = "border: 1px solid #1A981F; background-color: #E0FCD0";
                rightCss = leftCss;
                equal++;
            }
            else if (dr.getTag() == DiffRow.Tag.INSERT) {
                leftCss = "border : 1px solid #8E8E8E; background-color: #DEDEDE";
                rightCss = "border : 1px solid #0033CC; background-color: #CFFEF0";
                inserted++;
            }

            htmlDiffbody
                    .append("<td style='background-color: #cccccc; font-family: monospace; font-weight: bold;'>" +
                            line + "</td>");
            htmlDiffbody
                    .append("<td style='" + leftCss +
                            " ; color: black; font-family: monospace; '>" + dr.getOldLine() +
                            "</td>");
            htmlDiffbody
                    .append("<td style='background-color: #cccccc; font-family: monospace; font-weight: bold;'>" +
                            line + "</td>");
            htmlDiffbody.append("<td style='" + rightCss +
                    "; color: black; font-family: monospace'>" + dr.getNewLine() + "</td>");

            line++;
        }

        htmlHead.append(
                String.format("<ul><li>Inserted Lines : %s</li><li>Deleted Lines : %s</li><li>Modified Lines : %s</li><li>Non Modified lines :%s </li></ul> <hr />", new Object[] {
                        Integer.valueOf(inserted), Integer.valueOf(deleted), Integer.valueOf(changes), Integer.valueOf(equal) }));

        htmlDiffbody.append("</table></pre></div></body></html>");
        fWriter.write(htmlHead.toString());
        fWriter.write(htmlDiffbody.toString());
        fWriter.close();
    }
    }

    @Override
    public void generateAggregatedReport(String targetFolder, List<DiffRow> diffs, String reportName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
