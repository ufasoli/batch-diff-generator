package com.ufasoli.diffgenerator.diff.compare.url.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.inject.Inject;
import com.ufasoli.diffgenerator.util.ApplicationConfig;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlUtil {

    private ApplicationConfig applicationConfig;

    @Inject
    public UrlUtil(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }


    public List<String> urlToLines(String stringUrl) {

        List urlLines = new ArrayList();
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                urlLines.add(inputLine);



            }

            in.close();

            if (urlLines.size() == 1) {
                FileWriter tmpFile = new FileWriter(applicationConfig.getReportsFolder() + "tmp.json");
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = (JsonNode) mapper.readValue((String) urlLines.get(0), JsonNode.class);

                ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                String formattedObject = writer.writeValueAsString(rootNode);

                tmpFile.write(formattedObject);

                tmpFile.close();
                urlLines.clear();
                urlLines.addAll(fileToLines(new File(applicationConfig.getReportsFolder() + "tmp.json")));
            }

            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlLines;
    }

    public List<String> fileToLines(File file)
    {
        List lines = new ArrayList();
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null)
            {

                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

}