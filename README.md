# 📋 Sistema Gerenciador de Tarefas - MongoDB + Redis
# 🎯 Descrição
Sistema completo de gerenciamento de tarefas desenvolvido em Java com integração MongoDB Atlas e Redis Cloud. Permite operações CRUD completas com cache inteligente para otimização de performance.

# 🏗️ Arquitetura do Sistema
text
📦 Sistema de Tarefas
├── 🎨 Main (Interface de Usuário)
├── 🔧 TarefaService (Lógica de Negócio)
├── 💾 ConexaoMongo (Banco de Dados Principal)
├── ⚡ ConexaoRedis (Sistema de Cache)
└── 📐 Tarefa (Modelo de Dados)
# 🚀 Tecnologias Utilizadas
Tecnologia	Versão	Finalidade
Java	8+	Linguagem principal
MongoDB Atlas	-	Banco de dados NoSQL em nuvem
Redis Cloud	-	Cache em memória
MongoDB Java Driver	4.10+	Conexão com MongoDB
Jedis	4.4+	Cliente Redis para Java
# 📋 Funcionalidades Implementadas
 # ✅ Operações CRUD Completas
📝 Cadastrar - Adicionar nova tarefa

📋 Listar - Visualizar todas as tarefas

✏️ Atualizar - Modificar tarefa existente

🗑️ Excluir - Remover tarefa

# ⚡ Sistema de Cache com Redis
Cache inteligente de consultas

Otimização de performance em operações de leitura

Gerenciamento automático de conexões

