class Stmt {
    Core type;
    Assign a;
    If i;
    Loop l;
    Print p;
    Read r;
    Decl d;
    Call c;

    void parse() {
        this.type = Parser.scanner.currentToken();
        if (this.type == Core.ID) {
            this.a = new Assign();
            this.a.parse();
        } else if (this.type == Core.IF) {
            this.i = new If();
            this.i.parse();
        } else if (this.type == Core.FOR) {
            this.l = new Loop();
            this.l.parse();
        } else if (this.type == Core.PRINT) {
            this.p = new Print();
            this.p.parse();
        } else if (this.type == Core.READ) {
            this.r = new Read();
            this.r.parse();
        } else if (this.type == Core.BEGIN) {
            this.c = new Call();
            this.c.parse();
        } else if (this.type == Core.INTEGER || this.type == Core.OBJECT) {
            this.d = new Decl();
            this.d.parse();
        } else {
            System.out.println(
                    "ERROR: Expected ID, IF, FOR, PRINT, READ, INTEGER, OBJECT, recieved "
                            + this.type);
            System.exit(0);
        }
    }

    void print(int i) {
        if (this.type == Core.ID) {
            this.a.print(i);
        } else if (this.type == Core.IF) {
            this.i.print(i);
        } else if (this.type == Core.FOR) {
            this.l.print(i);
        } else if (this.type == Core.PRINT) {
            this.p.print(i);
        } else if (this.type == Core.READ) {
            this.r.print(i);
        } else if (this.type == Core.BEGIN) {
            this.c.print(i);
        } else if (this.type == Core.INTEGER || this.type == Core.OBJECT) {
            this.d.print(i);
        }
    }

    void execute() {
        if (this.type == Core.ID) {
            this.a.execute();
        } else if (this.type == Core.IF) {
            this.i.execute();
        } else if (this.type == Core.FOR) {
            this.l.execute();
        } else if (this.type == Core.PRINT) {
            this.p.execute();
        } else if (this.type == Core.READ) {
            this.r.execute();
        } else if (this.type == Core.BEGIN) {
            this.c.execute();
        } else if (this.type == Core.INTEGER || this.type == Core.OBJECT) {
            this.d.execute();
        }
    }

}
