import java.util.ArrayList;

class Function {
    StmtSeq ss;
    Parameter p;
    String ID;

    void parse() {
        Parser.expectedToken(Core.PROCEDURE);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        this.ID = Parser.scanner.getId();
        Variables.addProcedure(this.ID, this);
//        Variables.procedure.put(this.ID, this);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.OBJECT);
        Parser.scanner.nextToken();
        this.p = new Parameter();
        this.p.parse();
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.IS);
        Parser.scanner.nextToken();
        this.ss = new StmtSeq();
        this.ss.parse();
        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();
    }

    void print(int i) {
        Parser.printSpaces(i);
        System.out.print("procedure " + this.ID + " (object ");
        this.p.print(i);
        System.out.println(") is");
        this.ss.print(i + 1);
        Parser.printSpaces(i);
        System.out.println("end");
    }

    void execute(ArrayList<String> args) {
        GCollector.addLayer();
        ArrayList<String> params = this.p.execute();
        for (int i = 0; i < params.size(); i++) {
            String localID = params.get(i);
            Object val = Variables.getValue(args.get(i));
            Variables.changeValue(localID, val);
        }
        this.ss.execute();
        GCollector.removeLayer();
    }

}
