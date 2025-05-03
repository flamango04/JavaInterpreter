import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Variables {
    static Map<String, Object> global;
    static Map<String, Function> procedure = new HashMap<String, Function>();
    static Stack<Stack<Map<String, Object>>> Frames = new Stack<Stack<Map<String, Object>>>();
    static Stack<Map<String, Object>> localStack;
    static Map<Map<String, Object>, String> defaultVals;

    static void createNewLocal() {
        GCollector.addLayer();
        Map<String, Object> local = new HashMap<String, Object>();
        localStack.push(local);
    }

    static void addToLocal(String id, Object val) {
        if (localStack.size() > 0) {
            Map<String, Object> local = localStack.pop();
            local.put(id, val);
            localStack.push(local);
        } else {
            global.put(id, val);
        }
    }

    static Object getValue(String id) {
        boolean inLocal = false;
        Object retVal = null;
        for (Map<String, Object> i : localStack) {
            if (i.containsKey(id)) {
                inLocal = true;
                retVal = i.get(id);
            }
        }
        if (!inLocal) {
            if (global.containsKey(id)) {
                retVal = global.get(id);
            } else {
                System.out.println(
                        "ERROR: Variable " + id + " does not exist in any scope.");
                System.exit(0);
            }
        }
        return retVal;
    }

    static Object changeValue(String id, Object val) {
        boolean inLocal = false;
        Object retVal = null;
        for (Map<String, Object> i : localStack) {
            if (i.containsKey(id)) {
                inLocal = true;
                retVal = i.get(id);
                i.put(id, val);
            }
        }
        if (!inLocal) {
            if (global.containsKey(id)) {
                retVal = global.get(id);
                global.put(id, val);
            } else {
                addToLocal(id, val);
            }
        }
        return retVal;
    }

    static boolean isInt(String id) {
        Object temp = getValue(id);
        boolean ret = false;
        if (temp == null) {
            System.out.println("ERROR: variable " + id + " is null.");
            System.exit(0);
        }
        if (temp.getClass().equals(Integer.class)) {
            ret = true;
        }
        return ret;
    }

    static void deleteLastLocal() {
        localStack.pop();
        GCollector.removeLayer();
    }

    static void createFrame() {
        Frames.push(localStack);
        localStack = new Stack<Map<String, Object>>();
    }

    static void deleteFrame() {
        localStack = Frames.pop();
    }

    static void addProcedure(String id, Function f) {
        if (procedure.containsKey(id)) {
            System.out.println("ERROR: Multiple Procedures with the Same Name");
            System.exit(0);
        }
        procedure.put(id, f);
    }

}
