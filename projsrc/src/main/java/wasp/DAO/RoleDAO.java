package wasp.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wasp.DTO.Role;

import wasp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDAO extends DAO<Role> {

    @Override
    public Role getById(int id) throws SQLException {
        Role role = new Role();
        String sql = "SELECT * FROM roleList WHERE roleID = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet result = stmt.executeQuery();) {

            stmt.setInt(1, id);

            if (result.next()) {
                int storedRoleID = result.getInt("roleID");
                String storedRoleCode = result.getString("roleCode");
                String storedRoleLabel = result.getString("roleLabel");

                role.setRoleID(storedRoleID);
                role.setRoleCode(storedRoleCode);
                role.setRoleLabel(storedRoleLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;
    }

    @Override
    public List<Role> getAll() throws SQLException {
        List<Role> roleList = new ArrayList<>();
        String sql = "SELECT * FROM sexList";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet result = stmt.executeQuery();) {

            while (result.next()) {
                Role role = new Role();
                int storedRoleID = result.getInt("roleID");
                String storedRoleCode = result.getString("roleCode");
                String storedRoleLabel = result.getString("roleLabel");

                role.setRoleID(storedRoleID);
                role.setRoleCode(storedRoleCode);
                role.setRoleLabel(storedRoleLabel);

                roleList.add(role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roleList;

    }

    @Override
    public void insert(Role t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Role t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void remove(Role t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

}
