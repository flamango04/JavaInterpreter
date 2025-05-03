class Print {
    Expr e;
    String ID;

    void parse() {
        Parser.expectedToken(Core.PRINT);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        this.e = new Expr();
        this.e.parse();
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print(int i) {
        Parser.printSpaces(i);
        System.out.print("print(");
        this.e.print(i);
        System.out.println(");");
    }

    void execute() {
        System.out.println(this.e.execute());
    }

}
