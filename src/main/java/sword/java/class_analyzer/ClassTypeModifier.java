package sword.java.class_analyzer;

public enum ClassTypeModifier {

    CLASS {
        @Override
        public String toString() {
            return "class";
        }
    },

    INTERFACE {
        @Override
        public String toString() {
            return "interface";
        }
    },

    ANNOTATION {
        @Override
        public String toString() {
            return "annotation";
        }
    },

    ENUM {
        @Override
        public String toString() {
            return "enum";
        }
    }
}
