package corp.Br1aN.ctrl.article.company.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.pgclient.PgPool;

import io.vertx.ext.web.RoutingContext;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddCompanyHandler implements Handler<RoutingContext> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final String INSERT_COMPANY = "INSERT INTO public.company ( company_name, company_owner, company_status, company_valid_to, company_valid_from, company_created_by, company_created_at,"+
                                    "company_updated_by, company_updated_at, company_deleted_flag) VALUES( $1, $2, 'aktif',$3, $4, $5, NOW(), null, null, false)";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public AddCompanyHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      JsonObject dataRequest = context.getBodyAsJson();
      HttpServerResponse response = context.response();

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

      JsonObject dataResponse = null;
      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        Tuple data = Tuple.of(dataRequest.getValue("name"), dataRequest.getValue("ower"), LocalDateTime.parse(dataRequest.getString("valid_to"), formatter),
                  LocalDateTime.parse(dataRequest.getString("valid_from"), formatter), dataRequest.getValue("username"));
        conn.preparedQuery( INSERT_COMPANY, data, ar2 -> {
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
