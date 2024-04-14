-- 01. 영화 '퍼스트 맨'의 제작 연도, 영문 제목, 러닝 타임, 플롯을 출력하세요.
SELECT m.releaseyear AS `제작 연도`,
       m.title       AS `영문 제목`,
       CASE
         WHEN runningtime % 60 = 0 THEN runningtime / 60
         ELSE Concat(Floor(runningtime / 60), '시간 ', runningtime % 60, '분'
              )
       end           AS `러닝 타임`,
       m.plot        AS 플롯
FROM   movie AS m;

-- 02. 2003년에 개봉한 영화의 한글 제목과 영문 제목을 출력하세요
SELECT m.koreantitle AS `한글 제목`,
       m.title       AS `영문 제목`
FROM   movie AS m
WHERE  m.releaseyear IN ( 2013 );

-- 03. 영화 '글래디에이터'의 작곡가를 고르세요
SELECT p.*
FROM   person AS p
       JOIN appear AS ap
         ON ap.personid = p.personid
       JOIN movie AS m
         ON ap.movieid = m.movieid
       JOIN role AS r
         ON r.roleid = ap.roleid
WHERE  ( m.koreantitle = '글래디에이터'
         AND r.rolekorname = '작곡' );

-- 04. 영화 '매트릭스' 의 감독이 몇명인지 출력하세요
SELECT Count(ap.personid) AS 매트릭스감독수
FROM   appear AS ap
       JOIN movie AS m
         ON m.movieid = ap.movieid
       JOIN `role` AS r
         ON r.roleid = ap.roleid
WHERE  ( r.rolekorname = '감독'
         AND m.koreantitle = '매트릭스' );

SELECT movieid,
       Count(*)
FROM   appear
WHERE  roleid = 2
GROUP  BY movieid;

-- 05. 감독이 2명 이상인 영화를 출력하세요
SELECT m.title
FROM   movie AS m
       JOIN appear AS ap
         ON m.movieid = ap.movieid
WHERE  ap.roleid = 2
GROUP  BY ap.movieid
HAVING Count(ap.personid) >= 2;

-- 06. '한스 짐머'가 참여한 영화 중 아카데미를 수상한 영화를 출력하세요
SELECT m.title
FROM   movie AS m
       JOIN appear AS ap
         ON ap.movieid = m.movieid
       JOIN awardinvolve AS aw
         ON aw.appearid = ap.appearid
       JOIN person AS p
         ON p.personid = ap.personid
WHERE  aw.winningid = 2
       AND p.koreanname = '한스 짐머'
GROUP  BY ap.movieid;

-- 07. 감독이 '제임스 카메론'이고 '아놀드 슈워제네거'가 출연한 영화를 출력하세요
SELECT m.title
FROM   movie AS m
WHERE  m.movieid IN (SELECT ap.movieid
                     FROM   appear AS ap
                            JOIN person AS p
                              ON p.personid = ap.personid
                            JOIN role AS r
                              ON r.roleid = ap.roleid
                     WHERE  ( p.koreanname = '제임스 카메론'
                              AND r.roleid = 2 )
                             OR ( p.koreanname = '아놀드 슈워제네거'
                                  AND r.roleid = 6 )
                     GROUP  BY ap.movieid
                     HAVING Count(ap.movieid) = 2);

-- 08. 상영시간이 100분 이상인 영화 중 레오나르도 디카프리오가 출연한 영화를 고르시오
SELECT DISTINCT m.title
FROM   movie AS m
       JOIN appear AS ap
         ON ap.movieid = m.movieid
       JOIN person AS p
         ON p.personid = ap.personid
WHERE  m.runningtime >= 100
       AND p.koreanname = '레오나르도 디카프리오';

-- 09. 청소년 관람불가 등급의 영화 중 가장 많은 수익을 얻은 영화를 고르시오
SELECT m.title
FROM   movie AS m
WHERE  m.gradeinkoreaid = 4
ORDER  BY ( m.boxofficeusgross + m.boxofficewwgross - m.budget ) DESC
LIMIT  1;

-- 10. 1999년 이전에 제작된 영화의 수익 평균을 고르시오
SELECT Avg(m.boxofficeusgross + m.boxofficewwgross - m.budget) 수익평균
FROM   movie m
WHERE  m.releaseyear < 1999;

-- 11. 가장 많은 제작비가 투입된 영화를 고르시오.
SELECT m.title
FROM   movie m
ORDER  BY m.budget DESC
LIMIT  1;

-- 12. 제작한 영화의 제작비 총합이 가장 높은 감독은 누구입니까?
SELECT p.koreanname
FROM   person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN movie m
         ON m.movieid = ap.movieid
WHERE  ap.roleid = 2
GROUP  BY p.personid
ORDER  BY Sum(m.budget) DESC
LIMIT  1;

-- 13. 출연한 영화의 모든 수익을 합하여, 총 수입이 가장 많은 배우를 출력하세요.
SELECT p.koreanname 
FROM   person p
       JOIN appear AS ap
         ON ap.personid = p.personid
       JOIN movie AS m
         ON ap.movieid = m.movieid
WHERE  ap.roleid IN ( 6, 7 )
GROUP  BY p.personid
ORDER  BY Sum(m.boxofficeusgross + m.boxofficewwgross - m.budget) DESC
LIMIT  1;

-- 14. 제작비가 가장 적게 투입된 영화의 수익을 고르세요. (제작비가 0인 영화는 제외합니다)
SELECT m.title,
       ( m.boxofficeusgross + m.boxofficewwgross - m.budget ) 수익
FROM   movie m
WHERE  m.budget > 0
ORDER  BY m.budget
LIMIT  1;

-- 15. 제작비가 5000만 달러 이하인 영화의 미국내 평균 수익을 고르세요
SELECT Concat('$', Avg(m.boxofficeusgross - m.budget)) `미국내 평균 수익`
FROM   movie m
WHERE  m.budget <= 50000000;

-- 16. 액션 장르 영화의 평균 수익을 고르세요
SELECT Concat('$', Avg(m.boxofficeusgross - m.budget))
       `액션 장르 평균 수익`
FROM   movie m
       JOIN moviegenre mg
         ON mg.movieid = m.movieid
WHERE  mg.genreid = 4;

-- 17. 드라마, 전쟁 장르의 영화를 고르세요.
SELECT m.title,
       (SELECT Group_concat(g.genrename)
        FROM   genre AS g
               JOIN moviegenre AS mg
                 ON mg.genreid = g.genreid
        WHERE  mg.movieid = m.movieid) AS 장르
FROM   movie m
       JOIN moviegenre mg
         ON mg.movieid = m.movieid
WHERE  mg.genreid = 1
        OR mg.genreid = 19
GROUP  BY m.movieid
HAVING Count(m.movieid) = 2;

-- 18. 톰 행크스가 출연한 영화 중 상영 시간이 가장 긴 영화의 제목, 한글제목, 개봉연도를 출력하세요.
SELECT m.title,
       m.koreantitle,
       m.releaseyear
FROM   movie m
       JOIN appear ap
         ON ap.movieid = m.movieid
       JOIN person p
         ON ap.personid = p.personid
WHERE  p.koreanname = '톰 행크스'
       AND ap.roleid = 6
GROUP  BY m.movieid
ORDER  BY m.runningtime
LIMIT  1;

-- 19. 아카데미 남우주연상을 가장 많이 수상한 배우를 고르시오
SELECT p.koreanname
FROM   person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN awardinvolve aw
         ON aw.appearid = ap.appearid
WHERE  aw.sectorid = 2
       AND aw.winningid = 2
GROUP  BY p.personid
ORDER  BY Count(aw.sectorid) DESC
LIMIT  1;

-- 20. 아카데미상을 가장 많이 수상한 배우를 고르시오 ('수상자 없음'이 이름인 영화인은 제외합니다)
SELECT p.name,
       Count(aw.involveid) PrizeCnt
FROM   person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN awardinvolve aw
         ON aw.appearid = ap.appearid
WHERE  ap.roleid IN ( 6, 7 )
       AND aw.winningid = 2 and p.koreanName NOT IN ('수상자 없음')
GROUP  BY p.personid
ORDER  BY Count(aw.involveid) DESC
LIMIT  1;

-- 21. 아카데미 남우주연상을 2번 이상 수상한 배우를 고르시오
SELECT p.name
FROM person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN awardinvolve aw
         ON aw.appearid = ap.appearid
WHERE  aw.sectorid = 2
       AND aw.winningid = 2 and p.koreanName NOT IN ('수상자 없음')
GROUP BY p.PersonID
HAVING COUNT(aw.InvolveID)>=2;
         
         
-- 23. 아카데미상을 가장 많이 수상한 사람을 고르세요.
SELECT p.name, COUNT(ap.PersonID) 수상횟수
FROM person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN awardinvolve aw
         ON aw.appearid = ap.appearid
where aw.winningid = 2 and p.koreanName NOT IN ('수상자 없음')
group by p.PersonID
order by COUNT(ap.PersonID) DESC LIMIT 1;


-- 24. 아카데미상에 가장 많이 노미네이트 된 영화를 고르세요.
SELECT m.title, COUNT(m.movieid) 수상횟수
FROM movie m
       JOIN appear ap
         ON ap.movieid = m.MovieID
       JOIN awardinvolve aw
         ON aw.appearid = ap.appearid
where aw.winningid = 1
group by m.movieid
order by COUNT(m.movieid) DESC LIMIT 1;


-- 25. 가장 많은 영화에 출연한 여배우를 고르세요.
select p.name, COUNT(ap.movieid) 출연횟수
from person p
	join appear ap on ap.personid = p.personid
    where ap.roleid = 7
    group by p.personid
    order by COUNT(ap.movieid) DESC limit 1;


-- 26. 수익이 가장 높은 영화 TOP 10을 출력하세요.
select m.Title, (m.BoxOfficeUSGross + m.BoxOfficeWWGross) 수익
from movie m
order by 수익 DESC LIMIT 10;


-- 27. 수익이 10억불 이상인 영화중 제작비가 1억불 이하인 영화를 고르시오.
select m.Title, (m.BoxOfficeUSGross + m.BoxOfficeWWGross) as 수익, m.budget 제작비
from movie m
where (m.BoxOfficeUSGross + m.BoxOfficeWWGross)>=1000000000 and m.budget<100000000;

-- 28. 전쟁 영화를 가장 많이 감독한 사람을 고르세요.
SELECT p.name
FROM   person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN moviegenre mg
         ON mg.movieid = ap.movieid
WHERE  mg.genreid = 19
       AND ap.roleid = 2
GROUP  BY p.personid
ORDER  BY Count(p.personid) DESC;

-- 29. 드라마에 가장 많이 출연한 사람을 고르세요.
select p.name, count(p.personid) 드라마출연횟수
from person p
	join appear ap
    on ap.personid = p.personid
    join moviegenre mg
    on mg.movieid = ap.movieid
where mg.genreid = 1 and ap.roleid in (6,7)
group by p.personid
order by count(p.personid) DESC LIMIT 1;    

-- 30. 드라마 장르에 출연했지만 호러 영화에 한번도 출연하지 않은 사람을 고르세요.
SELECT p.name,
       p.personid
FROM   person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN moviegenre mg
         ON mg.movieid = ap.movieid
WHERE  ( mg.genreid IN ( 1 )
         AND ap.roleid IN ( 6, 7 ) )
       AND p.personid NOT IN (SELECT p.personid
                              FROM   person p
                              WHERE  mg.genreid IN ( 22 )
                                     AND ap.roleid IN ( 6, 7 ))
GROUP  BY p.personid;

-- SELECT COUNT(*) AS total_rows
-- FROM (
--   SELECT p.name, p.personid
--   FROM person p
--   JOIN appear ap ON ap.personid = p.personid
--   JOIN moviegenre mg ON mg.movieid = ap.movieid
--   WHERE (mg.genreid IN (1) AND (ap.roleid IN (6) or ap.roleid in (7)))
--   AND p.personid NOT IN (
--     SELECT p.personid
--     FROM person p
--     JOIN appear ap ON ap.personid = p.personid
--     JOIN moviegenre mg ON mg.movieid = ap.movieid
--     WHERE mg.genreid IN (22) AND ap.roleid IN (6, 7)
--   )
--   GROUP BY p.personid
-- ) AS t;


-- 31. 아카데미 영화제가 가장 많이 열린 장소는 어디인가요?
select ay.Location, count(ay.awardyearid) 개최횟수
from awardyear ay
group by ay.Location
order by count(ay.awardyearid) DESC LIMIT 1;

-- 33. 첫 번째 아카데미 영화제가 열린지 올해 기준으로 몇년이 지났나요?
select 2024 - ay.year
from awardyear ay
order by ay.year LIMIT 1;


-- 34. SF 장르의 영화 중 아카데미 영화제 후보에 가장 많이 오른 영화를 구하세요.
select m.Title, count(m.movieid) 후보횟수
from movie m
join appear ap on ap.movieid = m.movieid
join awardinvolve aw on ap.appearid = aw.appearid
join moviegenre mg on mg.movieid = m.movieid
where mg.GenreID = 15 and aw.winningid=1
group by m.movieid 
order by count(m.movieid) DESC LIMIT 1;

-- 35. 드라마 장르의 영화의 아카데미 영화제 작품상 수상 비율을 구하세요.
SELECT Round((SELECT Count(DISTINCT ap.movieid)
              FROM   appear ap
                     JOIN awardinvolve aw
                       ON aw.appearid = ap.appearid
                     JOIN moviegenre mg
                       ON mg.movieid = ap.movieid
              WHERE  aw.sectorid IN ( 1, 27, 104 )
                     AND aw.winningid IN ( 2 )
                     AND mg.genreid IN ( 1 )) / (SELECT Count(DISTINCT m.movieid)
                                                 FROM   movie m
                                                        JOIN moviegenre mg
                                                          ON m.movieid = mg.movieid
                                                 WHERE  mg.genreid = 1) * 100, 3
       ) AS award_ratio;
       
-- SELECT count(*)
-- fROM   movie m
--       JOIN moviegenre mg
--          ON m.movieid = mg.movieid
-- WHERE  mg.genreid = 1;
-- SELECT COUNT(distinct ap.movieid)
--         from appear ap
--         join awardinvolve aw on aw.appearid = ap.appearid
--         join moviegenre mg on mg.movieid = ap.movieid
--         where aw.sectorid IN (1,27,104) and aw.winningid in (2) and mg.genreid in (1);