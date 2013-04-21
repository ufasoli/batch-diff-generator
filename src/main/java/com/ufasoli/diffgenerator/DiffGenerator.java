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
 * Auteur       : Ulises Fasoli
 */
public class DiffCompare
{
    public static void main(String[] args)
    {
        Options options = new Options();
        options.addOption("config", true, "Folder where all the configuration files can be found");
        CommandLineParser parser = new PosixParser();
        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("config"))
            {
                String config = line.getOptionValue("config");

                List urls = FileUtils.fileToLines(new File(config + "urls.txt"));
                Properties applicationproperties = new Properties();
                applicationproperties.load(new FileInputStream(new File(config + "config.properties")));

                for (String url : urls)
                {
                    String leftUrl = applicationproperties.getProperty("left.base.url") + url;
                    String rightUrl = applicationproperties.getProperty("right.base.url") + url;
                    UrlDiffCompare comparator = new UrlDiffCompare(leftUrl, rightUrl, url.substring(1, url.length()).replace("/", "_").replace("?pretty=true", ""), applicationproperties.getProperty("reports.output.folder"));

                    comparator.compare();
                }
            }
            else
            {
                throw new RuntimeException("The argument config is mandatory");
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String url2 = "http://localhost:8080/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json?pretty=true";
        String url1 = "http://localhost:8080/tvguide/epg/schedule/F/L2/2013-01-18/2013-01-18.json?pretty=true";
    }
}
