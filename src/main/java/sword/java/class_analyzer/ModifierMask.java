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

    public boolean isFinal() {
        return (mMask & 0x10) != 0;
    }

    public boolean isAbstract() {
        return (mMask & 0x400) != 0;
    }

    public String getVisibilityString() {
        return visibility.toString();
    }

    public String getModifiersString() {
        String result = getVisibilityString();
        if (result.length() != 0) {
            result = result + ' ';
        }

        if (isFinal()) {
            result = result + "final ";
        }

        if (isAbstract()) {
            result = result + "abstract ";
        }

        return result.substring(0, result.length() - 1);
    }
}
