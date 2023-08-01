INSERT INTO tb_service (svcCode,presentType,authType,spName,serviceName,attrList,predList,callBackUrl,regDt,udtDt) VALUES
    ('sangin.1',     1,  NULL,             '상추',           '서비스1-일반인증',                        NULL                             , NULL                                        ,NULL, '2023-08-01 15:08:15.0', NULL),
	('example.2',     1,  '["pin","face"]', 'GS25',           '서비스2-안심인증',                        NULL                             , NULL                                        ,NULL, '2021-12-15 15:08:15.0', NULL),
    ('zkp.1',         2,  NULL,             'GS25',           '성인인증',                                NULL                             , '[{"zkpbirth":{"type":"LE","value":"-19"}}]',NULL, '2021-12-29 15:42:15.0', NULL),
	('zkp.2',         2,  NULL,             'GS25',           '기타 영지식 증명',                        '["zkpaddr","zkpsex","zkpasort"]', NULL                                        ,NULL, '2021-12-15 15:08:15.0', NULL),
	('zkp.3',         2,  NULL,             'GS25',           '혼합 영지식 증명',                        '["zkpaddr","zkpsex","zkpasort"]', '[{"zkpbirth":{"type":"LE","value":"-19"}}]',NULL, '2021-12-15 15:08:15.0', NULL),
	('nm.spe.isf.1',  1,  NULL,             '국가보훈등록증', '유공자.특정신분증 자체',                  NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.spe.prt.1',  1,  NULL,             '국가보훈등록증', '유공자.특정 신분증 일부 정보.1',          NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.spe.prt.2',  1,  NULL,             '국가보훈등록증', '유공자.특정 신분증 일부 정보',            NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.spe.prt.3',  1,  NULL,             '국가보훈등록증', '유공자.특정 신분증 일부 정보.2',          NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.any.prt.1',  1,  NULL,             '국가보훈등록증', '유공자.Any신분증 중 하나에서 일부정보',   NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.anyc.pt.1',  1,  NULL,             '국가보훈등록증', '유공자.유공자증AnyVC중 일부정보복합요청', NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.spvc.prt1',  1,  NULL,             '국가보훈등록증', '유공자.유공자증특정VC 일부정보요청',      NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.any.isf.1',  1,  NULL,             '국가보훈등록증', '유공자.Any신분증 중 하나 자체',           NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.indepat.1',  1,  NULL,             '국가보훈등록증', '유공자.독립유공자',                       NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.nationm.1',  1,  NULL,             '국가보훈등록증', '유공자.국가유공자',                       NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.demmeri.1',  1,  NULL,             '국가보훈등록증', '유공자.518민주유공자',                    NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.smmerit.1',  1,  NULL,             '국가보훈등록증', '유공자.특수임무유공자',                   NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.rewardp.1',  1,  NULL,             '국가보훈등록증', '유공자.보훈보상대상자',                   NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.defolia.1',  1,  NULL,             '국가보훈등록증', '유공자.고엽제대상자',                     NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.applica.1',  1,  NULL,             '국가보훈등록증', '유공자.지원대상자',                       NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.dischas.1',  1,  NULL,             '국가보훈등록증', '유공자.제대군인',                         NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL),
    ('nm.nationm.2',  1,  NULL,             '국가보훈등록증', '유공자.국가유공자',                       NULL,                              NULL,                                        NULL, '2022-12-08 11:12:12.0', NULL);
