# JPA 및 DB 설정

## H2 데이터베이스 실행

### 설치
* [h2 데이터베이스 홈페이지](https://www.h2database.com/html/main.html): `1.4.200`
* 다운로드 후 압축 풀기

### 실행

* 압축 푼 경로에 이동하여 실행: java가 깔려있어야 함
```bash
~/h2/bin  ll                                                    ok | 02:10:27
total 4976
-rw-rw-r--@ 1 eraser  staff   2.4M  1 17 10:12 h2-2.1.210.jar
-rw-rw-r--@ 1 eraser  staff    98B  1 17 10:12 h2.bat
-rw-rw-r--@ 1 eraser  staff   109B  1 17 10:12 h2.sh
-rw-rw-r--@ 1 eraser  staff   105B  1 17 10:12 h2w.bat
~/h2/bin chmod +x h2.sh
~/h2/bin ./h2.sh
```
* 실행 결과
  * `http://localhost:8082/login.jsp?jsessionid=94a80934db4f30ec4a81194d27f6fa8f` 
  * `8082` 포트, 데이터베이스 파일을 실행하려면 url 뒤의 키 값을 유지해야 함

### 파일로 실행

* 데이터베이스 파일 생성
  * 설치 경로(JDBC URL): `jdbc:h2:~/jpashop` (최소 한번)
    * 파일 모드로 실행하기 위함
    * 파일 생성 확인: `~/jpashop.mv.db `
* 이후 접속 방법: `jdbc:h2:tcp://localhost/~/jpashop`
