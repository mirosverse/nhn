# 사용자/아이템/전적 관리를 위한 파일 기반의 데이터베이스 (with JSON)
날짜 : 2024.03.19

## 회고
- JSONArray가 클래스의 get()메서드가 있는 필드들을 자동으로 넣어주지만, 필드의 타입이 객체라면 맵핑해줘야한다는 단점이 있다. (->Jackson 라이브러리)
- jar파일로 실행하면 배포가 쉽다는 장점이 있지만, 개발을 하고 디버깅을 하기엔 아주 번거로웠어서 IDE와 maven의 간편함이 더욱 잘 느껴졌다. 

## 요구사항 정리
### 관리 정보
* 사용자
  * 아이디
  * 닉네임
* 아이템
  * 아이디
  * 모델
  * 체력(0 ~ 10000)
  * 공격력(0 ~ 100)
  * 방어력(0 ~ 50)
  * 이동속도(0 ~ 100)
  * 공격속도(0 ~ 100)
* 전적
  * 대전 횟수
  * 승리 횟수
* 변경 이력

### 명령어 옵션
```
^s| Option ^s| Long Option ^s| 설명
^s|-a ^s|--add |데이터를 추가합니다.
^s|-t ^s|--type |데이터의 종류를 지정합니다.
^s|-i ^s|--id |아이디
^s|-n ^s|--name |이름
^s|-l ^s|--list |목록을 보여 줍니다.
^s|-c ^s|--count |대전 횟수
^s|-W ^s|--Win |승리 횟수
^s|-h ^s|--help |도움말
^s|-e ^s|--energy |체력
^s|-a ^s|--attack |공격력
^s|-d ^s|--defence |방어력
^s|-m ^s|--moving-speed |이동속도
^s|-A ^s|--attack-speed |공격속도
^s|-L ^s|--history |변경이력
^s|-f ^s|--db-file |데이터 저장 파일

```
* 옵션에 따라 값이 추가될 수 있다.
* 옵션별로 조합이 가능하다.
* 프로그램 시작시 데이터 저장 파일이 지정되어야 한다.
  * 파일이 존재할 경우, 읽어서 JSONObject기반의 데이터베이스를 생성한다.
  * 프로그램 종료시 데이터를 파일에 문자열로 저장한다.

* 예를 들어,
  * 아이디 1234인 xtra 추가
+
```
java -jar recorder -a -t user -i 1234 --name "xtra" -f ./recorder.json
```
* 사용자 추가 오류
```
r recorder -a -t user -i 1234 --name "xtra" --attack 1234 -f ./recorder.json
```
사용자 추가시 공격력이 필요하지 않습니다.
----
* 사용자 목록 보기
```
java -jar recorder -l -t user -f ./recorder.json

ID    NAME
00001 XTRA
00002 Tiger
00003 무식이
```
### 요구 사항
1. 생성된 데이터는 JSON 문자열로 파일에 저장한다.
2. 파일에서 JSON 문자열을 읽어 들여서 각 object를 생성한다.
3. 데이터를 저장할 때마다 변경 이력을 추가하라
** 변경 이력에는 시간과 변경 내용이 포함된다.
** 변경 내용은 변경된 구성 요소를 구분할 수 있도록 한다.

### 실행 파일 만들기

* 왼쪽 아래 "MAVEN"에서 Lifecycle을 열고,
  * clean
  * compile
  * package를 순서대로 실행
* target에 recorder-1.0-SNAPSHOT.jar 생성됨
실행

```
java -jar recorder-1.0-SNAPSHOT.jar -a --user -i 1234 -n xtra
```
