package corp.Br1aN.ctrl.article.tempalte.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.ext.web.RoutingContext;

import io.vertx.pgclient.PgPool;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;

public class DelTemplateHandler implements Handler<RoutingContext> {
  private static final String DEL = "DELETE FROM public.tempalte WHERE tempalte_id=$1 ";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public DelTemplateHandler( PgPool pool ){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {

    });
  }
}
