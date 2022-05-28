package presentation.vsJava.shorter



















names = ["Ted","Fred","Jed","Ned"]
println(names)
shortNames = names.findAll{it.size() < 4}
println shortNames.size()
shortNames.each {println(it)}
