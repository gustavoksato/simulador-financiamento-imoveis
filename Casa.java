package Financiamento;

import java.io.Serializable;

class Casa extends Financiamento implements Serializable {
    private final double areaConstruida;
    private final double areaTerreno;

    public Casa(double valor, double taxaMensal, int meses, double areaConstruida, double areaTerreno) {
        super(valor, taxaMensal, meses);
        this.areaConstruida = areaConstruida;
        this.areaTerreno = areaTerreno;
    }

    public double getAreaConstruida() {
        return areaConstruida;
    }
    public double getAreaTerreno(){
        return areaTerreno;
    }

    @Override
    public void exibirinfo() {
        System.out.println("---------Casa----------");
        System.out.println("o valor do imóvel é de: " + valor);
        System.out.println("A sua taxa mensal sobre este imóvel deve ser de " + taxaMensal);
        System.out.println("A quantidade de parcelas a serem pagas é de: " + meses);
        System.out.println("O valor total do financiamento é de: " + calcularFinanciamento());        return;
    }
    @Override
    public double calcularFinanciamento(){
        return valor * Math.pow(1 + taxaMensal, meses);
    }
    @Override
    public double calcularParcela(){
        return calcularFinanciamento() / meses + 80;
    }

}
