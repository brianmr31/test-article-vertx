package corp.Br1aN.ctrl.article.company.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.pgclient.PgPool;

import io.vertx.ext.web.RoutingContext;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;

public class PutCompanyHandler implements Handler<RoutingContext> {

  private static final String DEL_SETTING = "DELETE FROM public.setting WHERE setting_id=$1 ";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public PutCompanyHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      JsonObject dataRequest = context.getBodyAsJson();
      HttpServerResponse response = context.response();
      if (ar.succeeded()) {

        if( dataRequest.containsKey("name") == false ){
          this.dataResponse = new JsonObject().put("msg", "company name is params missing").put("code","err_company_add").put("data",false);
          response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
        }
        if( dataRequest.containsKey("ower") == false ){
          this.dataResponse = new JsonObject().put("msg", "company data is params missing").put("code","err_company_add").put("data",false);
          response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
        }
        if( dataRequest.containsKey("valid_to") == false ){
          this.dataResponse = new JsonObject().put("msg", "company valid to is params missing").put("code","err_company_add").put("data",false);
          response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
        }
        if( dataRequest.containsKey("valid_from") == false ){
          this.dataResponse = new JsonObject().put("msg", "company valid from is params missing").put("code","err_company_add").put("data",false);
          response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
        }
        if( dataRequest.containsKey("username") == false ){
          this.dataResponse = new JsonObject().put("msg", "username is params missing").put("code","err_company_add").put("data",false);
          response.setStatusCode(400).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
        }

        SqlConnection conn = ar.result();
        Tuple data = Tuple.of( Integer.parseInt(context.request().getParam("id")) );
        conn.preparedQuery( DEL_SETTING, data, ar2 -> {
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
