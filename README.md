# MessagingService

Websocket 을 활용한 실시간 메세징 서비스

<!-- prettier-ignore-start -->
![SpringBoot](https://shields.io/badge/springboot-black?logo=springboot&style=for-the-badge%22)
![Docker](https://shields.io/badge/docker-black?logo=docker&style=for-the-badge%22)
![Mysql](https://shields.io/badge/mysql-black?logo=mysql&style=for-the-badge%22)
![Redis](https://shields.io/badge/redis-black?logo=redis&style=for-the-badge%22)
<!-- prettier-ignore-end -->

### System Requirements

- [java] 17
- [springboot] 3.3.0
- [docker] 20.10.12
- [mysql] 8
- [redis] 7.2.5

## Local 실행 환경
### MySQL, Redis start
`/bin/docker-compose-up.sh`

### MySQL, Redis stop
`/bin/docker-compose-down.sh`

### Spring Boot application 실행
`./gradlew bootRun`

## docker 실행 상태에서 DB/Redis 접근
### MySQL
`docker exec -it ms-mysql bash` \
`mysql -u local -p`

### Redis
`docker exec -it ms-redis sh` \
`redis-cli`