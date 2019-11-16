package corp.Br1aN.ctrl.article.setting.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.ext.web.RoutingContext;

import io.vertx.pgclient.PgPool;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;

public class AddSettingHandler implements Handler<RoutingContext> {

  private static final String INSERT_SETTING = "INSERT INTO public.setting ( setting_app_company, setting_name, setting_data, setting_type, setting_created_by, setting_created_at,"+
                                    "setting_updated_by, setting_updated_at, setting_deleted_flag) VALUES( $1, $2, $3, $4, $5, NOW(), null, null, false)";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public AddSettingHandler( PgPool pool ){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {

    this.pool.getConnection( ar -> {
      JsonObject dataRequest = context.getBodyAsJson();
      HttpServerResponse response = context.response();

      if( dataRequest.containsKey("app_company") == false ){
        this.dataResponse = new JsonObject().put("msg", "setting app company is params missing").put("code","err_setting_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("name") == false ){
        this.dataResponse = new JsonObject().put("msg", "setting name is params missing").put("code","err_setting_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("data") == false ){
        this.dataResponse = new JsonObject().put("msg", "setting data is params missing").put("code","err_setting_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("type") == false ){
        this.dataResponse = new JsonObject().put("msg", "setting type is params missing").put("code","err_setting_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("username") == false ){
        this.dataResponse = new JsonObject().put("msg", "username is params missing").put("code","err_setting_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }

      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        Tuple data = Tuple.of(dataRequest.getValue("app_company"), dataRequest.getValue("name"), dataRequest.getValue("data"), dataRequest.getValue("type"), dataRequest.getValue("username"));
        conn.preparedQuery( INSERT_SETTING, data, ar2 -> {
          if (ar2.succeeded()) {
            this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data",false);
            response.setStatusCode(200).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
          } else {
            System.out.println("Failure: " + ar2.cause().getMessage());
            this.dataResponse = new JsonObject().put("msg", "error insert setting").put("code","err_insert_setting").put("data",false);
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
