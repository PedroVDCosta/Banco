package transaccoes;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import contas.Conta;

public class Levantamento extends Transaccao {
	
	public String mostrar() {
		return "Levantamento" + " - " + data + " - " + conta.obterNib() + " - " + valor;
	}
	
	public String obterTipo() {
		return "Levantamento";
	}
	
	public Levantamento(Date data_transacao, Conta conta_transacao, double valor_transacao) {
		super(data_transacao, conta_transacao, valor_transacao);
		
	}

	@Override
	public void escreverFicheiro(PrintWriter pw) {
		// TODO Auto-generated method stub
		SimpleDateFormat std = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
		pw.write(conta.obterNib() + ";" + conta.obterTipo() + ";" + conta.obterNib() + ";" + conta.obterSaldo() + ";" + std.format(data) + ";" + "\n");
	
	}
}
