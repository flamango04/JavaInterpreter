import java.util.HashMap;
import java.util.Map;

class Assign {
    Core type;
    String ID;
    String s;
    String CID;
    Expr e;

    void parse() {
        Parser.expectedToken(Core.ID);
        this.ID = Parser.scanner.getId();
//        Parser.checkExist(this.ID, Parser.scope);
        Parser.scanner.nextToken();
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.ASSIGN) {
            Parser.scanner.nextToken();
            if (Parser.scanner.currentToken() == Core.NEW) {
//                Parser.checkType(this.ID, true);

                Parser.scanner.nextToken();
                Parser.expectedToken(Core.OBJECT);
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.LPAREN);
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.STRING);
                this.s = Parser.scanner.getString();
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.COMMA);
                Parser.scanner.nextToken();
                this.e = new Expr();
                this.e.parse();
                Parser.expectedToken(Core.RPAREN);
                Parser.scanner.nextToken();
            } else {
                this.e = new Expr();
                this.e.parse();
            }
            Parser.expectedToken(Core.SEMICOLON);
            Parser.scanner.nextToken();
        } else if (this.type == Core.LSQUARE) {
//            Parser.checkType(this.ID, true);
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.STRING);
            this.s = Parser.scanner.getString();
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.RSQUARE);
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ASSIGN);
            Parser.scanner.nextToken();
            this.e = new Expr();
            this.e.parse();
            Parser.expectedToken(Core.SEMICOLON);
            Parser.scanner.nextToken();
        } else if (this.type == Core.COLON) {
//            Parser.checkType(this.ID, true);
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            this.CID = Parser.scanner.getId();
//            Parser.checkExist(this.CID, Parser.scope);
//            Parser.checkType(this.CID, true);
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.SEMICOLON);
            Parser.scanner.nextToken();
        } else {
            System.out.println(
                    "ERROR: Expected ASSIGN, LSQUARE, or COLON, recieved " + this.type);
            System.exit(0);
        }

    }

    void print(int i) {
        if (this.type == Core.ASSIGN) {
            if (this.s != null) {
                Parser.printSpaces(i);
                System.out.print(this.ID + " = new object(\'" + this.s + "\', ");
                this.e.print(i);
                System.out.println(");");
            } else {
                Parser.printSpaces(i);
                System.out.print(this.ID + " = ");
                this.e.print(i);
                System.out.println(";");
            }
        } else if (this.type == Core.LSQUARE) {
            Parser.printSpaces(i);
            System.out.print(this.ID + "[\'" + this.s + "\'] = ");
            this.e.print(i);
            System.out.println(";");
        } else if (this.type == Core.COLON) {
            Parser.printSpaces(i);
            System.out.println(this.ID + " = " + this.CID + ";");
        }
    }

    void execute() {
        if (this.type == Core.ASSIGN) {
            if (this.s != null) {
                Map<String, Object> n = new HashMap<String, Object>();
                GCollector.addVariable();
                n.put(this.s, this.e.execute());
                Variables.changeValue(this.ID, n);
                Variables.defaultVals.put(n, this.s);
            } else {
                if (Variables.isInt(this.ID)) {
                    Variables.changeValue(this.ID, this.e.execute());
                } else {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> temp = (Map<String, Object>) Variables
                            .getValue(this.ID);
                    String def = Variables.defaultVals.get(temp);
                    Object newVal = this.e.execute();
                    temp.put(def, newVal);
                    Variables.defaultVals.put(temp, def);
                }
            }
        } else if (this.type == Core.LSQUARE) {
            assert !Variables.isInt(this.ID);
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) Variables.getValue(this.ID);
            map.put(this.s, this.e.execute());
        } else if (this.type == Core.COLON) {
            Variables.changeValue(this.ID, Variables.getValue(this.CID));
            if (this.CID.equals("x")) {
                GCollector.removeVariable();
            }
        }

    }

}
