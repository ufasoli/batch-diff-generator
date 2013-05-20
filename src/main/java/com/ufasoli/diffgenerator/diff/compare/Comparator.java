package com.ufasoli.diffgenerator.diff.compare;

import difflib.DiffRow;

import java.util.List;

/**
 * Class Name   : Comparator
 * Description  :
 * Date         : 28 janv. 2013
 * Auteur       : Ulises Fasoli
 */
public interface Comparator {

	          public List<DiffRow> compare(String left, String right);
}
