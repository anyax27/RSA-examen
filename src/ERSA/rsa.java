package ERSA;

import java.math.BigInteger;
import java.util.*;
import java.io.*;
/**
 *
 * @author Yax + Nadia
 */
public class rsa implements Serializable{
    //variables uwu
    
    int Primo = 0;
    BigInteger n, q, p;
    BigInteger phi;
    BigInteger e, d;
    
    //constructor
    public rsa(int tamPrimo){
        //para que no salga en chino el cifrado, literal
        this.Primo = (BigInteger.TEN.pow(tamPrimo)).subtract(BigInteger.ONE).toString(2).length() - 1;
    }
    

    //generar las claves

    public void generarClaves(){
        // n = p*q
        n = p.multiply(q); 
        phi = p.subtract(BigInteger.valueOf(1));
        phi = phi.multiply(q.subtract(BigInteger.valueOf(1)));

        //elegir el numero coprimo o primo relativo menor que n

        do e = new BigInteger(2*Primo, new Random());
            while ((e.compareTo(phi)!=-1) || (e.gcd(phi).compareTo(BigInteger.valueOf(1))!=0));
        //ahora debemos hacer la operacion modulo == d = e^ 1 mod phi

        d = e.modInverse(phi);
        
        //comprobar
        System.out.println("n:"+n);
        System.out.println("phi:"+phi);
        System.out.println("e:"+e);
        System.out.println("d:"+d);
        

    }
    
    //metodo para generar numeros primos
    
    public void generarPrimos(){
        //para los primos son p y q
        p = new BigInteger(Primo, new Random());
        do q = new BigInteger(Primo, new Random());
            while(q.compareTo(p)==0);
        
        //comprobar xd
        
        System.out.println("p:"+p);
        System.out.println("q:"+q);
    }

    /*
    Cifrar con el numero e ya que "e" y n es la clave publica
    */ 

    public BigInteger[] encriptar(String mensaje) throws FileNotFoundException, IOException  {
        //variables
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        //lo primero que debemos hacer es correr el tamaño de bigdigitos
        for(i = 0; i<bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        //vamos a cifrar
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];

        for(i = 0; i<bigdigitos.length; i++){
            encriptado[i] = bigdigitos[i].modPow(e,n);
        } 
        return encriptado;   
    }

    
    //descifrar array de biginteger con la clave privada, d y n

    public String desencriptar(BigInteger[] encriptado){
        BigInteger[] desencriptar = new BigInteger[encriptado.length];

        for(int i = 0; i<desencriptar.length; i++){
            desencriptar[i] = encriptado[i].modPow(d, n);
        }

        char[]charArray  = new char[desencriptar.length];

        for(int i = 0; i<charArray.length; i++){
            charArray[i] = (char)(desencriptar[i].intValue());
        }

        return (charArray.toString());
    }
    
}