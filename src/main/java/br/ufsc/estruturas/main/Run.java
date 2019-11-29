package br.ufsc.estruturas.main;

import br.ufsc.estruturas.server.ServerVerticle;
import io.vertx.core.Vertx;

public class Run {

	
	/** 
	 * Deploy do Verticle atraves do objeto da classe ServerVerticle
	 * @param args
	 */
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new ServerVerticle());
		System.out.println(">>> Verticle iniciado e rodando na porta 9000");
	}
}
