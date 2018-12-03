package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;


@Component
public class SqlLiteCredentialsRepository implements CredentialsRepository {

    private final Credentials credentials;

    private final JdbcTemplate jdbcTemplate;

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

    private String getUsername(){
        return jdbcTemplate.queryForObject(
                "SELECT `value` FROM `data` WHERE `key` = 'username'", new Object[] { },
                (rs, rowNum) -> rs.getString("value")
        );
    }

    private String getPassword(){
        return jdbcTemplate.queryForObject(
                "SELECT `value` FROM `data` WHERE `key` = 'password'", new Object[] { },
                (rs, rowNum) -> rs.getString("value")
        );
    }

    private Long getDevice(){
        String value = jdbcTemplate.queryForObject(
                "SELECT `value` FROM `data` WHERE `key` = 'device'", new Object[] { },
                (rs, rowNum) -> rs.getString("value")
        );
        return Long.parseLong(value);
    }

    private void setUsername(String username){
        jdbcTemplate.update(
                "UPDATE `data` SET `value` = ? WHERE `key` = 'username'",
                username
        );
        this.credentials.setUsername(username);
    }

    private void setPassword(String password){
        jdbcTemplate.update(
                "UPDATE `data` SET `value` = ? WHERE `key` = 'password'",
                password
        );
        this.credentials.setPassword(password);
    }

    private void setDevice(Long device){
        jdbcTemplate.update(
                "UPDATE `data` SET `value` = ? WHERE `key` = 'device'",
                device.toString()
        );
        this.credentials.setDevice(device);
    }

    @Override
    public Credentials get() {
        return credentials;
    }

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
