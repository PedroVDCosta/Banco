package transaccoes;

import java.io.PrintWriter;
import java.util.Date;

import contas.Conta;

public abstract class Transaccao {
	protected Date data;
	protected Conta conta;
	protected double valor;
	
	public abstract String mostrar();
	
	public abstract String obterTipo();
	
	public Transaccao(Date data_transacao, Conta conta_transacao, double valor_transacao) {
		data = data_transacao;
		conta = conta_transacao;
		valor = valor_transacao;
	}
	
	public abstract void escreverFicheiro(PrintWriter pw);
}
