package corp.Br1aN.ctrl.article.category.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import corp.Br1aN.ctrl.article.master.models.Model;

public class Category extends Model {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private Long category_id;
  private String category_name;
  private String category_type;
  private String category_app_company;
  private Long category_parent_id;
  private Long category_queue;
  private String category_created_by;
	private String category_created_at;
  private String category_updated_by;
  private String category_updated_at;
	private boolean category_deleted_flag;

  public Category(){

  }
  public Category(Long category_id, String category_name, String category_type, String category_app_company, Long category_parent_id, Long category_queue,
      String category_created_by, LocalDateTime category_created_at, String category_updated_by, LocalDateTime category_updated_at, boolean category_deleted_flag){
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_type = category_type;
        this.category_app_company = category_app_company;
        this.category_parent_id = category_parent_id;
        this.category_queue = category_queue;
        this.category_created_by = category_created_by;
        if( category_created_at != null ){
          this.category_created_at = category_created_at.format(formatter);
        }
        this.category_updated_by = category_updated_by;
        if( category_updated_at != null ){
          this.category_updated_at = category_updated_at.format(formatter);
        }
        this.category_deleted_flag = category_deleted_flag;
  }

  public Long getCategoryId(){
    return this.category_id;
  }
  public void setCategoryId(Long category_id){
    this.category_id = category_id;
  }
  public String getCategoryName(){
    return this.category_name;
  }
  public void setCategoryName(String category_name){
    this.category_name = category_name;
  }
  public String getCategoryType(){
    return this.category_type;
  }
  public void setCategoryType(String category_type){
    this.category_type = category_type;
  }

  public String getCategoryAppCompany(){
    return this.category_app_company;
  }
  public void setCategoryAppCompany(String category_app_company){
    this.category_app_company = category_app_company;
  }

  public Long getCategoryParentId(){
    return this.category_parent_id;
  }
  public void setCategoryParentId(Long category_parent_id){
    this.category_parent_id = category_parent_id;
  }
  public Long getCategoryQueue(){
    return this.category_queue;
  }
  public void setCategoryQueue(Long category_queue){
    this.category_queue = category_queue;
  }

  public String getCategoryCreatedBy(){
    return this.category_created_by;
  }
  public void setCategoryCreatedBy(String category_created_by){
    this.category_created_by = category_created_by;
  }
  public String getCategoryCreatedAt(){
    return this.category_created_at;
  }
  public void setCategoryCreatedAt(LocalDateTime category_created_at){
    this.category_created_at = category_created_at.format(formatter);
  }

  public String getCategoryUpdatedBy(){
    return this.category_updated_by;
  }
  public void setCategoryUpdatedBy(String category_updated_by){
    this.category_updated_by = category_updated_by;
  }
  public String getCategoryUpdatedAt(){
    return this.category_updated_at;
  }
  public void setCategoryUpdatedAt(LocalDateTime category_updated_at){
    this.category_updated_at = category_updated_at.format(formatter);
  }

  public boolean getCategoryDeletedFlag(){
    return this.category_deleted_flag;
  }
  public void setCategoryDeletedFlag(boolean category_deleted_flag){
    this.category_deleted_flag = category_deleted_flag;
  }
}
