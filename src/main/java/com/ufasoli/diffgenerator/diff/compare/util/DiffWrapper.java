package com.ufasoli.diffgenerator.diff.compare.util;

import difflib.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ufasoli
 * Date: 23/05/13
 * Time: 21:38
 * Project : batch-diff-generator
 */
public class DiffWrapper {


    private List<DiffRow> diffRows;

    public DiffWrapper(List<DiffRow> diffRows){
        this.diffRows = diffRows;
    }

    public List<Chunk> getChangesFromOriginal()
            throws IOException
    {
        return getChunksByType(Delta.TYPE.CHANGE);
    }

    public List<Chunk> getInsertsFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.INSERT);
    }

    public List<Chunk> getDeletesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.DELETE);
    }

    private List<Chunk> getChunksByType(Delta.TYPE type) throws IOException {
        List<Chunk> listOfChanges = new ArrayList();
        List<Delta> deltas = getDeltas();
        for (Delta delta : deltas) {
            if (delta.getType() == type) {
                listOfChanges.add(delta.getRevised());
            }
        }
        return listOfChanges;
    }

    private List<Delta> getDeltas() throws IOException
    {
        Patch patch = DiffUtils.diff(this.url1Lines, this.url2Lines);

        return patch.getDeltas();
    }

    private List<String> urlToLines(String stringUrl)
    {

        return urlUtil.urlToLines(stringUrl);
    }

    private List<String> fileToLines(File file)
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
