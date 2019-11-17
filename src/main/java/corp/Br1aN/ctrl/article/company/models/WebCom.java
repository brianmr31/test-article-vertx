package corp.Br1aN.ctrl.article.company.models;

import corp.Br1aN.ctrl.article.master.models.Model;

public class WebCom extends Model {

  private Long company_id;
  private String company_name;
  private String company_owner;

  public WebCom(){

  }
  public WebCom(Long company_id, String company_name, String company_owner){
    this.company_id = company_id;
    this.company_name = company_name;
    this.company_owner = company_owner;
  }

  public Long getCompanyId(){
    return this.company_id;
  }
  public void setCompanyId(Long company_id){
    this.company_id = company_id;
  }
  public String getCompanyName(){
    return this.company_name;
  }
  public void setCompanyName(String company_name){
    this.company_name = company_name;
  }
  public String getCompanyOwner(){
    return this.company_owner;
  }
  public void setCompanyOwner(String company_owner){
    this.company_owner = company_owner;
  }
}
