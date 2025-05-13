package wasp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wasp.DTO.Job;
import wasp.database.DatabaseConnection;

public class JobDAO extends DAO<Job> {

    @Override
    public Job getById(int id) throws SQLException {
        String sql = "SELECT * FROM jobHistory WHERE employeeID = ?";
        Job job = new Job();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                job.setJobID(result.getInt("jobID"));
                job.setEmployeeID(result.getInt("employeeID"));
                job.setPosition(result.getString("position"));
                job.setDepartment(result.getString("department"));
                job.setEmployDate(result.getDate("employDate"));
                job.setEffectivityDate(result.getDate("effectivityDate"));
                job.setSeparationDate(result.getDate("separationDate"));
            }
        }

        return job;
    }

    @Override
    public List<Job> getAll() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobHistory";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Job job = new Job();

                job.setJobID(result.getInt("jobID"));
                job.setEmployeeID(result.getInt("employeeID"));
                job.setPosition(result.getString("position"));
                job.setDepartment(result.getString("department"));
                job.setEmployDate(result.getDate("employDate"));
                job.setEffectivityDate(result.getDate("effectivityDate"));
                job.setSeparationDate(result.getDate("separationDate"));

                jobs.add(job);
            }
        }

        return jobs;
    }

    @Override
    public void insert(Job t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Job t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void remove(Job t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

}
