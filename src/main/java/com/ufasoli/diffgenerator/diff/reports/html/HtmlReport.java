package com.ufasoli.diffgenerator.diff.reports.html;

import com.google.inject.Inject;
import com.ufasoli.diffgenerator.diff.reports.Report;
import com.ufasoli.diffgenerator.util.ApplicationConfig;
import difflib.DiffRow;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * User: ufasoli
 * Date: 21/04/13
 * Time: 19:22
 */
public class HtmlReport implements Report {

    private ApplicationConfig applicationConfig;

    @Inject
    public HtmlReport(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }


    @Override
    public void generateReport(List<DiffRow> diffRows, String reportName, String leftTitle, String rightTitle) throws IOException {

        String outputFolder = applicationConfig.getReportsFolder();

        try {
            FileWriter fWriter = new FileWriter(outputFolder + reportName + ".html");
            StringBuilder htmlHead = new StringBuilder();

            htmlHead.append(htmlHead());
            htmlHead.append(htmlLegend());

            StringBuilder htmlDiffbody = new StringBuilder();
            htmlDiffbody.append(htmlTableHead());
            htmlDiffbody.append(htmlTableLegend(leftTitle, rightTitle));


            int line = 1;
            int changes = 0;
            int deleted = 0;
            int inserted = 0;
            int equal = 0;

            for (DiffRow dr : diffRows) {
                htmlDiffbody.append("<tr>");
                String leftCss = "gray";
                String rightCss = "gray";

                if (dr.getTag() == DiffRow.Tag.CHANGE) {
                    leftCss = "change-left";
                    rightCss = "change-right";
                    changes++;
                } else if (dr.getTag() == DiffRow.Tag.DELETE) {
                    leftCss = "deleted-left ";
                    rightCss = "deleted-right ";
                    deleted++;
                } else if (dr.getTag() == DiffRow.Tag.EQUAL) {
                    leftCss = "equals-left";
                    rightCss = "equals-right";
                    equal++;
                } else if (dr.getTag() == DiffRow.Tag.INSERT) {
                    leftCss = "inserted-left" ;
                    rightCss = "inserted-right";
                    inserted++;
                }

                htmlDiffbody
                        .append("<td class='line-number'>" + line + "</td>");
                htmlDiffbody
                        .append("<td class='" + leftCss +"'>" + dr.getOldLine() + "</td>");
                htmlDiffbody
                        .append("<td class='line-number'>" + line + "</td>");

                htmlDiffbody.append("<td class='" + rightCss +"'>" + dr.getNewLine() + "</td>");

                line++;
            }

            htmlHead.append(
                    String.format("<ul><li>Inserted Lines : %s</li><li>Deleted Lines : %s</li><li>Modified Lines : %s</li><li>Non Modified lines :%s </li></ul> <hr />", new Object[]{
                            Integer.valueOf(inserted), Integer.valueOf(deleted), Integer.valueOf(changes), Integer.valueOf(equal)}));

            htmlDiffbody.append("</table></pre></div></body></html>");
            fWriter.write(htmlHead.toString());
            fWriter.write(htmlDiffbody.toString());
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String htmlHead(){
        return  "<!DOCTYPE html><html><head><meta charset='utf-8'></head><body><div id='comparaison'><pre class='wrapper'> ";
    }

    private String htmlLegend(){

       StringBuilder sb = new StringBuilder("<h3>Legend</h3>");
        sb.append("<ul>");
        sb.append("<li>changes : <span class='change-left'>&nbsp;</span></li>");
        sb.append("<li> deleted  : <span class='deleted-left'>&nbsp;</span></li>");
        sb.append("<li>Equal : <span class='equals-left'>&nbsp;</span></li>");
        sb.append("<li>inserted : <span class='inserted-left'>&nbsp;</span></li>");
        sb.append("</ul>");

        return sb.toString();
    }

    private String htmlTableHead(){
        return  "<table class='diff-table'>";
    }

    private String htmlTableLegend(String leftTitle, String rightTitle) {
       return String.format(
                "<tr><th>Line</th><th>%s</th><th>Line</th><th>%s</th></tr>", new Object[]{leftTitle, rightTitle});
    }
}
