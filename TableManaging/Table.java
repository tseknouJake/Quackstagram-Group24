package TableManaging;

import java.util.ArrayList;
import java.util.function.Predicate;


/**
 * The general interface of a table (no matter how the data is stored)
 * T - the ParameterClass object associated with the table
 */
public interface Table<T> {
    
    /**
     * Fetches all rows from table that match the condition
     * @param condition - if null, fetches all rows
     * @return - an ArrayList of objects T created from rows that match the condition (or empty array if none do)
     */
    public ArrayList<T> fetchRows(Predicate <T> condition);

    /**
     * Insert a row into the table
     * @param row  - a T object
     * @param append - if false : overrites existing text, if true : adds
     */
    public void insertRow(T row, boolean append);

    /**
     * Updates all rows that match a condition
     * @param condition - if null, updates all rows
     * @param newData - a T object
     */
    public void updateRows(Predicate<T> condition, T newData);

    /**
     * Delete all rows that match a condition
     * @param condition - if null, deletes all rows
     */
    public void deleteRows(Predicate<T> condition);
    
}
