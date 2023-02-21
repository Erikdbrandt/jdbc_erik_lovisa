package el.jdbc_erik_lovisa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class PostGradDOA {


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    public record Artist(int artist_id, String name) {
    }


    public void getAllArtists() {

        String sql = "SELECT * FROM artist";

        try(Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = conn.prepareStatement(sql)) {

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Artist artist = new Artist(result.getInt("artist_id"), result.getString("name"));
                System.out.println(artist);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
