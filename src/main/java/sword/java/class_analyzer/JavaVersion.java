package sword.java.class_analyzer;

public enum JavaVersion {

    JDK1_1 {
        @Override
        public String toString() {
            return "JDK 1.1";
        }
    },

    JDK1_2 {
        @Override
        public String toString() {
            return "JDK 1.2";
        }
    },

    JDK1_3 {
        @Override
        public String toString() {
            return "JDK 1.3";
        }
    },

    JDK1_4 {
        @Override
        public String toString() {
            return "JDK 1.4";
        }
    },

    J2SE5_0 {
        @Override
        public String toString() {
            return "J2SE 5.0";
        }
    },

    J2SE6_0 {
        @Override
        public String toString() {
            return "J2SE 6.0";
        }
    },

    J2SE7_0 {
        @Override
        public String toString() {
            return "J2SE 7.0";
        }
    };

    private JavaVersion() { }

    public static JavaVersion get(int majorVersion) {
        switch (majorVersion) {
        case 0x2D:
            return JDK1_1;
        case 0x2E:
            return JDK1_2;
        case 0x2F:
            return JDK1_3;
        case 0x30:
            return JDK1_4;
        case 0x31:
            return J2SE5_0;
        case 0x32:
            return J2SE6_0;
        case 0x33:
            return J2SE7_0;
        }

        throw new IllegalArgumentException();
    }
}
