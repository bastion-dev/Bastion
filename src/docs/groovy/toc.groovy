@Grapes([
    @Grab(group = 'net.sourceforge.nekohtml', module = 'nekohtml', version = '1.9.14'),
])
import org.cyberneko.html.parsers.SAXParser

class Depth {
    def lastLevel;
    def output;

    public Depth(StringBuilder output) {
        lastLevel = 1
        this.output = output
    }

    public void adjust(int level) {
        if (lastLevel < level) {
            int listsToOpen = level - lastLevel;
            openLists(listsToOpen)
        } else {
            int listsToClose = lastLevel - level;
            closeLists(listsToClose)
        }
        lastLevel = level;
    }

    private void closeLists(int levels) {
        addTag(levels, '</ul>')
    }

    private void openLists(int levels) {
        addTag(levels, '<ul>')
    }

    private void addTag(int amount, String tag) {
        for (int i = 0; i < amount; i++) {
            output.append(tag);
        }
    }
}

def userGuideHtmlFile = new File((String) tocSrc)
def pomFileSrc = new File((String) pomSrc)
def page = new XmlSlurper(new SAXParser()).parse(userGuideHtmlFile)
def pom = new XmlSlurper(new SAXParser()).parse(pomFileSrc)
def pomVersion = pom.'BODY'.'PROJECT'.'VERSION'.text()

def depth = new Depth(new StringBuilder());
page.depthFirst().each { node ->
    if (['H2', 'H3', 'H4'].contains(node.name())) {
        switch (node.name()) {
            case 'H2':
                depth.adjust(2);
                break;
            case 'H3':
                depth.adjust(3);
                break;
            case 'H4':
                depth.adjust(4);
                break;
        }
        def anchor = node.A.'@href'.text()
        def heading = node.text()
        depth.output.append("<li><a href='${anchor}'>${heading}</a></li>")
    }
}
depth.adjust(1)
def htmlWithoutToc = userGuideHtmlFile.getText('UTF-8')
def htmlWithToc = htmlWithoutToc.replace('[TOC]', depth.output.toString()).replace('[VERSION]', pomVersion);
userGuideHtmlFile.write(htmlWithToc, 'UTF-8')