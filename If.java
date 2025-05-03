import java.util.HashMap;

class If {
    Cond c;
    StmtSeq s;
    StmtSeq s2;

    void parse() {
        Parser.scope = new HashMap<String, Core>();
        Parser.expectedToken(Core.IF);
        Parser.scanner.nextToken();
        this.c = new Cond();
        this.c.parse();
        Parser.expectedToken(Core.THEN);
        Parser.scanner.nextToken();
        this.s = new StmtSeq();
        this.s.parse();

        if (Parser.scanner.currentToken() == Core.ELSE) {
            Parser.scanner.nextToken();
            this.s2 = new StmtSeq();
            this.s2.parse();
        }
        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();
        Parser.scope = new HashMap<String, Core>();
    }

    void print(int i) {
        Parser.printSpaces(i);
        System.out.print("if ");
        this.c.print(i);
        System.out.println(" then");
        this.s.print(i + 1);
        if (this.s2 != null) {
            Parser.printSpaces(i);
            System.out.println("else ");
            this.s2.print(i + 1);
        }
        Parser.printSpaces(i);
        System.out.println("end");

    }

    void execute() {
        if (this.c.execute()) {
            Variables.createNewLocal();
            this.s.execute();
            Variables.deleteLastLocal();
        } else if (this.s2 != null) {
            Variables.createNewLocal();
            this.s2.execute();
            Variables.deleteLastLocal();
        }
    }

}
