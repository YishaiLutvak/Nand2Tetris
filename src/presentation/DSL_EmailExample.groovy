package presentation

class EmailDsl {
    String toText
    String fromText
    String body

    def static make(closure) {
        EmailDsl emailDsl = new EmailDsl()
        // any method called in closure will be delegated to the EmailDsl class
        closure.delegate = emailDsl
        closure()
        return emailDsl
    }

    def to(String toText) { this.toText = toText }
    def from(String fromText) { this.fromText = fromText }
    def body(String bodyText) { this.body = bodyText }

    @Override
    String toString() {
        return "EmailDsl { to = '${this.toText}', from = '${this.fromText}', body = '${this.body}' }"
    }
}

def myEmailDsl = EmailDsl.make {
    to "Nirav Assar"
    from "Barack Obama"
    body "How are things? We are doing well. Take care"
}

println(myEmailDsl)

