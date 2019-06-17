package kdxf_track;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:ben.gu
 * @Date:2019/6/14 6:17 PM
 */
public class TestMultiTrack {

    private static final String HTTP_HEADER_AUTHORIZATION = "Authorization";

    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";

    private static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";

    static final String BOUNDARY = "=====iflytekdripvcservicenextpart=====";

    static final String MULTIPARTBOUNDARY = "\"" + BOUNDARY + "\"";

    static final String MULTIPARTCONTENTTYPE = "multipart/mixed;boundary=" + MULTIPARTBOUNDARY;

    static final String LRLN = "\r\n";

    static final String CONTENTTYPEFORMAT = "Content-Type:{0}" + LRLN;

    static final String CONTENTLENGTHFORMAT = "Content-Length:{0}" + LRLN;

    static final String STARTSEPERATER = "--" + BOUNDARY + LRLN;

    static final String END_SEPERATER = BOUNDARY + "--" + LRLN;

    static final String AUDIO_FORMAT_MP3 = "mp3";

    static final String AUDIO_FORMAT_WAV = "wav";

    static final int AUDIO_BIT_16 = 16;

    static final int AUDIO_RATE_16000 = 16;

    static final String CONTENT_TYPE_MP3 = "audio/mp3";

    public static void main(String args[]) throws Exception {
        RequestPackage jsonParam = new RequestPackage();
        Param param = buildParam();
        jsonParam.setParam(param);
        Base base = buildBase();
        jsonParam.setBase(base);

        List<PartEntry> multis = Lists.newArrayList();

        byte[] datas = getFileBytes();
        PartEntry entry = new PartEntry(CONTENT_TYPE_MP3, datas);
        multis.add(entry);

        byte[] bytes = new TestMultiTrack().packageMultiData(JSON.toJSONString(jsonParam), multis);

        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpPost post = new HttpPost("https://vc.com/v1/v1/vc.do");
        post.setHeader(HTTP_HEADER_CONTENT_TYPE, MULTIPARTCONTENTTYPE);
//        post.setHeader(HTTP_HEADER_CONTENT_LENGTH, bytes.length + "");

        HttpEntity entity = new ByteArrayEntity(bytes);
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);

        int statusCode = response.getStatusLine().getStatusCode();
        System.err.println("statusCode:" + statusCode);

        printResult(response);

    }

    private static String printResult(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();
        String mimeType = contentType.getMimeType();
        // 获取字节数组
        byte[] content = EntityUtils.toByteArray(entity);
        if (charset == null) {
            // 默认编码转成字符串
            String temp = new String(content);
            String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
            Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(temp);
            if (m.find() && m.groupCount() == 1) {
                charset = Charset.forName(m.group(1));
            } else {
                charset = Charset.forName("ISO-8859-1");
            }
        }

        return printResp(new String(content, charset));
    }

    public static String printResp(String response) {
        Assert.assertNotNull(response);
        System.err.println("response->" + response);
        return response;
    }

    private static byte[] getFileBytes() throws IOException {
        FileInputStream fis = new FileInputStream(
                new File("/Users/xmly/code/test/test/src/test/java/kdxf_track/人声音.mp3"));
        FileChannel channel = fis.getChannel();

        int size = (int) channel.size();
        byte[] datas = new byte[size];

        ByteBuffer byteBuffer = ByteBuffer.allocate(size);

        int read = channel.read(byteBuffer);
        byteBuffer.flip();

        byteBuffer.get(datas, 0, read);
        byteBuffer.clear();
        channel.close();
        fis.close();
        return datas;
    }

    private static Base buildBase() {
        Base base = new Base();
        base.setAppid("670b14728ad9902aecba32e22fa4f6bd");
        base.setTimestamp(System.currentTimeMillis() + "");
        return base;
    }

    private static Param buildParam() {
        Param param = new Param();
        AudioCodec audioCodec = new AudioCodec();
        audioCodec.setBit(AUDIO_BIT_16);
        audioCodec.setFormat(AUDIO_FORMAT_MP3);
        audioCodec.setRate(AUDIO_RATE_16000);
        audioCodec.setChannels(1);

        param.setReqcodec(audioCodec);

        AudioCodec audioCodecResp = new AudioCodec();
        audioCodecResp.setBit(AUDIO_BIT_16);
        audioCodecResp.setFormat(AUDIO_FORMAT_MP3);
        audioCodecResp.setRate(AUDIO_RATE_16000);
        audioCodecResp.setChannels(1);
        param.setRescodec(audioCodecResp);

        param.setSpd(50);
        param.setVcn("xiaoyi");
        param.setVol(50);
        param.setPit(50);
        return param;
    }

    private byte[] packageMultiData(String json, List<PartEntry> multis) {
        List<byte[]> parts = new ArrayList();
        byte[] tmp = json.getBytes();
        addPart(parts, "application/json", tmp);
        for (PartEntry entry : multis) {
            addPart(parts, entry.getContentType(), entry.getData());
        }
        parts.add(END_SEPERATER.getBytes());
        int len = 0;
        for (byte[] p : parts) {
            len += p.length;
        }
        byte[] packed = new byte[len];
        int destPos = 0;
        for (byte[] p : parts) {
            System.arraycopy(p, 0, packed, destPos, p.length);
            destPos += p.length;
        }
        return packed;
    }

    private void addPart(List parts, String contentType, byte[] part) {
        parts.add(STARTSEPERATER.getBytes());
        parts.add(MessageFormat.format(CONTENTTYPEFORMAT, contentType).getBytes());
        parts.add(MessageFormat.format(CONTENTLENGTHFORMAT, part.length).getBytes());
        parts.add(LRLN.getBytes());//段头和段体之间有⼀一个\r\n parts.add(part); parts.add(LRLN.getBytes());//每个 段体也以\r\n结束

    }

    static class PartEntry {
        String contentType;

        byte[] data;

        public PartEntry(String contentType, byte[] data) {
            this.contentType = contentType;
            this.data = data;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }

    static class RequestPackage {

        private Param param;

        private Base base;

        public void setParam(Param param) {
            this.param = param;
        }

        public void setBase(Base base) {
            this.base = base;
        }

        public Param getParam() {
            return param;
        }

        public Base getBase() {
            return base;
        }
    }

    static class Param {

        //请求⾳音频的编码
        private AudioCodec reqcodec;

        //返回⾳音频的编码
        private AudioCodec rescodec;

        private String vcn;

        private int spd;

        private int vol;

        private int pit;

        private String sub;

        public AudioCodec getReqcodec() {
            return reqcodec;
        }

        public void setReqcodec(AudioCodec reqcodec) {
            this.reqcodec = reqcodec;
        }

        public AudioCodec getRescodec() {
            return rescodec;
        }

        public void setRescodec(AudioCodec rescodec) {
            this.rescodec = rescodec;
        }

        public String getVcn() {
            return vcn;
        }

        public void setVcn(String vcn) {
            this.vcn = vcn;
        }

        public int getSpd() {
            return spd;
        }

        public void setSpd(int spd) {
            this.spd = spd;
        }

        public int getVol() {
            return vol;
        }

        public void setVol(int vol) {
            this.vol = vol;
        }

        public int getPit() {
            return pit;
        }

        public void setPit(int pit) {
            this.pit = pit;
        }

        public String getSub() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

    }

    static class AudioCodec {
        private String format;

        private int bit;

        private int rate;

        private int channels;

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getBit() {
            return bit;
        }

        public void setBit(int bit) {
            this.bit = bit;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public int getChannels() {
            return channels;
        }

        public void setChannels(int channels) {
            this.channels = channels;
        }
    }

    static class Base {
        private String appid;

        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
