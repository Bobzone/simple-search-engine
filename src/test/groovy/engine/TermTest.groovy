package engine

import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors


class TermTest extends Specification {

    public static final String TEST_DOCS = "src/main/resources/test-docs"

    @Unroll
    def "Term constructor works"() {
        when:
        final List<Term> files = Files.walk(Paths.get(TEST_DOCS))
                .filter { it -> Files.isRegularFile(it) }
                .map { it.toFile() }
                .map { it -> new Term(it) }
                .collect(Collectors.toList())
        then:
        files.size() == 3
    }
}
