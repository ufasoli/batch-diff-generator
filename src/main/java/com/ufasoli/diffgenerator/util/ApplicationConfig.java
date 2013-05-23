package com.ufasoli.diffgenerator.util;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.inject.name.Named;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: ufasoli
 * Date: 22/04/13
 * Time: 21:33
 */
public class ApplicationConfig {

    public static enum CompareType {URL, FILE}

    ;

    /**
     * Where the report(s) will
     * be created
     */
    private String reportsFolder;

    /**
     * The list of files/url
     * that will be processed
     */
    private List<String> targetFiles;


    /**
     * The 'Left' base folder or url
     */
    private String baseLeft;


    /**
     * The 'Right' base folder or url
     */
    private String baseRight;

    /**
     * What will be compared (files/urls)
     */
    private CompareType compareType;


    private String configFolder = "";


    private final String URLS_FILE = "urls.txt";


    public ApplicationConfig(@Named("config.folder") String configFolder, @Named("base.right") String baseRight, @Named("base.left") String baseLeft, @Named("reports.output.folder") String reportsFolder) {
        this.baseLeft = baseLeft;
        this.baseRight = baseRight;
        this.configFolder = configFolder;
        this.reportsFolder = reportsFolder;
    }

    public ApplicationConfig() {

    }

    /**
     * Loads the list of urls in the urls.txt
     * files into a list of strings to ease the iteration
     *
     * @throws IOException
     */
    private void loadUrls() throws IOException {
        String urlFilePath = configFolder;

        if (!configFolder.endsWith("/")) {
            urlFilePath += "/";
        }
        urlFilePath += URLS_FILE;

        try {
            Files.readLines(new File(urlFilePath), Charsets.UTF_8);
        } catch (IOException e) {
            throw new IOException(String.format("Unable to bootstrap application as the file [%s] cannot be found. Msg : [%s]", urlFilePath, e.getMessage()));
        }
    }

    public String getReportsFolder() {
        return reportsFolder;
    }

    public void setReportsFolder(String reportsFolder) {
        this.reportsFolder = reportsFolder;
    }

    public List<String> getTargetFiles() {
        return targetFiles;
    }

    public void setTargetFiles(List<String> targetFiles) {
        this.targetFiles = targetFiles;
    }

    public String getBaseLeft() {
        return baseLeft;
    }

    public void setBaseLeft(String baseLeft) {
        this.baseLeft = baseLeft;
    }

    public String getBaseRight() {
        return baseRight;
    }

    public void setBaseRight(String baseRight) {
        this.baseRight = baseRight;
    }

    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }
}
