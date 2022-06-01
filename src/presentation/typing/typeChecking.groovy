package presentation.typing

class Duck {
    void quack() {
        println 'Quack!'
    }
}
class QuackingBird {
    void quack() {
        println 'Quack!'
    }
}
@groovy.transform.TypeChecked
void accept(Duck quacker) {
    quacker.quack()
}

//@groovy.transform.TypeChecked
//void accept(quacker) {
//    quacker.quack() // compile error!
//}

accept(new Duck())

