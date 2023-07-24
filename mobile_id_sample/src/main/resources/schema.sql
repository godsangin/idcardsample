DROP TABLE tb_service IF EXISTS;
CREATE TABLE tb_service (
  svcCode varchar(50) NOT NULL ,
  presentType integer NOT NULL DEFAULT 1 ,
  encryptType integer NOT NULL DEFAULT 2 ,
  keyType integer NOT NULL DEFAULT 2 ,
  authType varchar(50) DEFAULT NULL ,
  spName varchar(100) NOT NULL ,
  serviceName varchar(100) NOT NULL ,
  attrList varchar(100) DEFAULT NULL,
  predList varchar(100) DEFAULT NULL,
  callBackUrl varchar(100) DEFAULT NULL ,
  regDt timestamp NOT NULL DEFAULT current_timestamp() ,
  udtDt timestamp NULL DEFAULT NULL ,
  PRIMARY KEY (svcCode)
);

DROP TABLE tb_trx_info IF EXISTS;
CREATE TABLE tb_trx_info (
  trxCode varchar(50) NOT NULL ,
  svcCode varchar(50) NOT NULL ,
  mode varchar(50) NOT NULL ,
  deviceId varchar(100) DEFAULT NULL ,
  branchName varchar(100) DEFAULT NULL ,
  nonce varchar(100) DEFAULT NULL,
  zkpNonce varchar(100) DEFAULT NULL,
  vpVerifyResult varchar(1) NOT NULL DEFAULT 'N' ,
  vp CHARACTER VARYING DEFAULT NULL ,
  trxStsCode varchar(4) NOT NULL DEFAULT '0001' ,
  profileSendDt timestamp NULL DEFAULT NULL ,
  imgSendDt timestamp NULL DEFAULT NULL ,
  vpReceptDt timestamp NULL DEFAULT NULL ,
  errorCn varchar(4000) DEFAULT NULL,
  regDt timestamp NOT NULL DEFAULT current_timestamp() ,
  udtDt timestamp NULL DEFAULT NULL ,
  PRIMARY KEY (trxCode)
);
