import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZnodeProcess implements Watcher {
    private Process process;
    private ZooKeeper zooKeeper;
    private String command;
    private ZnodeCounter counter;

    ZnodeProcess(ZooKeeper zooKeeper, String command) {
        this.zooKeeper = zooKeeper;
        this.command = command;
        this.counter = new ZnodeCounter(zooKeeper);

        addShutdownHook();
        if (nodeExists()) {
            startProcess();
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::endProcess));
    }

    private void endProcess() {
        if (isProcessStarted()) {
            this.process.destroy();
        }
    }


    private boolean isProcessStarted() {
        return this.process != null;
    }


    private boolean nodeExists() {
        try {
            return null != this.zooKeeper.exists(ClientWatcher.test_znode, this);
        } catch (KeeperException | InterruptedException e) {
            return false;
        }
    }

    private void startProcess() {
        try {
            this.zooKeeper.getChildren(ClientWatcher.test_znode, this.counter);
            this.process = Runtime.getRuntime().exec(this.command);
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case NodeCreated:
                startProcess();
                break;
            case NodeDeleted:
                endProcess();
                break;
            default:
        }
        try {
            this.zooKeeper.exists(ClientWatcher.test_znode, this);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
