package wasp.DTO;

public class Role {

    private int roleID;
    private String roleCode;
    private String roleLabel;

    public Role() {
    }

    public Role(int roleID, String roleCode, String roleLabel) {
        this.roleID = roleID;
        this.roleCode = roleCode;
        this.roleLabel = roleLabel;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    @Override
    public String toString() {
        return String.format("Role{id:%d label:%s}", roleID, roleLabel);
    }
}
