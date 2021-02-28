package transaccoes;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import contas.Conta;

public class Transferencia extends Transaccao {
	private Conta contadestino;
	
	public String mostrar() {
		return "Transferência" + " - " + data + " - " + conta.obterNib() + " - " + valor;
	}
	
	public String obterTipo() {
		return "Transferência";
	}
	
	public Transferencia(Date data_transacao, Conta conta_transacao, double valor_transacao, Conta contadestino_transacao) {
		super(data_transacao, conta_transacao, valor_transacao);
		contadestino = contadestino_transacao;
	}

	@Override
	public void escreverFicheiro(PrintWriter pw) {
		// TODO Auto-generated method stub
		SimpleDateFormat std = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
		pw.write(conta.obterNib() + ";" + conta.obterTipo() + ";" + contadestino.obterNib() + ";" + conta.obterSaldo() + ";" + std.format(data) + ";" + conta.obterNib() + ";" + "\n");
	}
}
