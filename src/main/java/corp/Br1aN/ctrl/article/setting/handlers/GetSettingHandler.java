package corp.Br1aN.ctrl.article.setting.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.pgclient.PgPool;

import io.vertx.ext.web.RoutingContext;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Row;

import corp.Br1aN.ctrl.article.setting.models.Setting;

public class GetSettingHandler implements Handler<RoutingContext> {

  private static final String GET_SETTING = "SELECT setting_id, setting_app_company, setting_name, setting_data, setting_type, setting_created_by, setting_created_at, setting_updated_by, setting_updated_at, "+
                                "setting_deleted_flag from setting where setting_id = $1 limit 1";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  private Tuple data = null;

  public GetSettingHandler( PgPool pool ){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      HttpServerResponse response = context.response();
      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        Tuple data = Tuple.of( Integer.parseInt(context.request().getParam("id")) );
        conn.preparedQuery( GET_SETTING, data, ar2 -> {
          if (ar2.succeeded()) {
            RowSet<Row> rows = ar2.result();
            if( rows.size() == 0 ){
              this.dataResponse = new JsonObject().put("msg", "data is missing").put("code","data_is_missing").put("data",false);
              response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            }else {
              Setting setting = null ;
              for (Row row : rows) {
                setting = new Setting(row.getLong(0), row.getString(1), row.getString(2), row.getString(3), row.getString(4), row.getString(5), row.getLocalDateTime(6),
                    row.getString(7), row.getLocalDateTime(8), row.getBoolean(9) );
              }
              this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", setting.toJsonObject() );
              response.setStatusCode(200).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            }
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
