class Cmpr {
    Expr e1;
    Expr e2;
    Core type;

    void parse() {
        this.e1 = new Expr();
        this.e1.parse();
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.EQUAL || this.type == Core.LESS) {
            Parser.scanner.nextToken();
            this.e2 = new Expr();
            this.e2.parse();
        } else {
            System.out.println("ERROR: Expected EQUAL or LESS, recieved " + this.type);
            System.exit(0);
        }
    }

    void print(int i) {
        this.e1.print(i);
        if (this.type == Core.EQUAL) {
            System.out.print(" == ");
        } else if (this.type == Core.LESS) {
            System.out.print(" < ");
        }
        this.e2.print(i);
    }

    boolean execute() {
        boolean retVal;
        int firstExpr = this.e1.execute();
        int secondExpr = this.e2.execute();
        if (this.type == Core.EQUAL) {
            retVal = firstExpr == secondExpr;
        } else {
            retVal = firstExpr < secondExpr;
        }
        return retVal;

    }

}
