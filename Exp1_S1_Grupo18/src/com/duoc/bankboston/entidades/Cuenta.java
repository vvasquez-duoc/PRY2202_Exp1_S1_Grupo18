package com.duoc.bankboston.entidades;

public class Cuenta {
    private int numero;
    private int saldo;
    
    public Cuenta(int numero, int saldo){
        this.numero = numero;
        this.saldo = saldo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
