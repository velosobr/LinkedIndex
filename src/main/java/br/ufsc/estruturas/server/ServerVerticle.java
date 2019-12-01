package br.ufsc.estruturas.server;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import br.ufsc.estruturas.data.DataProducts;
import br.ufsc.estruturas.indexation.DirInvertedIndex;
import br.ufsc.estruturas.model.Product;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
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
		router.post("/api/label").handler(this::getProductsByLabel);
		router.post("/api/type").handler(this::getProductsByType);
		router.post("/api/typelabel").handler(this::getProductsByTypeAndLabel);
		router.post("/api/id").handler(this::getProductsById);
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
			e.printStackTrace();
		}		
	}

	/** 
	 * Metodo responsavel responder a requisição do tipo POST
	 * Ele busca todos os produtos por Label, converte eles em json e atribui esse
	 * json ao corpo da requisição, se não existir retorna null
	 * @param routingContext
	*/
	private void getProductsByLabel(RoutingContext routingContext) {
		try {
			JsonObject jsonObject = routingContext.getBodyAsJson();
			List<Product> products = dataProducts.getByLabel(jsonObject.getString("label"));
			String json = Json.encodePrettily(products);
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(json));	
		} catch (NullPointerException e) {
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(null));	
		} catch (Exception ex){

		}	
	}

	/** 
	 * Metodo responsavel responder a requisição do tipo POST
	 * Ele busca todo o produtos por id, converte ele em json e atribui esse
	 * json ao corpo da requisição, se não existir retorna null
	 * @param routingContext
	*/
	private void getProductsById(RoutingContext routingContext) {
		try {
			Product[] product = new Product[1];
			JsonObject jsonObject = routingContext.getBodyAsJson();
			product[0] = dataProducts.getById(Integer.parseInt(jsonObject.getString("id")));
			String json = Json.encodePrettily(product);
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(json));	
		} catch (NullPointerException e) {
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(null));	
		} catch (Exception ex){

		}	
	}

	/** 
	 * Metodo responsavel responder a requisição do tipo POST
	 * Ele busca todos os produtos por Type, converte eles em json e atribui esse
	 * json ao corpo da requisição, se não existir retorna null 
	 * @param routingContext
	*/
	private void getProductsByType(RoutingContext routingContext) {
		try {
			JsonObject jsonObject = routingContext.getBodyAsJson();
			List<Product> products = dataProducts.getByType(jsonObject.getString("type"));
			String json = Json.encodePrettily(products);
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(json));	
		} catch (NullPointerException e) {
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(null));	
		} catch (Exception ex){

		}	
	}

	/** 
	 * Metodo responsavel responder a requisição do tipo POST
	 * Ele busca todos os produtos por Type e Label, verifica qual é a menor lista e faz a união dos
	 * produtos encontrados, converte eles em json e atribui esse
	 * json ao corpo da requisição, se não existir retorna null
	 * @param routingContext
	*/
	private void getProductsByTypeAndLabel(RoutingContext routingContext) {
		try {
			JsonObject jsonObject = routingContext.getBodyAsJson();
			String type = jsonObject.getString("type");
			String label = jsonObject.getString("label");
			List<Product> productsByType = dataProducts.getByType(type);
			List<Product> productsByLabel = dataProducts.getByLabel(label);
			List<Product> products = new ArrayList<>();
			if(productsByType.size() > productsByLabel.size()){
				for (Product product : productsByLabel) {
					if(productsByType.contains(product)){
						products.add(product);
					}
				}
			} else{
				for (Product product : productsByType) {
					if(productsByLabel.contains(product)){
						products.add(product);
					}
				}
			}
			String json = Json.encodePrettily(products);
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(json));	
		} catch (NullPointerException e) {
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
		 		.end(Json.encodePrettily(null));	
		} catch (Exception ex){

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
		Product p = new Product(1, "Pringles", "Nestle", "Salgadinho", "12");
		Product p1 = new Product(2, "Trakinas", "Mondelez", "Bolacha", "3");
		Product p2 = new Product(3, "Lã de aço", "BomBril", "Limpeza", "2");
		Product p3 = new Product(4, "Cheetos", "Nestle", "Salgadinho", "12");

		this.dataProducts = new DataProducts(new Product[10], new DirInvertedIndex(new TreeMap<>(String.CASE_INSENSITIVE_ORDER)),
				new DirInvertedIndex(new TreeMap<>(String.CASE_INSENSITIVE_ORDER)));

		dataProducts.insertProduct(p);
		dataProducts.insertProduct(p1);
		dataProducts.insertProduct(p2);
		dataProducts.insertProduct(p3);
	}

}
