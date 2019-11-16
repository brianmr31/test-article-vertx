-- DROP TABLE public."setting";
CREATE TABLE public."setting" (
	setting_id bigserial PRIMARY KEY,
  setting_app_company varchar(255) NULL,
	setting_name varchar(255) NULL,
	setting_data varchar(255) NULL,
  setting_type varchar(255) NULL,
	setting_created_by varchar(255) NULL,
	setting_created_at timestamp NULL,
  setting_updated_by varchar(255) NULL,
  setting_updated_at timestamp NULL,
	setting_deleted_flag bool NULL
);
