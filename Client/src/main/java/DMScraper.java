import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class DMScraper implements Runnable{
    private ArrayList<Message> messageList;
    private User user;
    private YouAreEll webber;

    public DMScraper(User user, YouAreEll webber) {
        this.user = user;
        this.webber = webber;
    }

    public void run() {
        this.messageList = new ArrayList<Message>();
        ObjectMapper objectMapper = new ObjectMapper();
        Message[] incoming = new Message[0];
        String scrape;
        while (true){
            waitN(5000);
            scrape = webber.get_DMs(user);
            waitN(100);
            try {
                incoming = objectMapper.readValue(scrape, Message[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Message message:incoming) {
                if (newMessageCheck(message)){
                    System.out.println("New DM!\n" + message.getFromid() + " says: " + message.getMessage());
                    this.messageList.add(message);
                }
            }
        }
    }

    private void waitN(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean newMessageCheck(Message newMessage){
        for (Message message:this.messageList) {
            if (message.getSequence().equals(newMessage.getSequence())) return false;
        }
        return true;
    }
}
