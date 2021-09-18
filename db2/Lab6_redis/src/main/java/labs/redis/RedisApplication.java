package labs.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.Scanner;

@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisApplication.class);
	private Connection psql;
	private Jedis jedis;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

    @Override
	public void run(String ...args) {
        this.connectToRedis();
        this.connectToPostgres();
        Scanner input = new Scanner(System.in);
        StringBuilder queryBuilder = new StringBuilder();
        while(true) {
            System.out.println("Useful commands:\nexit - to exit\nclear - to clear redis logs");
            System.out.print("=# ");
            String bufQuery = input.nextLine();
            if (bufQuery.equals("exit")) {
                break;
            }
            if (bufQuery.equals("clear")) {
                this.clearLogs();
                continue;
            }
            if (bufQuery.contains(";")) {
                if (queryBuilder.append(bufQuery).toString().trim().toLowerCase().startsWith("select")) {
                    this.execute(queryBuilder.toString());
                } else {
                    System.out.println("You can use only select queries");
                }
            } else {
                System.out.println("Incorrect query. Try again");
            }
            queryBuilder.setLength(0);

        }
        this.disconnectFromPostgres();
        this.disconnectFromRedis();
    }

    private void execute(String query) {
	    try {
	        String log = this.jedis.hget("sql-logs", query);
	        if (log == null) {
                Statement statement = psql.createStatement();
                ResultSet results = statement.executeQuery(query);
                ResultSetMetaData metaData = results.getMetaData();

                String answer = "";
                StringBuilder answerMaker = new StringBuilder(answer);

                for (int i = 1; i <= metaData.getColumnCount(); ++i ) {
                    answerMaker.append(metaData.getColumnName(i));
                    if ( i != metaData.getColumnCount() ) {
                        answerMaker.append('\t');
                    } else {
                        answerMaker.append('\n');
                    }
                }

                while( results.next() ) {
                    for(int i = 1; i <= metaData.getColumnCount(); ++i) {
                        answerMaker.append(results.getString(i));
                        if ( i != metaData.getColumnCount() ) {
                            answerMaker.append('\t');
                        } else if (!results.isLast()) {
                            answerMaker.append('\n');
                        }
                    }
                }

                jedis.hset("sql-logs", query, answerMaker.toString());
                System.out.println("Result:\n" + answerMaker.toString());
                statement.close();
            } else {
	            System.out.println("Result from logs:\n" + log);
            }
        } catch (Exception e) {
	        System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void connectToPostgres() {
        try {
            psql = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lab6", "lab6", "lab666");
            System.out.println("PSQL says hello");
        } catch ( Exception e ) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    private void disconnectFromPostgres() {
        try {
            psql.close();
            System.out.println("PSQL says goodbye");
        } catch ( Exception e ) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    private void connectToRedis() {
        jedis = new Jedis("localhost");
        System.out.println("Redis says hello");
    }

    private void disconnectFromRedis() {
	    jedis.close();
	    System.out.println("Redis says goodbye");
    }

    private void clearLogs() {
        jedis.del("sql-logs");
        System.out.println("Redis cleared");
    }
}
