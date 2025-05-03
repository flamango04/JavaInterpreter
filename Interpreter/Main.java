class Main {

    public static void main(String[] args) {
        // Initialize the scanner with the input file
        Scanner S = new Scanner(args[0]);
        Parser.scanner = S;
        Scanner R = new Scanner(args[1]);
        Parser.reader = R;

        Procedure p = new Procedure();
        p.parse();
//        p.print();
        p.execute();
    }

}
