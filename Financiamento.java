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

abstract class Financiamento implements Serializable {
    protected double valor;
    protected double taxaMensal;
    protected int meses;

    // construtor:
    public Financiamento(double valor, double taxaMensal, int meses) {
        this.valor = valor;
        this.taxaMensal = taxaMensal;
        this.meses = meses;
    }
    //métodos
    public abstract double calcularFinanciamento();

    public abstract double calcularParcela();

    public double getvalor(){
        return valor;
    }
    public int getmeses(){
        return meses;
    }
    public double gettaxa(){
        return taxaMensal;
    }
    //método para exibir informações do financiamento
    public abstract void exibirinfo();

}


