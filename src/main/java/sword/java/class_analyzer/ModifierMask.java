package sword.java.class_analyzer;

public class ModifierMask {

    private final VisibilityModifier visibility;
    private final int mMask;

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

    // When talking about class access flags, this value is ACC_SUPER.
    // Description is: Treat superclass methods specially when invoked by the invokespecial instruction.
    public boolean isSynchronized() {
        return (mMask & 0x20) != 0;
    }

    public boolean isVolatile() {
        return (mMask & 0x40) != 0;
    }

    public boolean isTransient() {
        return (mMask & 0x80) != 0;
    }

    public boolean isNative() {
        return (mMask & 0x100) != 0;
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

    /**
     * This applied to classes to specify this is declared as an annotation class.
     */
    public boolean isAnnotation() {
        return (mMask & 0x2000) != 0;
    }

    /**
     * This applies to classes to say it is declared as an enum.
     */
    public boolean isEnum() {
        return (mMask & 0x4000) != 0;
    }

    public String getVisibilityString() {
        return visibility.toString();
    }

    public String getModifiersString() {
        String result = getVisibilityString();
        if (result.length() != 0) {
            result = result + ' ';
        }

        if (isStatic()) {
            result = result + "static ";
        }

        if (isFinal()) {
            result = result + "final ";
        }

        if (isSynchronized()) {
            result = result + "synchronized ";
        }

        if (isVolatile()) {
            result = result + "volatile ";
        }

        if (isTransient()) {
            result = result + "transient ";
        }

        if (isNative()) {
            result = result + "native ";
        }

        if (isAbstract()) {
            result = result + "abstract ";
        }

        return result.substring(0, result.length() - 1);
    }
}