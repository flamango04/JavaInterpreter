import java.util.ArrayList;

class Call {
    Parameter p;
    String ID;

    void parse() {
        Parser.expectedToken(Core.BEGIN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        this.ID = Parser.scanner.getId();
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        this.p = new Parameter();
        this.p.parse();
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();

    }

    void print(int i) {
        Parser.printSpaces(i);
        System.out.print("begin " + this.ID + "(");
        this.p.print(i);
        System.out.println(");");
    }

    void execute() {
        if (!Variables.procedure.containsKey(this.ID)) {
            System.out.println("ERROR: Procedure does not exist");
            System.exit(0);
        }
//        Variables.createFrame();
        ArrayList<String> params = this.p.execute();
        Function f = Variables.procedure.get(this.ID);
        f.execute(params);
//        Variables.deleteFrame();
    }

}
