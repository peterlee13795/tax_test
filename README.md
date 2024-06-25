# 요구사항
1. 회원가입
2. 로그인
3. 스크래핑
4. 결정세액 조회

# 사용 라이브러리
- Spring Security
   - 사용 목적
     - 회원 인증/인가
     - API 경로에 따라 권한 요구 (Filter)
- OpenFeign
  - 사용 목적
    - 외부API 연동
    - 요청 retry, thread pool 관리를 위해 사용
    - 외부API 가독성 상향
- Spring Validation
  - 사용목적
    - api 요청 시 파라미터 유효성 검증

# 주목할만한 객체
- com.tax.test.type.TaxAmountType
  - 다형성을 가진 결정세액 계산 방법 선택 계산에 대한 책임 분리
- com.tax.test.api.auth.SignupChecker
  - 회원가입 허용 유저에 대한 유효성 검증 책임 분리
- com.tax.test.core.resolver.MemberPrincipalArgumentResolver
  - Spring Security Holder에서 접속된 회원 정보를 controller layer에서 접근하게 함
- com.tax.test.api.validator.RegNoChecker
  - 회원가입 시 regNo에 대한 유효성 검증