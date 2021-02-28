package contas;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import gestaobancaria.Banco;

public class Prazo extends Conta {
	private double taxajuro;
	private Date datavalidade;
	private double valorjuros;
	private double taxajuroaplicada;
	
	/**
	 * Levanta dinheiro desta conta, decrementando o saldo existente
	 * Isto so acontece se a conta tiver o saldo pelo menos igual ao que se pretende levantar
	 * @param valor saldo a retirar desta conta
	 * @return Devolve true ou falso caso tenha sido possivel de fazer o levantamento
	 */
	public boolean levantar(double valor) {
		if(saldo >= valor) {
			saldo -= valor;
			valorjuros += aplicarTaxaJuro(valor);
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
			valorjuros += aplicarTaxaJuro(valor);
			contadestino.saldo += valor + valorjuros;
			return true;
		}
		return false;
	}
	
	/**
	 * Retorna uma String representantiva do identificador da conta e do tipo
	 * @return String com informacao da conta
	 */
	public String mostrar() {
		return "Identificador da conta: " + nib + " Tipo de Conta: Prazo";
	}
	
	/**
	 * Retorna uma String com todas as informacoes da conta especifica
	 * @return String com todas as informacoes da conta
	 */
	public String mostrarInformacoes() {
		return "Saldo: " + saldo + " Nib: " + nib + " DataCriacao: " + datacriacao + " Estado: "+ (activa == true ? "Ativo" : "Inativo") + "" + " Tipo de Conta: Prazo";
	}
	
	/**
	 * Escreve no ecra o saldo corrente da conta
	 */	
	public void mostrarSaldo() {
		System.out.println("Saldo corrente da conta: " + saldo + "Valor de juros acumulado até ao momento: " + valorjuros);
	}
	
	public String obterTipo() {
		return "Prazo";
	}
	
	public Prazo() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(datacriacao);
		cal.add(Calendar.YEAR, 1);
		datavalidade = cal.getTime();
	}
	
	public Prazo(int nib_prazo, double saldo_prazo, boolean activa_prazo, double valorjuros_prazo, Date datacriacao, Date datavalidade) {
		this.nib = nib_prazo;
		this.saldo = saldo_prazo;
		this.activa = activa_prazo;
		this.valorjuros = valorjuros_prazo;
		this.datacriacao = datacriacao;
		this.datavalidade = datavalidade;
	}
	
	
	public double aplicarTaxaJuro(double valor) {
		Date agora = new Date();
		long tempod = agora.getTime() - datacriacao.getTime(); 
		taxajuroaplicada = tempod * taxajuro * valor;
		return taxajuroaplicada;
	}
	
	@Override
	public void escreverFicheiro(PrintWriter pw, int userid)  {
		SimpleDateFormat std = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
		pw.write(userid + ";" + obterTipo() + ";" + saldo + ";" + nib + ";" + activa + ";" +  std.format(datacriacao) + ";" + valorjuros + ";" + std.format(datavalidade) + ";\n");
	}
}
