package sword.java.class_analyzer;

public enum VisibilityModifier {

    PUBLIC {
        @Override
        public String toString() {
            return "public";
        }
    },

    PROTECTED {
        @Override
        public String toString() {
            return "protected";
        }
    },

    PACKAGE {
        @Override
        public String toString() {
            return "";
        }
    },

    PRIVATE {
        @Override
        public String toString() {
            return "private";
        }
    };

    @Override
    public abstract String toString();
}
