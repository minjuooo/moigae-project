# moigae-common-private

𝓶𝓸𝓲𝓰𝓪𝓮 🦮, 𝓶𝓮𝓽𝓪𝓷𝓮𝓽 𝓲𝓷𝓽𝓮𝓻𝓷𝓼𝓱𝓲𝓹

<br/><br/>

### Pull Request 하는 방법

1. 우측 상단의 Fork를 누른다.
2. 본인의 Git 계정에서 Fork한 Repository 상세 페이지에 접속한다.
3. 인터넷에 Git Pirvate Repository를 검색한 후 Clone 받는다.
4. 작업 후, Commit -> 본인의 Origin에 Push 한다.
5. 본인의 Origin Repository에서 원본 Repository로 Pull Request를 보낸다.

<br/><br/>

### ♻ 브랜치 전략 - Github flow

* 소규모 팀에서 사용하기 좋은 전략이라고 판단하여 본 프로젝트에 도입

<br/>

<img src="https://user-images.githubusercontent.com/76596316/218664821-90beae88-398b-4159-9469-7c0ddf04e99e.png" width="400">

<br/><br/>

### ♻ 도메인형 디렉터리 구조

* 보다 효율적이고 견고한 패키지 구조에 대한 고민 -> 도메인형 디렉터리 구조로 변경

<br/>

```
  com 
  ㄴ day
      ㄴ dream
          ㄴ component
          |   ㄴ user 
          |   |   ㄴ api
          |   |   |   ㄴ request
          |   |   |   ㄴ response
          |   |   ㄴ application
          |   |   ㄴ dto
          |   |   ㄴ repository
          |   |   ㄴ domain
          |   |   ㄴ exception 
          |   ... 
          ㄴ core
              ㄴ auth
              ㄴ common
              ㄴ config
              ㄴ error
              ㄴ infra
              ㄴ util
```

<br/><br/>

### ✔ 커밋 컨벤션

---

✅ 기본적으로 커밋 메시지는 제목 / 본문 / 관련 이슈로 구분 <br/>
✅ feat : 새로운 기능 추가 <br/>
✅ fix : 버그 수정 <br/>
✅ docs : 문서 수정 <br/>
✅ style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 <br/>
✅ refactor : 코드 리펙토링 <br/>
✅ test : 테스트 코드, 리펙토링 테스트 코드 추가 <br/>
✅ chore : 기타 변경사항 <br/>
✅ 제목은 50자를 넘기지 않고, 맞침표를 붙이지 않는다. <br/>
✅ 본문은 "어떻게" 보다 "무엇을" 과 "왜"를 설명한다. <br/>
✅ 제목과 구분되기 위해 한 칸 띄워 작성한다. <br/>

<br/><br/>