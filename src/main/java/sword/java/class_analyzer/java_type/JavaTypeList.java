package sword.java.class_analyzer.java_type;

import sword.java.class_analyzer.independent_type.JavaType;

public class JavaTypeList extends JavaType {

    private final JavaType mJavaTypes[];

    JavaTypeList() {
        mJavaTypes = new JavaType[0];
    }

    JavaTypeList(JavaType javaType) {
        int listSize = 1;
        if (javaType instanceof JavaTypeList) {
            listSize = ((JavaTypeList) javaType).mJavaTypes.length;
        }

        mJavaTypes = new JavaType[listSize];

        if (javaType instanceof JavaTypeList) {
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

        if (first instanceof JavaTypeList) {
            listSize += ((JavaTypeList) first).mJavaTypes.length - 1;
        }

        if (last instanceof JavaTypeList) {
            listSize += ((JavaTypeList) last).mJavaTypes.length - 1;
        }

        mJavaTypes = new JavaType[listSize];

        int index = 0;

        if (first instanceof JavaTypeList) {
            final JavaType list[] = ((JavaTypeList) first).mJavaTypes;
            final int elements = list.length;

            for (int i=0; i<elements; i++) {
                mJavaTypes[index++] = list[i];
            }
        }
        else {
            mJavaTypes[index++] = first;
        }

        if (last instanceof JavaTypeList) {
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
