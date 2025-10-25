package gerenciador_de_tarefas;

import java.util.List;
import java.util.Scanner;
import org.bson.types.ObjectId;

public class Main {
    public static void main(String[] args) {
        
        // Inicializa as conexões (elas serão carregadas no construtor do serviço)
        TarefaService service = new TarefaService(); 
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n=== GERENCIADOR DE TAREFAS ===");
            System.out.println("1 - Cadastrar tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Atualizar tarefa");
            System.out.println("4 - Excluir tarefa");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            
            try {
                if (sc.hasNextInt()) {
                    opcao = sc.nextInt();
                    sc.nextLine(); // Consome a nova linha após nextInt()
                } else {
                    System.err.println("Entrada inválida. Digite um número de 0 a 4.");
                    sc.nextLine(); // Limpa a entrada inválida
                    opcao = -1;
                    continue; // Volta para o início do loop
                }
                
                switch (opcao) {
                    case 1: {
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Descrição: ");
                        String descricao = sc.nextLine();
                        
                        // Tarefas novas começam como não concluídas (false)
                        service.cadastrar(new Tarefa(titulo, descricao, false)); 
                        System.out.println("\n✅ Tarefa cadastrada com sucesso!");
                        break; // CRÍTICO: Não executa o próximo case
                    }
                    case 2: {
                        List<Tarefa> tarefas = service.listar(); 
                        System.out.println("\n--- LISTA DE TAREFAS ---");
                        if (tarefas.isEmpty()) {
                            System.out.println("Nenhuma tarefa encontrada.");
                        } else {
                            tarefas.forEach(System.out::println);
                        }
                        System.out.println("--------------------------");
                        break; // CRÍTICO: Não executa o próximo case
                    }
                    case 3: {
                        listarTarefas(service);
                        System.out.print("\nDigite o ID da tarefa para atualizar (24 caracteres): ");
                        String id = sc.nextLine();
                        
                        System.out.print("Novo título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Nova descrição: ");
                        String descricao = sc.nextLine();
                        
                        // CORREÇÃO do Scanner: Leitura como String e conversão manual
                        System.out.print("Concluída? (true/false): ");
                        String concluidaStr = sc.nextLine();
                        boolean concluida = Boolean.parseBoolean(concluidaStr); 
                        
                        // Captura exceção de ID inválido aqui no Main
                        service.atualizar(id, new Tarefa(titulo, descricao, concluida)); 
                        System.out.println("\n✅ Tarefa atualizada com sucesso!");
                        break; // CRÍTICO: Não executa o próximo case
                    }
                    case 4: {
                        listarTarefas(service);
                        System.out.print("\nDigite o ID da tarefa para excluir (24 caracteres): ");
                        String id = sc.nextLine();
                        
                        service.deletar(id);
                        System.out.println("\n✅ Tarefa excluída com sucesso!");
                        break; // CRÍTICO: Não executa o próximo case
                    }
                    case 0: 
                        System.out.println("Saindo...");
                        break;
                    default: 
                        System.out.println("Opção inválida!");
                        break;
                }
            } catch (IllegalArgumentException e) {
                 // Captura o erro lançado por new ObjectId(id) no TarefaService
                 System.err.println("\n❌ Erro de formato de ID. O ID deve ter 24 caracteres hexadecimais.");
            } catch (Exception e) {
                System.err.println("\nOcorreu um erro inesperado: " + e.getMessage());
                // e.printStackTrace(); // Opcional: Remova o print para uma saída mais limpa
            }
            
        } while (opcao != 0);

        // Boa prática: fechar as conexões ao sair
        ConexaoMongo.closeConnection();
        ConexaoRedis.closeConnection();
        sc.close();
    }
    
    // Método auxiliar para listar tarefas antes de atualizar/excluir
    private static void listarTarefas(TarefaService service) {
         List<Tarefa> tarefas = service.listar(); 
         System.out.println("\n--- LISTA DE TAREFAS ---");
         if (tarefas.isEmpty()) {
             System.out.println("Nenhuma tarefa encontrada.");
         } else {
             tarefas.forEach(System.out::println);
         }
         System.out.println("--------------------------");
    }
}
