package pattern.struct.adapter;

/**
 * @author:ben.gu
 * @Date:2020/8/11 9:44 上午
 */
public class DefaultDataService implements DataService {

    @Override
    public void save(String text) {
        System.out.println("保存:" + text + "到数据库");
    }
}
