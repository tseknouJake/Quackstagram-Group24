package TableManaging.Parsers;

/**
 * Class used to translate from parameter objects to CSV and back
 */
public interface Parser<T> {

    /**
     * Translates from a CSVline to a Parameter Object
     * @param csvLine
     * @return the Parameter Object associated with the parser
     */
    T parseRow (String csvLine);

    /**
     * Translates from a Parameter Object to a CSVline
     * @param row
     * @return A CSVline of the object
     */
    String toCSV (T row);
}
