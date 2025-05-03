import java.util.ArrayList;

class Parameter {
    String ID;
    Parameter p;

    void parse() {
        this.ID = Parser.scanner.getId();
        Parser.scanner.nextToken();
        if (Parser.scanner.currentToken() == Core.COMMA) {
            Parser.scanner.nextToken();
            this.p = new Parameter();
            this.p.parse();
        }
    }

    void print(int i) {
        System.out.print(this.ID);
        if (this.p != null) {
            System.out.print(", ");
            this.p.print(i);
        }
    }

    ArrayList<String> execute() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(this.ID);
        if (this.p != null) {
            ArrayList<String> appen = this.p.execute();
            for (String s : appen) {
                if (ret.contains(s)) {
                    System.out.println("ERROR: Multiple Parameters with the Same Name");
                    System.exit(0);
                }
            }
            ret.addAll(appen);
        }
        return ret;
    }

}
