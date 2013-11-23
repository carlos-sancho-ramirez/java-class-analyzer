package sword.java.class_analyzer.java_type;

public class JavaTypeList extends JavaType {

    private final JavaType mJavaTypes[];

    JavaTypeList() {
        mJavaTypes = new JavaType[0];
    }

    JavaTypeList(JavaType javaType) {
        int listSize = 1;
        if (javaType.isTypeList()) {
            listSize = ((JavaTypeList) javaType).mJavaTypes.length;
        }

        mJavaTypes = new JavaType[listSize];

        if (javaType.isTypeList()) {
            final JavaType list[] = ((JavaTypeList) javaType).mJavaTypes;
            final int elements = list.length;

            for (int i=0; i<elements; i++) {
                mJavaTypes[i] = list[i];
            }
        }
        else {
            mJavaTypes[0] = javaType;
        }
    }

    JavaTypeList(JavaType first, JavaType last) {
        int listSize = 2;

        if (first.isTypeList()) {
            listSize += ((JavaTypeList) first).mJavaTypes.length - 1;
        }

        if (last.isTypeList()) {
            listSize += ((JavaTypeList) last).mJavaTypes.length - 1;
        }

        mJavaTypes = new JavaType[listSize];

        int index = 0;

        if (first.isTypeList()) {
            final JavaType list[] = ((JavaTypeList) first).mJavaTypes;
            final int elements = list.length;

            for (int i=0; i<elements; i++) {
                mJavaTypes[index++] = list[i];
            }
        }
        else {
            mJavaTypes[index++] = first;
        }

        if (last.isTypeList()) {
            final JavaType list[] = ((JavaTypeList) last).mJavaTypes;
            final int elements = list.length;

            for (int i=0; i<elements; i++) {
                mJavaTypes[index++] = list[i];
            }
        }
        else {
            mJavaTypes[index++] = last;
        }
    }

    @Override
    public String signature() {
        String result = "";
        for (JavaType javaType : mJavaTypes) {
            result = result + javaType.signature();
        }

        return result;
    }

    @Override
    boolean isTypeList() {
        return true;
    }

    @Override
    public String getJavaRepresentation() {
        String result = "";
        final int count = mJavaTypes.length;
        for (int i=0; i<count; i++) {
            result = result + mJavaTypes[i];

            if (i < count - 1) {
                result = result + ", ";
            }
        }

        return result;
    }

    public JavaType[] toArray() {
        return mJavaTypes;
    }
}
