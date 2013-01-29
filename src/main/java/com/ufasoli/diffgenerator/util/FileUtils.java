package com.ufasoli.diffgenerator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name   : FileUtils
 * Description  :
 * Date         : 28 janv. 2013
 * Copyright    : ARTE G.E.I.E.     
 * Auteur       : Ulises Fasoli
 */
public class FileUtils {

	
	public static List<String> fileToLines(File file){
		  final List<String> lines = new ArrayList<String>();
	        String line;
	        BufferedReader in = null;
	        try {
	            in = new BufferedReader(new FileReader(file));
	            while ((line = in.readLine()) != null) {
	                lines.add(line);
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace(); 
	        } catch (IOException e) {
	            e.printStackTrace(); 
	        }
	        finally{
	        	if(in != null ){
	        		try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        }


	        return lines;
	}
}
