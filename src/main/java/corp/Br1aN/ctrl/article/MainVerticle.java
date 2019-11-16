package corp.Br1aN.ctrl.article;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import io.vertx.core.json.JsonObject;

import corp.Br1aN.ctrl.article.MainRouter;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    int port = 8888 ;
    VertxOptions options = new VertxOptions();
    // options.setWorkerPoolSize(40);
    // options.setEventLoopPoolSize(40);
    Vertx vertx = Vertx.vertx(options);

    HttpServer server = vertx.createHttpServer();
    System.out.println("|> Start Server");

    MainRouter mainRouter = new MainRouter(vertx);
    mainRouter.createRouter();

    server.requestHandler(mainRouter.getRouter()).listen(port);
  }
}
