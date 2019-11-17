package corp.Br1aN.ctrl.article.setting.handlers;

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

import corp.Br1aN.ctrl.article.setting.models.WebConf;

public class WebSettingHandler implements Handler<RoutingContext> {

  private static final String LIST_SETTING = "SELECT setting_name, setting_data, setting_type FROM public.setting ";
  private static final String LIST_COUNT_SETTING = "SELECT count(setting_id) as count FROM public.setting ";
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

  public WebSettingHandler(PgPool pool){
    this.pool = pool;
  }
  public void handle(RoutingContext context) {
    this.pool.getConnection( ar -> {
      String setting_app_company = "testting";
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
        this.order = " setting_id asc ";
      }else{
        // System.out.println("paramsOrder "+paramsOrder.get(0));
        this.order = paramsOrder.get(0);
      }
      this.data = Tuple.of( this.limit, this.offset);
      List<String> paramsWhere = context.queryParam("where");
      if( paramsWhere.isEmpty() ){
        this.where = " ";
        this.finalQuery = LIST_SETTING + " where setting_app_company = '"+setting_app_company+"' and setting_deleted_flag = false order by " + this.order + STANDARD_QUERY;
        this.totalFinalQuery = LIST_COUNT_SETTING + " where setting_app_company = '"+setting_app_company+"' and setting_deleted_flag = false ";
      }else{
        System.out.println("paramsWhere "+paramsWhere.get(0));
        this.where = paramsWhere.get(0);
        this.finalQuery = LIST_SETTING+" where setting_app_company = '"+setting_app_company+"' and setting_deleted_flag = false and "+this.where+" order by "+this.order+STANDARD_QUERY;
        this.totalFinalQuery = LIST_COUNT_SETTING+" where setting_app_company = '"+setting_app_company+"' and setting_deleted_flag = false and "+this.where;
      }
      HttpServerResponse response = context.response();

      if (ar.succeeded()) {
        this.conn = ar.result();
        this.conn.preparedQuery( this.finalQuery, this.data, ar2 -> {
          if (ar2.succeeded()) {
            RowSet<Row> rows = ar2.result();
            if( rows.size() == 0 ){
              this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", new JsonObject().put("content", "false").put("total", 0) );
              response.setStatusCode(200).putHeader("content-type", "application/json").end(this.dataResponse.encodePrettily());
            }else {
              List<WebConf> setting = new ArrayList<WebConf>() ;
              for (Row row : rows) {
                setting.add(new WebConf(row.getString(0), row.getString(1), row.getString(2) ) );
              }
              this.conn.query( this.totalFinalQuery, ar3 -> {
                RowSet<Row> counts = ar3.result();
                Long total = 0L;
                for (Row row : counts) {
                  total = row.getLong(0) ;
                  // System.out.println("total "+row.getLong(0));
                }
                this.dataResponse = new JsonObject().put("msg", "ok").put("code","ok").put("data", new JsonObject().put("content", setting).put("total", total) );
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
