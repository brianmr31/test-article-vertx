package corp.Br1aN.ctrl.article.setting.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.pgclient.PgPool;

import io.vertx.ext.web.RoutingContext;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;

public class PutSettingHandler implements Handler<RoutingContext> {

  private static final String PUT_SETTING = "UPDATE public.setting SET setting_app_company=$2, setting_name=$3, setting_data=$4, setting_type=$5, setting_updated_by=$6, setting_updated_at=Now() WHERE setting_id=$1";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public PutSettingHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      HttpServerResponse response = context.response();
      JsonObject dataRequest = context.getBodyAsJson();
      JsonObject dataResponse = null;
      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        Tuple data = Tuple.of(Integer.parseInt(context.request().getParam("id")), dataRequest.getValue("app_company"), dataRequest.getValue("name"), dataRequest.getValue("data"), dataRequest.getValue("type"), dataRequest.getValue("username") );
        conn.preparedQuery( PUT_SETTING, data, ar2 -> {
          if (ar2.succeeded()) {
            this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", false );
            response.setStatusCode(200).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
          }else{
            System.out.println("Failure: " + ar2.cause().getMessage());
            this.dataResponse = new JsonObject().put("msg", "data is missing ").put("code","data_is_missing").put("data",false);
            response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
          }
        });
      }else{
        System.out.println("Failure: " + ar.cause().getMessage());
        this.dataResponse = new JsonObject().put("msg", "connection is error").put("code","err_connection").put("data",false);
        response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
    });
  }
}
