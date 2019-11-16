package corp.Br1aN.ctrl.article.setting;

import io.vertx.core.Vertx;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import io.vertx.pgclient.PgPool;

import io.vertx.core.json.JsonObject;

import corp.Br1aN.ctrl.article.setting.handlers.AddSettingHandler;
import corp.Br1aN.ctrl.article.setting.handlers.GetSettingHandler;
import corp.Br1aN.ctrl.article.setting.handlers.DelSettingHandler;
import corp.Br1aN.ctrl.article.setting.handlers.PutSettingHandler;
import corp.Br1aN.ctrl.article.setting.handlers.ListSettingHandler;

public class RouterSetting {

  private Router router = null ;
  private PgPool pool = null;

  private AddSettingHandler addSettingHandler = null;
  private GetSettingHandler getSettingHandler = null;
  private DelSettingHandler delSettingHandler = null;
  private PutSettingHandler putSettingHandler = null;
  private ListSettingHandler listSettingHandler = null;

  public RouterSetting(){

  }

  public RouterSetting(Router router, PgPool pool){
    this.pool = pool;
    this.router = router;
    this.addSettingHandler = new AddSettingHandler(this.pool);
    this.getSettingHandler = new GetSettingHandler(this.pool);
    this.delSettingHandler = new DelSettingHandler(this.pool);
    this.putSettingHandler = new PutSettingHandler(this.pool);
    this.listSettingHandler = new ListSettingHandler(this.pool);
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
    this.router.get("/api/v1/settings").handler(this.listSettingHandler);
    this.router.post("/api/v1/setting").handler(this.addSettingHandler);
    this.router.post("/api/v1/setting/:id").handler(this.putSettingHandler);
    this.router.get("/api/v1/setting/:id").handler(this.getSettingHandler);
    this.router.get("/api/v1/setting/del/:id").handler(this.delSettingHandler);
    this.router.get("/test").handler( req -> {
      JsonObject json = new JsonObject().put("message", "Hello World");
      req.response()
        .putHeader("content-type", "application/json")
        .end(json.encodePrettily());
    });
  }

}
