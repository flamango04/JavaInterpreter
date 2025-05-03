class Term {
    Factor f;
    Term t;
    Core type;
    String ID;

    void parse() {
        this.f = new Factor();
        this.f.parse();
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.MULTIPLY || this.type == Core.DIVIDE) {
            Parser.scanner.nextToken();
            this.t = new Term();
            this.t.parse();
        }
    }

    void print(int i) {
        this.f.print(i);
        if (this.t != null) {
            if (this.type == Core.MULTIPLY) {
                System.out.print(" * ");
            } else if (this.type == Core.DIVIDE) {
                System.out.print(" / ");
            }
            this.t.print(i);
        }
    }

    int execute() {
        int retVal = this.f.execute();
        if (this.t != null) {
            if (this.type == Core.MULTIPLY) {
                retVal *= this.t.execute();
            } else if (this.type == Core.DIVIDE) {
                int div = this.t.execute();
                if (div == 0) {
                    System.out.print("ERROR: Division by 0.");
                    System.exit(0);
                } else {
                    retVal /= div;
                }
            }
        }
        return retVal;
    }

}
