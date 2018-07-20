# Zadanie domowe
Stworzyć aplikację w środowisku Java (Zookeeper) która wykorzystując mechanizm obserwatorów (watches) umożliwia następujące funkcjonalności:

- Jeśli tworzony jest znode o nazwie znode_testowy uruchamiana jest zewnętrzna aplikacja (dowolna, określona w linii poleceń),
- Jeśli jest kasowany znode_testowy aplikacja zewnętrzna jest zatrzymywana,
- Każde dodanie potomka do znode_testowy powoduje wyświetlenie graficznej informacji na ekranie o aktualnej ilości potomków.

Dodatkowo aplikacja powinna mieć możliwość wyświetlenia całej struktury drzewa znode_testowy.   

Stworzona aplikacja powinna działać w środowisku „Replicated ZooKeeper”.

ZooKeeper 3.4.12 API - http://zookeeper.apache.org/doc/r3.4.12/api/index.html
