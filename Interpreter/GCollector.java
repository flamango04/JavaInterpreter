import java.util.Stack;

class GCollector {
    static int currentLayer = 1;
    static Stack<Integer> Variables = new Stack<Integer>();

    static void addVariable() {
        Variables.push(currentLayer);
        System.out.println("gc:" + Variables.size());
    }

    static void removeVariable() {
        Variables.pop();
        System.out.println("gc:" + Variables.size());
    }

    static void addLayer() {
        currentLayer++;
    }

    static void removeLayer() {
        while (!Variables.isEmpty() && Variables.peek() == currentLayer) {
            Variables.pop();
            System.out.println("gc:" + Variables.size());
        }
    }

    static void clearAll() {
        while (!Variables.isEmpty()) {
            Variables.pop();
            System.out.println("gc:" + Variables.size());
        }
    }

}
