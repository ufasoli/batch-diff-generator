package com.ufasoli.diffgenerator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ufasoli.diffgenerator.diff.compare.Comparator;
import com.ufasoli.diffgenerator.util.FileUtils;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;

/**
 * Class Name   : BatchDiff
 * Description  :
 * Date         : 17 janv. 2013
 * Auteur       : Ulises Fasoli
 */
public class BatchDiff {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    	Options options = new Options();
    	options.addOption("c", "config", true, "Folder where all the configuration files can be found");
    	CommandLineParser parser = new PosixParser();
    	try {
			CommandLine line = parser.parse(options, args);
			
			if(line.hasOption("config") || line.hasOption("c")){

				String config = line.getOptionValue("config") == null ? line.getOptionValue("c") :  line.getOptionValue("config");

                Injector injector = Guice.createInjector(new Bootstrapper(config));

                Comparator c = injector.getInstance(Comparator.class);

				
				List<String> urls = FileUtils.fileToLines(new File(config + "urls.txt"));

			/*
				for(String url :urls){

					String leftUrl = applicationproperties.getProperty("left.base.url")+url;
					String rightUrl = applicationproperties.getProperty("right.base.url")+url;
					UrlComparator comparator = new UrlComparator(leftUrl, rightUrl, url.substring(1, url.length()).replace("/", "_").replace("?pretty=true", ""), applicationproperties.getProperty("reports.output.folder"));
//					UrlComparator comparator = new UrlComparator("http://localhost:8080/tvguide/epg/broadcast/F/L2/1446714_044750-006.json?pretty=true", "http://localhost:8080/tvguide/epg/broadcast/F/L3/1446714_044750-006.json?pretty=true", url.substring(1, url.length()).replace("/", "_").replace("?pretty=true", ""), applicationproperties.getProperty("reports.output.folder"));
					comparator.compare();
				}
			*/
				
			}else{
				throw new RuntimeException("The argument config is mandatory");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
//        String url1 = "http://arte-epg-apipreprod.sdv.fr/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json";
        String url2 = "http://localhost:8080/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json?pretty=true";
        String url1 = "http://localhost:8080/tvguide/epg/schedule/F/L2/2013-01-18/2013-01-18.json?pretty=true";
       
//        UrlComparator urlDiffCompare = new UrlComparator(url1, url2);

    }

   
}