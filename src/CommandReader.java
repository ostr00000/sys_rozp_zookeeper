import org.apache.zookeeper.ZooKeeper;

import java.util.Scanner;

class CommandReader {
    private ZnodeTreePrinter printer;
    private Scanner scanner;

    CommandReader(ZooKeeper zooKeeper) {
        this.printer = new ZnodeTreePrinter(zooKeeper);
        this.scanner = new Scanner(System.in);
    }

    void startRead() {
        while (true){
            String command = this.scanner.nextLine();
            if(command.equals("p")){
                printer.print();
            }else if (command.equals("e")){
                break;
            }
        }
    }
}
