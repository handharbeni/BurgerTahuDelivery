package illiyin.mhandharbeni.utilslibrary;

import android.content.pm.PackageManager;

/**
 * Created by root on 9/21/17.
 */

public abstract class PermissionUtil {
    public static boolean verifyPermissions(int[] grantResults) {
        if(grantResults.length < 1){
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
