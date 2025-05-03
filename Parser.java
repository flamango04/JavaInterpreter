import java.util.HashMap;
import java.util.Map;

class Parser {
    //scanner is stored here as a static field so it is available to the parse method
    public static Scanner scanner;
    public static Scanner reader;
    public static Map<String, Core> variables = new HashMap<String, Core>();
    public static Map<String, Core> scope = new HashMap<String, Core>();

    //helper method for handling error messages, used by the parse methods
    static void expectedToken(Core expected) {
        if (scanner.currentToken() != expected) {
            System.out.println("ERROR: Expected " + expected + ", recieved "
                    + scanner.currentToken());
            System.exit(0);
        }
    }

    static void printSpaces(int numIndex) {
        for (int i = 0; i < numIndex; i++) {
            System.out.print("   ");
        }
    }

//    static void checkType(String ID, boolean obj) {
//        if (obj) {
//            if (variables.get(ID) != Core.OBJECT) {
//                System.out.println("ERROR: invalid variable type, expected OBJECT, got "
//                        + variables.get(ID).toString());
//                System.exit(0);
//            }
//        } else {
//            if (variables.get(ID) != Core.INTEGER) {
//                System.out.println("ERROR: invalid variable type, expected INTEGER, got "
//                        + variables.get(ID).toString());
//                System.exit(0);
//            }
//        }
//    }

//    static void checkNew(String ID, Core type, Map<String, Core> scope) {
//        if (scope.containsKey(ID)) {
//            System.out.println("ERROR: variable " + ID + " already exists in scope.");
//            System.exit(0);
//        } else {
//            scope.put(ID, type);
//        }
//    }
//
//    static void checkExist(String ID, Map<String, Core> scope) {
//        if (!variables.containsKey(ID) && !scope.containsKey(ID)) {
//            System.out.println("ERROR: variable " + ID + " does not exist.");
//            System.exit(0);
//        }
//    }

}
