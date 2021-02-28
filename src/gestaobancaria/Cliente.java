package gestaobancaria;

import java.io.PrintWriter;
import java.util.ArrayList;

import contas.Conta;

public class Cliente implements Comparable{
	private String nome;
	private int password;
	private int userid;
	private boolean ativo;
	private ArrayList<Conta> contascliente;
	
	public String obterInformacoes(){
		String temp =  "Nome - " + nome + " Identificador - " + userid + " Estado - " + (ativo ? "Activo" : "Inativo");
		
		return temp;
	}
	
	public void adicionarConta(Conta conta) {
		contascliente.add(conta);
	}
	
	public ArrayList<Conta> obterContas() {
		return contascliente;
	}

	public Conta obterConta(int nib) {
		for(Conta c: contascliente) {
			if(c.obterNib() == nib)
				return c;
		}
		return null;
	}
	
	public Cliente(String nome_cliente, int userid_cliente, int password_cliente){
		nome = nome_cliente;
		password = password_cliente;
		userid = userid_cliente;
		ativo = true;
		contascliente = new ArrayList<Conta>();
	}
	
	public Cliente(String nome_cliente, int userid_cliente, int password_cliente, boolean ativo_cliente){
		nome = nome_cliente;
		password = password_cliente;
		userid = userid_cliente;
		ativo = ativo_cliente;
		contascliente = new ArrayList<Conta>();
	}
	
	public int obteruserID() {
		return userid;
	}
	
	public String obterNome() {
		return nome;
	}
	
	public int obterPassword() {
		return password;
	}
	
	public boolean obterAtivo() {
		return ativo;
	}
	
	public void desactivar() {
		ativo = false;
	}
	
	@Override
	public int compareTo(Object arg0) {
		Cliente clienteAComparar = (Cliente)arg0;
		return userid-clienteAComparar.userid; //ordem ascendente se fosse descendente seria return clienteAComparar.userid - userid;
	}
	
	public void escreverFicheiro(PrintWriter pw) {
		pw.write(nome + ";" + userid + ";" + password + ";" + ativo + ";" + "\n");
	}
}
