import java.util.List;

public class MessageSender implements Runnable{
    private List<String> list;
    private User user;
    private YouAreEll webber;
    private Message message;
    private String sentence = "";

    public MessageSender(List<String> list, User user, YouAreEll webber) {
        this.list = list;
        this.user = user;
        this.webber = webber;
    }

    public void run() {
        buildSentence();
        buildMessage();
        String results = webber.send_message(message);
        simulateLag();
        SimpleShell.prettyPrintMessage(results);
    }

    private void simulateLag() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void buildMessage() {
        if (list.get(1).equalsIgnoreCase("group")){
            message = new Message(user.getGithub(), "", sentence);
        } else {
            message = new Message(user.getGithub(), list.get(1), sentence);
        }
    }

    private void buildSentence() {
        for (int i = 2; i < list.size(); i++) {
            sentence += list.get(i) + " ";
        }
        sentence = sentence.substring(0, sentence.length()-1);
    }
}
