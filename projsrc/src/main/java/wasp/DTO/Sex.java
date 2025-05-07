package wasp.DTO;

public class Sex {

    private int sexID;
    private String sexCode;
    private String sexLabel;

    public Sex() {
    }

    public Sex(int sexID, String sexCode, String sexLabel) {
        this.sexID = sexID;
        this.sexCode = sexCode;
        this.sexLabel = sexLabel;
    }

    public int getSexID() {
        return sexID;
    }

    public void setSexID(int sexID) {
        this.sexID = sexID;
    }

    public String getSexCode() {
        return sexCode;
    }

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public String getSexLabel() {
        return sexLabel;
    }

    public void setSexLabel(String sexLabel) {
        this.sexLabel = sexLabel;
    }
}
