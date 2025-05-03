class Decl {
    Core type;
    String ID;

    void parse() {
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.INTEGER || this.type == Core.OBJECT) {
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            this.ID = Parser.scanner.getId();
            Parser.scanner.nextToken();
//            Parser.checkNew(this.ID, this.type, Parser.scope);
//            Parser.variables.put(this.ID, this.type);
            Parser.expectedToken(Core.SEMICOLON);
            Parser.scanner.nextToken();
        } else {
            System.out
                    .println("ERROR: Expected INTEGER or OBJECT, recieved " + this.type);
            System.exit(0);
        }
    }

    void print(int i) {
        if (this.type == Core.INTEGER) {
            Parser.printSpaces(i);
            System.out.println("integer " + this.ID + ";");
        } else if (this.type == Core.OBJECT) {
            Parser.printSpaces(i);
            System.out.println("object " + this.ID + ";");
        }
    }

    void execute() {
        if (this.type == Core.INTEGER) {
            Variables.addToLocal(this.ID, 0);
        } else if (this.type == Core.OBJECT) {
            Variables.addToLocal(this.ID, null);
        }
    }

}
