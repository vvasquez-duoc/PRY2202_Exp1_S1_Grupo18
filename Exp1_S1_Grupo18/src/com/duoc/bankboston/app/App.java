package com.duoc.bankboston.app;

import com.duoc.bankboston.entidades.Cliente;
import com.duoc.bankboston.entidades.Cuenta;
import com.duoc.bankboston.utilitarios.Utilitarios;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class App {
    static ArrayList<String> itemsMenu = new ArrayList<>();
    static ArrayList<Integer> cuentasUsadas = new ArrayList<>();
    static HashMap<Integer, Cuenta> cuentasCorrientes = new HashMap<>();
    static HashMap<String, Cliente> clientesRegistrados = new HashMap<>();
    
    public static void main(String[] args) {
        itemsMenu.add("Registrar Cliente");
        itemsMenu.add("Ver Datos del Cliente");
        itemsMenu.add("Depositar");
        itemsMenu.add("Girar");
        itemsMenu.add("Consultar Saldo");
        itemsMenu.add("Salir");
        
        Utilitarios.limpiaPantalla();
        menuPrincipal();
    }
    
    static void menuPrincipal(){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        
        try{
            do{
                System.out.println("BIENVENIDO A BANK BOSTON");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION");
                for(int i=0; i<itemsMenu.size(); i++){
                    System.out.println("["+(i + 1)+"] "+itemsMenu.get(i));
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenu.size()){
                    Utilitarios.limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenu.size());
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            menuPrincipal();
        }
        
        if(opcion == 1){
            Utilitarios.limpiaPantalla();
            registrarCliente();
        }
        
        if(opcion == 2){
            Utilitarios.limpiaPantalla();
            verDatosCliente();
        }
        
        if(opcion == 3){
            Utilitarios.limpiaPantalla();
            depositoCliente();
        }
        
        if(opcion == 4){
            Utilitarios.limpiaPantalla();
            giroCliente();
        }
        
        if(opcion == 5){
           Utilitarios.limpiaPantalla();
           verSaldoCliente();
        }
        
        if(opcion == 6){
            Utilitarios.limpiaPantalla();
            System.out.println("GRACIAS POR USAR BANK BOSTON");
        }
    }
    
    static void registrarCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        String NOM;
        String AP;
        String AM;
        String DOM;
        String COM;
        String TEL;
        int CTA;
        boolean rutEsValido = true;
        
        do{
            Utilitarios.limpiaPantalla();
            if(rutEsValido == false) System.out.println("** EL RUT INGRESADO NO ES VALIDO **");
            System.out.println("INGRESE RUT DEL CLIENTE:");
            System.out.println("* ingrese RUT con puntos y guion. EJ: 11.222.333-4");
            RUT = teclado.nextLine();
            rutEsValido = Utilitarios.validaRut(RUT);
        }while(rutEsValido == false);
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE NOMBRE DEL CLIENTE:");
        NOM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE APELLIDO PATERNO DEL CLIENTE:");
        AP = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE APELLIDO MATERNO DEL CLIENTE:");
        AM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE DOMICILIO DEL CLIENTE:");
        DOM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE COMUNA DEL DOMICILIO DEL CLIENTE:");
        COM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE TELEFONO DEL CLIENTE:");
        TEL = teclado.nextLine();
        
        do{
            CTA = Utilitarios.generarNroCtaCte();
        }while(cuentasUsadas.contains(CTA));
        
        cuentasUsadas.add(CTA);
        
        Cuenta auxCta = new Cuenta(CTA, 0);
        Cliente auxCte = new Cliente(RUT, NOM, AP, AM, DOM, COM, TEL, CTA);
        
        cuentasCorrientes.put(CTA, auxCta);
        clientesRegistrados.put(RUT, auxCte);
        
        Utilitarios.limpiaPantalla();
        System.out.println("CLIENTE REGISTRADO EXITOSAMENTE!");
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        menuPrincipal();
    }

    static void verDatosCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        boolean rutEsValido = true;
        
        if(clientesRegistrados.isEmpty()){
            System.out.println("NO HAY CLIENTES REGISTRADOS PARA MOSTRAR DATOS");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            Utilitarios.limpiaPantalla();
            menuPrincipal();
        }else{
            do{
                Utilitarios.limpiaPantalla();
                if(rutEsValido == false) System.out.println("** EL RUT INGRESADO NO ES VALIDO **");
                System.out.println("INGRESE RUT DEL CLIENTE:");
                System.out.println("* ingrese RUT con puntos y guion. EJ: 11.222.333-4");
                RUT = teclado.nextLine();
                rutEsValido = Utilitarios.validaRut(RUT);
            }while(rutEsValido == false);
            
            if(!clientesRegistrados.containsKey(RUT)){
                Utilitarios.limpiaPantalla();
                System.out.println("** EL RUT INGRESADO NO ES CLIENTE DEL BANCO **");
                System.out.println("** POR FAVOR, REGISTRAR PREVIAMENTE         **");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }else{
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int nroCuenta = auxCliente.getNumeroCuenta();
                Cuenta auxCuenta = cuentasCorrientes.get(nroCuenta);
                int saldoCuenta = auxCuenta.getSaldo();
                NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
                formato.setGroupingUsed(true);
                
                Utilitarios.limpiaPantalla();
                System.out.println("** DATOS DEL CLIENTE **");
                System.out.println("");
                System.out.println("RUT:              "+RUT);
                System.out.println("NOMBRE:           "+auxCliente.getNombre().toUpperCase());
                System.out.println("APELLIDO PATERNO: "+auxCliente.getApellidoPaterno().toUpperCase());
                System.out.println("APELLIDO MATERNO: "+auxCliente.getApellidoMaterno().toUpperCase());
                System.out.println("DOMICILIO:        "+auxCliente.getDomicilio().toUpperCase());
                System.out.println("COMUNA:           "+auxCliente.getComuna().toUpperCase());
                System.out.println("TELEFONO:         "+auxCliente.getTelefono().toUpperCase());
                System.out.println("NUMERO CUENTA:    "+nroCuenta);
                System.out.println("SALDO CUENTA:     $"+formato.format(saldoCuenta));
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }   
    }

    static void verSaldoCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        boolean rutEsValido = true;
        
        if(clientesRegistrados.isEmpty()){
            System.out.println("NO HAY CLIENTES REGISTRADOS PARA MOSTRAR DATOS");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            Utilitarios.limpiaPantalla();
            menuPrincipal();
        }else{
            do{
                Utilitarios.limpiaPantalla();
                if(rutEsValido == false) System.out.println("** EL RUT INGRESADO NO ES VALIDO **");
                System.out.println("INGRESE RUT DEL CLIENTE:");
                System.out.println("* ingrese RUT con puntos y guion. EJ: 11.222.333-4");
                RUT = teclado.nextLine();
                rutEsValido = Utilitarios.validaRut(RUT);
            }while(rutEsValido == false);
            
            if(!clientesRegistrados.containsKey(RUT)){
                Utilitarios.limpiaPantalla();
                System.out.println("** EL RUT INGRESADO NO ES CLIENTE DEL BANCO **");
                System.out.println("** POR FAVOR, REGISTRAR PREVIAMENTE         **");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }else{
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int nroCuenta = auxCliente.getNumeroCuenta();
                Cuenta auxCuenta = cuentasCorrientes.get(nroCuenta);
                int saldoCuenta = auxCuenta.getSaldo();
                NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
                formato.setGroupingUsed(true);
                
                Utilitarios.limpiaPantalla();
                System.out.println("** SALDO ACTUAL DEL CLIENTE **");
                System.out.println("");
                System.out.println("SALDO CUENTA:     $"+formato.format(saldoCuenta));
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }
    }

    static void depositoCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        boolean rutEsValido = true;
        int deposito = 0;
        
        if(clientesRegistrados.isEmpty()){
            System.out.println("NO HAY CLIENTES REGISTRADOS PARA HACER DEPOSITOS");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            Utilitarios.limpiaPantalla();
            menuPrincipal();
        }else{
            do{
                Utilitarios.limpiaPantalla();
                if(rutEsValido == false) System.out.println("** EL RUT INGRESADO NO ES VALIDO **");
                System.out.println("INGRESE RUT DEL CLIENTE:");
                System.out.println("* ingrese RUT con puntos y guion. EJ: 11.222.333-4");
                RUT = teclado.nextLine();
                rutEsValido = Utilitarios.validaRut(RUT);
            }while(rutEsValido == false);
            
            if(!clientesRegistrados.containsKey(RUT)){
                Utilitarios.limpiaPantalla();
                System.out.println("** EL RUT INGRESADO NO ES CLIENTE DEL BANCO **");
                System.out.println("** POR FAVOR, REGISTRAR PREVIAMENTE         **");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }else{
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int nroCuenta = auxCliente.getNumeroCuenta();
                Cuenta auxCuenta = cuentasCorrientes.get(nroCuenta);
                int saldoCuenta = auxCuenta.getSaldo();
                NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
                formato.setGroupingUsed(true);
                
                Utilitarios.limpiaPantalla();
                System.out.println("** REALIZAR DEPOSITO **");
                System.out.println("");
                deposito = pedirDeposito();
                saldoCuenta += deposito;
                auxCuenta.setSaldo(saldoCuenta);
                cuentasCorrientes.put(nroCuenta, auxCuenta);
                Utilitarios.limpiaPantalla();
                System.out.println("DEPOSITO REALIZADO DE MANERA EXITOSA!");
                System.out.println("USTED TIENE UN SALDO ACTUAL DE: $"+formato.format(saldoCuenta));
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }
    }
    
    static void giroCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        boolean rutEsValido = true;
        int giro = 0;
        
        if(clientesRegistrados.isEmpty()){
            System.out.println("NO HAY CLIENTES REGISTRADOS PARA HACER GIROS");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            Utilitarios.limpiaPantalla();
            menuPrincipal();
        }else{
            do{
                Utilitarios.limpiaPantalla();
                if(rutEsValido == false) System.out.println("** EL RUT INGRESADO NO ES VALIDO **");
                System.out.println("INGRESE RUT DEL CLIENTE:");
                System.out.println("* ingrese RUT con puntos y guion. EJ: 11.222.333-4");
                RUT = teclado.nextLine();
                rutEsValido = Utilitarios.validaRut(RUT);
            }while(rutEsValido == false);
            
            if(!clientesRegistrados.containsKey(RUT)){
                Utilitarios.limpiaPantalla();
                System.out.println("** EL RUT INGRESADO NO ES CLIENTE DEL BANCO **");
                System.out.println("** POR FAVOR, REGISTRAR PREVIAMENTE         **");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }else{
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int nroCuenta = auxCliente.getNumeroCuenta();
                Cuenta auxCuenta = cuentasCorrientes.get(nroCuenta);
                int saldoCuenta = auxCuenta.getSaldo();
                NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
                formato.setGroupingUsed(true);
                
                Utilitarios.limpiaPantalla();
                System.out.println("** REALIZAR GIRO **");
                System.out.println("");
                giro = pedirGiro(saldoCuenta);
                saldoCuenta -= giro;
                auxCuenta.setSaldo(saldoCuenta);
                cuentasCorrientes.put(nroCuenta, auxCuenta);
                Utilitarios.limpiaPantalla();
                System.out.println("GIRO REALIZADO DE MANERA EXITOSA!");
                System.out.println("USTED TIENE UN SALDO ACTUAL DE: $"+formato.format(saldoCuenta));
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }
    }
    
    static int pedirDeposito(){
        Scanner teclado = new Scanner(System.in);
        int deposito = 0;
        try{
            do{
                System.out.println("INGRESE EL MONTO A DEPOSITAR");
                System.out.println("");
                deposito = teclado.nextInt();
                if(deposito <= 0){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** EL MONTO A DEPOSITAR DEBE SER MAYOR A CERO (0) PESOS");
                    System.out.println("");
                }
            }while(deposito <= 0);
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: EL MONTO INGRESADO NO ES UN NUMERO");
            System.out.println("");
            pedirDeposito();
        }
        return deposito;
    }
    
    static int pedirGiro(int saldoActual){
        Scanner teclado = new Scanner(System.in);
        int giro = 0;
        NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
        formato.setGroupingUsed(true);
                
        try{
            do{
                System.out.println("INGRESE EL MONTO A GIRAR");
                System.out.println("");
                giro = teclado.nextInt();
                if(giro <= 0){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** EL MONTO A DEPOSITAR DEBE SER MAYOR A CERO (0) PESOS");
                    System.out.println("");
                }
                if(giro > saldoActual){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** EL MONTO A GIRAR NO PUEDE SER MAYOR AL SALDO ACTUAL ($"+formato.format(saldoActual)+")");
                    System.out.println("");
                }
            }while(giro <= 0 || giro > saldoActual);
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: EL MONTO INGRESADO NO ES UN NUMERO");
            System.out.println("");
            pedirGiro(saldoActual);
        }
        return giro;
    }
}
