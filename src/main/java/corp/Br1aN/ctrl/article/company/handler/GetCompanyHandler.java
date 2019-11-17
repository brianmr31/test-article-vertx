package corp.Br1aN.ctrl.article.company.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.pgclient.PgPool;

import io.vertx.ext.web.RoutingContext;

import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Row;

import corp.Br1aN.ctrl.article.company.models.Company;

public class GetCompanyHandler implements Handler<RoutingContext> {

  private static final String GET_COMPANY = "SELECT company_id, company_name, company_owner, company_valid_from, company_valid_to, company_created_by, company_created_at, "+
    "company_updated_by, company_updated_at, company_deleted_flag FROM public.company WHERE company_id=$1 and company_deleted_flag = false limit 1";

  private JsonObject dataResponse = null;

  private PgPool pool = null;

  public GetCompanyHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      HttpServerResponse response = context.response();
      JsonObject dataResponse = null;
      if (ar.succeeded()) {
        SqlConnection conn = ar.result();
        Tuple data = Tuple.of( Integer.parseInt(context.request().getParam("id")) );
        conn.preparedQuery( GET_COMPANY, data, ar2 -> {
          if (ar2.succeeded()) {
            RowSet<Row> rows = ar2.result();
            if( rows.size() == 0 ){
              this.dataResponse = new JsonObject().put("msg", "data is missing").put("code","data_is_missing").put("data",false);
              response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            }else {
              Company company = null ;
              for (Row row : rows) {
                company = new Company(row.getLong(0), row.getString(1), row.getString(2), row.getLocalDateTime(3), row.getLocalDateTime(4), row.getString(5), row.getLocalDateTime(6),
                    row.getString(7), row.getLocalDateTime(8), row.getBoolean(9) );
              }
              this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", company.toJsonObject() );
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
