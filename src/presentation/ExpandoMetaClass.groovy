package presentation

// https://en.wikipedia.org/wiki/Apache_Groovy

/**
 * Prototype extension
 * Groovy offers support for prototype extension through ExpandoMetaClass,
 * Extension Modules (only in Groovy 2),
 * Objective-C-like Categories and DelegatingMetaClass.
 * ExpandoMetaClass offers a domain-specific language (DSL) to express the changes in the class easily,
 * similar to Ruby's open class concept:
 */

/*
 * Groovy offers support for prototype
 * extension through ExpandoMetaClass,
 * ExpandoMetaClass offers a
 * domain-specific language (DSL)
 * to express the changes in the class easily,
 * similar to Ruby's open class concept:
 */
Number.metaClass {
    println(delegate.toString())
    sqrt = { Math.sqrt(delegate) }
}

assert 9.sqrt() == 3
assert 4.sqrt() == 2











/**
 * Groovy's changes in code through prototyping are not visible in Java,
 * since each attribute/method invocation in Groovy goes through the metaclass registry.
 * The changed code can only be accessed from Java by going to the metaclass registry.
 * Groovy also allows overriding methods as getProperty(), propertyMissing() among others,
 * enabling the developer to intercept calls to an object and specify an action for them,
 * in a simplified aspect-oriented way.
 * The following code enables the class java.lang.String to respond to the hex property:
 */


enum Color {
    BLACK('#000000'), WHITE('#FFFFFF'), RED('#FF0000'), BLUE('#0000FF')
    String hex
    Color(String hex) {
        this.hex = hex
    }
}

String.metaClass.getProperty = { String property ->
    def stringColor = delegate
    if (property == 'hex') {
        Color.values().find { it.name().equalsIgnoreCase stringColor }?.hex
    }
}

assert "WHITE".hex == "#FFFFFF"
assert "BLUE".hex == "#0000FF"
assert "BLACK".hex == "#000000"
assert "GREEN".hex == null