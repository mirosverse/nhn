# 스레드 풀 예제 - 파일 다운로드 스레드
날짜 : 2024.03.12

## ExecutorService (Worker.java)
- ExecutorService : 자바에서 스레드풀을 관리하고 작업을 실행하는 인터페이스
  - `ExecutorService executor = Executors.newFixedThreadPool(5);`
    -  Executors 클래스의 정적 메서드를 사용하여 크기가 5인 스레드풀을 생성   
  - `submit(Runnable)`
    - 스레드풀에 제출. 파라미터는 Runnable or Callable 객체
  - `executor.execute(Runnable);`
    - 스레드풀에 제출. 
  - `executor.shutdown();`
    - 스레드풀에 대해 작업을 종료하라는 신호를 보낸다. 이 메서드 호출 이후에는 더 이상 새로운 작업을 스레드풀에 제출할 수 없음.
  - `while (!executor.isTerminated()) {`
    - 스레드풀의 모든 작업이 끝날때까지    

## ThreadGroup (Exam03)
- `ThreadGroup group = new ThreadGroup("group");`
  - 스레드들을 그룹화하고 관리하는 클래스
 
## ExecutorService - `submit()` vs `execute()`
- `submit()`
  - Future 객체를 반환 (제출한 작업의 결과)
  - 예외는 Future의 get() 메서드로 접근 가능
- `execute()`
  - void를 반환
  - 예외는 호출한 스레드로 던져짐
- 대부분의 경우엔 submit를 사용하는게 나을 것 같다. 
