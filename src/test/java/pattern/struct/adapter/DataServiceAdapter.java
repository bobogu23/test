package pattern.struct.adapter;

/**
 * @author:ben.gu
 * @Date:2020/8/11 9:52 上午
 */
public class DataServiceAdapter extends DefaultDataService {
    private DataEncrypt dataEncrypt;

    public DataServiceAdapter(DataEncrypt dataEncrypt) {
        this.dataEncrypt = dataEncrypt;
    }

    @Override
    public void save(String text) {
        text = dataEncrypt.encrypt(text);
        super.save(text);
    }
}
