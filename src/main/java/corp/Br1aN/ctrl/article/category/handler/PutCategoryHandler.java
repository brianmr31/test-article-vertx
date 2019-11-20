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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PutCategoryHandler implements Handler<RoutingContext> {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final String PUT_CATEGORY = "UPDATE public.category SET category_name=$2, category_type=$3, category_app_company=$4, category_parent_id=$5, category_queue=$6, category_updated_by=$7, "+
                                      "category_updated_at=Now() WHERE category_id=$1";

  private JsonObject dataResponse = null;
  private String parent = "0";
  private int queue = 0;

  private PgPool pool = null;

  public PutCategoryHandler(PgPool pool){
    this.pool = pool;
    this.dataResponse = null;
    this.parent = "0";
    this.queue = 0;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      JsonObject dataRequest = context.getBodyAsJson();
      HttpServerResponse response = context.response();

      if( dataRequest.containsKey("name") == false ){
        this.dataResponse = new JsonObject().put("msg", "category name is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("type") == false ){
        this.dataResponse = new JsonObject().put("msg", "category data is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("app_company") == false ){
        this.dataResponse = new JsonObject().put("msg", "category status to is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("parent_id") != false ){
        this.parent = dataRequest.getString("parent_id");
      }
      if( dataRequest.containsKey("username") == false ){
        this.dataResponse = new JsonObject().put("msg", "username is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }

      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        conn.preparedQuery("select category_id, category_name, category_queue from public.category where category_id=$1 limit 1", Tuple.of( Long.parseLong(this.parent) ), parent_res -> {
          RowSet<Row> parent_categorys = parent_res.result();
          if( parent_categorys.rowCount() != 0 ){
            for (Row row_parent : parent_categorys) {
              if( row_parent != null ){
                this.queue = row_parent.getInteger(2) + 1 ;
              }
            }
          }

          if (parent_res.succeeded()) {
            Tuple data = Tuple.of(Integer.parseInt(context.request().getParam("id")), dataRequest.getValue("name"), dataRequest.getValue("type"), dataRequest.getValue("app_company"),
                  this.parent, this.queue, dataRequest.getValue("username") );
            conn.preparedQuery( PUT_CATEGORY, data, ar2 -> {
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
            System.out.println("Failure: " + parent_res.cause().getMessage());
            this.dataResponse = new JsonObject().put("msg", "connection is error").put("code","err_connection").put("data",false);
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
