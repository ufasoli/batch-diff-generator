package com.ufasoli.diffgenerator.diff.compare.url;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.ufasoli.diffgenerator.diff.compare.Comparator;
import com.ufasoli.diffgenerator.diff.compare.url.util.UrlUtil;
import com.ufasoli.diffgenerator.util.ApplicationConfig;
import difflib.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UrlComparator implements Comparator
{
    private final UrlUtil urlUtil = new UrlUtil(this);
    private final ApplicationConfig applicationConfig;
    private String url1;
    private String url2;
    private String reportName;
    private String outputFolder;
    private List<String> url1Lines;
    private List<String> url2Lines;

    @Inject
    public UrlComparator(ApplicationConfig applicationConfig)
    {

        this.applicationConfig = applicationConfig;
    }

    @Override
    public List<DiffRow> compare(String left, String right) {

        this.url1Lines = urlUtil.urlToLines(this.url1);
        this.url2Lines = urlUtil.urlToLines(this.url2);

        DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
        builder.showInlineDiffs(false);
        builder.columnWidth(120);
        DiffRowGenerator drg = builder.build();

        return drg.generateDiffRows(this.url1Lines, this.url2Lines);

    }



    public String getUrl1() {
        return this.url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return this.url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<String> getUrl1Lines() {
        return this.url1Lines;
    }

    public void setUrl1Lines(List<String> url1Lines) {
        this.url1Lines = url1Lines;
    }

    public List<String> getUrl2Lines() {
        return this.url2Lines;
    }

    public void setUrl2Lines(List<String> url2Lines) {
        this.url2Lines = url2Lines;
    }

    public String getOutputFolder() {
        return this.outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
}