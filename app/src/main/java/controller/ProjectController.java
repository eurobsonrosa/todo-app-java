/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.connectionFactory;

/**
 *
 * @author Robson
 */
public class ProjectController {

    public void save(Project project) {

        String sql = "INSERT INTO projetos(name, description, createdAt, updatedAt)"
                + " VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            statement.execute();
        } catch (Exception e) {

            throw new RuntimeException("Erro ao salvar projeto " + e.getMessage(), e);
        } finally {

            connectionFactory.closeConnection(conn, statement);

        }

    }

    public void update(Project project) {

        String sql = "UPDATE projetos SET name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar projeto " + e.getMessage());
        } finally {
            connectionFactory.closeConnection(conn, statement);
        }

    }

    public List<Project> getAll() {

        String sql = "SELECT * FROM projetos";

        List<Project> projects = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;

        ResultSet resultSet = null;

        try {

            conn = connectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }

            return projects;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar projetos " + e.getMessage(), e);
        } finally {
            connectionFactory.closeConnection(conn, statement, resultSet);
        }
    }

    public void removeById(int projectId) {

        String sql = "DELETE FROM projetos WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setInt(1, projectId);

            statement.execute();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao excluir projeto " + e.getMessage(), e);
        } finally {
            connectionFactory.closeConnection(conn, statement);
        }

    }

}
