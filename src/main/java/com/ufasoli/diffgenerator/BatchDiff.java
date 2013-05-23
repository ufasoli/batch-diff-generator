package com.ufasoli.diffgenerator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ufasoli.diffgenerator.diff.compare.Comparator;
import org.apache.commons.cli.*;

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

                List<String> urls =

                Comparator comparator = injector.getInstance(Comparator.class);


				
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