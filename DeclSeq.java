class DeclSeq {
    Decl d;
    DeclSeq ds;
    Core type;
    Function f;

    void parse() {
        if (Parser.scanner.currentToken() == Core.PROCEDURE) {
            this.f = new Function();
            this.f.parse();
        } else if (Parser.scanner.currentToken() == Core.INTEGER
                || Parser.scanner.currentToken() == Core.OBJECT) {
            this.d = new Decl();
            this.d.parse();
        } else {
            System.out.println("ERROR: Invalid Token");
            System.exit(0);
        }
        if (Parser.scanner.currentToken() == Core.INTEGER
                || Parser.scanner.currentToken() == Core.OBJECT
                || Parser.scanner.currentToken() == Core.PROCEDURE) {
            this.ds = new DeclSeq();
            this.ds.parse();
        }
    }

    void print(int i) {
        if (this.d != null) {
            this.d.print(i);
        } else if (this.f != null) {
            this.f.print(i);
        }
        if (this.ds != null) {
            this.ds.print(i);
        }
    }

    void execute() {
        if (this.d != null) {
            this.d.execute();
        } //else if (this.f != null) {
//            this.f.execute();
//        }
        if (this.ds != null) {
            this.ds.execute();
        }
    }

}
