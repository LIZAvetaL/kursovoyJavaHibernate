package Server.Commands;
import Server.Services.UserService;


public class Commands {

    public static Object split(String command) {
        String[] commandNumber = command.split(",", 2);
        String[] commands;
        Object result = true;
        switch (commandNumber[0]) {
            case "User":
                commands = command.split(",", 3);
                result = UserService.checkSingIn(commands[1],commands[2]);
                break;

        }
        return result;
    }

}
