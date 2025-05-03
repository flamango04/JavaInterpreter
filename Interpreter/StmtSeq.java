class StmtSeq {
    Stmt s;
    StmtSeq ss;
    Core type;

    void parse() {
        this.s = new Stmt();
        this.s.parse();
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.ID || this.type == Core.IF || this.type == Core.FOR
                || this.type == Core.PRINT || this.type == Core.READ
                || this.type == Core.INTEGER || this.type == Core.OBJECT
                || this.type == Core.BEGIN) {
            this.ss = new StmtSeq();
            this.ss.parse();
        }
    }

    void print(int i) {
        this.s.print(i);
        if (this.ss != null) {
            this.ss.print(i);
        }
    }

    void execute() {
        this.s.execute();
        if (this.ss != null) {
            this.ss.execute();
        }
    }

}
