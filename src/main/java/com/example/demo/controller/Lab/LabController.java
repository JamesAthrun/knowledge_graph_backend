package com.example.demo.controller.Lab;

import com.example.demo.util.GlobalLogger;
import com.example.demo.util.Recorder;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

@RestController()
@RequestMapping("")
public class LabController {
    @Autowired
    Recorder recorder;
    @Autowired
    GlobalLogger logger;

    @GetMapping("/saveRecorder")
    public ResultBean saveRecorder(@RequestParam String key) {
        if (key.equals("wrng")) {
            recorder.save();
            logger.log("save recorder");
        }
        return ResultBean.success();
    }

    @GetMapping("/loadRecorder")
    public ResultBean loadRecorder(@RequestParam String key) {
        if (key.equals("wrng")) {
            recorder.load();
            logger.log("load recorder");
        }
        return ResultBean.success();
    }

    @RequestMapping("/video/{videoId}")
    public void video(HttpServletRequest request, HttpServletResponse response, @PathVariable String videoId) throws Exception {
        String vname;
        String vpath;
        if (videoId.equals("0")) {
            vname = "潮土油";
            vpath = "src/main/resources/video/潮土油.mp4";
        } else if (videoId.equals("1")) {
            vname = "三寸天堂";
            vpath = "src/main/resources/video/三寸天堂.mp4";
        } else {
            logger.error("no such video");
            return;
        }

        response.reset();
        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");

        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        File file = new File(vpath);
        if (file.exists()) {
            RandomAccessFile targetFile = new RandomAccessFile(file, "r");
            long fileLength = targetFile.length();
            //播放
            if (rangeString != null) {

                long range = Long.parseLong(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
                //设置内容类型
                response.setHeader("Content-Type", "video/mp4");
                //设置此次相应返回的数据长度
                response.setHeader("Content-Length", String.valueOf(fileLength - range));
                //设置此次相应返回的数据范围
                response.setHeader("Content-Range", "bytes " + range + "-" + (fileLength - 1) + "/" + fileLength);
                //返回码需要为206，而不是200
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                //设定文件读取开始位置（以字节为单位）
                targetFile.seek(range);
            } else {//下载

                //设置响应头，把文件名字设置好
                response.setHeader("Content-Disposition", "attachment; filename=" + vname);
                //设置文件长度
                response.setHeader("Content-Length", String.valueOf(fileLength));
                //解决编码问题
                response.setHeader("Content-Type", "application/octet-stream");
            }

            byte[] cache = new byte[1024 * 300];
            int flag;
            while ((flag = targetFile.read(cache)) != -1) {
                outputStream.write(cache, 0, flag);
            }
        } else {
            String message = "file:" + vname + " not exists";
            //解决编码问题
            response.setHeader("Content-Type", "application/json");
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        }
        outputStream.flush();
        outputStream.close();

    }

}
