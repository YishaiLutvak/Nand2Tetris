package project11

/**
 *
 */
class Symbol {

    static enum KIND {STATIC, FIELD, ARG, VAR, NONE};

    private String type;
    private KIND kind;
    private int index;

    Symbol(String type, KIND kind, int index) {
        this.type = type;
        this.kind = kind;
        this.index = index;
    }

    String getType() {
        return type;
    }

    KIND getKind() {
        return kind;
    }

    int getIndex() {
        return index;
    }

    @Override
    String toString() {
        return "Symbol{" +
                "type='" + type + '\'' +
                ", kind=" + kind +
                ", index=" + index +
                '}';
    }
}