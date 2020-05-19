import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

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
}
