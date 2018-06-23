package com.csaura.main;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;


public class Mainmethod {

    public static void main(String [] args){
        Vertx vertx;
        int port = 8081;
        DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", port));
        vertx = Vertx.vertx();


        vertx.deployVerticle(MyFirstVerticle.class.getName(),options);

    }
}
