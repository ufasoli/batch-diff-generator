package com.ufasoli.diffgenerator.diff.reports.html;

import com.google.inject.Inject;
import com.ufasoli.diffgenerator.diff.compare.util.DiffCounter;
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


            int lineNumber = 1;
            DiffCounter diffCounter = new DiffCounter();

            for (DiffRow dr : diffRows) {

                 htmlDiffbody.append(htmlTr(dr, diffCounter,lineNumber));
                lineNumber++;
            }


            htmlHead.append(htmlSummary(diffCounter));


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

    /**
     * Builds the <ul> HTML element containg the color coded
     * legend for each of the changes type (CHANGE, INSERT, EQUAL, DELETED)
     *
     * @return the <ul> HTML element containg the color coded legend
     */
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

    /**
     *
     * @see /resources/html/css/default.css
     * @return a <table> HTML element with the css class 'diff-table'
     */
    private String htmlTableHead(){
        return  "<table class='diff-table'>";
    }

    /**
     * Builds the diff table headers (<th>)
     * with the left and right title
     *
     * @param leftTitle the title of the table's left column
     * @param rightTitle the title of the table's right column
     * @return  a <tr><th></th></tr> bundle of elements with the left and right titles
     */
    private String htmlTableLegend(String leftTitle, String rightTitle) {
       return String.format(
                "<tr><th>Line</th><th>%s</th><th>Line</th><th>%s</th></tr>", new Object[]{leftTitle, rightTitle});
    }

    /**
     * Creates a <tr> html element from the DiffRow object.
     * Depending on the diffRow tag the corresponding counter will be increased
     * in the diffCounter object (INSERTED, DELETED, CHANGED,  EQUAL).
     * Each line will be represented in a <td> with the appropriate CSS depending on the
     * difference.
     * <ul>
     *     <li>CHANGE : </li>
     *       <ul>
     *           <li>left td css : change-left </li>
     *           <li>right td css : change-right </li>
     *       </ul>
     *        <li>DELETE : </li>
     *       <ul>
     *           <li>left td css : deleted-left </li>
     *           <li>right td css : deleted-right </li>
     *       </ul>
     *        <li>EQUAL : </li>
     *       <ul>
     *           <li>left td css : equals-left </li>
     *           <li>right td css : equals-right </li>
     *       </ul>
     *        <li>INSERT : </li>
     *       <ul>
     *           <li>left td css : inserted-left </li>
     *           <li>right td css : inserted-right </li>
     *       </ul>
     *
     * </ul>
     *
     * @param diffRow     the object containing the differences
     * @param diffCounter the diffCounter wrapper object that will keep count of the changes
     * @param lineNumber  the lineNumber where the diff was found
     * @return            a <tr> object with the appropriate CSS depending on the difference
     */
    private String htmlTr(DiffRow diffRow, DiffCounter diffCounter, int lineNumber ){

        StringBuilder htmlTr = new StringBuilder();

        htmlTr.append("<tr>");

        String leftCss = "gray";
        String rightCss = "gray";

        if (diffRow.getTag() == DiffRow.Tag.CHANGE) {
            leftCss = "change-left";
            rightCss = "change-right";
        } else if (diffRow.getTag() == DiffRow.Tag.DELETE) {
            leftCss = "deleted-left ";
            rightCss = "deleted-right ";
        } else if (diffRow.getTag() == DiffRow.Tag.EQUAL) {
            leftCss = "equals-left";
            rightCss = "equals-right";
        } else if (diffRow.getTag() == DiffRow.Tag.INSERT) {
            leftCss = "inserted-left" ;
            rightCss = "inserted-right";
        }

        htmlTr.append("<td class='line-number'>" + lineNumber + "</td>");
        htmlTr.append("<td class='" + leftCss + "'>" + diffRow.getOldLine() + "</td>");
        htmlTr.append("<td class='line-number'>" + lineNumber + "</td>");
        htmlTr.append("<td class='" + rightCss + "'>" + diffRow.getNewLine() + "</td>");
        htmlTr.append("</tr>");


        //increase the appropriate counter
        diffCounter.increase(diffRow.getTag());

        return htmlTr.toString();

    }

    /**
     * Builds a <ul> element with the number of occurrences
     * for each of the change types (CHANGE, DELETE, INSERT, EQUAL)
     *
     * @param diffCounter the object containing the number of occurrences
     * @return a <ul> element enumerating the list of changes
     */
    private String htmlSummary(DiffCounter diffCounter){

        StringBuilder htmlSummary = new StringBuilder();
        htmlSummary.append("<ul>");
        htmlSummary.append(String.format("<li>Modified Lines : %s</li>", diffCounter.getChanges()));
        htmlSummary.append(String.format("<li>Deleted Lines : %s</li>",diffCounter.getDeleted()));
        htmlSummary.append(String.format("<li>Inserted Lines : %s</li>",diffCounter.getInserted()));
        htmlSummary.append(String.format("<li>Equal Lines : %s</li>",diffCounter.getEqual()));
        htmlSummary.append("</ul>");
       return  htmlSummary.toString();
    }

    /**
     * Closes the HTML document with the appropriate tags
     * @return
     */
    private String htmlTail(){
        return "</table></pre></div></body></html>";
    }
}
