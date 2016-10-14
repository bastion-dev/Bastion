@Grapes([
    @Grab(group = 'net.sourceforge.nekohtml', module = 'nekohtml', version = '1.9.14'),
    @Grab(group='commons-io', module='commons-io', version='2.5')
])
import org.cyberneko.html.parsers.SAXParser
import org.apache.commons.io.FileUtils

def pomFileSrc = new File((String) pomSrc)
def pom = new XmlSlurper(new SAXParser()).parse(pomFileSrc)
def pomVersion = pom.'BODY'.'PROJECT'.'VERSION'.text()

def readmeFile = new File((String) readmeSrc)
def readmeFileText = readmeFile.getText('UTF-8')
readmeFileText = readmeFileText.replace('[VERSION]', pomVersion)
def sourcesDir = new File((String) sources);

readmeFileText.eachMatch("\\[ex:([A-Za-z0-1\\-]+?)\\]") {
    exampleText, exampleName ->
        def exampleBoundary = '// docs:' + exampleName
        for (def source : FileUtils.iterateFiles(sourcesDir, ['java', 'groovy'] as String[], true)) {
            String sourceText = source.getText('UTF-8')
            def exampleStartIndex = sourceText.indexOf(exampleBoundary)
            if (exampleStartIndex != -1) {
                exampleStartIndex += exampleBoundary.length()
                def exampleEndIndex = sourceText.indexOf(exampleBoundary, exampleStartIndex)
                if (exampleEndIndex != -1) {
                    def example = '```java\n' + sourceText.substring(exampleStartIndex, exampleEndIndex).stripIndent().trim() + '\n```'
                    readmeFileText = readmeFileText.replace(exampleText, example)
                    return
                }
            }
        }
}

def targetReadme = new File((String) readmeDst)
targetReadme.write(readmeFileText, 'UTF-8')