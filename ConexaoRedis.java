package gerenciador_de_tarefas;

import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class ConexaoRedis {

    private static UnifiedJedis jedis; 

    public static UnifiedJedis getConnection() {
        if (jedis == null) {
            try {
                // Configurando autenticação
                // ⚠️ Verifique se 'default' e 'root' estão corretos no Redis Cloud.
                JedisClientConfig config = DefaultJedisClientConfig.builder()
                        .user("default")
                        .password("root")
                        .build();

                // Conexão com Host e Porta Remotos
                // O UnifiedJedis fará o primeiro ping aqui, onde a falha pode ocorrer.
                jedis = new UnifiedJedis(
                    new HostAndPort("redis-14270.crce216.sa-east-1-2.ec2.redns.redis-cloud.com", 14270),
                    config
                );
                
                // Teste de conexão: Garante que o servidor está realmente respondendo
                jedis.ping();
                
                System.out.println("✅ Conexão Redis inicializada!");
                
            } catch (JedisConnectionException e) {
                System.err.println("❌ Falha crítica ao conectar ao Redis Cloud!");
                System.err.println("Verifique se o servidor Redis está ativo, se as credenciais (user/password) estão corretas e se o IP do seu computador está liberado.");
                e.printStackTrace();
                // Interrompe o programa, pois a conexão de cache é essencial.
                throw new RuntimeException("Falha na inicialização do Redis.", e);
            }
        }
        return jedis;
    }
    
    // Método opcional para fechar a conexão
    public static void closeConnection() {
        if (jedis != null) {
            jedis.close();
            jedis = null;
            System.out.println("❌ Conexão Redis fechada.");
        }
    }
}
