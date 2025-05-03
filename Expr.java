class Expr {
    Core type;
    String ID;
    Term t;
    Expr e;

    void parse() {
        this.t = new Term();
        this.t.parse();
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.ADD || this.type == Core.SUBTRACT) {
            Parser.scanner.nextToken();
            this.e = new Expr();
            this.e.parse();
        }

    }

    void print(int i) {
        this.t.print(i);
        if (this.e != null) {
            if (this.type == Core.ADD) {
                System.out.print(" + ");
            } else {
                System.out.print(" - ");
            }
            this.e.print(i);
        }
    }

    int execute() {
        int retVal = this.t.execute();
        if (this.e != null) {
            if (this.type == Core.ADD) {
                retVal += this.e.execute();
            } else if (this.type == Core.SUBTRACT) {
                retVal -= this.e.execute();
            }
        }
        return retVal;
    }

}
