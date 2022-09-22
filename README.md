# Short Link Management

## 사용 기술 및 환경

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=plastic&logo=Springboot&logoColor=white"/> <img src="https://img.shields.io/badge/MySql-4479A1?style=plastic&logo=MySql&logoColor=white"> <img src="https://img.shields.io/badge/JAVA11-F7901E?style=plastic&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-DC382D?style=plastic&logo=Redis&logoColor=white"/>

## 개요
- 긴 Url을 Short Link형태로 만들어주는 API 개발

## System Design
![image](https://user-images.githubusercontent.com/73684562/190848511-cc50f0e3-76f9-443e-b016-883aa3c6f742.png)


## DB설계(ERD)
![image](https://user-images.githubusercontent.com/73684562/190848517-d7489e9d-78d9-430f-a2ef-e0a30a9f12d7.png)

## API Document
[AB180 ShortenUrl API 보러가기](https://documenter.getpostman.com/view/20884244/2s7YmonTiQ)
| API | Description |
| --- | --- |
| POST /short-links | 원본 URL 단축 |
| GET /short-links/{short_id} | 단축 URL 조회 |
| GET /r/{short_id} | 원래 입력했던 URL로 redirect 해주는 API |

## 고려사항

대용량 데이터가 들어온다고 가정하여 부하분산 처리
- Base62알고리즘 사용
    - MD5, SHA-1와 같은 Hash Function은 암호화 결과값이 길게 나타낸다. 알고리즘은 길이가 짧은 Short Link를 만드는데 부적합.
    - Base62문자표에서 +, /, = 3개의 문자표가 추가된 것이 Base64인데 URL에는 해당 3개의 문자표가 포함되어 나타내어 진다
    - 따라서 해당 문자를 포함하여 사용할 경우 문자가 변경될 우려가 있어 해당 문자표를 제외한 Base62로 알고리즘을 선택
    
- DB를 사용하여 Origin URL에 대응하는 key 생성
    - 중복 코드 우려
        - 여러 사용자가 동일한 URL을 입력하면 동일한 Shorten URL을 얻을 수 있기 때문에 고유한 URL을 얻지 못한다. → Origin URl에 시퀀스번호를 추가하여 Unique한 ShortenURL 생성
    - 기존 origin Url을 그대로 BASE62로 인코딩 할 경우 상당히 긴 URL이 발생 할 수 있다.  
        - Origin URL 그대로 ex1) https://smelllee.tistory.com/66?category=1083296 → 36zGdn4a0hTk5u2vHyPkVlSeva2JUQ5qZXTs7AH73xr78opzx3tRexEm4hKgLbJxu
        - 시퀀스 ex2) 1000 → tqd3A

- Paritioning
    - 키를 이용하여 객체를 저장하는 Hash Partitioning 고려
    - 파티셔닝 선택이유 ? ShortenURL API는 쓰기보단 검색 및 읽기의 활용량이 훨씬 더 많기때문에 master(쓰기), slave(읽기)로 관리하는 레플리카보다는 특정 기준에 따라 분산하여 저장하는 파티셔닝이 더 적합하다고 판단.
    - 해시 파티셔닝 선택이유? 해시 키의 첫글자를 기반으로 URL을 각 파티션에 분산하여 저장 할수는 있지만 불균형한 DB서버를 가질 우려가 있다 따라서 해시 기반으로 파티션을 지정하고 골고루 분포하도록 고려
    
- Cache
    - 자주 접근하는 URL에 캐시기능을 넣어 DB에 접근하기 전에 캐시에서 먼저 데이터 확인 → 서버 Scale Out
    ![image](https://user-images.githubusercontent.com/73684562/190849443-ce190978-dc90-47e8-ba9e-f1a35f77751b.png)
    
## 아쉬운점
- DB ID 타입
    - INT 사용 시 약 21억개의 데이터를 저장 할 수 있다. 적은 범위를 탐색하므로 속도가 비교적 빠르다.
    - BIGINT 사용 시 약 922경의 데이터를 저장 할 수 있지만, 데이터가 많아질수록 속도가 느리다.
    => 약 10억개의 데이터라고 가정했기때문에 BIGINT(약 922경) 보다는 INT로 사용했어도 충분할것 같다.
- DB 선택 ( RDB vs NoSQL)
    - 단축 URL API는 단순 변환 및 읽기이고 관계설정이 필요없으므로 데이터의 정합성을 보장하는 RDB보단 가용성이 중요한 NoSQL을 사용했어도 무방했다.


## 기록
https://smelllee.tistory.com/86


