package com.csaura.main;

import com.csaura.data.Whiskey;
import com.csaura.dataGeneration.Generator;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.Map;

public class MyFirstVerticle extends AbstractVerticle {

    Generator generator = new Generator();
    Map<Integer, Whiskey> products = generator.createSomeData();

    @Override
    public void start(Future<Void> fut) {



        // Create a router object.
        Router router = Router.router(vertx);

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
        });


        router.route("/assets/*").handler(StaticHandler.create("assets"));

        router.get("/api/whiskies").handler(this::getAll);

        //enables the reading of the request body for all routes under “/api/whiskies”
        router.route("/api/whiskies*").handler(BodyHandler.create());
        router.post("/api/whiskies").handler(this::addOne);

        router.delete("/api/whiskies/:id").handler(routingContext->{
            String id = routingContext.request().getParam("id");
            if (id == null) {
                routingContext.response().setStatusCode(400).end();
            } else {
                Integer idAsInteger = Integer.valueOf(id);
                products.remove(idAsInteger);
            }
            routingContext.response().setStatusCode(204).end();
        });


        vertx.createHttpServer().requestHandler(router::accept)
                .listen(config().getInteger("http.port",8080), result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }

    private void addOne(RoutingContext routingContext) {
        final Whiskey whisky = Json.decodeValue(routingContext.getBodyAsString(),
                Whiskey.class);
        products.put(whisky.getId(), whisky);
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(whisky));
    }

    private void getAll(RoutingContext routingContext) {

        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(products.values()));
    }

}
