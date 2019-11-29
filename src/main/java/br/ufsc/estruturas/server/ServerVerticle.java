package br.ufsc.estruturas.server;

import java.util.HashMap;

import br.ufsc.estruturas.data.DataProducts;
import br.ufsc.estruturas.indexation.DirInvertedIndex;
import br.ufsc.estruturas.model.Product;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class ServerVerticle extends AbstractVerticle {
	private DataProducts dataProducts;	

	
	/** 
	 * Metodo responsavel por criar o server, recebe como parametro um future object
	 * que é assíncrono
	 * @param future
	 * @throws InterruptedException
	 */
	@Override
	public void start(Future<Void> future) throws InterruptedException {
		mockOfProducts();
		
		//Criação das Rotas
		Router router = Router.router(vertx);		
		router.route().handler(BodyHandler.create());
		router.post("/api/products").handler(this::addProduct);
		router.get("/api/products").handler(this::getAll);
		router.route("/").handler(ctx -> {
			ctx.response().sendFile("assets/index.html");
		});
		
		//Iniciando HttpServer na porta 9000
		vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 9000),
				result -> {
					if (result.succeeded()) {
						future.complete();
					} else {
						future.fail(result.cause());
					}
				});

		future.complete();
	}

	
	/** 
	 * Metodo responsavel responder a requisição do tipo GET
	 * Ele busca todos os produtos, converte eles em json e atribui esse
	 * json ao corpo da requisição. 
	 * @param routingContext
	*/
	
	private void getAll(RoutingContext routingContext) {
		try {
			Product[] products = dataProducts.getProducts();
			String json = Json.encodePrettily(products);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").end(json);	
		} catch (Exception e) {
			//TODO: handle exception
		}		
	}

	
	/** 
	 * Metodo responsavel responder a requisição do tipo POST
	 * Ele recebe um produto no formato Json digitado pelo usuario, decodifica para um objeto da classe Product
	 * Depois insere esse produto.
	 * Ao final responde a requisição feita com o produto inserido para ser renderizado na tela
	 * @param routingContext
	*/
	private void addProduct(RoutingContext routingContext) {
		Product product = Json.decodeValue(routingContext.getBodyAsString(), Product.class);
		dataProducts.insertProduct(product);
		routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(product));
	}

	/**
	 * Mock com produtos criados e inseridos para que exista dados para exibir na tela
	*/
	public void mockOfProducts() {
		Product p = new Product("Pringles", "Nestle", "Salgadinho", "12");
		Product p1 = new Product("Trakinas", "Mondelez", "Bolacha", "3");
		Product p2 = new Product("Lã de aço", "BomBril", "Limpeza", "2");
		Product p3 = new Product("Cheetos", "Nestle", "Salgadinho", "12");

		this.dataProducts = new DataProducts(new Product[10], new DirInvertedIndex(new HashMap<>()),
				new DirInvertedIndex(new HashMap<>()));

		dataProducts.insertProduct(p);
		dataProducts.insertProduct(p1);
		dataProducts.insertProduct(p2);
		dataProducts.insertProduct(p3);
	}

}
