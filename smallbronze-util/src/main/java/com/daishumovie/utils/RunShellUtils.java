package com.daishumovie.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Created by yang on 2017/5/24.
 */
@Slf4j
public class RunShellUtils {

    private static final String cmd_ffmpeg = "/usr/local/ffmpeg/bin/ffmpeg";
    //    private static final String cmd_ffmpeg = "D:\\application\\program\\ffmpeg-3.3.2-win64\\bin\\ffmpeg.exe";
    private static final String cmd_ffprobe = "/usr/local/ffmpeg/bin/ffprobe";

    public static String getVideoSize(String filepath) {

        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return "1000x1000";
        }

        System.out.println("videoSize_filepath:" + filepath);
        //export LD_LIBRARY_PATH=/usr/local/ffmpeg/lib/:/usr/local/lib;
        String cmd = cmd_ffprobe + " -i " + filepath + " -show_streams 2>&1 | grep \"width\\|height\\|rotation\" | grep -v coded | awk -F\"=\" '{a[$1]=$2}END{if(length(a[\"rotation\"])>0){print a[\"height\"]\"x\"a[\"width\"]} else {print a[\"width\"]\"x\"a[\"height\"]}}'";
//        String cmd = "/usr/local/ffmpeg/bin/ffprobe -i " + filepath + " -show_streams 2>&1 | grep \"width\\|height\" | grep -v coded | awk -F\"=\" '{a[$1]=$2}END{print a[\"width\"]\"x\"a[\"height\"]}'";
        System.out.println("videoSize_filepath:" + filepath);
        return getRunShellResult(cmd);
    }

    public static String getVideoCut(String filepath, String newFile, String start, String duration) {
        System.out.println("filepath:" + filepath);
        String cmd = cmd_ffmpeg + " -i  " + filepath + " -vcodec copy -acodec copy -ss " + start + " -t " + duration + " " + newFile + " -y -v quiet";
        System.out.println("newFile:" + newFile);
        return getRunShellResult(cmd);
    }

    //去除噪音
    public static void removieSound(String filepath, String newFile) {
        System.out.println("filepath:" + filepath);
        String cmd = cmd_ffmpeg + " -i  " + filepath + " -af silenceremove=1:0:-50dB:-1:0:-50dB " + newFile;
        System.out.println("newFile:" + newFile);
        getRunShellResult(cmd);
    }

    //获取音频总播放时长
    public static String getAudioDuration(String filepath) {
        String cmd = cmd_ffprobe + " -i " + filepath + " -show_format 2>&1 | grep Duration|awk -F , '{print $1}' |awk -F ' ' '{print $2}' ";
        System.out.println("file:" + filepath + "|cmd:" + cmd);
        return getRunShellResult(cmd);
    }

    //获取音频采样率
    public static String getAuditSampleRate(String filepath) {
        String cmd = cmd_ffprobe + " -i " + filepath + " -show_format 2>&1 | grep Hz|awk -F , '{print $2}' |awk -F ' ' '{print $1}' ";
        System.out.println("file:" + filepath + "|cmd:" + cmd);
        return getRunShellResult(cmd);
    }

    public static String getFileSize(String filepath) {
        System.out.println("fileSize_filepath:" + filepath);
        String cmd = "/usr/bin/ls -l " + filepath + " | awk '{print $5}'";
        System.out.println("fileSize_filepath:" + filepath);
        return getRunShellResult(cmd);
    }


    public static String getCover(String videoFilepath, String outFilePath) {
        return getCover(videoFilepath, outFilePath, "5.000");
    }

    public static String getCover(String videoFilepath, String outFilePath, String time) {

        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return "http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/video/cover/2017/9/13/2F4428D5955D4318B779894DB4290E10.jpg";
        }

        ///usr/local/ffmpeg/bin/ffmpeg -i output.mp4 -y -loglevel quiet -f image2 -ss 37.050 -t 0.001 -s 352x240 b.jpg
        System.out.println("videoFilepath:" + videoFilepath + "|outfilepath=" + outFilePath);
        String cmd = cmd_ffmpeg + " -i " + videoFilepath + " -y -loglevel quiet -f image2 -ss " + time + " " +outFilePath;
        System.out.println("outfilepath:" + outFilePath);
        return getRunShellResult(cmd);
    }

    public static String getDuration(String filepath) {

        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return "1000";
        }
        System.out.println("fileSize_duration:" + filepath);
        //export LD_LIBRARY_PATH=/usr/local/ffmpeg/lib/:/usr/local/lib;
        String cmd = cmd_ffprobe + " -i " + filepath + " -show_format 2>&1 | grep duration | sed -n 's/duration=//p'";
        System.out.println("fileSize_duration:" + cmd);
        String old_duration = getRunShellResult(cmd);
        if (!StringUtil.isEmpty(old_duration)) {
//            if (old_duration.contains(".")){
//                return old_duration.substring(0,old_duration.indexOf("."));
//            }else{
            return old_duration;
//            }
        } else {
            return "0";
        }
    }

    public static int getWget(String sourcepath,String localPath){
        return getRunShell("/usr/bin/wget " + sourcepath+ " -q -t 3 -O "+localPath);
    }
    /**
     * @param filePath 视频路径
     * @param interval 每个切片的时间间隔
     * @param m3u8Name m3u8 文件名
     * @return
     */
    public static String sectForm3u8(String filePath, int interval, String m3u8Name) throws Exception {

        //视频父级路径
        String fileDir = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
        System.out.println("========================= section for m3u8 start =========================");
        String m3u8Dir = fileDir + m3u8Name + File.separator;
        File m3u8DirFile = new File(m3u8Dir);
        if (!m3u8DirFile.exists()) {
            if (!m3u8DirFile.mkdirs()) {
                throw new RuntimeException("视频路径不存在");
            }
        }
        String cmd = cmd_ffmpeg + " -i " + filePath + " -hls_time " + interval + " -hls_list_size 50 " + m3u8Dir + m3u8Name + ".m3u8";
        getRunShellResult(cmd);
//        runCmdInWindows(cmd);
        System.out.println("========================= section for m3u8 end =========================");
        return m3u8Dir;
    }

    private static String getRunShellResult(String cmd) {
        log.info("cmd:" + cmd);
        Process ps;
        String result = "";
        try {

            ps = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd}, null, null);
            BufferedInputStream in = new BufferedInputStream(ps.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                result = lineStr;
            }
            log.info("result:" + result);
            // 关闭输入流
            br.close();
            in.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static int getRunShell(String cmd) {
        log.info("cmd:" + cmd);
        Process ps;
        try {

            ps = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
            ps.waitFor();
            return ps.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * windows 下需要修改 cmd_ffmpeg的路径 !!!!!!!
     *
     * @param cmd
     */
    private static void runCmdInWindows(String cmd) {

        String result = StringUtil.empty;
        try {
            Process process = Runtime.getRuntime().exec(cmd + " -y -v quiet");
            BufferedInputStream in = new BufferedInputStream(process.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                result = lineStr;
            }
            System.out.println("result:" + result);
            // 关闭输入流
            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
