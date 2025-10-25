package gerenciador_de_tarefas;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConexaoMongo {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // O nome do banco de dados na URL.
    private static final String DATABASE_NAME = "tarefas"; 
    
    // ⚠️ CRÍTICO: Substitua <SUA_SENHA_AQUI> pela senha REAL do seu usuário no Atlas.
    // O endereço do cluster foi atualizado para tarefas.raueyiw.mongodb.net
    private static final String CONNECTION_STRING = 
        "mongodb+srv://oliveirathayanne438_db_user:root@tarefas.raueyiw.mongodb.net/?appName=Tarefas";

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                // 1. Criar a string de conexão
                ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
                
                // 2. Configurar o Server API (necessário para o Atlas)
                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(connectionString)
                        // A linha abaixo é crucial para o MongoDB Atlas 
                        .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                        .build();
                
                // 3. Criar o Cliente e Obter o Banco de Dados
                mongoClient = MongoClients.create(settings);
                database = mongoClient.getDatabase(DATABASE_NAME);
                
                // Opcional: Testar a conexão (executa um comando 'ping')
                database.runCommand(new Document("ping", 1));
                System.out.println("✅ Conexão com MongoDB bem-sucedida! Database: " + DATABASE_NAME);

            } catch (MongoException e) {
                System.err.println("❌ Falha crítica ao conectar ao MongoDB Atlas.");
                System.err.println("1. Verifique se a senha foi substituída em CONNECTION_STRING.");
                System.err.println("2. Verifique se seu IP foi liberado no painel 'Network Access' do Atlas.");
                e.printStackTrace();
                throw new RuntimeException("Falha na inicialização do MongoDB.", e);
            }
        }
        return database;
    }
    
    // Método para fechar a conexão (boa prática)
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexão com MongoDB fechada.");
        }
    }
}
