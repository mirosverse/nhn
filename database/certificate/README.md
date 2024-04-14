# 증명서 발급 시스템 데이터베이스 설계
날짜 : 2024.04.09 <br>
erd : https://www.erdcloud.com/d/3NmiZCLtPihJMF6Dx <br>
목적 : 가족증명서, 주민등록본증, 사망신고서, 출생신고서

## ERD 설계
<img width="1012" alt="스크린샷 2024-04-15 오전 8 27 52" src="https://github.com/zaysverse/nhn/assets/90399537/b44c0ac7-cb71-4aa1-bcbd-5f8811cc6aac">

## select 쿼리
```
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
    
-- 주민등록등본 select
SELECT 
    MainPerson.Name AS '세대주 성명',
    ChangeReason.ChangeReasonName AS '세대구성 사유 및 일자',
    CONCAT(Address.State, Address.City, Address.Road) AS '주소',
    AddressHistory.ChangeDate AS '신고일'
FROM 
    ImmediateFamily AS IF1
JOIN 
    ImmediateFamily AS IF2 ON IF1.FamilyNo = IF2.FamilyNo
JOIN 
    Person AS MainPerson ON IF1.PersonId = MainPerson.PersonId
JOIN 
    AddressHistory ON IF1.PersonId = AddressHistory.PersonId
JOIN 
    Address ON AddressHistory.AddressId = Address.AddressId
JOIN 
    ChangeReason ON AddressHistory.ChangeReasonNo = ChangeReason.ChangeReasonNo
WHERE 
     MainPerson.PersonId = 23;
SELECT 
    Person.Name AS '세대주 성명',
    ChangeReason.ChangeReasonName AS '세대구성 사유 및 일자',
    CONCAT(Address.State, Address.City, Address.Road) AS '주소',
    AddressHistory.ChangeDate AS '신고일'
FROM 
    Person
JOIN 
    AddressHistory ON Person.PersonId = AddressHistory.PersonId
JOIN 
    Address ON AddressHistory.AddressId = Address.AddressId
JOIN 
    ChangeReason ON AddressHistory.ChangeReasonNo = ChangeReason.ChangeReasonNo
WHERE 
    Person.PersonId = 1;
    



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

-- 주민등록등본 : 주소
SELECT 
    Person.Name AS '세대주 성명',
    CONCAT(ChangeReason.ChangeReasonName,' ', addresshistory.changedate) AS '세대구성 사유 및 일자',
    CONCAT(Address.State, Address.City, Address.Road) AS '주소',
    AddressHistory.ChangeDate AS '신고일',
	CASE 
		WHEN (select ChangeDate from AddressHistory where PersonId =1 order by ChangeDate desc limit 1 ) = AddressHistory.ChangeDate THEN '현주소'
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
WHERE 
    Person.PersonId = 1 
order by addressHistory.ChangeDate DESC;

-- 주민등록등본 : 세대주 관계
SELECT
    HouseholdRelation.HouseholdName AS '세대주 관계',
    Person.Name AS '성명',
    Person.registerNo AS '주민등록번호',
    AddressHistory.ChangeDate AS '신고일',
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
ORDER BY
    HouseholdRelation.HouseholdName, Person.Name;


SELECT
    HouseholdRelation.HouseholdName AS '세대주 관계',
    Person.Name AS '성명',
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
    ) and addresshistory not in (SELECT addresshistory FROM Household WHERE PersonId = 1
GROUP BY
    HouseholdRelation.HouseholdName, Person.Name, Person.registerNo, ChangeReason.ChangeReasonName
ORDER BY
    HouseholdRelation.HouseholdName, Person.Name;
    
SELECT
    HouseholdRelation.HouseholdName AS '세대주 관계',
    Person.Name AS '성명',
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
    (Household.HouseholdId = 1 AND AddressHistory.ChangeReasonNo = 1)
    OR Household.HouseholdId <> 1
GROUP BY
    HouseholdRelation.HouseholdName, Person.Name, Person.registerNo, ChangeReason.ChangeReasonName
ORDER BY
    HouseholdRelation.HouseholdName, Person.Name;
```

## 회고
- 요구사항 분석 이슈
  - 처음에 요구사항을 잘못 분석한 것 같다.
  - '세대'라는 개념이 주소를 기반으로 구성되어지는 것으로 판단하고, 그에 기반하여 erd를 만들었는데 나중에 알고보니 다른 개념이었다.
  - 기존의 세대로부터(아버지,어머니 등) 누군가가 나와서 한 세대를 이루고(사유: 세대 분리) 그 세대로 다시 다른 사람들이 들어가는(사유:전입 등) 식의 개념이었다.
- 개선점 
  - 초반에 요구사항 파악을 100% 파악하고 들어가야 한다. 좀 더 시간을 쏟자.
    - **개체테이블인지 관계테이블인지 우선 정하고!**
  - 세대는 사람과 사람사이의 관계를 넣어야한다 (일대일로)
      - 나는 세대에 가족번호를 인덱스로 사람들을 넣었는데, 이럼 가족들의 범위가 애매하고
  - 사람이 집을 가지면, 이사를 갈때마다 사람이 업데이트되는데 이게 error...
      - ⇒ 세대주만 집을 가지는게 베스트 !

## 피드백 개선 버전
- 요구사항을 더 자세하게 
- household의 개념을 재정의했다.
  - 그에 따라 changeReason테이블이 HouseholdHistory, AddressHistory, ChangeReason테이블 나뉘었다.
- insert 더미데이터와 select도 바뀜
