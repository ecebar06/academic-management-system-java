import java.io.FileWriter;
import java.io.IOException;
/**
 * Interface for objects that can generate formatted reports.
 * Implementing classes must define how their data is written to a FileWriter.
 */
public interface Reportable {
    void generateReport(FileWriter writer) throws IOException;


}
