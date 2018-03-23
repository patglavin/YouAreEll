public class MessageLogger implements Runnable{
    private User user;
    private YouAreEll webber;

    public MessageLogger(User user, YouAreEll webber) {
        this.user = user;
        this.webber = webber;
    }

    public void run() {
        String results = webber.get_messages(user);
        simulateLag();
        SimpleShell.prettyPrintMessages(results);
    }

    private void simulateLag() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
