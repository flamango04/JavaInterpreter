class Cond {
    Core type;
    String ID;
    Cond c;
    Cmpr cm;
    Core orand;

    void parse() {
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.NOT) {
            Parser.scanner.nextToken();
            this.c = new Cond();
            this.c.parse();
        } else if (this.type == Core.LSQUARE) {
            Parser.scanner.nextToken();
            this.c = new Cond();
            this.c.parse();
            Parser.expectedToken(Core.RSQUARE);
            Parser.scanner.nextToken();
        } else {
            this.cm = new Cmpr();
            this.cm.parse();
            if (Parser.scanner.currentToken() == Core.OR
                    || Parser.scanner.currentToken() == Core.AND) {
                this.orand = Parser.scanner.currentToken();
                Parser.scanner.nextToken();
                this.c = new Cond();
                this.c.parse();
            }
        }
    }

    void print(int i) {
        if (this.type == Core.NOT) {
            System.out.print("not ");
            this.c.print(i);
        } else if (this.type == Core.LSQUARE) {
            System.out.print("[");
            this.c.print(i);
            System.out.print("]");
        } else {
            this.cm.print(i);
            if (this.c != null) {
                System.out.print(" " + this.orand.toString().toLowerCase() + " ");
                this.c.print(i);
            }
        }
    }

    boolean execute() {
        boolean retVal;
        if (this.type == Core.NOT) {
            retVal = !this.c.execute();
        } else if (this.type == Core.LSQUARE) {
            retVal = this.c.execute();
        } else {
            retVal = this.cm.execute();
            if (this.c != null) {
                if (this.orand.equals(Core.AND)) {
                    retVal = retVal && this.c.execute();
                } else if (this.orand.equals(Core.OR)) {
                    retVal = retVal || this.c.execute();
                }
            }
        }
        return retVal;
    }

}
