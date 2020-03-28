package db.shard;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 使用sharding-jdbc 分库分表例子
 * https://shardingsphere.apache.org/document/current/cn/manual/sharding-jdbc/usage/sharding/
 * @author:ben.gu
 * @Date:2020/3/28 10:29 PM
 */
public class ShardingJdbcTest {

    public static void main(String args[]) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/db00");
        dataSource1.setUsername("root");
        dataSource1.setPassword("bobogu23@");
        dataSourceMap.put("db00", dataSource1);

        // 配置第二个数据源
        BasicDataSource dataSource2 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/db01");
        dataSource2.setUsername("root");
        dataSource2.setPassword("bobogu23@");
        dataSourceMap.put("db01", dataSource2);

        // 配置tb_test_shard表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("tb_test_shard",
                "db0${0..1}.tb_test_shard_${0..1}");
        //tb_test_shard_0

        // 配置分库 + 分表策略
        orderTableRuleConfig
                .setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "db0${id % 2}"));
        //分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("id", "tb_test_shard_${id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,
                new Properties());
        String sql = "select * from  tb_test_shard where id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 36);
        try (ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                System.out.println("id:" + rs.getInt(1));
                System.out.println("status:" + rs.getInt(2));
                System.out.println("position:" + rs.getInt(3));
            }

        }

    }
}
