import java.util.Map;

class Loop {
    Expr e1;
    Expr e2;
    Cond c;
    StmtSeq ss;
    String ID;

    void parse() {
        Parser.expectedToken(Core.FOR);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        this.ID = Parser.scanner.getId();
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ASSIGN);
        Parser.scanner.nextToken();
        this.e1 = new Expr();
        this.e1.parse();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
        this.c = new Cond();
        this.c.parse();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
        this.e2 = new Expr();
        this.e2.parse();
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.DO);
        Parser.scanner.nextToken();
        this.ss = new StmtSeq();
        this.ss.parse();
        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();
    }

    void print(int i) {
        Parser.printSpaces(i);
        System.out.print("for (" + this.ID + " = ");
        this.e1.print(i);
        System.out.print("; ");
        this.c.print(i);
        System.out.print("; ");
        this.e2.print(i);
        System.out.println(") do");
        this.ss.print(i + 1);
        Parser.printSpaces(i);
        System.out.println("end");
    }

    void execute() {
        if (Variables.isInt(this.ID)) {
            Variables.changeValue(this.ID, this.e1.execute());
        } else {
            @SuppressWarnings("unchecked")
            Map<String, Object> temp = (Map<String, Object>) Variables.getValue(this.ID);
            String def = Variables.defaultVals.get(temp);
            Object newVal = this.e1.execute();
            temp.put(def, newVal);
            Variables.defaultVals.put(temp, def);
        }
        Variables.createNewLocal();
        while (this.c.execute()) {
            Variables.createNewLocal();
            this.ss.execute();
            Variables.deleteLastLocal();

            if (Variables.isInt(this.ID)) {
                Variables.changeValue(this.ID, this.e2.execute());
            } else {
                @SuppressWarnings("unchecked")
                Map<String, Object> temp = (Map<String, Object>) Variables
                        .getValue(this.ID);
                String def = Variables.defaultVals.get(temp);
                Object newVal = this.e2.execute();
                temp.put(def, newVal);
                Variables.defaultVals.put(temp, def);
            }

        }
        Variables.deleteLastLocal();
    }

}
