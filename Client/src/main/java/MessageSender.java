import java.util.List;

public class MessageSender implements Runnable{
    List<String> list;
    User user;
    YouAreEll webber;

    public MessageSender(List<String> list, User user, YouAreEll webber) {
        this.list = list;
        this.user = user;
        this.webber = webber;
    }

    public void run() {
        Message message;
        String sentence = "";
        for (int i = 2; i < list.size(); i++) {
            sentence += list.get(i) + " ";
        }

        sentence = sentence.substring(0, sentence.length()-1);
        if (list.get(1).equalsIgnoreCase("group")){
            message = new Message(user.getGithub(), "", sentence);
        } else {
            message = new Message(user.getGithub(), list.get(1), sentence);
        }

        String results = webber.send_message(message);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        SimpleShell.prettyPrintMessage(results);
    }
}
