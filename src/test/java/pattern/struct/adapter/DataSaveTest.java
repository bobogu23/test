package pattern.struct.adapter;

/**
 * @author:ben.gu
 * @Date:2020/8/11 9:47 上午
 */
public class DataSaveTest {

    public static void main(String[] args) {
        DataService dataService = new DefaultDataService();
        dataService.save("111");

        dataService = new DataServiceAdapter(new DataEncrypt());
        dataService.save("111");
    }
}
