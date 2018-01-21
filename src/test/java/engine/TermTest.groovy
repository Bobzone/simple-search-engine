package engine

import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors


class TermTest extends Specification {

    @Unroll
    def "Term constructor works"() {
        when:
        final List<Term> files = Files.walk(Paths.get("test-docs"))
                .filter { it -> Files.isRegularFile(it) }
                .map { it.toFile() }
                .map { it -> new Term(it) }
                .collect(Collectors.toList())
        then:
        files.size() == 3
    }
}
