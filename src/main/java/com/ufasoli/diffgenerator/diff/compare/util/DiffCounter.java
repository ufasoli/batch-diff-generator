package com.ufasoli.diffgenerator.diff.compare.util;

import difflib.DiffRow;

/**
 * User: ufasoli
 * Date: 25/05/13
 * Time: 18:57
 * Project : batch-diff-generator
 */
public class DiffCounter {

    private int changes = 0;
    private int deleted = 0;
    private int inserted = 0;
    private int equal = 0;


    public void increase(DiffRow.Tag tag) {

        switch (tag) {

            case CHANGE:
                increaseChanges();
                break;

            case DELETE:
                increaseDeleted();

                break;
            case EQUAL:
                increaseEqual();
                break;

            case INSERT:
                increaseInserted();
                break;

        }
    }

    public void increaseChanges() {
        changes += 1;
    }


    public void increaseDeleted() {
        changes += 1;
    }


    public void increaseInserted() {
        changes += 1;
    }

    public void increaseEqual() {
        changes += 1;
    }


    public int getChanges() {
        return changes;
    }

    public void setChanges(int changes) {
        this.changes = changes;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getInserted() {
        return inserted;
    }

    public void setInserted(int inserted) {
        this.inserted = inserted;
    }

    public int getEqual() {
        return equal;
    }

    public void setEqual(int equal) {
        this.equal = equal;
    }
}
