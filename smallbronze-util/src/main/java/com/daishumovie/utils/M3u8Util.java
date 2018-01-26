package com.daishumovie.utils;

import com.google.gson.Gson;
import com.iheartradio.m3u8.*;
import com.iheartradio.m3u8.data.MediaPlaylist;
import com.iheartradio.m3u8.data.Playlist;
import com.iheartradio.m3u8.data.TrackData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author Cheng Yufei
 * @create 2017-09-05 11:12
 **/
public class M3u8Util {

    /**
     * 合并 m3u8 文件
     *
     * @param filePathList
     * @return
     * @throws ParseException
     * @throws PlaylistException
     * @throws IOException
     */
    public static String merge(List<String> filePathList, String ossFileSavePath) throws ParseException, PlaylistException, IOException {

        if (CollectionUtils.isNullOrEmpty(filePathList)) {
            return getResult("FAIL", "合并文件为空", "");
        }

        List<Playlist> playlistList = new ArrayList<>();
        for (String filePath : filePathList) {
            playlistList.add(getPlaylist(saveFileFromUrl(filePath, ossFileSavePath)));
           /* //test
            playlistList.add(getPlaylist(filePath));*/
        }
        if (CollectionUtils.isNullOrEmpty(playlistList)) {
            return getResult("FAIL", "合并文件playlist为空", "");
        }
        List<Integer> durationList = new ArrayList<>();
        List<TrackData> resultTrackDates = new ArrayList<>();
        for (Playlist playlist : playlistList) {
            MediaPlaylist mediaPlaylist = playlist.getMediaPlaylist();
            durationList.add(mediaPlaylist.getTargetDuration());
            resultTrackDates.addAll(mediaPlaylist.getTracks());
        }

        int resultDuration = Collections.max(durationList);
        // 组装 MediaPlaylist
        MediaPlaylist.Builder mb = new MediaPlaylist.Builder();
        mb.withIsIframesOnly(false);
        mb.withIsOngoing(false);
        mb.withPlaylistType(null);
        mb.withStartData(null);
        mb.withTargetDuration(resultDuration);
        mb.withTracks(resultTrackDates);
        mb.withUnknownTags(new ArrayList<>());
        MediaPlaylist resultMediaPlaylist = mb.build();
        //组装 Playlist
        Playlist.Builder pb = new Playlist.Builder();
        pb.withCompatibilityVersion(4);
        pb.withMediaPlaylist(resultMediaPlaylist);
        pb.withExtended(true);
        pb.withMasterPlaylist(null);
        Playlist resultPlaylist = pb.build();

        //合并结果
        String result = writePlaylist(resultPlaylist);
        System.out.println(result);

        // TODO: 2017/9/6 合并完文件名称
        String str = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
        String dateStr = str.replaceAll("-", "").replaceAll(":", "");
        String resultPath = ossFileSavePath + File.separator + "merge_" + StringUtils.deleteWhitespace(dateStr) + ".m3u8";
        PrintWriter pw = new PrintWriter(new FileOutputStream(new File(resultPath)));
        pw.write(result);
        pw.flush();
        pw.close();


        return getResult("SUCCESS", "合并文件成功", resultPath);
    }

    /**
     * 获取 文件保存本地
     *
     * @param urlStr
     * @param ossFileSavePath 下载oss文件 存储路径确定
     * @return
     */
    public static String saveFileFromUrl(String urlStr, String ossFileSavePath) {
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(2000);
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = getBytes(inputStream);


            File saveDir = new File(ossFileSavePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }

            String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1);
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return saveDir + File.separator + fileName;
        } catch (IOException e) {
            return getResult("FAIL", "下载文件出错", e.getLocalizedMessage());
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length = 0;
        while ((length = inputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, length);
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static Playlist getPlaylist(String filePath) throws IOException, ParseException, PlaylistException {
        try (InputStream is = new FileInputStream(filePath)) {
            return new PlaylistParser(is, Format.EXT_M3U, Encoding.UTF_8).parse();
        }
    }

    public static String writePlaylist(Playlist playlist) throws IOException, ParseException, PlaylistException {

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PlaylistWriter writer = new PlaylistWriter(os, Format.EXT_M3U, Encoding.UTF_8);
            writer.write(playlist);

            return os.toString(Encoding.UTF_8.getValue());
        }
    }


    public static String getResult(String status, String msg, Object object) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("msg", msg);
        map.put("resultDate", object);
        return gson.toJson(map);
    }
}
