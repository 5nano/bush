package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDao {

    private Statement statement;
    private ResultSet resultSet;

    public CompaniesDao() throws SQLException {
        statement = PostgresConnector.getInstance().getConnection().createStatement();
    }

    public void insert(Company company) throws SQLException {
        PreparedStatement preparedStatement = PostgresConnector.getInstance()
                .getPreparedStatementFor("INSERT INTO compania VALUES (default, ?,?)");

        preparedStatement.setString(1, company.getName());
        preparedStatement.setString(2, company.getDescription());

        preparedStatement.executeUpdate();
    }

    public List<Company> getCompanies() throws SQLException {
        resultSet = statement.executeQuery("SELECT nombre,descripcion FROM compania");
        List<Company> companies = new ArrayList<>();
        while (resultSet.next()) {
            companies.add(new Company(resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return companies;
    }

    public void getCompany(Company Company) throws SQLException {
        String query = "SELECT Nombre,Descripcion FROM compania WHERE nombre = '" + Company.getName() + "'";
        resultSet = statement.executeQuery(query);
    }

    public void delete(String companyName) throws SQLException {
        PreparedStatement preparedStatement = PostgresConnector.getInstance()
                .getPreparedStatementFor("DELETE FROM compania WHERE nombre ='" + companyName + "'");
        preparedStatement.executeUpdate();
    }

    public void modify(Company company) throws SQLException {
        this.delete(company.getName());
        this.insert(company);
    }
}