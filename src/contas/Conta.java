package contas;

import java.io.PrintWriter;

//import gestaobancaria.Banco;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import transaccoes.*;

public abstract class Conta {	
	protected double saldo;
	protected int nib;
	protected Date datacriacao;
	protected boolean activa;
	protected ArrayList<Transaccao> transacoesconta;
	
	//METODOS ABSTRACTOS
	
	/**
	 * Levanta dinheiro desta conta, decrementando o saldo existente
	 * Isto so acontece se a conta tiver o saldo pelo menos igual ao que se pretende levantar
	 * @param valor saldo a retirar desta conta
	 * @return Devolve true ou falso caso tenha sido possivel de fazer o levantamento
	 */
	public abstract boolean levantar(double valor);
	
	/**
	 * Transfere dinheiro desta conta para a conta destino recebida como parametro
	 * Isto so acontece se a conta tiver o saldo pelo menos igual ao que se pretende transferir
	 * E criada um objecto transaccao tanto na conta de origem como na conta de destino
	 * ESTE METODO ASSUME QUE A CONTA DESTINO EXISTE
	 * @param valor valor que se pretende transferir
	 * @param contadestino Conta para a qual se esta a transferir dinheiro
	 * @return Devolve true ou falso caso tenha sido possivel efectuar a transferencia
	 */
	public abstract boolean transferir(double valor, Conta contadestino);	
	
	/**
	 * Retorna uma String representantiva do identificador da conta e do tipo
	 * @return String com informacao da conta
	 */
	public abstract String mostrar();
	
	/**
	 * Retorna uma String com todas as informacoes da conta especifica
	 * @return String com todas as informacoes da conta
	 */
	public abstract String mostrarInformacoes();
	
	/**
	 * Escreve no ecra o saldo corrente da conta
	 */	
	public abstract void mostrarSaldo();
	
	public abstract String obterTipo();	
	
			
	//METODOS IMPLEMENTADOS	
	
	public Conta(){
		datacriacao = new Date();
		saldo = 0;
		
		Random geradoraleatorio = new Random();
		int numaleatorio = geradoraleatorio.nextInt(Integer.MAX_VALUE);
		nib = numaleatorio;
		activa=true;
		transacoesconta = new ArrayList<Transaccao>();
	}
		
	/**
	 * Deposita um valor na Conta. Este metodo nao tem qualquer tipo de verificacoes
	 * @param valor valor a depositar
	 */
	public void depositar(double valor){
		saldo += valor;			
	}
		
	public int obterNib(){
		return nib;
	}
	
	public double obterSaldo(){
		return saldo;		
	}
	
	public void desactivar(){
		activa = false;
	}
	
	public boolean estaActiva(){
		return activa;
	}
	
	public void mostrarExtracto() {
		for(Transaccao t: transacoesconta) {
			System.out.println(t.mostrar());
		}
	}
	
	public ArrayList<Transaccao> obterTransaccoes() {
		return transacoesconta;
	}
	
	public void adicionarTransaccao(Transaccao transacao) {
		transacoesconta.add(transacao);
	}
	
	public abstract void escreverFicheiro(PrintWriter pw, int userid);
}
