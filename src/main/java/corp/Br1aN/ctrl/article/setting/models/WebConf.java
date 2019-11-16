package corp.Br1aN.ctrl.article.setting.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import corp.Br1aN.ctrl.article.master.models.Model;

public class WebConf extends Model {

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private String setting_name;
  private String setting_data;
  private String setting_type;

  public WebConf(){

  }
  public WebConf(String setting_name, String setting_data, String setting_type){
    this.setting_name = setting_name;
    this.setting_data = setting_data;
    this.setting_type = setting_type;
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
}
