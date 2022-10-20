/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.connectionFactory;

/**
 *
 * @author Robson
 */
public class TaskController {

    public void save(Task task) throws SQLException {

        String sql = "INSERT INTO tarefas (idProject, name, description, "
                + "completed, notes, deadline, createdAt, updatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //criando conexão com o banco de dados.
            conn = connectionFactory.getConnection();
            
            //preparando a query
            statement = conn.prepareStatement(sql);
            
            //setando valores
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            
            //executando query
            statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar tarefa "
                    + e.getMessage(), e);
        } finally {
            connectionFactory.closeConnection(conn, statement);

        }

    }

    public void update(Task task) {

        String sql = "UPDATE tarefas SET idProject = ?, name = ?, "
                + "description = ?, notes = ?, completed = ?, deadline = ?, "
                + "createdAt = ?, updatedAt = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement statemet = null;

        try {

            conn = connectionFactory.getConnection();
            statemet = conn.prepareStatement(sql);

            statemet.setInt(1, task.getIdProject());
            statemet.setString(2, task.getName());
            statemet.setString(3, task.getDescription());
            statemet.setString(4, task.getNotes());
            statemet.setBoolean(5, task.isCompleted());
            statemet.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statemet.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statemet.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statemet.setInt(9, task.getId());

            statemet.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar tarefa "
                    + e.getMessage(), e);
        } finally {
            connectionFactory.closeConnection(conn, statemet);
        }

    }

    public void removeById(int taskId) {

        String sql = "DELETE FROM tarefas WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectionFactory.getConnection(); // pede conexão
            statement = conn.prepareStatement(sql); //prepara sql
            statement.setInt(1, taskId); // altera a primeira interrogação do sql por taskId. Prepara o sql para executar no banco de dados.
            statement.execute(); //executa           

        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover tarefa "
                    + e.getMessage(), e);
        } finally {
            connectionFactory.closeConnection(conn, statement);
        }

    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tarefas WHERE idProject = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //Lista de tarefa que será devolvida
        List<Task> tasks = new ArrayList<>();

        try {
            conn = connectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();

                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setCreatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);
            }
            //retorna lista de tarefas
            return tasks;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar tarefas "
                    + e.getMessage(), e);
        } finally {
            connectionFactory.closeConnection(conn, statement, resultSet);
        }

    }
}
