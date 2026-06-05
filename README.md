Simulador de Financiamento Imobiliário
Aplicação de console desenvolvida em Java para simular financiamentos imobiliários de diferentes tipos de imóveis, com cálculo de parcelas, valor total e persistência de dados em arquivo.

📋 Funcionalidades

Simulação de financiamento para Casa, Apartamento e Terreno.
Cálculo do valor total do financiamento com juros compostos.
Cálculo do valor de cada parcela (com taxas específicas por tipo de imóvel).
Suporte a múltiplas simulações em uma mesma sessão.
Persistência de dados ao encerrar o programa:

Exportação em arquivo de texto (.txt)
Serialização binária (.ser)

Leitura e recarregamento dos dados salvos em ambos os formatos

A classe Financiamento define o contrato com os métodos abstratos:
calcularFinanciamento() — implementado com regras distintas por subclasse
calcularParcela() — parcela simples, com acréscimo fixo de R$80 para Casa
exibirinfo() — exibição formatada dos dados do imóvel

▶️Exemplo de uso:
Simulador de financiamento
1 - iniciar
2 - sair
Escolha uma opção: 1

Selecione o tipo de imóvel:
1 - Casa
2 - Terreno
3 - Apartamento
Digite a opção: 1

Digite o valor do imóvel: 350000
Digite a taxa mensal sobre este imóvel: 0.008
Digite a quantidade de parcelas a serem pagas: 240
Digite a área construída em m²: 120
Digite a área do terreno em m²: 200

---------Casa----------
o valor do imóvel é de: 350000.0
A sua taxa mensal sobre este imóvel deve ser de 0.008
A quantidade de parcelas a serem pagas é de: 240
O valor total do financiamento é de: 1918674.21...
valor da parcela: 7994.47...

💾 Persistência de Dados
Ao selecionar a opção 2 (sair), o programa automaticamente:

Salva todos os financiamentos simulados em financiamentos.txt (formato CSV com ;)
Serializa a lista em financiamentos.ser (formato binário Java)
Relê e exibe os dados de ambos os arquivos como confirmação


🛠️ Tecnologias utilizadas
Java (POO, herança, polimorfismo, classes abstratas)
Serialização Java (ObjectOutputStream / ObjectInputStream)
I/O de arquivos (FileWriter, BufferedReader, PrintWriter)
Tratamento de exceções (IOException, InputMismatchException)

📚 Conceitos demonstrados
Herança e polimorfismo
Classes e métodos abstratos
Get \ Set
Serialização de objetos
Leitura e escrita de arquivos texto e binários
Tratamento de entradas inválidas com try-catch

👤 Autor
Gustavo Sato
Estudante de Análise e Desenvolvimento de Sistemas (PUCPR) e Ciências Biológicas (UFPR)
LinkedIn · GitHub



