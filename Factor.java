import java.util.Map;

class Factor {
    boolean hasString = false;
    Core type;
    String ID;
    String s;
    int con;
    Expr e;

    void parse() {
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.ID) {
            this.ID = Parser.scanner.getId();
//            Parser.checkExist(this.ID, Parser.scope);
            Parser.scanner.nextToken();
            if (Parser.scanner.currentToken() == Core.LSQUARE) {
//                Parser.checkType(this.ID, true);
                this.hasString = true;
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.STRING);
                this.s = Parser.scanner.getString();
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.RSQUARE);
                Parser.scanner.nextToken();
            }
        } else if (this.type == Core.CONST) {
            this.con = Parser.scanner.getConst();
            Parser.scanner.nextToken();
        } else if (this.type == Core.LPAREN) {
            Parser.scanner.nextToken();
            this.e = new Expr();
            this.e.parse();
            Parser.expectedToken(Core.RPAREN);
            Parser.scanner.nextToken();
        } else {
            System.out.println(
                    "ERROR: Expected ID, CONST, or LPAREN, recieved " + this.type);
            System.exit(0);
        }
    }

    void print(int i) {
        if (this.type == Core.ID) {
            System.out.print(this.ID);
            if (this.hasString) {
                System.out.print(" [\'" + this.s + "\']");
            }
        } else if (this.type == Core.CONST) {
            System.out.print(this.con);
        } else if (this.type == Core.LPAREN) {
            System.out.print("(");
            this.e.print(i);
            System.out.print(")");
        }
    }

    int execute() {
        int retVal = 0;
        if (this.type == Core.ID) {
            if (this.hasString) {
                @SuppressWarnings("unchecked")
                Map<String, Object> t = (Map<String, Object>) Variables.getValue(this.ID);
                if (t.containsKey(this.s)) {
                    retVal = (int) t.get(this.s);
                } else {
                    System.out.println("ERROR: " + this.ID + " does not contain key \'"
                            + this.s + "\'");
                    System.exit(0);
                }
            } else {
                if (!Variables.isInt(this.ID)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> temp = (Map<String, Object>) Variables
                            .getValue(this.ID);
                    String def = Variables.defaultVals
                            .getOrDefault(Variables.getValue(this.ID), "default");
                    String test = Variables.defaultVals.get(temp);

                    retVal = (int) temp.get(def);
                } else {
                    retVal = (int) Variables.getValue(this.ID);
                }
            }
        } else if (this.type == Core.CONST) {
            retVal = this.con;
        } else if (this.type == Core.LPAREN) {
            retVal = this.e.execute();
        }
        return retVal;
    }

}
