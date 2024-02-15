package karate;

import org.springframework.boot.test.context.SpringBootTest;

import com.intuit.karate.junit5.Karate;

import br.com.vivo.Application;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {Application.class})
public class TestRunner {

    @Karate.Test
    Karate dummyTest() {
        return Karate.run("classpath:features/dummy.feature");
    }

    @Karate.Test
    Karate hello1Test() {
        return Karate.run("classpath:features/hello/hello1.feature");
    }

    @Karate.Test
    Karate hello2Test() {
        return Karate.run("classpath:features/hello/hello2.feature");
    }
}
