package presentation.typing

String s = new Date()               // implicit call to toString
println(s)

Boolean boxed = 'some string'       // Groovy truth
println(boxed)

boolean primitive = 'some string'   // Groovy truth
println(primitive)

Class myClass = 'java.lang.String'  // class coercion
println(myClass)

println("8 + '7' is ${8 + '7'}")    // casting 8 to String












// https://objectpartners.com/2013/08/19/optional-typing-in-groovy/