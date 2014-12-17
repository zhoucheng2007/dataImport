CREATE TABLE BASE_V6REPORT_SNAPSHOT ( 
	SNAPSHOT_ID VARCHAR(30) NOT NULL, 
	USER_ID VARCHAR(30), 
	OPTION_STR CLOB, 
	GENE_TIME VARCHAR(30), 
	NOTE VARCHAR(1000), 
	PRIMARY KEY (SNAPSHOT_ID) 
) IN TD_BASE INDEX IN TD_BASE_IDX;