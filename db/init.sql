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
