package corp.Br1aN.ctrl.article;

import io.vertx.core.Vertx;

import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.pgclient.PgPool;

public class Connection {

  private PgConnectOptions connectOptions = null;
  private PoolOptions poolOptions = null;
  private PgPool pool = null;

  private int port = 5432 ;
  private String host = "localhost" ;
  private String database = "testmac" ;
  private String username = "admin" ;
  private String password = "password" ;

  public Connection( Vertx vertx ) {
    this.connectOptions = new PgConnectOptions()
      .setPort(this.port)
      .setHost(this.host)
      .setDatabase(this.database)
      .setUser(this.username)
      .setPassword(this.password);

    // Pool Options
    this.poolOptions = new PoolOptions().setMaxSize(10000);

    // Create the pool from the data object
    this.pool = PgPool.pool(vertx, connectOptions, poolOptions);
  }
  public PgConnectOptions getConnection(){
    return this.connectOptions ;
  }
  public PoolOptions getOptions(){
    return this.poolOptions ;
  }
  public PgPool getPool(){
    return this.pool ;
  }

}
