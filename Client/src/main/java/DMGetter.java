public class DMGetter implements Runnable{
    private User user;
    private YouAreEll webber;

    public DMGetter(User user, YouAreEll webber) {
        this.user = user;
        this.webber = webber;
    }

    public void run() {
        String results = webber.get_DMs(user);
        SimpleShell.prettyPrintMessages(results);
    }
}
