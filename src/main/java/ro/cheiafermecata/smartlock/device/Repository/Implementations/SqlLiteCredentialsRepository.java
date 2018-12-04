package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;


@Component
public class SqlLiteCredentialsRepository implements CredentialsRepository {

    private final Credentials credentials;

    private final JdbcTemplate jdbcTemplate;

    /**
     * Creates the basic structure for the database if it does not exist
     * @param jdbcTemplate the interface to the db
     */
    SqlLiteCredentialsRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.credentials = new Credentials();
        try{
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `data` (`key` TEXT NOT NULL,`value` TEXT NOT NULL,PRIMARY KEY(`key`))");
            jdbcTemplate.execute("INSERT INTO `data`(`key`,`value`) SELECT 'username','' WHERE NOT EXISTS (SELECT 1 FROM `data` WHERE `key` =  'username')");
            jdbcTemplate.execute("INSERT INTO `data`(`key`,`value`) SELECT 'password','' WHERE NOT EXISTS (SELECT 1 FROM `data` WHERE `key` =  'password')");
            jdbcTemplate.execute("INSERT INTO `data`(`key`,`value`) SELECT 'device','0' WHERE NOT EXISTS (SELECT 1 FROM `data` WHERE `key` =  'device')");
        }catch (Exception e){
            this.credentials.setUsername("");
            this.credentials.setPassword("");
            this.credentials.setDevice(0L);
        }
        this.credentials.setUsername(this.getUsername());
        this.credentials.setPassword(this.getPassword());
        this.credentials.setDevice(this.getDevice());
    }

    /**
     * Gets the username from the db
     * @return the username
     */
    private String getUsername(){
        return jdbcTemplate.queryForObject(
                "SELECT `value` FROM `data` WHERE `key` = 'username'", new Object[] { },
                (rs, rowNum) -> rs.getString("value")
        );
    }

    /**
     * Gets the password from the db
     * @return the password
     */
    private String getPassword(){
        return jdbcTemplate.queryForObject(
                "SELECT `value` FROM `data` WHERE `key` = 'password'", new Object[] { },
                (rs, rowNum) -> rs.getString("value")
        );
    }

    /**
     * Gets the device id from the db
     * @return the device id
     */
    private Long getDevice(){
        String value = jdbcTemplate.queryForObject(
                "SELECT `value` FROM `data` WHERE `key` = 'device'", new Object[] { },
                (rs, rowNum) -> rs.getString("value")
        );
        return Long.parseLong(value);
    }

    /**
     * Updates the username in the db and in memory
     * @param username the username to persist
     */
    private void setUsername(String username){
        jdbcTemplate.update(
                "UPDATE `data` SET `value` = ? WHERE `key` = 'username'",
                username
        );
        this.credentials.setUsername(username);
    }

    /**
     * Updates the password in the db and in memory
     * @param password the password to persist
     */
    private void setPassword(String password){
        jdbcTemplate.update(
                "UPDATE `data` SET `value` = ? WHERE `key` = 'password'",
                password
        );
        this.credentials.setPassword(password);
    }

    /**
     * Updates the device id in the db and in memory
     * @param device the device it to persist
     */
    private void setDevice(Long device){
        jdbcTemplate.update(
                "UPDATE `data` SET `value` = ? WHERE `key` = 'device'",
                device.toString()
        );
        this.credentials.setDevice(device);
    }

    /**
     * Return the credential from memory
     * @return the stored credentials
     */
    @Override
    public Credentials get() {
        return credentials;
    }

    /**
     * Updates the changed values of the stored credentials in db and in memory
     * @param credentials the credentials to persist
     */
    @Override
    public void save(Credentials credentials) {
        if(!this.credentials.getUsername().equals(credentials.getUsername())){
            this.setUsername(credentials.getUsername());
        }
        if(!this.credentials.getPassword().equals(credentials.getPassword())){
            this.setPassword(credentials.getPassword());
        }
        if(!credentials.getDevice().equals(0L) && !this.credentials.getDevice().equals(credentials.getDevice())){
            this.setDevice(credentials.getDevice());
        }
    }
}
