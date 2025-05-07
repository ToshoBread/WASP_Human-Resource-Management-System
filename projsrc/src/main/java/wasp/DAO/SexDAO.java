package wasp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wasp.DTO.Sex;
import wasp.database.DatabaseConnection;

public class SexDAO extends DAO<Sex> {

    @Override
    public Sex getById(int id) throws SQLException {
        Sex sex = new Sex();
        String sql = "SELECT * FROM sexList WHERE sexID = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet result = stmt.executeQuery();) {

            stmt.setInt(1, id);

            if (result.next()) {
                int storedSexID = result.getInt("sexID");
                String storedSexCode = result.getString("sexCode");
                String storedSexLabel = result.getString("sexLabel");

                sex.setSexID(storedSexID);
                sex.setSexCode(storedSexCode);
                sex.setSexLabel(storedSexLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sex;
    }

    @Override
    public List<Sex> getAll() throws SQLException {
        List<Sex> sexList = new ArrayList<>();
        String sql = "SELECT * FROM sexList";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet result = stmt.executeQuery();) {

            while (result.next()) {
                Sex sex = new Sex();
                int storedSexID = result.getInt("sexID");
                String storedSexCode = result.getString("sexCode");
                String storedSexLabel = result.getString("sexLabel");

                sex.setSexID(storedSexID);
                sex.setSexCode(storedSexCode);
                sex.setSexLabel(storedSexLabel);

                sexList.add(sex);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sexList;
    }

    @Override
    public void insert(Sex t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Sex t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void remove(Sex t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

}
