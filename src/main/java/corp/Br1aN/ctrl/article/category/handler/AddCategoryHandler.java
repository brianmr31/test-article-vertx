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

public class AddCategoryHandler implements Handler<RoutingContext> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final String INSERT_CATEGORY = "INSERT INTO public.category ( category_name, category_type, category_app_company, category_parent_id, category_queue, category_created_by, category_created_at,"+
                                    "category_updated_by, category_updated_at, category_deleted_flag) VALUES( $1, $2, $3, $4, $5, $6, NOW(), null, null, false)";

  private JsonObject dataResponse = null;
  private String parent = "0";
  private int queue = 0;

  private PgPool pool = null;

  public AddCategoryHandler(PgPool pool){
    this.pool = pool;
    this.dataResponse = null;
    this.parent = "0";
    this.queue = 0;
  }
  public void clear(){
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
        this.dataResponse = new JsonObject().put("msg", "category type is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("app_company") == false ){
        this.dataResponse = new JsonObject().put("msg", "category app_company is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }
      if( dataRequest.containsKey("parent_id") != false ){
        this.parent = dataRequest.getString("parent_id");
        // queue = queue + 1;
      }
      if( dataRequest.containsKey("username") == false ){
        this.dataResponse = new JsonObject().put("msg", "username is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }

      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        conn.preparedQuery("select category_id, category_name, category_queue from public.category where category_id=$1 limit 1", Tuple.of( Long.parseLong(parent) ), parent_res -> {
          RowSet<Row> parent_categorys = parent_res.result();
          if( parent_categorys.rowCount() != 0 ){
            for (Row row_parent : parent_categorys) {
              if( row_parent != null ){
                this.queue = row_parent.getInteger(2) + 1 ;
              }
            }
          }

          System.out.println("Parent : "+this.queue);
          Tuple data = Tuple.of(dataRequest.getValue("name"), dataRequest.getValue("type"), dataRequest.getString("app_company"),
                    this.parent, this.queue, dataRequest.getValue("username"));
          if (parent_res.succeeded()) {
            conn.preparedQuery( INSERT_CATEGORY, data, ar2 -> {
              if (ar2.succeeded()) {
                this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", false );
                response.setStatusCode(200).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
                this.clear();
              }else{
                System.out.println("Failure: " + ar2.cause().getMessage());
                this.dataResponse = new JsonObject().put("msg", "data is missing ").put("code","data_is_missing").put("data",false);
                response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
                this.clear();
              }
            });
          }else{
            System.out.println("Failure: " + parent_res.cause().getMessage());
            this.dataResponse = new JsonObject().put("msg", "data is missing ").put("code","data_is_missing").put("data",false);
            response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            this.clear();
          }
        });
      }else{
        System.out.println("Failure: " + ar.cause().getMessage());
        this.dataResponse = new JsonObject().put("msg", "connection is error").put("code","err_connection").put("data",false);
        response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
        this.clear();
      }
    });
  }
}
