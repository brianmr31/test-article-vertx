package corp.Br1aN.ctrl.article.tempalte.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import corp.Br1aN.ctrl.article.master.models.Model;

public class Template extends Model {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private long template_id;

  private String template_name;
  private String template_params;
  private String template_desc;

  private String template_app_company;

  private String template_created_by;
  private String template_created_at;
  private String template_updated_by;
  private String template_updated_at;
  private boolean template_deleted_flag;

  public Template(){

  }

  public Long getTemplateId(){
    return this.template_id;
  }
  public void setTemplateId(long template_id){
    this.template_id = template_id;
  }

  public String getTemplateName(){
    return this.template_name;
  }
  public void setTemplateName(String template_name){
    this.template_name = template_name;
  }

  public String getTemplateParams(){
    return this.template_params;
  }
  public void setTemplateParams(String tempalte_params){
    this.template_params = template_params;
  }

  public String getTemplateDesc(){
    return this.template_desc;
  }
  public void setTemplateDesc(String template_desc){
    this.template_desc = template_desc;
  }

  public String getTemplateAppCompany(){
    return this.template_app_company;
  }
  public void setTemplateAppCompany(String template_app_company){
    this.template_app_company = template_app_company;
  }

  public String getTemplateCreatedBy(){
    return this.template_created_by;
  }
  public void setTemplateCreatedBy(String template_created_by){
    this.template_created_by = template_created_by;
  }
  public String getTemplateCreatedAt(){
    return this.template_created_at;
  }
  public void setTemplateCreatedAt(LocalDateTime template_created_at){
    this.template_created_at = template_created_at.format(formatter);
  }

  public String getTemplateUpdatedBy(){
    return this.template_updated_by;
  }
  public void setTemplateUpdatedBy(String template_updated_by){
    this.template_updated_by = template_updated_by;
  }
  public String getTemplateUpdatedAt(){
    return this.template_updated_at;
  }
  public void setTemplateUpdatedAt(LocalDateTime template_updated_at){
    this.template_updated_at = template_updated_at.format(formatter);
  }

  public boolean getTemplateDeletedFlag(){
    return this.template_deleted_flag;
  }
  public void setTemplateDeletedFlag(boolean template_deleted_flag){
    this.template_deleted_flag = template_deleted_flag;
  }
}
