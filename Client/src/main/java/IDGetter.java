public class IDGetter implements Runnable{
    private User user;
    private YouAreEll webber;

    public IDGetter(User user, YouAreEll webber) {
        this.user = user;
        this.webber = webber;
    }

    public void run() {
        String results = webber.get_ids(user);
        simulateLag();
        SimpleShell.prettyPrintUsers(results);
    }

    private void simulateLag() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
