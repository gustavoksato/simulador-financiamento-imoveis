package Financiamento;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    //atributos
    private Scanner scanner;

    public static void salvarFinanciamentosTxt(List<Financiamento> financiamentos, String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Financiamento fin : financiamentos) {
                String linha = fin.getvalor() + ";" +
                        fin.calcularFinanciamento() + ";" +
                        fin.gettaxa() + ";" +
                        fin.getmeses();

                if (fin instanceof Casa) {
                    Casa casa = (Casa) fin;
                    linha += ";" + casa.getAreaConstruida() + ";" + casa.getAreaTerreno();
                    linha = "CASA;" + linha;
                } else if (fin instanceof Apartamento) {
                    Apartamento apartamento = (Apartamento) fin;
                    linha += ";" + apartamento.getNumeroVagasGaragem() + ";" + apartamento.getNumeroAndar();
                    linha = "APARTAMENTO;" + linha;
                } else if (fin instanceof Terreno) {
                    Terreno terreno = (Terreno) fin;
                    linha += ";" + terreno.getZona();
                    linha = "TERRENO;" + linha;
                }
                writer.println(linha);
            }
            System.out.println("Dados dos financiamentos salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os financiamentos no arquivo: " + e.getMessage());
        }
    }

    public static void salvarFinanciamentosSerializado(List<Financiamento> financiamentos, String nomeArquivo) {
        try (FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(financiamentos);
            System.out.println("Dados dos financiamentos serializados com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao serializar os financiamentos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Financiamento> lerFinanciamentosSerializado(String nomeArquivo) {
        List<Financiamento> financiamentos = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(nomeArquivo);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            financiamentos = (List<Financiamento>) objectIn.readObject();
            System.out.println("Dados dos financiamentos desserializados com sucesso do arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao desserializar os financiamentos (arquivo não encontrado ou corrompido): " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao desserializar: Classe Financiamento não encontrada.");
        }
        return financiamentos;
    }

    public static List<Financiamento> lerFinanciamentosTexto(String nomeArquivo) {
        List<Financiamento> financiamentosLidos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length > 0) {
                    String tipo = dados[0];
                    double valor = Double.parseDouble(dados[1]);
                    double valorFinanciamento = Double.parseDouble(dados[2]);
                    double taxaMensal = Double.parseDouble(dados[3]);
                    int meses = Integer.parseInt(dados[4]);

                    if (tipo.equals("CASA")) {
                        double areaConstruida = Double.parseDouble(dados[5]);
                        double areaTerreno = Double.parseDouble(dados[6]);
                        financiamentosLidos.add(new Casa(valor, taxaMensal, meses, areaConstruida, areaTerreno));
                    } else if (tipo.equals("APARTAMENTO")) {
                        double taxaAnual = taxaMensal * 12;
                        int prazoAnos = meses / 12;
                        int numeroVagasGaragem = Integer.parseInt(dados[5]);
                        int numeroAndar = Integer.parseInt(dados[6]);
                        financiamentosLidos.add(new Apartamento(valor, taxaAnual, prazoAnos, numeroVagasGaragem, numeroAndar));
                    } else if (tipo.equals("TERRENO")) {
                        String tipoZona = dados[5];
                        financiamentosLidos.add(new Terreno(valor, taxaMensal, meses, tipoZona));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler os financiamentos do arquivo: " + e.getMessage());
        }
        return financiamentosLidos;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;
        List<Financiamento> listaFinanciamentos = new ArrayList<>();

        while (opcao != 2) {
            System.out.println("Simulador de financiamento");
            System.out.println("1 - iniciar");
            System.out.println("2 - sair");
            System.out.println("Escolha uma opção");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Inválido");
                scanner.next();
                continue;
            }
            if (opcao == 1) {
                simuladorFinanciamento(scanner, listaFinanciamentos);
            } else if (opcao == 2) {
                System.out.println("Encerrando Simulador");
                salvarFinanciamentosTxt(listaFinanciamentos, "financiamentos.txt");
                salvarFinanciamentosSerializado(listaFinanciamentos, "financiamentos.ser");

                System.out.println("\nDados lidos do arquivo de texto:");
                List<Financiamento> financiamentosTexto = lerFinanciamentosTexto("financiamentos.txt");
                for (Financiamento fin : financiamentosTexto) {
                    fin.exibirinfo();
                    System.out.println("-------------------------");
                }

                System.out.println("\nDados lidos do arquivo serializado:");
                List<Financiamento> financiamentosSerializado = lerFinanciamentosSerializado("financiamentos.ser");
                for (Financiamento fin : financiamentosSerializado) {
                    fin.exibirinfo();
                    System.out.println("-------------------------");
                }
                break;
            } else {
                System.out.println("Resposta Inválida");
            }

        }
        scanner.close();

    }

    public static void simuladorFinanciamento(Scanner scanner, List<Financiamento> listaFinanciamentos) {
        Financiamento imovel = null;
        double areaConstruida = 0;
        double areaTerreno = 0;
        String zona = "";
        int numeroVagas = 0;
        int numAndar = 0;

        System.out.println("Selecione o tipo de imóvel:");
        System.out.println("1 - Casa");
        System.out.println("2 - Terreno");
        System.out.println("3 - Apartamento");
        System.out.print("Digite a opção: ");

        int tipo = 0;
        try {
            tipo = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Opção inválida.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        System.out.print("Digite o valor do imóvel: ");
        double valor = 0;
        try {
            valor = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Valor do imóvel inválido.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        System.out.println("Digite a taxa mensal sobre este imóvel: ");
        double taxaMensal = 0;
        try {
            taxaMensal = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Taxa mensal inválida.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        System.out.println("Digite a quantidade de parcelas a serem pagas: ");
        int meses = 0;
        try {
            meses = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Número de parcelas inválido.");
            scanner.next();
            return;
        }
        scanner.nextLine();


        if (tipo == 1) {
            System.out.println("Digite a área construída em m²");
            try {
                areaConstruida = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Área construída inválida.");
                scanner.next();
                return;
            }
            scanner.nextLine();
            System.out.println("Digite a área do terreno em m²");
            try {
                areaTerreno = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Área do terreno inválida.");
                scanner.next();
                return;
            }
            scanner.nextLine();
            imovel = new Casa(valor, taxaMensal, meses, areaConstruida, areaTerreno);
            System.out.println("Informações coletadas: ");
            Casa casa = (Casa) imovel; // Fazemos o casting de 'imovel' para o tipo 'Casa'
            System.out.println("área construída: " + casa.getAreaConstruida());
            System.out.println("área do terreno; " + casa.getAreaTerreno());
        } else if (tipo == 2) {
            System.out.println("Digite o tipo de zona do terreno: ");
            zona = scanner.nextLine();
            imovel = new Terreno(valor, taxaMensal, meses, zona);
            if (imovel != null) {
                Terreno terreno = (Terreno) imovel;
                System.out.println("Tipo de zona = " + terreno.getZona());
            }
        } else if (tipo == 3) {
            System.out.print("Digite o número de vagas na garagem: ");
            try {
                numeroVagas = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Número de vagas inválido.");
                scanner.next();
                return;
            }
            scanner.nextLine();
            System.out.print("Digite o número do andar: ");
            try {
                numAndar = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Número do andar inválido.");
                scanner.next();
                return;
            }
            scanner.nextLine();
            imovel = new Apartamento(valor, taxaMensal, meses, numeroVagas, numAndar);
            if (imovel != null) {
                Apartamento apartamento = (Apartamento) imovel;
                System.out.println("Número de vagas na garagem: " + apartamento.getNumeroVagasGaragem());
                System.out.println("Número do andar: " + apartamento.getNumeroAndar());
            }
        } else {
            System.out.println("Tipo inválido");
            return;
        }

        if (imovel != null) {
            imovel.exibirinfo();
            double calculo = imovel.calcularFinanciamento();
            System.out.printf("Valor total: " + calculo);
            double parcela = imovel.calcularParcela();
            System.out.println("valor da parcela:" + parcela);
            listaFinanciamentos.add(imovel);
        }
    }
}


