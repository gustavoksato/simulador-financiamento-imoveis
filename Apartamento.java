package Financiamento;

import java.io.Serializable;

class Apartamento extends Financiamento implements Serializable {    int numeroVagas;
    int numAndar;
    public Apartamento(double valor, double taxaMensal, int meses, int numeroVagas, int numAndar) {
        super(valor, taxaMensal, meses);

        this.numAndar = numAndar;
        this.numeroVagas = numeroVagas;
    }

    public int getNumeroAndar() {
        return numAndar;
    }

    public int getNumeroVagasGaragem() {
        return numeroVagas;
    }

    @Override
    public void exibirinfo() {
        System.out.println("---------Apartamento----------");
        System.out.println("o valor do imóvel é de: " + valor);
        System.out.println("A sua taxa mensal sobre este imóvel deve ser de " + taxaMensal);
        System.out.println("A quantidade de parcelas a serem pagas é de: " + meses);
        System.out.println("O valor total do financiamento é de: " + calcularFinanciamento());        return;
    }
    @Override
    public double calcularFinanciamento(){
        return valor * Math.pow(1 + taxaMensal, meses);
    }
    public double calcularParcela(){
        return calcularFinanciamento() / meses;
    }

}
