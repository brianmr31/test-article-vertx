package corp.Br1aN.ctrl.article.content.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.ext.web.RoutingContext;

import io.vertx.pgclient.PgPool;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;

public class PutContentHandler implements Handler<RoutingContext> {
  private static final String INSERT_SETTING = "INSERT INTO public.setting ( setting_app_company, setting_name, setting_data, setting_type, setting_created_by, setting_created_at,"+
                                    "setting_updated_by, setting_updated_at, setting_deleted_flag) VALUES( $1, $2, $3, $4, $5, NOW(), null, null, false)";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public PutContentHandler( PgPool pool ){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {

    });
  }
}
