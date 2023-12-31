# 모바일신분증 연계 SP서버 구축 가이드

## 모바일신분증 개발지원센터
> - 홈페이지 주소 : https://dev.mobileid.go.kr
> - 연계서비스 이용절차 : https://dev.mobileid.go.kr/mip/dfs/apiuse/apiusestep.do

### 사전준비
> - 연계서비스 이용절차(1~6번) 선행 및 SP서버에서 DID 및 Wallet 생성, 모바일신분증 블록체인 내 DID 등록
> - 연계서비스 이용절차(7번) 내 SP서버 샘플코드 다운로드
> - Java 및 Spring Boot 실행환경 구축(Eclipse, VSCode 등)
> - 테스트 앱 설치 및 테스트데이터 등록을 통한 테스트용 모바일신분증 발급

### 1. SP 서버 구축하기
> - 연계서비스를 이용하기 위해서는 이용기관에서 SP서버를 자체구축하여야 합니다.
> - SP서버 구축을 위해서는 아래의 샘플소스코드를 빌드하여 SP서버에서 실행하거나, 이용기관의 BIZ로직이 정의되어있는 WAS(Web Application Service)에 SDK를 추가하여 구현할 수 있습니다.
> - 샘플 소스코드 다운로드

#### 1.1 SP 서버 설정
SP서버의 기본환경을 설정합니다.(application.properties 설정)
SP서버의 인터넷망 IP, 서비스의 포트번호, Connection Timeout, 이용할 블록체인 서버의 주소를 설정합니다.
<img src="./스크린샷 2023-10-10 오후 9.26.34.png" />
```groovy
server.port=8081
server.servlet.context-path=/
spring.mvc.view.prefix=/WEB-INF-views/
spring.mvc.view.suffix=.jsp
server.servlet.jsp.init-parameters.development=true
spring.devtools.liverload.enagled=true
```

#### 1.2. DID 및 Wallet 설정
이용기관에서 생성한 DID 및 Wallet 정보를 설정합니다.(application.properties 설정)
두 파일의 경로와 생성 시 설정한 키값, 발급받은 블록체인 계정 및 서비스코드를 설정합니다.
<img src="./스크린샷 2023-10-10 오후 9.26.56.png" />

#### 1.3. 서비스코드 및 인증방식 설정
서버 생성 시 초기 데이터베이스를 설정합니다.(data.sql)
기관 환경에 따라 SP서버와 서비스명, 기관 BI, 인증방식(일반인증, 안심인증) 등 상호작용할 초기데이터를 설정합니다.
<img src="./스크린샷 2023-10-10 오후 9.25.59.png" />

#### 1.4. 샘플소스 빌드 및 실행을 통한 간이테스트
설정을 완료한 뒤 샘플소스를 빌드하여 실행하고, 발급받은 테스트앱 및 테스트신분증으로 신분증 인증절차를 테스트합니다.
<img src="./스크린샷 2023-07-27 오후 8.26.33.png" />

#### 1.5. 소스코드 분석 및 개인정보 복호화 테스트

#### 1.6. 기관 현황분석 및 현지화 개발

### 2. 간편인증용 SP 서버 구축하기
