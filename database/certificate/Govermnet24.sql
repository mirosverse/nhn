CREATE database GOVERMENT24;
USE GOVERMENT24;

CREATE TABLE `Person` (
	`PersonId`	int	NOT NULL auto_increment PRIMARY KEY,
	`registerNo`	varchar(14)	NOT NULL UNIQUE,
    `BirthDate` int NOT NULL,
	`Name`	varchar(10)	NOT NULL,
	`GenderNo`	int	NOT NULL,
	`aliveNo`	int	NOT NULL,
	`email`	varchar(20)	NULL,
	`Phone`	varchar(15)	NULL,
	`AddressId`	int	NOT NULL
);

CREATE TABLE `Gender` (
	`GenderNo`	int	NOT NULL PRIMARY KEY,
	`GenderName`	varchar(1)	NOT NULL
);

CREATE TABLE `Alive` (
	`aliveNo`	int	NOT NULL PRIMARY KEY,
	`aliveName`	varchar(1)	NOT NULL
);

CREATE TABLE `FamilyRelation` (
	`FamilyRelationNo`	int	NOT NULL PRIMARY KEY,
	`FamilyRelationName`	varchar(5)	NOT NULL
);

CREATE TABLE `AddressHistory` (
	`PersonId`	int	NOT NULL,
	`AddressId`	int	NOT NULL,
	`ChangeDate`	date NOT NULL,
	`ChangeReasonNo`	int	NOT NULL
);

CREATE TABLE `ChangeReason` (
	`ChangeReasonNo`	int	NOT NULL PRIMARY KEY,
	`ChangeReasonName`	varchar(10)	NOT NULL
);

CREATE TABLE `ImmediateFamily` (
	`FamilyNo`	int	NOT NULL,
	`FamilyRelationNo`	int	NOT NULL,
	`PersonId`	int	NOT NULL
    );

CREATE TABLE `Birth` (
	`BirthLocationNo`	int	NOT NULL,
	`Birthdaytime`	datetime	NOT NULL,
	`QualificationNo`	int	NOT NULL,
	`PersonId`	int	NOT NULL,
    `ReporterId`	int	NOT NULL,
    `ReportDate`	date	NOT NULL
);

CREATE TABLE `BirthLocation` (
	`BirthLocationNo`	int	NOT NULL PRIMARY KEY,
	`BirthLocationName`	varchar(3)	NOT NULL
);

CREATE TABLE `Qualification` (
	`QualificationNo`	int	NOT NULL PRIMARY KEY,
	`QualificationName`	varchar(5)	NOT NULL
);

CREATE TABLE `Death` (
	`DeathDateTime`	datetime	NOT NULL,
	`AddressId`	int	NOT NULL,
	`DeathLocationNo`	int	NOT NULL,
	`QualificationNo`	int	NOT NULL,
	`PersonId`	int	NOT NULL, 
	`ReporterId`	int	NOT NULL,
    `ReportDate`	date	NOT NULL
);

CREATE TABLE `DeathLocation` (
	`DeathLocationNo`	int	NOT NULL PRIMARY KEY,
	`DataLocationName`	varchar(20)	NOT NULL
);

CREATE TABLE `Address` (
	`AddressId`	int	NOT NULL auto_increment PRIMARY KEY,
	`zipCode`	int	NOT NULL,
	`State`	varchar(20)	NOT NULL,
	`City`	varchar(100)	NOT NULL,
	`Road`	varchar(100)	NOT NULL
);

CREATE TABLE `Household` (
	`PersonId`	int	NOT NULL,
    `HouseholdId`	int	NOT NULL,
	`HouseholdNo`	int	NOT NULL 
);

CREATE TABLE `HouseholdRelation` (
	`HouseholdNo`	int	NOT NULL PRIMARY KEY,
	`HouseholdName`	varchar(10)	NOT NULL
);

CREATE TABLE `CertificateNo` (
	`CertificateCode`	int	NOT NULL PRIMARY KEY auto_increment,
	`CertificateID`	int	NOT NULL,
	`PersonId`	int	NOT NULL,
	`DateGerated`	date	NOT NULL
);

CREATE TABLE `Certificate` (
	`CertificateID`	int	NOT NULL PRIMARY KEY,
	`CertificateName`	varchar(20)	NOT NULL
);


ALTER TABLE Person
ADD CONSTRAINT fk_Person_alive
FOREIGN KEY (aliveNo) REFERENCES Alive(aliveNo);

ALTER TABLE Person
ADD CONSTRAINT fk_Person_gender
FOREIGN KEY (GenderNo) REFERENCES gender(GenderNo);

ALTER TABLE Person
ADD CONSTRAINT fk_Person_address
FOREIGN KEY (AddressId) REFERENCES address(AddressId);

ALTER TABLE CertificateNo
ADD CONSTRAINT fk_CerficateNo_certificateId
FOREIGN KEY (CertificateId) REFERENCES Certificate(CertificateId);

ALTER TABLE CertificateNo
ADD CONSTRAINT fk_CerficateNo_PersonId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE AddressHistory
ADD CONSTRAINT fk_AddressHistory_PersonId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE AddressHistory
ADD CONSTRAINT fk_AddressHistory_address
FOREIGN KEY (AddressId) REFERENCES address(AddressId);

ALTER TABLE AddressHistory
ADD CONSTRAINT fk_AddressHistory_changeReason
FOREIGN KEY (ChangeReasonNo) REFERENCES ChangeReason(ChangeReasonNo);

ALTER TABLE Household
ADD CONSTRAINT fk_Household_person
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE Household
ADD CONSTRAINT fk_Household_householdrelation
FOREIGN KEY (HouseholdNo) REFERENCES HouseholdRelation(HouseholdNo);

ALTER TABLE Household
ADD CONSTRAINT fk_Household_householdid
FOREIGN KEY (HouseholdId) REFERENCES Person(PersonId);

ALTER TABLE ImmediateFamily
ADD CONSTRAINT fk_immediateFamily_person
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE ImmediateFamily
ADD CONSTRAINT fk_immediateFamily_familyRelationNo
FOREIGN KEY (FamilyRelationNo) REFERENCES FamilyRelation(FamilyRelationNo);

ALTER TABLE Birth
ADD CONSTRAINT fk_Birth_person
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE Birth
ADD CONSTRAINT fk_Birth_BirthLocationNo
FOREIGN KEY (BirthLocationNo) REFERENCES BirthLocation(BirthLocationNo);

ALTER TABLE Birth
ADD CONSTRAINT fk_Birth_Qualification
FOREIGN KEY (QualificationNo) REFERENCES Qualification(QualificationNo);

ALTER TABLE Birth
ADD CONSTRAINT fk_Birth_ReporterId
FOREIGN KEY (ReporterId) REFERENCES Person(PersonId);

ALTER TABLE Death
ADD CONSTRAINT fk_Death_person
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE Death
ADD CONSTRAINT fk_Death_DeathLocationNo
FOREIGN KEY (DeathLocationNo) REFERENCES DeathLocation(DeathLocationNo);

ALTER TABLE Death
ADD CONSTRAINT fk_Death_Qualification
FOREIGN KEY (QualificationNo) REFERENCES Qualification(QualificationNo);

ALTER TABLE Death
ADD CONSTRAINT fk_Death_ReporterId
FOREIGN KEY (ReporterId) REFERENCES Person(PersonId);

ALTER TABLE Death
ADD CONSTRAINT fk_Death_AddressId
FOREIGN KEY (AddressId) REFERENCES Address(AddressId);

-- Gender 테이블 더미 데이터 삽입
INSERT INTO Gender (GenderNo, GenderName) VALUES
(1, 'M'),
(2, 'F');

-- Alive 테이블 더미 데이터 삽입
INSERT INTO Alive (aliveNo, aliveName) VALUES
(1, 'Y'),
(2, 'N');

-- FamilyRelation 테이블 더미 데이터 삽입
INSERT INTO FamilyRelation (FamilyRelationNo, FamilyRelationName) VALUES
(1, '본인'),
(2, '부'),
(3, '모'),
(4, '배우자'),
(5, '자녀');

-- ChangeReason 테이블 더미 데이터 삽입
INSERT INTO ChangeReason (ChangeReasonNo, ChangeReasonName) VALUES
(1, '세대분리'),
(2, '전입'),
(3, '출생등록'); 

-- Address 테이블 더미 데이터 삽입
INSERT INTO Address (zipCode, State, City, Road) VALUES
(12345, '서울시', '동작구', '상도로 940번길'),
(12346, '경기도', '성남시 분당구', '불정로 90번길'),
(12347, '경기도', '성남시 분당구', '대왕판교로 645번길'),
(12348, '강원도', '고성군', '금강산로 290번길');

-- HouseholdRelation 테이블 더미 데이터 삽입
INSERT INTO HouseholdRelation (HouseholdNo, HouseholdName) VALUES
(1, '본인'),
(2, '배우자'),
(3, '자녀'),
(4, '동거인');

-- BirthLocation 테이블 더미 데이터 삽입
INSERT INTO BirthLocation (BirthLocationNo, BirthLocationName) VALUES
(1, '자택'),
(2, '병원'),
(3, '기타');

-- Qualification 테이블 더미 데이터 삽입
INSERT INTO Qualification (QualificationNo, QualificationName) VALUES
(1, '부'),
(2, '모'),
(3, '동거친족'),
(4, '기타'),
(5, '비동거친족'),
(6, '동거자');

-- DeathLocation 테이블 더미 데이터 삽입
INSERT INTO DeathLocation (DeathLocationNo, DataLocationName) VALUES
(1, '주택'),
(2, '의료기관'),
(3, '사회복지시설(양로원, 고아원 등)'),
(4, '산업장'),
(5, '공공시설(학교, 운동장 등)'),
(6, '도로'),
(7, '상업 / 서비스시설(상점, 호텔 등)'),
(8, '농장(논밭, 축사, 양식장 등)'),
(9, '병원 이송 중 사망'),
(10, '기타');

-- Certificate 테이블 더미 데이터 삽입
INSERT INTO Certificate (CertificateID, CertificateName) VALUES
(1, '출생신고서'),
(2, '사망신고서'),
(3, '가족관계증명서'),
(4, '주민등록등본');
 
-- Person 테이블 더미 데이터 삽입
INSERT INTO Person (registerNo, BirthDate, Name, GenderNo, aliveNo, email, Phone, AddressId) VALUES
('790510-7891011', 19790510, '남기준', 1, 1, '남기준@example.com', '010-5647-8901', 3),
('540514-7891011', 19540514, '남석환', 1, 1, '남석환@example.com', '010-5647-8901', 3),
('551022-7891011', 19551022, '박한나', 2, 1, '박한나@example.com', '010-5647-8901', 3),
('820821-7891011', 19820821, '이주은', 2, 1, '이주은@example.com', '010-5647-8901', 3),
('120315-7891011', 20120315, '남기석', 1, 1, '남기석@example.com', '010-5647-8901', 3),
('851205-7891011', 19851205, '이선미', 2, 1, '이선미@example.com', '010-5647-8901', 3),
('130914-7891011', 19130914, '남길동', 1, 2, '남길동@example.com', '010-5647-8901', 4);

-- ImmediateFamily 테이블 더미 데이터 삽입   
INSERT INTO ImmediateFamily VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 4),
(1, 5, 5);

-- Household 테이블 더미 데이터 삽입
INSERT INTO Household VALUES
(1, 1, 1),
(6, 1, 2),
(5, 1, 3),
(4, 1, 4);

-- Birth 테이블 더미 데이터 삽입
Select * from person;
Select * from address;
INSERT INTO Birth (BirthLocationNo, Birthdaytime, QualificationNo, PersonId, ReporterId, ReportDate) VALUES
(2, '2012-03-15 14:59:00', 1, 5, 1, now());

-- Death 테이블 더미 데이터 삽입
INSERT INTO Death (DeathDateTime, AddressId, DeathLocationNo, QualificationNo, PersonId, ReporterId, ReportDate) VALUES
('2021-04-29 09:03:00', 4, 1, 5, 7, 2, now());

-- CertificateNo 테이블 더미 데이터 삽입
INSERT INTO CertificateNo (CertificateID, PersonId, DateGerated) VALUES
(1, 1, now());

INSERT INTO CertificateNo (CertificateID, PersonId, DateGerated) VALUES
(2, 1, now());

INSERT INTO CertificateNo (CertificateID, PersonId, DateGerated) VALUES
(3, 1, now());

INSERT INTO CertificateNo (CertificateID, PersonId, DateGerated) VALUES
(4, 1, now());

INSERT INTO AddressHistory VALUES
(1, 1, '2007-10-31', 2),
(1, 2, '2009-10-02', 1),
(1, 2, '2009-10-31', 2),
(1, 3, '2013-03-05', 2),
(4, 3, '2012-03-17', 2),
(5, 3, '2012-03-17', 3),
(6, 3, '2015-11-29', 2);

-- 사망신고서 select
SELECT 
    c.CertificateName AS '문서종류',
    d.ReportDate AS '신고일',
    p.Name AS '사망자 성명',
    p.registerNo AS '사망자 주민등록번호',
    d.DeathDateTime AS '사망일시',
    dl.DataLocationName AS '사망장소',
    CONCAT(a.State, ' ', a.City, ' ', a.Road) AS '사망장소 주소',
    reporter.Name AS '신고인 성명',
    reporter.registerNo AS '신고인 주민등록번호',
    al.aliveName AS '자격',
    q.QualificationName AS '자격명',
    reporter.email AS '이메일',
    reporter.Phone AS '전화번호'
FROM 
    Death d
JOIN 
    Person p ON d.PersonId = p.PersonId
JOIN 
    Person reporter ON d.ReporterId = reporter.PersonId
JOIN 
    Alive al ON al.aliveNo = p.aliveNo
JOIN 
    DeathLocation dl ON dl.DeathLocationNo = d.DeathLocationNo
JOIN
    Address a ON a.AddressId = d.AddressId
JOIN
    Certificate c ON c.CertificateID = 2 -- 2는 사망신고서의 ID입니다.
JOIN
    Qualification q ON q.QualificationNo = d.QualificationNo;



-- 가족관계증명서 
SELECT 
    Certificate.CertificateName AS '증명서명',
    CertificateNo.DateGerated AS '발급일',
    CertificateNo.CertificateCode AS '증명서확인번호',
    CONCAT(Address.State, ' ', Address.City, ' ', Address.Road) AS '등록기준지(본적)'
FROM   
    CertificateNo
JOIN
    Person ON CertificateNo.PersonId = Person.PersonId
JOIN
    Address ON Person.AddressId = Address.AddressId
JOIN
    Certificate ON CertificateNo.CertificateID = Certificate.CertificateID
WHERE
    CertificateNo.CertificateID = 3 AND Person.PersonId = 1;



-- 가족관계 증명서 Select
SELECT 
    CONCAT(Address.State, Address.City, Address.Road) AS '등록기준지',
    FamilyRelation.FamilyRelationName AS '구분',
    Person.Name AS '성명',
    DATE_FORMAT(FROM_UNIXTIME(Person.BirthDate), '%Y년 %m월 %d일') AS '출생연월일',
    Person.registerNo AS '주민등록번호',
    Gender.GenderName AS '성별'
FROM 
    Person
JOIN 
    Address ON Person.AddressId = Address.AddressId
JOIN 
    ImmediateFamily ON ImmediateFamily.PersonId = Person.PersonId
JOIN 
    FamilyRelation ON FamilyRelation.FamilyRelationNo = ImmediateFamily.FamilyRelationNo
JOIN 
    Gender ON Person.GenderNo = Gender.GenderNo
WHERE 
    ImmediateFamily.FamilyNo = (SELECT FamilyNo FROM ImmediateFamily WHERE PersonId = 1); -- 여기에 PersonId 값을 변경하여 특정 사람에 대한 정보를 선택할 수 있습니다.
    
-- 주민등록등본 select 윗부분
     
SELECT 
	Certificate.certificatename '증명서종류',
	CertificateNo.certificatecode '증명서확인번호',
    CertificateNo.DateGerated '발급일',
    Person.Name AS '세대주 성명',
    CONCAT(ChangeReason.ChangeReasonName,' ', addresshistory.changedate) AS '세대구성 사유 및 일자',
    CONCAT(Address.State, Address.City, Address.Road) AS '주소',
    AddressHistory.ChangeDate AS '신고일',
	CASE 
		WHEN (select ChangeDate from AddressHistory where personid = 1 order by ChangeDate desc limit 1 ) = AddressHistory.ChangeDate THEN '현주소'
		ELSE '주소'
	END '주소 현황'
FROM 
    Person
JOIN 
    AddressHistory ON Person.PersonId = AddressHistory.PersonId
JOIN 
    Address ON AddressHistory.AddressId = Address.AddressId
JOIN 
    ChangeReason ON AddressHistory.ChangeReasonNo = ChangeReason.ChangeReasonNo
JOIN 
    CertificateNo ON CertificateNo.PersonId = Person.PersonId
JOIN 
    certificate ON certificate.certificateid = CertificateNo.certificateid
WHERE 
    Person.PersonId = 1 and Certificate.certificatename = '주민등록등본' order  by AddressHistory.ChangeDate desc;
    

-- 주민등록등본 : 세대주 관계
SELECT
    HouseholdRelation.HouseholdName AS '세대주 관계',
Person.Name
'성명',
    Person.registerNo AS '주민등록번호',
    MAX(AddressHistory.ChangeDate) AS '신고일',
    ChangeReason.ChangeReasonName AS '변동사유'
FROM
    Person
JOIN
    Household ON Household.PersonId = Person.PersonId
JOIN
    HouseholdRelation ON HouseholdRelation.HouseholdNo = Household.HouseholdNo
JOIN
    AddressHistory ON AddressHistory.PersonId = Person.PersonId
JOIN
    ChangeReason ON AddressHistory.ChangeReasonNo = ChangeReason.ChangeReasonNo
WHERE
    Household.HouseholdId = (
        SELECT HouseholdId FROM Household WHERE PersonId = 1
    )
    AND NOT (HouseholdRelation.HouseholdName = '본인' AND ChangeReason.ChangeReasonName <>'세대분리')
GROUP BY
    HouseholdRelation.HouseholdName, Person.Name, Person.registerNo, ChangeReason.ChangeReasonName
ORDER BY
    HouseholdRelation.HouseholdName, Person.Name;
    
    
    
-- 출생신고서 출생자 select
SELECT
Birth.ReportDate As '신고일',
	Person.Name AS '성명',
    Gender.GenderName AS '성별',
	Birth.Birthdaytime AS '출생일시',
	Birthlocation.BirthlocationName AS '출생장소',
	CONCAT(Address.State, ' ', Address.City, ' ', Address.Road) AS '등록기준지'
FROM 
    Birth
JOIN 
    Person ON Birth.PersonId = Person.PersonId
JOIN 
    Gender ON Person.GenderNo = Gender.GenderNo
JOIN 
    BirthLocation ON Birth.BirthLocationNo = BirthLocation.BirthLocationNo
JOIN 
    Address ON Person.AddressId = Address.AddressId
WHERE Birth.PersonId = 5;

-- 출생신고서 출생자 select
SELECT
Birth.ReportDate As '신고일',
	Person.Name AS '성명',
    Gender.GenderName AS '성별',
	Birth.Birthdaytime AS '출생일시',
	Birthlocation.BirthlocationName AS '출생장소',
	CONCAT(Address.State, ' ', Address.City, ' ', Address.Road) AS '등록기준지'
FROM 
    Birth
JOIN 
    Person ON Birth.PersonId = Person.PersonId
JOIN 
    Gender ON Person.GenderNo = Gender.GenderNo
JOIN 
    BirthLocation ON Birth.BirthLocationNo = BirthLocation.BirthLocationNo
JOIN 
    Address ON Person.AddressId = Address.AddressId
WHERE Birth.PersonId = 5;
  
-- 출생신고서 신고인 select
SELECT Name AS '성명',
  Person.registerNo AS '주민등록번호',
  Qualification.QualificationName AS '자격',
  Person.email AS '이메일',
  Person.phone AS '전화번호'
  FROM 
    Person
JOIN Birth ON Birth.ReporterId = Person.PersonId
JOIN 
    Qualification ON Birth.QualificationNo = Qualification.QualificationNo
WHERE 
    Birth.ReporterId = 1;
    
-- 부 모
SELECT 
    Parent.Name AS 'Name',
    Parent.registerNo AS 'registerNo',
    FamilyRelation.FamilyRelationName AS 'FamilyRelationName'
FROM 
    ImmediateFamily AS ChildFamily
JOIN 
    Person AS Child ON ChildFamily.PersonId = Child.PersonId
JOIN 
    ImmediateFamily AS ParentFamily ON ChildFamily.FamilyNo = ParentFamily.FamilyNo
JOIN 
    Person AS Parent ON ParentFamily.PersonId = Parent.PersonId
JOIN 
    FamilyRelation ON ParentFamily.FamilyRelationNo = FamilyRelation.FamilyRelationNo
WHERE 
    Child.PersonId = 5
    AND ParentFamily.FamilyRelationNo IN (1,4);

