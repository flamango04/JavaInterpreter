import java.util.Map;

class Read {
    String ID;

    void parse() {
        Parser.expectedToken(Core.READ);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        this.ID = Parser.scanner.getId();
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print(int index) {
        Parser.printSpaces(index);
        System.out.println("read(" + this.ID + ");");
    }

    void execute() {
        if (!Parser.reader.currentToken().equals(Core.EOS)) {
            if (Variables.isInt(this.ID)) {
                Variables.changeValue(this.ID, Parser.reader.getConst());
            } else {
                @SuppressWarnings("unchecked")
                Map<String, Object> temp = (Map<String, Object>) Variables
                        .getValue(this.ID);
                String def = Variables.defaultVals.get(temp);
                temp.put(def, Parser.reader.getConst());
                Variables.defaultVals.put(temp, def);
            }
            Parser.reader.nextToken();
        } else {
            System.out.println("ERROR: file out of data");
            System.exit(0);
        }
    }

}
