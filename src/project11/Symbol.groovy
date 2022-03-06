package project11

/**
 *
 */
public class Symbol {

    public static enum KIND {STATIC, FIELD, ARG, VAR, NONE};

    private String type;
    private KIND kind;
    private int index;

    public Symbol(String type, KIND kind, int index) {
        this.type = type;
        this.kind = kind;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public KIND getKind() {
        return kind;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "type='" + type + '\'' +
                ", kind=" + kind +
                ", index=" + index +
                '}';
    }
}