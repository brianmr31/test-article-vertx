package corp.Br1aN.ctrl.article.category;

import io.vertx.core.Vertx;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import io.vertx.pgclient.PgPool;

import io.vertx.core.json.JsonObject;

import corp.Br1aN.ctrl.article.category.handlers.AddCategoryHandler;
import corp.Br1aN.ctrl.article.category.handlers.GetCategoryHandler;
import corp.Br1aN.ctrl.article.category.handlers.DelCategoryHandler;
import corp.Br1aN.ctrl.article.category.handlers.PutCategoryHandler;
import corp.Br1aN.ctrl.article.category.handlers.ListCategoryHandler;
// import corp.Br1aN.ctrl.article.category.handlers.WebCategoryHandler;

public class RouterCategory {

  private Router router = null ;
  private PgPool pool = null;

  private AddCategoryHandler addCategoryHandler = null;
  private GetCategoryHandler getCategoryHandler = null;
  private DelCategoryHandler delCategoryHandler = null;
  private PutCategoryHandler putCategoryHandler = null;
  private ListCategoryHandler listCategoryHandler = null;
  // private WebCategoryHandler webCategoryHandler = null;

  public RouterCategory(){

  }

  public RouterCategory(Router router, PgPool pool){
    this.pool = pool;
    this.router = router;
    this.addCategoryHandler = new AddCategoryHandler(this.pool);
    this.getCategoryHandler = new GetCategoryHandler(this.pool);
    this.delCategoryHandler = new DelCategoryHandler(this.pool);
    this.putCategoryHandler = new PutCategoryHandler(this.pool);
    this.listCategoryHandler = new ListCategoryHandler(this.pool);
    // this.webCategoryHandler = new WebCategoryHandler(this.pool);
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
    this.router.get("/api/v1/categoris").handler(this.listCategoryHandler);
    // this.router.get("/api/v1/webcoms").handler(this.webCategoryHandler);
    this.router.post("/api/v1/category").handler(this.addCategoryHandler);
    this.router.post("/api/v1/category/:id").handler(this.putCategoryHandler);
    this.router.get("/api/v1/category/:id").handler(this.getCategoryHandler);
    this.router.get("/api/v1/category/del/:id").handler(this.delCategoryHandler);
    this.router.get("/test").handler( req -> {
      JsonObject json = new JsonObject().put("message", "Hello World");
      req.response()
        .putHeader("content-type", "application/json")
        .end(json.encodePrettily());
    });
  }

}
