package corp.Br1aN.ctrl.article;

import io.vertx.core.Vertx;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import io.vertx.pgclient.PgPool;

import io.vertx.core.json.JsonObject;

import corp.Br1aN.ctrl.article.setting.RouterSetting;
import corp.Br1aN.ctrl.article.company.RouterCompany;
import corp.Br1aN.ctrl.article.Connection;

public class MainRouter {

  private Router router = null ;
  private PgPool pool = null;

  private RouterSetting routerSetting = null;
  private RouterCompany routerCompany = null;

  private Connection connection = null;

  public MainRouter(){

  }

  public MainRouter(Vertx vertx){
    this.connection = new Connection( vertx );
    this.pool = this.connection.getPool() ;
    this.router = Router.router(vertx);
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
    this.router.route().handler(BodyHandler.create());
    this.routerSetting = new RouterSetting(this.router, this.pool);
    this.routerSetting.createRouter();
    this.routerCompany = new RouterCompany(this.routerSetting.getRouter(), this.pool);
    this.routerCompany.createRouter();
    this.setRouter(this.routerCompany.getRouter());
  }

}
