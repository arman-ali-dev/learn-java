public class Try extends Thread {
    @Override
    public void run() {
        System.out.println("RUNNING");
        try {
            Thread.sleep(5000); // thread 5 sec ke liye sleep karega
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Try t = new Try();
        System.out.println(t.getState());
        t.start();

        System.out.println(t.getState());

        Thread.sleep(1000);

        System.out.println(t.getState());
        
        Thread.sleep(4000);
        System.out.println(t.getState());



        
    }
}
