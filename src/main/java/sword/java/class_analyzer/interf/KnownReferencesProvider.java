package sword.java.class_analyzer.interf;

import java.util.Set;

import sword.java.class_analyzer.pool.AbstractMethodEntry;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.FieldEntry;

public interface KnownReferencesProvider {

    /**
     * Returns a set of method entries that this piece of code uses to invoke methods.
     * In case no method can be called an empty set will be returned instead.
     */
    public Set<AbstractMethodEntry> getKnownInvokedMethods();

    /**
     * Returns a set of field entries that this piece of code uses to read or write.
     * In case no field is referenced, an empty set will be returned instead.
     */
    public Set<FieldEntry> getKnownReferencedFields();

    /**
     * Returns all class reference entries that this piece of code is accessing
     * in the way "T.class", obtaining an instance of Class<T>
     */
    public Set<ClassReferenceEntry> getKnownReflectionClassReferences();
}
