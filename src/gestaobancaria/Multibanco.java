package gestaobancaria;

import java.util.Scanner;

public class Multibanco extends InteraccaoBanco {

	public static void efetuarLogin() {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Insira o seu Login");
		int login = teclado.nextInt();
		System.out.println("Insira a sua Password");
		int password = teclado.nextInt();
		
		if(Banco.validarLogin(login, password) != null) {
			if(Banco.procurarCliente(login).obterAtivo() == true) {
				System.out.println("Bem vindo");
				cli = Banco.procurarCliente(login);
				InteraccaoBanco.processaMenuContas(cli.obterContas());
			}
			else {
				System.out.println("Não pode operar sobre um cliente inactivo");
			}
		}
		else {
			System.out.println("Dados de login invalidos");
		}
	}
	
	public static void main(String[] args) {
		Banco.iniciar();
		efetuarLogin();
		Banco.guardarDados();
	}
	
}
