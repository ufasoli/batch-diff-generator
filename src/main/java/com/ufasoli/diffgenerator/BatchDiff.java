package com.ufasoli.diffgenerator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ufasoli.diffgenerator.diff.compare.Comparator;
import com.ufasoli.diffgenerator.util.ApplicationConfig;
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

                ApplicationConfig applicationConfig = injector.getInstance(ApplicationConfig.class);

                for(String url : applicationConfig.getTargetFiles()){

                }

                Comparator comparator = injector.getInstance(Comparator.class);




				
			}else{
				throw new RuntimeException("The argument config is mandatory");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	


    }

   
}