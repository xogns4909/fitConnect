# fitConnect 커뮤니티 웹 애플리케이션

fitConnect는 운동 파트너를 찾고, 운동 관련 정보를 공유할 수 있는 커뮤니티 웹 애플리케이션입니다. Spring Boot와 React를 기반으로 하며, H2 데이터베이스를 사용해 사용자 데이터를 관리합니다.

## 시작하기

로컬 환경에서 프로젝트를 성공적으로 실행하기 위한 상세한 지침입니다.

### 사전 요구 사항

- **Java JDK 17**: [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) 또는 [OpenJDK](https://jdk.java.net/17/)에서 다운로드 가능합니다.
- **Node.js v18.12.1**: [Node.js 공식 웹사이트](https://nodejs.org/)에서 다운로드 가능합니다.
- **H2 데이터베이스 2.1.214**: [H2 데이터베이스](https://www.h2database.com/html/main.html)에서 다운로드 가능합니다.
### 설치 및 실행 절차

#### H2 데이터베이스 설정

1. H2 데이터베이스를 설치한 후, bin 디렉토리에서 `h2.bat` (Windows) 또는 `h2.sh` (Mac/Linux)를 실행하여 H2 콘솔을 시작합니다.
2. 웹 브라우저에서 `http://localhost:8082`로 접속합니다.
3. 새로운 데이터베이스 연결을 생성하기 위해 다음 정보를 입력합니다:
   - JDBC URL: `jdbc:h2:~/fitConnect`
   - 사용자 이름: `sa` (기본값)
   - 비밀번호: (비워두기)
4. 연결 버튼을 클릭하여 fitConnect 데이터베이스를 생성합니다.

#### 프로젝트 클론 및 설정

```bash
# 저장소 클론
git clone https://github.com/xogns4909/fitConnect/

# Spring Boot 애플리케이션 실행
./mvnw spring-boot:run

# 프론트엔드 디렉토리로 이동
cd frontend

# 필요한 npm 패키지 설치
npm install

# React 개발 서버 시작
npm run start

