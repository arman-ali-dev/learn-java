public class Ans6 {
    public static void main(String[] args) {
        try {
            int n = 10 / 0;
        } catch (Exception e) {

            // getting stack trace as array
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println(element.getClassName() + "." + element.getMethodName()
                        + " line " + element.getLineNumber());
            }
        }
    }
}