package sword.java.class_analyzer;

public class ClassModifierMask extends ModifierMask {

    private final ClassTypeModifier classType;

    public ClassModifierMask(int mask) {
        super(mask);

        final int enabledFlags =
            ((isInterface())? 1 : 0) +
            ((isAnnotation())? 1 : 0) +
            ((isEnum())? 1 : 0);

        if (enabledFlags == 0) {
            classType = ClassTypeModifier.CLASS;
        }
        else if (enabledFlags == 1) {
            if (isInterface()) {
                classType = ClassTypeModifier.INTERFACE;
            }
            else if (isAnnotation()) {
                classType = ClassTypeModifier.ANNOTATION;
            }
            else { //enum
                classType = ClassTypeModifier.ENUM;
            }
        }
        else {
            throw new IllegalArgumentException("Wrong class type provided in the mask");
        }
    }

    /**
     * Whether this class treats superclass methods specially when invoked by
     * the invokespecial instruction. (ACC_SUPER)
     */
    public boolean accessesSuper() {
        return (mMask & 0x20) != 0;
    }

    public boolean isInterface() {
        return (mMask & 0x0200) != 0;
    }

    public boolean isAnnotation() {
        return (mMask & 0x2000) != 0;
    }

    public boolean isEnum() {
        return (mMask & 0x4000) != 0;
    }

    @Override
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

        if (isAbstract()) {
            result = result + "abstract ";
        }

        return result + classType.toString();
    }
}
