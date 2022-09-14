import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import crud.repository.PostgresBeerPurchaseRepository;
import crud.repository.PostgresBeerRepository;
import crud.service.LoggedBeerService;
import database.Database;
import login.repository.PostgresUserRepository;
import login.service.LoggedLoginService;

public class Main {

    public static void main(String[] args) throws InterruptedException, SQLException {
        removeAllData();
        populateUsers();

        var userRepository = new PostgresUserRepository();
        var beerRepository = new PostgresBeerRepository();
        var beerPurchaseRepository = new PostgresBeerPurchaseRepository();
        var userService = new LoggedLoginService(userRepository);
        var beerService = new LoggedBeerService(beerRepository, beerPurchaseRepository);

        var adminOperations = new ArrayList<AdminOperation>();

        for (int i = 1; i <= 1000; i++) {
            var thread = new AdminOperation("administrator@gmail.com", "Beer Name " + i, userService, beerService);
            adminOperations.add(thread);
            thread.start();
        }

        for (int i = 0; i <= adminOperations.size() - 1; i++) {
            adminOperations.get(i).join();
        }

        var clientOperations = new ArrayList<ClientOperation>();

        for (int i = 1; i <= 400; i++) {
            var thread = new ClientOperation("client@gmail.com", "Beer Name " + i, userService, beerService);
            clientOperations.add(thread);
            thread.start();
        }

        for (int i = 0; i <= clientOperations.size() - 1; i++) {
            clientOperations.get(i).join();
        }
    }

    private static void removeAllData() throws SQLException {
        var connection = Database.getConnection();

        PreparedStatement query1 = connection.prepareStatement("delete from beers");
        PreparedStatement query2 = connection.prepareStatement("delete from logs");
        PreparedStatement query3 = connection.prepareStatement("delete from beer_purchase");
        PreparedStatement query4 = connection.prepareStatement("delete from users");

        query1.execute();
        query2.execute();
        query3.execute();
        query4.execute();
    }

    private static void populateUsers() throws SQLException {
        var connection = Database.getConnection();

        PreparedStatement query1 = connection.prepareStatement("INSERT INTO users (email, password, type) VALUES (?,?,?)");
        PreparedStatement query2 = connection.prepareStatement("INSERT INTO users (email, password, type) VALUES (?,?,?)");

        query1.setString(1, "administrator@gmail.com");
        query1.setString(2, "123412");
        query1.setString(3, "ADMINISTRATOR");

        query2.setString(1, "client@gmail.com");
        query2.setString(2, "123412");
        query2.setString(3, "CLIENT");

        query1.execute();
        query2.execute();
    }
}
