package sword.java.class_analyzer;

public abstract class ModifierMask {

    private final VisibilityModifier visibility;
    protected final int mMask;

    public ModifierMask(int mask) {
        mMask = mask;

        switch (mask & 7) {
        case 0:
            visibility = VisibilityModifier.PACKAGE;
            break;

        case 1:
            visibility = VisibilityModifier.PUBLIC;
            break;

        case 2:
            visibility = VisibilityModifier.PRIVATE;
            break;

        case 4:
            visibility = VisibilityModifier.PROTECTED;
            break;

        default:
            throw new IllegalArgumentException("Wrong visibility provided in the mask");
        }
    }

    public boolean isStatic() {
        return (mMask & 0x08) != 0;
    }

    public boolean isFinal() {
        return (mMask & 0x10) != 0;
    }

    public boolean isAbstract() {
        return (mMask & 0x400) != 0;
    }

    public boolean isStrict() {
        return (mMask & 0x800) != 0;
    }

    /**
     * Synthetic applies to classes, and it means this class is not present in
     * the source code and it is generated on the fly.
     */
    public boolean isSynthetic() {
        return (mMask & 0x1000) != 0;
    }

    public String getVisibilityString() {
        return visibility.toString();
    }

    /**
     * Returns the prefix to be displayed in front of either a class or member
     * in the expected order following the Java syntax.
     */
    public abstract String getModifiersString();
}
