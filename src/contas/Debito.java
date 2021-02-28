package contas;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import gestaobancaria.Banco;

public class Debito extends Conta {

	/**
	 * Levanta dinheiro desta conta, decrementando o saldo existente
	 * Isto so acontece se a conta tiver o saldo pelo menos igual ao que se pretende levantar
	 * @param valor saldo a retirar desta conta
	 * @return Devolve true ou falso caso tenha sido possivel de fazer o levantamento
	 */
	public boolean levantar(double valor) {
		if(saldo >= valor) {
			saldo -= valor;
			return true;
		}
		return false;
	}
	
	/**
	 * Transfere dinheiro desta conta para a conta destino recebida como parametro
	 * Isto so acontece se a conta tiver o saldo pelo menos igual ao que se pretende transferir
	 * E criada um objecto transaccao tanto na conta de origem como na conta de destino
	 * ESTE METODO ASSUME QUE A CONTA DESTINO EXISTE
	 * @param valor valor que se pretende transferir
	 * @param contadestino Conta para a qual se esta a transferir dinheiro
	 * @return Devolve true ou falso caso tenha sido possivel efectuar a transferencia
	 */
	public boolean transferir(double valor, Conta contadestino) {
		if(saldo >= valor) {
			saldo -= valor;
			contadestino.saldo += valor;
			return true;
		}
		return false;
	}
	
	/**
	 * Retorna uma String representantiva do identificador da conta e do tipo
	 * @return String com informacao da conta
	 */
	public String mostrar() {
		return "Identificador da conta: " + nib + " Tipo de Conta: Debito";
	}
	
	/**
	 * Retorna uma String com todas as informacoes da conta especifica
	 * @return String com todas as informacoes da conta
	 */
	public String mostrarInformacoes() {
		if(activa == true)
			return "Saldo: " + saldo + " Nib: " + nib + " DataCriacao: " + datacriacao + " Estado: Ativa" + " Tipo de Conta: Debito";
		return "Saldo: " + saldo + " Nib: " + nib + " DataCriacao: " + datacriacao + " Estado: Inativa" + " Tipo de Conta: Debito";
	}
	
	/**
	 * Escreve no ecra o saldo corrente da conta
	 */	
	public void mostrarSaldo() {
		System.out.println("Saldo corrente da conta: " + saldo);
	}
	
	public String obterTipo() {
		return "Debito";
	}
	
	//METODOS IMPLEMENTADOS	
	
	public Debito(){
		datacriacao = new Date();
		saldo = 0;
		
		Random geradoraleatorio = new Random();
		int numaleatorio = geradoraleatorio.nextInt(Integer.MAX_VALUE);
		
		activa=true;
	}
	
	public Debito(int nib_prazo, double saldo_prazo, boolean activa_prazo, Date datacriacao) {
		this.nib = nib_prazo;
		this.saldo = saldo_prazo;
		this.activa = activa_prazo;
		this.datacriacao = datacriacao;
	}

	@Override
	public void escreverFicheiro(PrintWriter pw, int userid)  {
		SimpleDateFormat std = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
		pw.write(userid + ";" + obterTipo() + ";" + saldo + ";" + nib + ";" + activa + ";" +  std.format(datacriacao) + ";\n");
	}
}
