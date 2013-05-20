package com.ufasoli.diffgenerator.diff.compare.url.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ufasoli.diffgenerator.diff.compare.url.UrlComparator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlUtil {
    private final UrlComparator urlComparator;

    public UrlUtil(UrlComparator urlComparator) {
        this.urlComparator = urlComparator;
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
                FileWriter tmpFile = new FileWriter(urlComparator.getOutputFolder() + "tmp.json");
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = (JsonNode) mapper.readValue((String) urlLines.get(0), JsonNode.class);

                ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                String formattedObject = writer.writeValueAsString(rootNode);

                tmpFile.write(formattedObject);

                tmpFile.close();
                urlLines.clear();
                urlLines.addAll(urlComparator.fileToLines(new File(urlComparator.getOutputFolder() + "tmp.json")));
            }

            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlLines;
    }
}