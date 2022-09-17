# shoten-url

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

## 고려사항

대용량 데이터가 들어온다고 가정하여 부하분산 처리

- Paritioning
    - 키를 이용하여 객체를 저장하는 Hash Partitioning 고려
- Cache
    - 자주 접근하는 URL에 캐시기능을 넣어 DB에 접근하기 전에 캐시에서 먼저 데이터 확인 → 서버 Scale Out
    ![image](https://user-images.githubusercontent.com/73684562/190849443-ce190978-dc90-47e8-ba9e-f1a35f77751b.png)

