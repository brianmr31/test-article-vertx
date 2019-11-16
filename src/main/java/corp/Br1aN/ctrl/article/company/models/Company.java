package corp.Br1aN.ctrl.article.company.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import corp.Br1aN.ctrl.article.master.models.Model;

public class Company extends Model {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private Long company_id;
  private String company_name;
  private String company_owner;
  private String company_valid_from;
  private String company_valid_to;
  private String company_created_by;
	private String company_created_at;
  private String company_updated_by;
  private String company_updated_at;
	private boolean company_deleted_flag;

  public Company(){

  }
  public Company(Long company_id, String company_name, String company_owner, LocalDateTime company_valid_from, LocalDateTime company_valid_to, String company_created_by,
      LocalDateTime company_created_at, String company_updated_by, LocalDateTime company_updated_at, boolean company_deleted_flag){
    this.company_id = company_id;
    this.company_name = company_name;
    this.company_owner = company_owner;
    this.company_valid_from = company_valid_from.format(formatter);
    this.company_valid_to = company_valid_to.format(formatter);
    this.company_created_by = company_created_by;
    if( company_created_at != null ){
      this.company_created_at = company_created_at.format(formatter);
    }
    this.company_updated_by = company_updated_by;
    if( company_updated_at != null ){
      this.company_updated_at = company_updated_at.format(formatter);
    }
    this.company_deleted_flag = company_deleted_flag;
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

  public String getCompanyValidFrom(){
    return this.company_valid_from;
  }
  public void setCompanyValidFrom(LocalDateTime company_valid_from){
    this.company_valid_from = company_valid_from.format(formatter);
  }
  public String getCompanyValidTo(){
    return this.company_valid_to;
  }
  public void setCompanyValidTo(LocalDateTime company_valid_to){
    this.company_valid_to = company_valid_to.format(formatter);
  }

  public String getCompanyCreatedBy(){
    return this.company_created_by;
  }
  public void setCompanyCreatedBy(String company_created_by){
    this.company_created_by = company_created_by;
  }
  public String getCompanyCreatedAt(){
    return this.company_created_at;
  }
  public void setCompanyCreatedAt(LocalDateTime company_created_at){
    this.company_created_at = company_created_at.format(formatter);
  }

  public String getCompanyUpdatedBy(){
    return this.company_updated_by;
  }
  public void setCompanyUpdatedBy(String company_updated_by){
    this.company_updated_by = company_updated_by;
  }
  public String getCompanyUpdatedAt(){
    return this.company_updated_at;
  }
  public void setCompanyUpdatedAt(LocalDateTime company_updated_at){
    this.company_updated_at = company_updated_at.format(formatter);
  }

  public boolean getCompanyDeletedFlag(){
    return this.company_deleted_flag;
  }
  public void setCompanyDeletedFlag(boolean company_deleted_flag){
    this.company_deleted_flag = company_deleted_flag;
  }
}
