import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Scanner {
    Set<String> keywords = new HashSet<>();
    Map<Character, String> symbols = new HashMap<>();
    BufferedReader in;
    String token = "";
    Core t;

    // Initialize the scanner
    Scanner(String filename) {
        try {
            this.in = new BufferedReader(new FileReader(filename));
        } catch (IOException e) {
            System.out.print("Error opening file");
            System.exit(0);
        }

        // Generate data structures
        this.generateSymbols();
        this.generateKeywords();
        this.nextToken();
    }

    // Advance to the next token
    public void nextToken() {
        this.token = "";
        try {
            // Skip to the first non-whitespace character
            char currChar = ' ';
            while (Character.isWhitespace(currChar) || currChar == '\n') {
                int intCurr = this.in.read();
                // Check if at EOS
                if (intCurr == -1) {
                    this.t = Core.EOS;
                    return;
                }
                currChar = (char) intCurr;
            }
            // Case 1: Is String
            if (currChar == '\'') {
                this.t = Core.STRING;
                currChar = (char) this.in.read();
                // Read until next "
                while (currChar != '\'') {
                    this.token += currChar;
                    currChar = (char) this.in.read();
                }
            } // Case 2: Is ID or Keyword
            else if (Character.isLetter(currChar)) {
                while (Character.isDigit(currChar) || Character.isLetter(currChar)) {
                    this.token += currChar;
                    this.in.mark(1);
                    currChar = (char) this.in.read();
                }
                this.in.reset();
                // If token exists in keyword set, it is a keyword
                // Else it is an ID
                if (this.keywords.contains(this.token)) {
                    this.t = Core.valueOf(this.token.toUpperCase());
                } else {
                    this.t = Core.ID;
                }
            } // Case 3: Is Constant
            else if (Character.isDigit(currChar)) {
                this.t = Core.CONST;
                while (Character.isDigit(currChar)) {
                    this.token += currChar;
                    this.in.mark(1);
                    currChar = (char) this.in.read();
                }
                this.in.reset();
                // Check Constant within range
                if (this.token.length() > 7 || Integer.parseInt(this.token) > 1000003) {
                    this.t = Core.ERROR;
                    System.out.print("ERROR: Constant integer value too large");
                    System.exit(0);
                } else {
                    this.t = Core.CONST;
                }
            } // Case 4: Is Symbol
            else if (this.symbols.containsKey(currChar)) {
                // Check if it's equal or assign
                if (currChar == '=') {
                    this.in.mark(1);
                    if (this.in.read() == '=') {
                        this.t = Core.EQUAL;
                    } else {
                        this.in.reset();
                        this.t = Core.ASSIGN;
                    }
                } else {
                    this.t = Core.valueOf(this.symbols.get(currChar));
                }
            } // Case 5: Invalid Token
            else {
                this.t = Core.ERROR;
                System.out
                        .print("ERROR: Invalid Token - Check Invalid symbol or spelling");
                System.exit(0);
            }
        } catch (IOException e) {
            this.t = Core.ERROR;
            System.out.print("ERROR: IOException - Error reading token");
            System.exit(0);
        }
    }

    // Return the current token
    public Core currentToken() {
        return this.t;
    }

    // Return the identifier string
    public String getId() {
        return this.token;
    }

    // Return the constant value
    public int getConst() {
        return Integer.parseInt(this.token);
    }

    // Return the character string
    public String getString() {
        return this.token;
    }

    // Generate a Map of Symbols
    private void generateSymbols() {
        this.symbols.put('+', "ADD");
        this.symbols.put('-', "SUBTRACT");
        this.symbols.put('*', "MULTIPLY");
        this.symbols.put('/', "DIVIDE");
        this.symbols.put('=', "ASSIGN");
        this.symbols.put('<', "LESS");
        this.symbols.put(':', "COLON");
        this.symbols.put(';', "SEMICOLON");
        this.symbols.put('.', "PERIOD");
        this.symbols.put(',', "COMMA");
        this.symbols.put('(', "LPAREN");
        this.symbols.put(')', "RPAREN");
        this.symbols.put('[', "LSQUARE");
        this.symbols.put(']', "RSQUARE");
        this.symbols.put('{', "LCURL");
        this.symbols.put('}', "RCURL");
    }

    // Generate a Set of Keywords
    private void generateKeywords() {
        this.keywords.add("and");
        this.keywords.add("begin");
        this.keywords.add("case");
        this.keywords.add("do");
        this.keywords.add("else");
        this.keywords.add("end");
        this.keywords.add("for");
        this.keywords.add("if");
        this.keywords.add("in");
        this.keywords.add("integer");
        this.keywords.add("is");
        this.keywords.add("new");
        this.keywords.add("not");
        this.keywords.add("object");
        this.keywords.add("or");
        this.keywords.add("print");
        this.keywords.add("procedure");
        this.keywords.add("read");
        this.keywords.add("return");
        this.keywords.add("then");
    }
}
