import java.util.*;
import java.io.*;
/**
 * Base class for academic entities.
 * Stores common fields: code, name, and description.
 */
public abstract class AcademicEntity {
    protected String code;
    protected String name;
    protected String description;
    public AcademicEntity(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
