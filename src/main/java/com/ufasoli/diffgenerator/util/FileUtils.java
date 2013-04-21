package com.ufasoli.diffgenerator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
    public static List<String> fileToLines(File file)
    {
        List lines = new ArrayList();

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null)
            {

                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            if (in != null)
                try {
                    in.close();
                }
                catch (IOException e1) {
                    e.printStackTrace();
                }
        }
        catch (IOException e)
        {
            e.printStackTrace();

            if (in != null)
                try {
                    in.close();
                }
                catch (IOException e1) {
                    e.printStackTrace();
                }
        }
        finally
        {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return lines;
    }
}