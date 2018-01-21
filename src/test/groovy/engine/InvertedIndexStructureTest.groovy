package engine

import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

class InvertedIndexStructureTest extends Specification {

    public static final String TEST_DOCS = "src/main/resources/test-docs"
    private List<File> files

    def setup() {
        files = Files.walk(Paths.get(TEST_DOCS))
                .filter { it -> Files.isRegularFile(it) }
                .map { it.toFile() }
                .collect(Collectors.toList())
    }

    @Unroll
    def "Java8 method of reading files works ok"() {
        when:
        final List<File> files = Files.walk(Paths.get(TEST_DOCS))
                .filter { it -> Files.isRegularFile(it) }
                .map { it.toFile() }
                .collect(Collectors.toList())
        then:
        this.files.size() == 3
    }

    @Unroll
    def "You can put data from single file into structure and it gets properly distributed in the mappings"() {
        given:
        File file = new File(TEST_DOCS + "/first.txt")
        when:
        def structure = new InvertedIndexStructure(file)
        then:
        structure.mappings.keySet().size() == 6
    }

    @Unroll
    def "Putting data from list of files works and gets properly distributed in the index structure"() {
        when:
        def structure = new InvertedIndexStructure(files)
        then:
        structure.mappings.keySet().size() == 12

    }

    @Unroll
    def "Searching for word 'brown' returns the first and second documents because they appeared there"() {
        when:
        def structure = new InvertedIndexStructure(files)
        def searchResult = structure.find("brown")
        then:
        searchResult.get(0).toString() == "first.txt"
        searchResult.get(1).toString() == "second.txt"
    }

    @Unroll
    def "Searching for word 'fox' returns the first and third documents because they appeared there"() {
        when:
        def structure = new InvertedIndexStructure(files)
        def searchResult = structure.find("fox")
        then:
        searchResult.get(0).toString() == "first.txt"
        searchResult.get(1).toString() == "third.txt"
    }
}