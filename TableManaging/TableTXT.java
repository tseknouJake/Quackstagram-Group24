package TableManaging;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import TableManaging.Parsers.Parser;

/**
 * See Table interface for method comments
 */
public class TableTXT<T> implements Table<T>{

    private final File file;

    /**
     * the specific parser of the table object
     */
    private final Parser<T> parser;


    public TableTXT(File file, Parser<T> parser){
        this.file = file;
        this.parser = parser;
    }

    @Override
    public ArrayList<T> fetchRows(Predicate<T> condition) {
        ArrayList<T> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null && line.length() > 0){
                T row = parser.parseRow(line);
                if (condition == null || condition.test(row)) {
                    rows.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return rows;
    }

    @Override
    public void insertRow(T row, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))){
            writer.write(parser.toCSV(row));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public void updateRows(Predicate<T> condition, T newData) { 
        // get all text
        ArrayList<T> rows = fetchRows(null);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (T row : rows) {
                if (condition.test(row)) {
                    writer.write(parser.toCSV(newData));
                } else {
                    writer.write(parser.toCSV(row));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public void deleteRows(Predicate<T> condition) {
        ArrayList<T> rows = fetchRows(null);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (T row : rows) {
                if (!condition.test(row)) {
                    writer.write(parser.toCSV(row));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

  
}
