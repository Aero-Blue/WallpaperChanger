import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;
import java.io.*;
import java.util.HashMap;

class WallpaperChanger {
    static void setWallpaper( String imagePath ) {
        User32.INSTANCE.SystemParametersInfo(20, 0, imagePath, 1);
    }

    interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class, new HashMap<String, Object>() {
            {
                put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
                put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
            }
        });

        void SystemParametersInfo(
                int uiAction,
                int uiParam,
                String pvParam,
                int fWinIni
        );
    }

    static String getWallpaper() {
        // Method to return the path of currently set wallpaper
        String filepath = null;
        try {
            // Simplest to use a reg query here to get the path of the current wallpaper
            Process process = Runtime.getRuntime().exec("reg query \"HKEY_CURRENT_USER\\Control Panel\\Desktop\" /v Wallpaper");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream())); // Recommended method
            String line;
            while((line = stdInput.readLine()) != null) {
                if(line.contains("Wallpaper")) {
                    filepath = line.substring(line.indexOf("C:\\")); // absolute path to image
                }
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return filepath;
    }
}
