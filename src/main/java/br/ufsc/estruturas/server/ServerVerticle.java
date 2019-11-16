package br.ufsc.estruturas.server;

import java.util.HashMap;

import br.ufsc.estruturas.data.DataProducts;
import br.ufsc.estruturas.indexation.DirInvertedIndex;
import br.ufsc.estruturas.model.Product;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class ServerVerticle extends AbstractVerticle {
	private DataProducts dataProducts;
	

	@Override
	public void start(Future<Void> future) throws InterruptedException {
		testando();
		
		Router router = Router.router(vertx);

		router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html").end("<h1>Meu Primeiro Server com Vertx Funcionando</h1>");
		});

		router.route("/assets/*").handler(StaticHandler.create("assets"));

		router.route("/api/products*").handler(BodyHandler.create());
		router.post("/api/products").handler(this::addProduct);
		router.get("/api/products").handler(this::getAll);
		
		vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 9000),
				result -> {
					if (result.succeeded()) {
						future.complete();
					} else {
						future.fail(result.cause());
					}
				});
	}

	private void getAll(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(dataProducts.getProducts()));
	}

	private void addProduct(RoutingContext routingContext) {
		System.out.println(routingContext.getBodyAsString().length());
		Product product = Json.decodeValue(routingContext.getBodyAsString(), Product.class);
		dataProducts.insertProduct(product);
		routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(product));
	}

	public void testando() {
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
