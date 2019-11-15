package br.ufsc.estruturas.server;

import java.util.HashMap;

import br.ufsc.estruturas.data.DataProdutos;
import br.ufsc.estruturas.indexation.DirInvertedIndex;
import br.ufsc.estruturas.model.Product;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class ServerVerticle extends AbstractVerticle {
	DataProdutos dataProdutos;

	@Override
	public void start(Future<Void> future) {
		testando();
		Router router = Router.router(vertx);

		/*router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html").end("<h1>Meu Primeiro Server com Vertx Funcionando</h1>");
		});
		*/

		router.route("/").handler(StaticHandler.create("assets"));
		router.get("/api/getAllProducts").handler(this::getAll);

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
				.end(Json.encodePrettily(dataProdutos.getByLabel("Nestle")));
	}

	public void testando() {
		Product p = new Product("Pringles", "Nestle", "Salgadinho", 12);
		Product p1 = new Product("Trakinas", "Mondelez", "Bolacha", 3);
		Product p2 = new Product("Lã de aço", "BomBril", "Limpeza", 2);
		Product p3 = new Product("Cheetos", "Nestle", "Salgadinho", 12);

		this.dataProdutos = new DataProdutos(new Product[10], new DirInvertedIndex(new HashMap<>()),
				new DirInvertedIndex(new HashMap<>()));

		dataProdutos.insertProduct(p);
		dataProdutos.insertProduct(p1);
		dataProdutos.insertProduct(p2);
		dataProdutos.insertProduct(p3);
	}

}
