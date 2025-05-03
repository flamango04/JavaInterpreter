import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Procedure {
    String name;
    DeclSeq ds;
    StmtSeq ss;

    void parse() {
        Parser.expectedToken(Core.PROCEDURE);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        this.name = Parser.scanner.getId();
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.IS);
        Parser.scanner.nextToken();
        if (Parser.scanner.currentToken() != Core.BEGIN) {
            this.ds = new DeclSeq();
            this.ds.parse();
        }
        Parser.expectedToken(Core.BEGIN);
        Parser.scanner.nextToken();
        this.ss = new StmtSeq();
        this.ss.parse();
        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.EOS);
    }

    void print() {
        System.out.println("procedure " + this.name + " is");
        if (this.ds != null) {
            this.ds.print(1);
        }
        System.out.println("begin ");
        this.ss.print(1);
        System.out.println("end");
    }

    void execute() {
        Variables.global = new HashMap<String, Object>();
        Variables.localStack = new Stack<Map<String, Object>>();
        Variables.defaultVals = new HashMap<Map<String, Object>, String>();
        if (this.ds != null) {
            this.ds.execute();
        }
        Variables.createNewLocal();
        this.ss.execute();
        Variables.deleteLastLocal();
        GCollector.clearAll();
    }

}
