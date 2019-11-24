-- DROP TABLE public."setting";
CREATE TABLE public."setting" (
	setting_id bigserial PRIMARY KEY,
  setting_app_company varchar(255) NULL,
	setting_name varchar(255) NULL,
	setting_data varchar(255) NULL,
  setting_type varchar(255) NULL, -- NoPhone, NoWork, Email, Address, Logo,
	setting_created_by varchar(255) NULL,
	setting_created_at timestamp NULL,
  setting_updated_by varchar(255) NULL,
  setting_updated_at timestamp NULL,
	setting_deleted_flag bool NULL
);
-- DROP TABLE public."company";
CREATE TABLE public."company" (
	company_id bigserial PRIMARY KEY,
	company_name varchar(255) NULL,
	company_owner varchar(255) NULL,
	company_status varchar(255) NULL,
	company_valid_from timestamp NULL,
	company_valid_to timestamp NULL,
	company_created_by varchar(255) NULL,
	company_created_at timestamp NULL,
  company_updated_by varchar(255) NULL,
  company_updated_at timestamp NULL,
	company_deleted_flag bool NULL
);
-- DROP TABLE public."category";
CREATE TABLE public."category" (
	category_id bigserial PRIMARY KEY,
	category_name varchar(255) NULL,
	category_type varchar(255) NULL, -- menu, category, subCategory
	category_app_company varchar(255) NULL,
	category_parent_id varchar(255) NULL,
	category_queue integer NULL,
	category_created_by varchar(255) NULL,
	category_created_at timestamp NULL,
  category_updated_by varchar(255) NULL,
  category_updated_at timestamp NULL,
	category_deleted_flag bool NULL
);

-- DROP TABLE public."template";
CREATE TABLE public."template" (
	template_id bigserial PRIMARY KEY,
	template_name varchar(255) NULL,
	template_params varchar(255) NULL,
	template_app_company varchar(255) NULL,
	template_desc varchar(255) NULL,
	template_created_by varchar(255) NULL,
	template_created_at timestamp NULL,
  template_updated_by varchar(255) NULL,
  template_updated_at timestamp NULL,
	template_deleted_flag bool NULL
);

-- DROP TABLE public."content";
CREATE TABLE public."content" (
	content_id bigserial PRIMARY KEY,
	content_title varchar(255) NULL,
	content_text varchar(255) NULL,
	content_publish varchar(255) NULL,
	content_app_company varchar(255) NULL,
	template_id bigint NULL,
	content_created_by varchar(255) NULL,
	content_created_at timestamp NULL,
  content_updated_by varchar(255) NULL,
  content_updated_at timestamp NULL,
	content_deleted_flag bool NULL
);

-- DROP TABLE public."gallery";
CREATE TABLE public."gallery" (
	gallery_id bigserial PRIMARY KEY,
	gallery_url varchar(255) NULL,
	gallery_tags varchar(255) NULL,
	content_id bigint NULL,
	gallery_app_company varchar(255) NULL,
	gallery_created_by varchar(255) NULL,
	gallery_created_at timestamp NULL,
  gallery_updated_by varchar(255) NULL,
  gallery_updated_at timestamp NULL,
	gallery_deleted_flag bool NULL
);
