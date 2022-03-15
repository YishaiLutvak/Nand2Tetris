package project11

/**
 *
 */
class Symbol {

    static enum KIND {STATIC, FIELD, ARG, VAR, NONE};

    private String type
    private KIND kind
    private int index

    /**
     * Constructor
     * @param type
     * @param kind
     * @param index
     */
    Symbol(String type, KIND kind, int index) {
        this.type = type
        this.kind = kind
        this.index = index
    }

    /**
     * Getter
     * @return type of Symbol
     */
    String getType() {
        return type
    }

    /**
     * Getter
     * @return kind of Symbol
     */
    KIND getKind() {
        return kind
    }

    /**
     * Getter
     * @return index of Symbol
     */
    int getIndex() {
        return index
    }

    @Override
    String toString() {
        return "Symbol{" +
                "type='" + type + '\'' +
                ", kind=" + kind +
                ", index=" + index +
                '}'
    }
}