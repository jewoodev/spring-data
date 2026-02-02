# Mybatis E-commerce Domain
## 회원 도메인 
- 회원은 회원등록을 하면 최초에 '대기중' 상태가 된다. 
- 회원가입 중에 이메일 인증에 성공해야 회원가입에 성공할 수 있다.
  - 이메일 인증 요청을 하면 회원이 입력한 메일 주소로 랜덤한 숫자 6자리가 발송된다.
- 회원이 되기 전에는 상품 정보를 확인하는 것만 가능하다. 
- 회원은 최초에는 상품을 구입할 수만 있다. 
  - 이후에 신청을 하고 허가를 받으면 판매자로써 상품을 등록할 수 있다.
- 회원은 비밀번호를 변경할 수 있다.
- 회원은 탈퇴할 수 있다.
  - 탈퇴 회원 정보는 삭제되지 않는다. 따라서 탈퇴한 회원의 이메일 주소와 아이디로는 가입이 불가능하다.
  - 등록 시간, 등록 완료 시간, 탈퇴 시간은 저장된다.

## 상품 도메인
- 상품은 한 번에 등록되거나 한 번에 수정될 수 있다.
- 상품은 '상품 이름'과 '가격'으로 검색이 가능하다.
  - 검색 결과는 검색어와 완전히 일치하거나 부분 일치로 처리한다.
    - 부분 일치는 앞부분 일치만을 허용한다.
 
# Domain model
## 기본 엔티티(BaseEntity)
- `createdAt`: 생성 시간
- `modifiedAt`: 수정 시간
---
## [회원 애그리거트]
## 회원(Member)
_Domain Model_
### 속성
- `memberId`: 식별자
- `username`: 아이디
- `password`: 비밀번호를 해시암호화 한 결과
- `email`: 이메일 주소
- `role'`: `Role` 회원 역할
### 행위
- `emailVerify`: 이메일 인증: (emailAddr, verificationCode, emailVerifier)
- `register`: 회원 등록: (username, password, email, role, passwordEncoder)
- `leave`: 회원 탈퇴: (memberId)
- `changePassword`: 비밀번호 변경: (newPassword, passwordEncoder)
### 규칙
- 회원의 비밀번호는 해시 암호화하여 저장한다.
- 아이디는 5 ~ 20 자 길이를 갖는다.
- 비밀번호는 8 ~ 100 자 길이를 갖는다.

## 비밀번호 부호기(PasswordEncoder)
_Domain Model Service_
### 행위
- `encode`: 비밀번호 암호화: (password)
- `matches`: 비밀번호 일치 여부 확인: (password, encodedPassword)

## 이메일 인증기(EmailVerifier)
_Domain Model Service_
### 행위
- `verify`: 인증: (emailAddr, verificationCode)

## 이메일 인증 캐시(EmailVerificationCache)
_Domain Model Service_
### 행위
- `getVerificationCode`: 인증 코드 가져오기: (emailAddr)
- `setVerificationCode`: 인증 코드 캐싱하기: (emailAddr, verificationCode)

## 중복 회원 검사기(MemberDuplicationVerifier)
_Domain Model Service_
### 행위
- `verify`: 중복 회원 검사: (member)

---
## [상품 애그리거트]