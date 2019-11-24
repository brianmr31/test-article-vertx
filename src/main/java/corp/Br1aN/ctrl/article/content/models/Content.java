package corp.Br1aN.ctrl.article.content.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import corp.Br1aN.ctrl.article.master.models.Model;

public class Content extends Model {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private long content_id;

  private String content_title;
  private String content_text;
  private String content_publish;
  private long template_id;

  private String content_app_company;

  private String content_created_by;
  private String content_created_at;
  private String content_updated_by;
  private String content_updated_at;
  private boolean content_deleted_flag;

  public Content(){

  }

  public Long getContentId(){
    return this.content_id;
  }
  public void setContentId(Long content_id){
    this.content_id = content_id;
  }

  public String getContentTitle(){
    return this.content_title;
  }
  public void setContentTitle(String content_title){
    this.content_title = content_title;;
  }

  public String getContentText(){
    return this.content_text;
  }
  public void setContentText(String content_text){
    this.content_text = content_text;
  }

  public String getContentPublish(){
    return this.content_publish;
  }
  public void setContentPublish(String content_publish){
    this.content_publish = content_publish;
  }

  public Long getTemplateId(){
    return this.template_id;
  }
  public void setTemplateId(Long template_id){
    this.template_id = template_id;
  }

  public String getTContentAppCompany(){
    return this.content_app_company;
  }
  public void setContentAppCompany(String content_app_company){
    this.content_app_company = content_app_company;
  }

  public String getContentCreatedBy(){
    return this.content_created_by;
  }
  public void setContentCreatedBy(String content_created_by){
    this.content_created_by = content_created_by;
  }
  public String getContentCreatedAt(){
    return this.content_created_at;
  }
  public void setContentCreatedAt(LocalDateTime content_created_at){
    this.content_created_at = content_created_at.format(formatter);
  }

  public String getContentUpdatedBy(){
    return this.content_updated_by;
  }
  public void setContentUpdatedBy(String content_updated_by){
    this.content_updated_by = content_updated_by;
  }
  public String getContentUpdatedAt(){
    return this.content_updated_at;
  }
  public void setContentUpdatedAt(LocalDateTime content_updated_at){
    this.content_updated_at = content_updated_at.format(formatter);
  }

  public boolean getContentDeletedFlag(){
    return this.content_deleted_flag;
  }
  public void setContentDeletedFlag(boolean content_deleted_flag){
    this.content_deleted_flag = content_deleted_flag;
  }
}
