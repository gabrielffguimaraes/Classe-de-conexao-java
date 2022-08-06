# Classe de conexão java 
Classe de conexão java criada com o padrão <ins>Static holder singleton pattern</ins>

# Beneficios

<b>Static factory</b> - A boa prática é que se utilizarmos esta estratégia não deixemos que seja possível instanciar a classe pelo construtor , criando um construtor privado para a classe, desta forma apenas um método de dentro da classe pode instanciar-la.

<b>Lazy initialization</b> - Garante que o recurso será apenas inicializado quando for utilizado .

<b>Thread safe</b> - mecanismos para garantir a ordem processamento de chamadas simultaneas ao mesmo metodo e evitar violação de  estado de uma classe.

<h3>Classe principal</h3>

<li>  contém um metodo de buscar a conexão do banco desejado aberto a extensão </li>

```
public class DBConnection {
        
    public  static Connection getConnection() throws Exception {
        return MysqlConnection.getInstance().getConnection();
    }
}
```

<h3>Classe secundária</h3>
<li> Classe que será consumida podendo ser qualquer banco , mysql  , postsgres etc . </li>

```
public class MysqlConnection {
    private final String URL = "jdbc:mysql://localhost:3306/teste";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private static Connection connection;
    
    // PRIVATE METHODS
    // GARANTE QUE NINGUÉM IRÁ INSTANCIAR ESTA CLASSE
    private MysqlConnection()  {}
    
    private static class InstanceHolder {
        private final static MysqlConnection instance = new MysqlConnection();
        private InstanceHolder() {
            this.instance.setConnection();
        }
    }
    private void setConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
    }
    private boolean isValidConnection() {
        if(connection == null) {
            return false;
        }
        try {
            connection.getMetaData();
        } catch(SQLException ex) {
            return false;
        }
        return true;
    }
    
    // PUBLIC METHODS
    public static MysqlConnection getInstance() {
        return InstanceHolder.instance;
    }

    public Connection getConnection() throws Exception {
        if(!isValidConnection()) {
            this.setConnection();
        }
        if(connection == null) {
            throw new RuntimeException("Erro ao estabelecer conexão.");
        }
        return connection;
    } 
}

```

