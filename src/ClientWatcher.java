import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClientWatcher implements Watcher {
    static String test_znode = "/znode_testowy";

    private ZooKeeper zooKeeper;

    public static void main(String[] args) {
        new ClientWatcher(parseConnectStringFromArgs(args), parseCommandFromArgs(args));
    }

    private static String parseConnectStringFromArgs(String[] args) {
        String connectString = "127.0.0.1:2181";
        if (args.length > 0) {
            connectString = args[0];
        }
        return connectString;
    }

    private static String parseCommandFromArgs(String[] args) {
        String command = "xterm -bg blue";
        if (args.length > 1) {
            List<String> commandArgs = Arrays.asList(args);
            commandArgs = commandArgs.subList(1, commandArgs.size());
            command = String.join(" ", commandArgs);
        }
        return command;
    }

    private ClientWatcher(String connectString, String command) {
        try {
            int timeoutInMilliseconds = 50000;
            this.zooKeeper = new ZooKeeper(connectString, timeoutInMilliseconds, this);
            new ZnodeProcess(this.zooKeeper, command);
        } catch (IOException e) {
            System.err.println("Cannot create to zookeeper");
            System.exit(1);
        }
        new CommandReader(this.zooKeeper).startRead();
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }
}
