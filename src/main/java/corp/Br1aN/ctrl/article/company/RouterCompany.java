package corp.Br1aN.ctrl.article.company;

import io.vertx.core.Vertx;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import io.vertx.pgclient.PgPool;

import io.vertx.core.json.JsonObject;

import corp.Br1aN.ctrl.article.company.handlers.AddCompanyHandler;
import corp.Br1aN.ctrl.article.company.handlers.GetCompanyHandler;
import corp.Br1aN.ctrl.article.company.handlers.DelCompanyHandler;
import corp.Br1aN.ctrl.article.company.handlers.PutCompanyHandler;
import corp.Br1aN.ctrl.article.company.handlers.ListCompanyHandler;

public class RouterCompany {

  private Router router = null ;
  private PgPool pool = null;

  private AddCompanyHandler addCompanyHandler = null;
  private GetCompanyHandler getCompanyHandler = null;
  private DelCompanyHandler delCompanyHandler = null;
  private PutCompanyHandler putCompanyHandler = null;
  private ListCompanyHandler listCompanyHandler = null;

  public RouterCompany(){

  }

  public RouterCompany(Router router, PgPool pool){
    this.pool = pool;
    this.router = router;
    this.addCompanyHandler = new AddCompanyHandler(this.pool);
    this.getCompanyHandler = new GetCompanyHandler(this.pool);
    this.delCompanyHandler = new DelCompanyHandler(this.pool);
    this.putCompanyHandler = new PutCompanyHandler(this.pool);
    this.listCompanyHandler = new ListCompanyHandler(this.pool);
  }

  public PgPool getpool(){
    return this.pool;
  }
  public void setpool( PgPool pool ){
    this.pool = pool;
  }
  public Router getRouter(){
    return this.router;
  }
  public void setRouter( Router router ){
    this.router = router;
  }

  public void createRouter(){
    this.router.get("/api/v1/companis").handler(this.listCompanyHandler);
    this.router.post("/api/v1/company").handler(this.addCompanyHandler);
    this.router.post("/api/v1/company/:id").handler(this.putCompanyHandler);
    this.router.get("/api/v1/company/:id").handler(this.getCompanyHandler);
    this.router.get("/api/v1/company/del/:id").handler(this.delCompanyHandler);
    this.router.get("/test").handler( req -> {
      JsonObject json = new JsonObject().put("message", "Hello World");
      req.response()
        .putHeader("content-type", "application/json")
        .end(json.encodePrettily());
    });
  }

}
