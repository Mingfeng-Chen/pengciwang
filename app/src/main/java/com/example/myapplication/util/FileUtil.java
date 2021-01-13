package com.example.myapplication.util;

import com.example.myapplication.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    // 读取本地文件
    public static String readLocalJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        File file = new File(MyApplication.getContext().getFilesDir(), fileName);
        // 文件不存在
        if (!file.exists()) {
            return null;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String s = stringBuilder.toString().replace("{\"wordRank\"", ",{\"wordRank\"");
        return "[" + s.substring(1) + "]";
    }
    //将assets目录下的文件保存到程序的data/data目录下，方便程序使用
    public static void saveLocalJson2Data(String fileName){
        InputStream in = null;
        FileOutputStream out = null;
        String path = MyApplication.getContext().getFilesDir()
                .getAbsolutePath() + "/" + fileName; // data/data目录
        File file = new File(path);
        if (!file.exists()) {
            try
            {
                in = MyApplication.getContext().getResources().getAssets().open(fileName); // 从assets目录下复制
                out = new FileOutputStream(file);
                int length = -1;
                byte[] buf = new byte[1024];
                while ((length = in.read(buf)) != -1)
                {
                    out.write(buf, 0, length);
                }
                out.flush();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally{
                if (in != null)
                {
                    try {
                        in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (out != null)
                {
                    try {
                        out.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
