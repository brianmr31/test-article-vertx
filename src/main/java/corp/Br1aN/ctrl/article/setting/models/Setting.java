package corp.Br1aN.ctrl.article.setting.modals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Setting extends Model {

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private Long setting_id;
  private String setting_app_company;
  private String setting_name;
  private String setting_data;
  private String setting_type;
  private String setting_created_by;
	private String setting_created_at;
  private String setting_updated_by;
  private String setting_updated_at;
	private boolean setting_deleted_flag;

  public Setting(){

  }
  public Setting(Long setting_id, String setting_app_company, String setting_name, String setting_data, String setting_type, String setting_created_by,
      LocalDateTime setting_created_at, String setting_updated_by, LocalDateTime setting_updated_at, boolean setting_deleted_flag){
    this.setting_id = setting_id;
    this.setting_app_company = setting_app_company;
    this.setting_name = setting_name;
    this.setting_data = setting_data;
    this.setting_type = setting_type;
    this.setting_created_by = setting_created_by;
    if( setting_created_at != null ){
      this.setting_created_at = setting_created_at.format(formatter);
    }
    this.setting_updated_by = setting_updated_by;
    if( setting_updated_at != null ){
      this.setting_updated_at = setting_updated_at.format(formatter);
    }
    this.setting_deleted_flag = setting_deleted_flag;
  }

  public Long getSettingId(){
    return this.setting_id;
  }
  public void setSettingId(Long setting_id){
    this.setting_id = setting_id;
  }

  public String getSettingAppType(){
    return this.setting_app_company;
  }
  public void setSettingAppCompany(String setting_app_company){
    this.setting_app_company = setting_app_company;
  }
  public String getSettingName(){
    return this.setting_name;
  }
  public void setSettingName(String setting_name){
    this.setting_name = setting_name;
  }

  public String getSettingData(){
    return this.setting_data;
  }
  public void setSettingData(String setting_data){
    this.setting_data = setting_data;
  }
  public String getSettingType(){
    return this.setting_type;
  }
  public void setSettingType(String setting_type){
    this.setting_type = setting_type;
  }

  public String getSettingCreatedBy(){
    return this.setting_created_by;
  }
  public void setSettingCreatedBy(String setting_created_by){
    this.setting_created_by = setting_created_by;
  }
  public String getSettingCreatedAt(){
    return this.setting_created_at;
  }
  public void setSettingCreatedAt(LocalDateTime setting_created_at){
    this.setting_created_at = setting_created_at.format(formatter);
  }

  public String getSettingUpdatedBy(){
    return this.setting_updated_by;
  }
  public void setSettingUpdatedBy(String setting_updated_by){
    this.setting_updated_by = setting_updated_by;
  }
  public String getSettingUpdatedAt(){
    return this.setting_updated_at;
  }
  public void setSettingUpdatedAt(LocalDateTime setting_updated_at){
    this.setting_updated_at = setting_updated_at.format(formatter);
  }

  public boolean getSettingDeletedFlag(){
    return this.setting_deleted_flag;
  }
  public void setSettingDeletedFlag(boolean setting_deleted_flag){
    this.setting_deleted_flag = setting_deleted_flag;
  }
}
