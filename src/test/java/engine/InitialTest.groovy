package engine

import spock.lang.Specification
import spock.lang.Unroll


public class InitialTest extends Specification {

    @Unroll
    def "test that spock works"() {
        given:
        def x = 1
        when:
        x++
        then:
        x == 2
    }

}