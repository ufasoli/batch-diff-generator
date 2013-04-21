package com.ufasoli.diffgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.ufasoli.diffgenerator.diff.compare.url.UrlComparattor;

/**
 * Class Name   : DiffCompare
 * Description  :
 * Date         : 17 janv. 2013
 * Auteur       : Ulises Fasoli
 */
public class DiffCompare {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    	Options options = new Options();
    	options.addOption("config", true, "Folder where all the configuration files can be found");
    	CommandLineParser parser = new PosixParser();
    	try {
			CommandLine line = parser.parse(options, args);
			
			if(line.hasOption("config")){
				
				String config = line.getOptionValue("config");
				
				
				
				List<String> urls = FileUtils.fileToLines(new File(config+"urls.txt"));
				Properties applicationproperties = new Properties();
				applicationproperties.load(new FileInputStream(new File(config+"config.properties")));
				
				for(String url :urls){
					
					String leftUrl = applicationproperties.getProperty("left.base.url")+url;
					String rightUrl = applicationproperties.getProperty("right.base.url")+url; 
					UrlComparattor comparator = new UrlComparattor(leftUrl, rightUrl, url.substring(1, url.length()).replace("/", "_").replace("?pretty=true", ""), applicationproperties.getProperty("reports.output.folder"));
//					UrlComparattor comparator = new UrlComparattor("http://localhost:8080/tvguide/epg/broadcast/F/L2/1446714_044750-006.json?pretty=true", "http://localhost:8080/tvguide/epg/broadcast/F/L3/1446714_044750-006.json?pretty=true", url.substring(1, url.length()).replace("/", "_").replace("?pretty=true", ""), applicationproperties.getProperty("reports.output.folder"));
					comparator.compare();
				}
				
				
			}else{
				throw new RuntimeException("The argument config is mandatory");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
//        String url1 = "http://arte-epg-apipreprod.sdv.fr/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json";
        String url2 = "http://localhost:8080/tvguide/epg/schedule/F/L2/2013-01-19/2013-01-19.json?pretty=true";
        String url1 = "http://localhost:8080/tvguide/epg/schedule/F/L2/2013-01-18/2013-01-18.json?pretty=true";
       
//        UrlComparattor urlDiffCompare = new UrlComparattor(url1, url2);

    }

   
}