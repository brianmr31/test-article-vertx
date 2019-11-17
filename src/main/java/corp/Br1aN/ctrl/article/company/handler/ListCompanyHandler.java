package corp.Br1aN.ctrl.article.company.handlers;

import java.util.List;
import java.util.ArrayList;

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

public class ListCompanyHandler implements Handler<RoutingContext> {

  private static final String LIST_COMPANY = "SELECT company_id, company_name, company_owner, company_status, company_valid_from, company_valid_to, company_created_by, company_created_at, " +
                            "company_updated_by, company_updated_at, company_deleted_flag FROM public.company ";
  private static final String LIST_COUNT_COMPANY = "SELECT count(company_id) as count FROM public.company ";
  private static final String STANDARD_QUERY = " limit $1 offset $2 ";
  private JsonObject dataResponse = null;

  private PgPool pool = null;

  private String finalQuery = "";
  private String totalFinalQuery = "";
  private int limit = 0;
  private int offset = 0;
  private String order = "";
  private String where = "";
  private Tuple data ;

  private SqlConnection conn;

  public ListCompanyHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {

      List<String> paramLimit = context.queryParam("size");
      if( paramLimit.isEmpty() ){
        this.limit = 10;
      }else{
        this.limit = Integer.parseInt(paramLimit.get(0));
      }
      List<String> paramsOffset = context.queryParam("page");
      if( paramsOffset.isEmpty() ){
        this.offset = 0;
      }else{
        this.offset = Integer.parseInt(paramsOffset.get(0));
      }
      List<String> paramsOrder = context.queryParam("order");
      if( paramsOrder.isEmpty() ){
        this.order = " company_id asc ";
      }else{
        // System.out.println("paramsOrder "+paramsOrder.get(0));
        this.order = paramsOrder.get(0);
      }
      this.data = Tuple.of( this.limit, this.offset);
      List<String> paramsWhere = context.queryParam("where");
      if( paramsWhere.isEmpty() ){
        this.where = " ";
        this.finalQuery = LIST_COMPANY + " order by " + this.order + STANDARD_QUERY;
        this.totalFinalQuery = LIST_COUNT_COMPANY;
      }else{
        System.out.println("paramsWhere "+paramsWhere.get(0));
        this.where = paramsWhere.get(0);
        this.finalQuery = LIST_COMPANY+" where "+this.where+" order by "+this.order+STANDARD_QUERY;
        this.totalFinalQuery = LIST_COUNT_COMPANY+" where "+this.where;
      }
      HttpServerResponse response = context.response();

      if (ar.succeeded()) {
        this.conn = ar.result();
        this.conn.preparedQuery( this.finalQuery, this.data, ar2 -> {
          if (ar2.succeeded()) {
            RowSet<Row> rows = ar2.result();
            if( rows.size() == 0 ){
              this.dataResponse = new JsonObject().put("msg", "data is missing ").put("code","data_is_missing").put("data",false);
              response.setStatusCode(500).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            }else {
              List<Company> company = new ArrayList<Company>() ;
              for (Row row : rows) {
                company.add( new Company(row.getLong(0), row.getString(1), row.getString(2), row.getString(3), row.getLocalDateTime(4), row.getLocalDateTime(5), row.getString(6), row.getLocalDateTime(7),
                    row.getString(8), row.getLocalDateTime(9), row.getBoolean(10) ) );
              }
              this.conn.query( this.totalFinalQuery, ar3 -> {
                RowSet<Row> counts = ar3.result();
                Long total = 0L;
                for (Row row : counts) {
                  total = row.getLong(0) ;
                  // System.out.println("total "+row.getLong(0));
                }
                this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", new JsonObject().put("content", company).put("total", total) );
                response.setStatusCode(200).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
              });
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
