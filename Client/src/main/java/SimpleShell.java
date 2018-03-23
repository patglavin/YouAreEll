import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SimpleShell {

    private static final Logger logger = LogManager.getLogger(SimpleShell.class);

    public static void prettyPrintUsers(String output) {
        ObjectMapper mapper = new ObjectMapper();
        User[] users = null;
        try {
            users = mapper.readValue(output, User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (User user:users){
            System.out.println("Name: " + user.getName() + " Github: " + user.getGithub() + "\n-----------------------------------------");
        }
    }

    public static void prettyPrintMessages(String output) {
        ObjectMapper mapper = new ObjectMapper();
        Message[] messages = null;
        try {
            messages = mapper.readValue(output, Message[].class);
        } catch (IOException e) {
            logger.trace("Pretty Print Message exception");
            e.printStackTrace();
        }
        for (Message message:messages){
            if (message.getToid().equals("")) System.out.println(message.getFromid() + ": " + message.getMessage() + "\n-----------------------------------------");
            else System.out.println(message.getFromid() + " says to " + message.getToid() + ": " + message.getMessage() + "\n-----------------------------------------");
        }
    }

    public static void prettyPrintMessage(String output) {
        ObjectMapper mapper = new ObjectMapper();
        Message message = null;
        try {
            message = mapper.readValue(output, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (message.getToid().equals("")) System.out.println(message.getFromid() + ": " + message.getMessage() + "\n-----------------------------------------");
        else System.out.println(message.getFromid() + " says to " + message.getToid() + ": " + message.getMessage() + "\n-----------------------------------------");
    }

    public static void main(String[] args) throws java.io.IOException {

        YouAreEll webber = new YouAreEll();
        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        System.out.println("your name is? ");
        String name = console.readLine();
        System.out.println("your github id is? ");
        String githubID = console.readLine();
        User user = new User(name, githubID);
        System.out.println(user.getName() + " " + user.getGithub());
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("cmd? ");
            commandLine = console.readLine();

            //input parsed into array of strings(command and arguments)
            String[] commands = commandLine.split(" ");
            List<String> list = new ArrayList<String>();

            //if the user entered a return, just loop again
            if (commandLine.equals(""))
                continue;
            if (commandLine.equals("exit")) {
                System.out.println("bye!");
                break;
            }

            //loop through to see if parsing worked
            for (int i = 0; i < commands.length; i++) {
                //System.out.println(commands[i]); //***check to see if parsing/split worked***
                list.add(commands[i]);

            }
            System.out.print(list); //***check to see if list was added correctly***
            history.addAll(list);
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    continue;
                }

                // Specific Commands.

                // ids
                if (list.contains("ids")) {
                    Thread thread = new Thread(new IDGetter(user, webber));
                    thread.start();
                    continue;
                }

                // messages
                if (list.contains("messages")) {
                    Thread thread = new Thread(new MessageLogger(user, webber));
                    thread.start();
                    continue;
                }
                // you need to add a bunch more.

                if (list.contains("dms")) {
                    String results = webber.get_DMs(user);
                    SimpleShell.prettyPrintMessages(results);
                    continue;
                }

                if (list.contains("send")) {
                    Thread thread = new Thread(new MessageSender(list, user, webber));
                    thread.start();
                    continue;
                }

                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));

                }//!<integer value i> command
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }

                // wait, wait, what curiousness is this?
                Process process = pb.start();

                //obtain the input stream
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //read output of the process
                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
                br.close();


            }

            //catch ioexception, output appropriate message, resume waiting for input
            catch (IOException e) {
                System.out.println("Input Error, Please try again!");
            }
            // So what, do you suppose, is the meaning of this comment?
            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */

        }


    }

}