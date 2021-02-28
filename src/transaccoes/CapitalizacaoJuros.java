package transaccoes;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import contas.Conta;

public class CapitalizacaoJuros extends Transaccao {
	
	public String mostrar() {
		return "Capitalizacao Juros" + " - " + data + " - " + conta.obterNib() + " - " + valor;
	}
	
	public String obterTipo() {
		return "Capitalizacao Juros";
	}
	
	public CapitalizacaoJuros(Date data_transacao, Conta conta_transacao, double valor_transacao) {
		super(data_transacao, conta_transacao, valor_transacao);
		
	}

	@Override
	public void escreverFicheiro(PrintWriter pw) {
		// TODO Auto-generated method stub
		SimpleDateFormat std = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
		pw.write(std.format(data) + ";" + conta.obterNib() + ";" + valor + ";" + "\n");
	}
}
