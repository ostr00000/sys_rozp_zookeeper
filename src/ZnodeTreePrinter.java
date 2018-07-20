import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

class ZnodeTreePrinter {
    private ZooKeeper zooKeeper;

    ZnodeTreePrinter(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    void print() {
        try {
            System.out.println("Znode tree:");
            print(ClientWatcher.test_znode, 0);
            System.out.println();
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(String nodePath, int deep) throws KeeperException, InterruptedException {
        if (nodeExists(nodePath)) {
            System.out.println(nodePath);
            printChildren(nodePath, deep);
        }
    }

    private boolean nodeExists(String nodePath) throws KeeperException, InterruptedException {
        return null != this.zooKeeper.exists(nodePath, false);
    }


    private void printChildren(String nodePath, int deep) throws KeeperException, InterruptedException {
        for (String child : this.zooKeeper.getChildren(nodePath, false)) {
            print(nodePath + "/" + child, deep + 1);
        }
    }
}
