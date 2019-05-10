package crawler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author:ben.gu
 * @Date:2019/5/8 3:04 PM
 */
public class NatureTrackGenetator {
    static Map<String, String> categoryMap = Maps.newHashMap();

    static Map<String, List<TrackResponseData.Entity>> categoryTracks = Maps.newHashMap();

    static List<String> urls = Lists.newArrayList();

    static String directory = "/Users/xmly/code/test/test/src/test/resources/tracks/";

    static {
        categoryMap.put("ff80808169e800a3016a00906662000a", "自然音-VIP");
        categoryMap.put("ff8080816768e7ca01676952dae30007", "自然音-动物");
        categoryMap.put("ff8080816768e65c0167695353e50009", "自然音-学校");
        categoryMap.put("ff8080816768e82101676953ca220009", "自然音-白噪音");
        categoryMap.put("ff8080816768e8210167695607dc000a", "自然音-野外");
        categoryMap.put("ff8080816768e7ca01676956e6450008", "自然音-都市");

        categoryTracks.put("ff80808169e800a3016a00906662000a", Lists.newArrayList());
        categoryTracks.put("ff8080816768e7ca01676952dae30007", Lists.newArrayList());
        categoryTracks.put("ff8080816768e65c0167695353e50009", Lists.newArrayList());
        categoryTracks.put("ff8080816768e82101676953ca220009", Lists.newArrayList());
        categoryTracks.put("ff8080816768e8210167695607dc000a", Lists.newArrayList());
        categoryTracks.put("ff8080816768e7ca01676956e6450008", Lists.newArrayList());

        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=1F2FAAAB346E45D6975A1E9AAB088A60&page=1&signToken=67191F925DD9B4DFCC6468BC7AC3D42F&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=2FCB27207F904A2484BDE209C4724BC3&page=5&signToken=E771225F4B6C85D9BA7A27B1404226A1&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=3F1756B0F83647F498D1D07A185D21DB&page=11&signToken=CA2E9847FF344D53A43B24D49BD83C97&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=4316F2AFD4A84D3C976EEDADE5DBD61C&page=8&signToken=0BA6C72E2A1E647F631882D3723B3D4B&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=4FACC7D6A61840E2ADFE4949238E04D7&page=10&signToken=E825DB83464DBB3A8400A1FA28B08AF0&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=9AFF4BFC227F4EB499BD2FFC3D72D170&page=6&signToken=5A2E3A15A87A15B87341E1CAA8E8DEF5&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=A985A2F65EBB4967A29162F35CADBCD0&page=7&signToken=2D8F3C44D8C13C9573B900517B7CAC0F&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=C6E3FECCF435434F9B4D61E38E9CFAB7&page=4&signToken=57EFF003F5350FD91C77E44B9F516B7C&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=D1C926C9852945A8BB95AF9410A4FC58&page=3&signToken=9D918B62BD0EA9FA2BE8FABCDBBEE523&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=E2F2CA78276645F49D9EDEAB8AD80E59&page=2&signToken=7598C03F5B2889ED684E33F58F58FBAF&user=5cd23ef5bc810224ba0c0f14");
        urls.add(
                "https://music.snailsleep.net/snail-music/music/natural/sound/list?accessKey=55cb56fcb734e3aa1ec8febd583d1cde&category=0&nonce_str=F35D52CECEB5409091BEA06EA94AFFAA&page=9&signToken=A17EFBD26EB9447DF6477CFF9C7C6F12&user=5cd23ef5bc810224ba0c0f14");

    }

    public static void main(String args[]) throws Exception {
        String thinkingCategoryUrl = "https://music.snailsleep.net/snail-music/music/sleeping/album/list?accessKey=d7e84063a6f4c5644012560d08d805bf&nonce_str=DCDDF6E311BE427DA6FB6901B92295D2&offset=100&page=1&signToken=77564935852B9EE677781F6FCF155A11&user=5cd23ef5bc810224ba0c0f14";
        String categoryTrackUrl = "https://music.snailsleep.net/snail-music/music/sleeping/single/list?accessKey=d7e84063a6f4c5644012560d08d805bf&album=%s&nonce_str=1E37CE86636E4229B9E19CD071F6D002&offset=1000&page=1&signToken=40066BCB2C9C19E3ADC03A6762D7E29A&user=5cd23ef5bc810224ba0c0f14";
        downLoadThinkingTracks(thinkingCategoryUrl, categoryTrackUrl, "助眠曲-");

    }

    private static void downLoadThinkingTracks(String categoryUrl, String trackUrl, String categoryName)
            throws Exception {
        //        String thinkingCategoryUrl = "https://music.snailsleep.net/snail-music/music/meditation/album/list?accessKey=d7e84063a6f4c5644012560d08d805bf&nonce_str=6FBFC7A3004A4CF9B7EE7E0B3CAEAA2C&offset=100&page=1&signToken=8AD2AAC64B08B11AFD12706EEB538C6F&user=5cd23ef5bc810224ba0c0f14";
        //        String categoryTrackUrl = "https://music.snailsleep.net/snail-music/music/meditation/single/list?accessKey=d7e84063a6f4c5644012560d08d805bf&album=%s&nonce_str=DC9A630D8EB84ACDA5E01D344EFDC1CB&signToken=14790363BDEE618E2CFEA81D0DFABCC2&user=5cd23ef5bc810224ba0c0f14";
        //获取冥想分类下的子分类
        HttpGet get = new HttpGet(categoryUrl);
        CloseableHttpResponse response = HttpPoster.getHttpClient().execute(get);
        String s = HttpPoster.parseResponse(response);
        System.err.println("response:" + s);
        TrackResponseData thinkingCategories = JSON.parseObject(s, TrackResponseData.class);
        //根据子分类查询声音列表
        List<TrackResponseData.Entity> entities = thinkingCategories.getResult().getEntities();

        for (TrackResponseData.Entity entity : entities) {
            String url = String.format(trackUrl, entity.getUnique());
            get = new HttpGet(url);
            response = HttpPoster.getHttpClient().execute(get);
            s = HttpPoster.parseResponse(response);
            TrackResponseData tracks = JSON.parseObject(s, TrackResponseData.class);
            List<TrackResponseData.Entity> list = tracks.getResult().getEntities();
            //排序
            Collections.sort(list);
            //写入文件

            try {
                File file = new File(directory + categoryName + entity.getName() + ".csv");
                FileOutputStream fos = new FileOutputStream(file);
                FileChannel channel = fos.getChannel();
                //写一行标题
                channel.write(ByteBuffer.wrap(("排序,URL,标题,图片" + System.lineSeparator()).getBytes("UTF-8")));

                int rank = 0;

                for (TrackResponseData.Entity e : list) {
                    ByteBuffer buffer = ByteBuffer.wrap((++rank + "," + e.getUrl() + "," + e.getName() + ","
                            + e.getCover() + System.lineSeparator()).getBytes("UTF-8"));
                    while (buffer.hasRemaining()) {
                        try {
                            channel.write(buffer);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                channel.force(true);
                channel.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private static void downloadNatureTracks() throws IOException {
        for (String url : urls) {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = HttpPoster.getHttpClient().execute(get);
            String s = HttpPoster.parseResponse(response);
            System.err.println("response:" + s);

            TrackResponseData trackResponseData = JSON.parseObject(s, TrackResponseData.class);
            //解析成具体数据,根据category 放入对应的map
            List<TrackResponseData.Entity> entities = trackResponseData.getResult().getEntities();
            for (TrackResponseData.Entity entity : entities) {
                categoryTracks.get(entity.getCategory()).add(entity);
            }

        }
        //排序

        categoryTracks.forEach((key, value) -> Collections.sort(value));

        //写入CSV文件
        categoryTracks.forEach((key, value) -> {
            String fileName = categoryMap.get(key);
            try {
                File file = new File(directory + fileName + ".csv");
                FileOutputStream fos = new FileOutputStream(file);
                FileChannel channel = fos.getChannel();
                List<TrackResponseData.Entity> entities = value;
                //写一行标题
                channel.write(ByteBuffer.wrap(("排序,URL,标题,图片" + System.lineSeparator()).getBytes("UTF-8")));

                int rank = 0;

                for (TrackResponseData.Entity e : entities) {
                    ByteBuffer buffer = ByteBuffer.wrap((++rank + "," + e.getUrl() + "," + e.getName() + ","
                            + e.getCover() + System.lineSeparator()).getBytes("UTF-8"));
                    while (buffer.hasRemaining()) {
                        try {
                            channel.write(buffer);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                channel.force(true);
                channel.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

}
