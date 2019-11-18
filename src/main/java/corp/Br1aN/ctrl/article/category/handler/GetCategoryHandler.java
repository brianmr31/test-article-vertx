package corp.Br1aN.ctrl.article.category.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.pgclient.PgPool;

import io.vertx.ext.web.RoutingContext;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Row;

import corp.Br1aN.ctrl.article.category.models.Category;

public class GetCategoryHandler implements Handler<RoutingContext> {

  private static final String GET_CATEGORY = "SELECT category_id, category_name, category_type, category_app_company, category_parent_id, category_queue, category_created_by, category_created_at, "+
    "category_updated_by, category_updated_at, category_deleted_flag FROM public.category WHERE category_id=$1 limit 1";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public GetCategoryHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      HttpServerResponse response = context.response();
      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        Tuple data = Tuple.of( Integer.parseInt(context.request().getParam("id")) );
        conn.preparedQuery( GET_CATEGORY, data, ar2 -> {
          if (ar2.succeeded()) {
            RowSet<Row> rows = ar2.result();
            if( rows.size() == 0 ){
              this.dataResponse = new JsonObject().put("msg", "data is missing").put("code","data_is_missing").put("data",false);
              response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            }else {
              Category category = null ;
              for (Row row : rows) {
                category = new Category(row.getLong(0), row.getString(1), row.getString(2), row.getString(3), row.getLong(4), row.getLong(5), row.getString(6), row.getLocalDateTime(7),
                    row.getString(8), row.getLocalDateTime(9), row.getBoolean(10) );
              }
              this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", category.toJsonObject() );
              response.setStatusCode(200).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            }
          }else{
            System.out.println("Failure: " + ar2.cause().getMessage());
            this.dataResponse = new JsonObject().put("msg", "data is missing").put("code","data_is_missing").put("data",false);
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
