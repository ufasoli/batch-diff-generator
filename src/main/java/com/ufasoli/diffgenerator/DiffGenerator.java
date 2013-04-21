package com.ufasoli.diffgenerator;

import com.ufasoli.diffgenerator.diff.compare.url.UrlComparattor;
import com.ufasoli.diffgenerator.util.FileUtils;
import org.apache.commons.cli.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Class Name   : DiffGenerator
 * Description  :
 * Date         : 17 janv. 2013
 * Auteur       : Ulises Fasoli
 */
public class DiffGenerator
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

                List<String> urls = FileUtils.fileToLines(new File(config + "urls.txt"));
                Properties applicationproperties = new Properties();
                applicationproperties.load(new FileInputStream(new File(config + "config.properties")));

                for (String url : urls)
                {
                    String leftUrl = applicationproperties.getProperty("left.base.url") + url;
                    String rightUrl = applicationproperties.getProperty("right.base.url") + url;
                    UrlComparattor comparator = new UrlComparattor(leftUrl, rightUrl, url.substring(1, url.length()).replace("/", "_").replace("?pretty=true", ""), applicationproperties.getProperty("reports.output.folder"));

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


    }
}
