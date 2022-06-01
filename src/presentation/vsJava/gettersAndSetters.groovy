package presentation.vsJava

/**
 * https://en.wikipedia.org/wiki/Apache_Groovy
 * GroovyBeans are Groovy's version of JavaBeans. Groovy implicitly generates getters and setters.
 * In the following code, setColor(String color) and getColor() are implicitly generated.
 * The last two lines, which appear to access color directly, are actually calling the implicitly generated methods.
 */
class AGroovyBean {
    String color
    String getColor(){
        print('getColor is called\n')
        return color
    }
}

def myGroovyBean = new AGroovyBean()

myGroovyBean.setColor('baby blue')
assert myGroovyBean.getColor() == 'baby blue'

println("----------")

myGroovyBean.color = 'pewter'
assert myGroovyBean.color == 'pewter'

