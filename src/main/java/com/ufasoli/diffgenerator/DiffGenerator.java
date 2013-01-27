package com.ufasoli.diffgenerator;

import difflib.*;
import difflib.Delta.TYPE;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name   : DiffGenerator
 * Description  :
 * Date         : 17 janv. 2013
 * Copyright    : ARTE G.E.I.E.     
 * Auteur       : Ulises Fasoli
 */
public class DiffGenerator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

//        String url1 = "http://arte-epg-apipreprod.sdv.fr/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json";
        String url2 = "http://www.arte.tv/papi/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json?pretty=true";
        String url1 = "http://www.arte.tv/papip/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json?pretty=true";
        DiffGenerator c = new DiffGenerator(url1, url2);

        c.compare();

    }

    private String url1;
    private String url2;

    private List<String> url1Lines;
    private List<String> url2Lines;

    public DiffGenerator(String url1, String url2) {

        this.url1 = url1;
        this.url2 = url2;
    }

    public void compare() {
        url1Lines = urlToLines(url1);
        url2Lines = urlToLines(url2);

        DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
        builder.showInlineDiffs(false);
        builder.columnWidth(120);
        DiffRowGenerator drg = builder.build();

        List<DiffRow> rows = drg.generateDiffRows(fileToLines(new File("/Users/ufasoli/original.json")), fileToLines(new File("/Users/ufasoli/edited.json")));

        try {
            FileWriter fWriter = new FileWriter("/Users/ufasoli/report.html");
            StringBuilder htmlBody = new StringBuilder("<!DOCTYPE html><html><head><meta charset='utf-8'></head><body><div id='comparaison'><pre style=' white-space: pre-wrap; word-wrap: break-word;'> <table ><tr>");

             int line =1;
            for(DiffRow dr : rows){


                htmlBody.append("<tr>");
                String css = "gray";

                if (dr.getTag() == DiffRow.Tag.CHANGE) {
                    css = "orange";

                } else if (dr.getTag() == DiffRow.Tag.DELETE) {

                    css = "red";
                } else if (dr.getTag() == DiffRow.Tag.EQUAL) {

                    css = "border-color: rgb(26, 152, 31); background-color: rgb(221, 248, 204);";

                }
                else if(dr.getTag() == DiffRow.Tag.INSERT){
                    css = "yellow";
                }

                htmlBody.append("<td style='background-color: gray'>"+line+"</td>");
                htmlBody.append("<td style='background-color: "+color+" ; color: gray;'>"+dr.getOldLine()+"</td>");
                htmlBody.append("<td style='background-color: gray'>"+line+"</td>");
                htmlBody.append("<td style='background-color: "+color+"; color: gray;'>"+dr.getNewLine()+"</td>");


                line++;

            }
            htmlBody.append("</table></pre></div></body></html>");
            fWriter.write(htmlBody.toString());
            fWriter.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        try {
            FileWriter fWriter = new FileWriter("c:/arte/tmp/file1.html");

            BufferedWriter out = new BufferedWriter(fWriter);

            StringBuilder sbO = new StringBuilder();
            StringBuilder sbM = new StringBuilder();



            StringBuilder htmlBody = new StringBuilder("<!DOCTYPE html><html><head><meta charset='utf-8'></head><body><div id='comparaison'><pre style=' white-space: pre-wrap; word-wrap: break-word;'> <table ><tr>");

            for (Delta d : getDeltas()) {

                htmlBody.append("<tr>");
                String color = "gray";

                if (d.getType() == TYPE.CHANGE) {
                    color = "green";

                } else if (d.getType() == TYPE.DELETE) {

                    color = "red";
                } else if (d.getType() == TYPE.INSERT) {

                    color = "orange";
                }



                htmlBody.append("<td>"+d.getOriginal().getPosition()+"</td>");

                List<String> originalLines = (List<String>)d.getOriginal().getLines();
                List<String> revisedLines = (List<String>)d.getRevised().getLines();


                for(String line : originalLines){

                    htmlBody.append(String.format("<td style='color: %s;'>", color));
                    htmlBody.append(String.format("<div style=' overflow:scroll;'>%s</div>", line));
                    htmlBody.append("</td>");
                }





                htmlBody.append("<td>"+d.getRevised().getPosition()+"</td>");
                htmlBody.append(String.format("<td style='color: %s; white-space: nowrap;'>", color));
                htmlBody.append(String.format("<div style='overflow:scroll;'>%s</div>", d.getRevised()));
                htmlBody.append("</td>");

                htmlBody.append("</tr>");

            }


            htmlBody.append("</table></pre></div></body></html>");


            out.write(htmlBody.toString());

            // Close the output stream
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Chunk> getChangesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.CHANGE);
    }

    public List<Chunk> getInsertsFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.INSERT);
    }

    public List<Chunk> getDeletesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.DELETE);
    }

    private List<Chunk> getChunksByType(Delta.TYPE type) throws IOException {
        final List<Chunk> listOfChanges = new ArrayList<Chunk>();
        final List<Delta> deltas = getDeltas();
        for (Delta delta : deltas) {
            if (delta.getType() == type) {
                listOfChanges.add(delta.getRevised());
            }
        }
        return listOfChanges;
    }

    private List<Delta> getDeltas() throws IOException {

        final Patch patch = DiffUtils.diff(url1Lines, url2Lines);

        return patch.getDeltas();

    }

    private List<String> urlToLines(String stringUrl) {

        List<String> urlLines = new ArrayList<String>();
        URL url;
        try {
            url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                urlLines.add(inputLine);
            }

            in.close();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return urlLines;

    }


    private List<String> fileToLines(File file){
        final List<String> lines = new ArrayList<String>();
        String line;
        final BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return lines;
    }
}
