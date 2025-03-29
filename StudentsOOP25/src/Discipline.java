import java.util.Objects;

public class Discipline {
    private String name;
    private boolean isMandatory;
    public Discipline(String name, boolean mandatory) {
        this.name = name;
        this.isMandatory = mandatory;
    }
    // <editor-fold desc="Getters and Setters">
    public String GetName() {
        return name;
    }
    public boolean GetIsMandatory()
    {
        return isMandatory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }
// </editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return isMandatory == that.isMandatory && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "name='" + name + '\'' +
                ", isMandatory=" + isMandatory +
                '}';
    }
}
