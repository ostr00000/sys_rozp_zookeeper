import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZnodeCounter implements Watcher {
    private ZooKeeper zooKeeper;
    private int counter;

    ZnodeCounter(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
        recount();
        printChildNumber();
    }

    @Override
    public void process(WatchedEvent event) {
        int childrenNumBefore = this.counter;
        recount();
        if (this.counter != childrenNumBefore) {
            printChildNumber();
        }
    }

    private void recount() {
        this.counter = 0;
        try {
            if (null != zooKeeper.exists(ClientWatcher.test_znode, this)) {
                recount(ClientWatcher.test_znode);
            }
        } catch (InterruptedException | KeeperException ignored) {
        }
    }

    private void recount(String nodePath) throws KeeperException, InterruptedException {
        for (String child : this.zooKeeper.getChildren(nodePath, this)) {
            this.counter += 1;
            recount(nodePath + "/" + child);
        }
    }

    private void printChildNumber() {
        System.out.println("Number of children: " + this.counter);
    }
}
