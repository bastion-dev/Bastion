@Grapes([
        @Grab(group = 'net.sourceforge.nekohtml', module = 'nekohtml', version = '1.9.14')
])
import org.cyberneko.html.parsers.SAXParser
def page = new XmlSlurper(new SAXParser()).parse(new File(tocSrc))
def lastDepth = 1;

page.depthFirst().each { node ->
    if (['H2', 'H3'].contains(node.name())) {
        if (lastDepth == 1 && node.name() == 'H2') {
            System.out.println('<ul>')
            lastDepth = 2
        } else if (lastDepth == 2 && node.name() == 'H3') {
            System.out.println('<ul>')
            lastDepth = 3
        } else if (lastDepth == 3 && node.name() == 'H2') {
            System.out.println('</ul>')
            lastDepth = 2
        }
        def anchor = node.A.'@href'.text()
        def heading = node.text();
        System.out.println("<li><a href='${anchor}'>${heading}</a></li>");
    }
}
System.out.println('</ul>')