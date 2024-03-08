# 벽돌깨기 게임 
고전적인 게임 중 벽돌 깨기가 있다. 게임 플레이어가 발사한 볼은 공간에 튕기면서 돌아다니고, 공간 내에 존재하는 다양한 막대들은 특성에 따라 깨지거나 반사만 시키는 등 다양한 행동을 취한다.

## 요구사항
- Ball
    - 모든 물체에 반사된다.
    - 한개 이상도 가능하다.
    - 즉, 동시에 두개 이상의 ball 사용도 가능하며, ball간 충돌시에도 튕긴다.
    - 게임당 1개 이상의 볼이 주어질 수 있다.
        - 동시 사용과는 다르다

- Brick
    - 단색 또는 복수의 색 사용이 가능하다.
    - 종류에 따라 점수를 달리 줄 수 있다.
    - 종류에 따라 강도를 정할 수 있다.
        - 1번에 깨지는 벽돌, 3번에 깨지는 벽돌이 존재할 수 있다.
        - 깨지지 않는 벽돌이 존재할 수 있다.

- Control Bar
    - 깨지지 않는다.
    - ball을 튕길때 마다 크기가 변경될 수 있다.
    - 위치에 따라 반사 각도가 달라 질 수 있다.

- 점수판 
    - 시작시 인원을 지정하고, 점수판은 해당 인원만큼 생성될 수 있다.


### 도메인 정리

- GameWorld ---- JFrame


- Ball (Regionable)  
    - color, region

- --- BoundedBall (Bounded)

- ------ MovableBall (Movable)


- Box (Regionable)
    - color, region


- ---MovableBox (Movable)
  - Vector


- ------ Brick (Breakable)
    - score, hp

- BrickStatus : 벽돌의 종류
  - hp가 1이면 한번에 깨진다. (점수 +1)
    - hp가 3이면 3번에 깨진다. (점수 +3)
    - hp가 -1이면 깨지지 않는다.  // 무한
        - ex. 깨지지 않는 벽돌, 컨트롤 바, 점수판


- Control Bar --- Box 
    - 


- Score
    - 벽돌 하나가 깨질때마다 점수 추가
        - 


### 기능 요구사항
- interface
    - Regionable(Ball, Control Bar, ScoreBoard)
    - Paintable(Ball) : 단색 또는 복수의 색 사용이 가능하다.
    - Bounded(Ball) : 반사 가능하다
    - Movable(Ball, Control Bar) 
    - Breakable(Brick)
      -    - hp가 1이면 한번에 깨진다. (점수 +1)
      - hp가 3이면 3번에 깨진다. (점수 +3)
      - hp가 -1이면 깨지지 않는다.  // 무한
          - ex. 깨지지 않는 벽돌, 컨트롤 바, 점수판
- 
