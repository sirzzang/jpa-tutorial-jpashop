# View 설정 및 확인

> 템플릿엔진 종류: Thymeleaf, Apache Freemarker, Mustache, Groovy Templates

* 동적 페이지: `templates`
* 정적 페이지: `static`

## 동적 페이지 반환

### Controller
 간단한 예제 컨트롤러를 만들어 보자.
* `Model`: Controller에서 데이터를 실어서 View로 넘길 수 있음
  * `data` 속성에 `hello`라는 값을 설정
* `return`문 뒤에 반환할 템플릿 이름(`.html` 생략)
  * 스프링 부트의 Thymeleaf가 view 이름을 매핑
  * 매핑 규칙: `resources:templates/` + `{viewname} + `.html`
```java
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello");
        return "hello"; // 반환할 템플릿 이름
    }
}
```

### View

 `hello.html` 뷰 예제를 만들어 보자. 컨트롤러에서 렌더링할 화면이다.
* `html` namespace: thymeleaf
* `p` 태그의 `th` 속성에서 `data` name 활용

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<p th:text="'안녕하세요. ' + ${data}" >안녕하세요. 손님</p>
</body>
</html>
```

 실행 후 `/hello`로 접속하면 다음과 같은 화면을 확인할 수 있다.
 
## 정적 페이지 반환

 렌더링 없이 정적인 페이지만을 반환할 수도 있다.
 
### 예제

 간단한 index 페이지를 작성해 보자.
 
```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
Hello
<a href="/hello">hello</a>
</body>
</html>
```

## spring-boot-devtools
 개발 시 편리한 기능을 담고 있는 라이브러리인데, 템플릿 작성 시 리로딩을 지원해서 편하다.

* `build.gradle` 설정 추가
```java
 
```
* 이후 소스 변경 시 `build` > `recompile`
