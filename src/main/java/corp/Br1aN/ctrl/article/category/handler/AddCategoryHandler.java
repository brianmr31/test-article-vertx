package corp.Br1aN.ctrl.article.category.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.pgclient.PgPool;

import io.vertx.ext.web.RoutingContext;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddCategoryHandler implements Handler<RoutingContext> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final String INSERT_CATEGORY = "INSERT INTO public.category ( category_name, category_type, category_app_company, category_parent_id, category_queue, category_created_by, category_created_at,"+
                                    "category_updated_by, category_updated_at, category_deleted_flag) VALUES( $1, $2, $3, $4, $5, $6, NOW(), null, null, false)";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public AddCategoryHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      JsonObject dataRequest = context.getBodyAsJson();
      HttpServerResponse response = context.response();
      String parent = null;
      int queue = 0;
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
        parent = dataRequest.getString("parent_id");
        queue = queue + 1;
      }
      if( dataRequest.containsKey("username") == false ){
        this.dataResponse = new JsonObject().put("msg", "username is params missing").put("code","err_category_add").put("data",false);
        response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
      }

      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        Tuple data = Tuple.of(dataRequest.getValue("name"), dataRequest.getValue("type"), dataRequest.getString("app_company"),
                  parent, queue, dataRequest.getValue("username"));
        conn.preparedQuery( INSERT_CATEGORY, data, ar2 -> {
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
