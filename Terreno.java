package Financiamento;

import java.io.Serializable;

class Terreno extends Financiamento implements Serializable {
    String zona;
    //construtor
    public Terreno(double valor, double taxaMensal, int meses, String zona) {
        super(valor, taxaMensal, meses);
        this.zona = zona;
    }

    public String getZona() {
        return zona;
    }

    @Override
    public void exibirinfo() {
        System.out.println("-------Terreno----------");
        System.out.println("o valor do imóvel é de: " + valor);
        System.out.println("A sua taxa mensal sobre este imóvel deve ser de " + taxaMensal);
        System.out.println("A quantidade de parcelas a serem pagas é de: " + meses);
        System.out.println("O valor total do financiamento é de: " + calcularFinanciamento());
        return;
    }
    @Override
    public double calcularFinanciamento(){
        return valor * Math.pow(1 + taxaMensal * 1.02, meses);
    }
    public double calcularParcela(){
        return calcularFinanciamento() / meses;
    }


}
