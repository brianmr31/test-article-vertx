package corp.Br1aN.ctrl.article.gallery.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import corp.Br1aN.ctrl.article.master.models.Model;

public class Gallery extends Model {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private long gallery_id;

  private String gallery_url;
  private String gallery_tags;
  private long content_id;

  private String gallery_created_by;
  private String gallery_created_at;
  private String gallery_updated_by;
  private String gallery_updated_at;
  private boolean gallery_deleted_flag;

  public Gallery(){

  }

  public long getGalleryId(){
    return this.gallery_id;
  }
  public void setGalleryId(Long gallery_id){
    this.gallery_id = gallery_id;
  }

  public String getGalleryUrl(){
    return this.gallery_url;
  }
  public void setGalleryUrl(String gallery_url){
    this.gallery_url = gallery_url;
  }

  public String getGalleryTags(){
    return this.gallery_tags;
  }
  public void setGalleryTags(String gallery_tags){
    this.gallery_tags = gallery_tags;
  }

  public Long getGalleryUrl(){
    return this.content_id;
  }
  public void setGalleryUrl(Long content_id){
    this.content_id = content_id;
  }

  public String getGalleryCreatedBy(){
    return this.gallery_created_by;
  }
  public void setGalleryCreatedBy(String gallery_created_by){
    this.gallery_created_by = gallery_created_by;
  }
  public String getGalleryCreatedAt(){
    return this.gallery_created_at;
  }
  public void setGalleryCreatedAt(LocalDateTime gallery_created_at){
    this.gallery_created_at = gallery_created_at.format(formatter);
  }

  public String getGalleryUpdatedBy(){
    return this.gallery_updated_by;
  }
  public void setGalleryUpdatedBy(String gallery_updated_by){
    this.gallery_updated_by = gallery_updated_by;
  }
  public String getGalleryUpdatedAt(){
    return this.gallery_updated_at;
  }
  public void setGalleryUpdatedAt(LocalDateTime gallery_updated_at){
    this.gallery_updated_at = gallery_updated_at.format(formatter);
  }

  public boolean getGalleryDeletedFlag(){
    return this.gallery_deleted_flag;
  }
  public void setGalleryDeletedFlag(boolean gallery_deleted_flag){
    this.gallery_deleted_flag = gallery_deleted_flag;
  }
}
