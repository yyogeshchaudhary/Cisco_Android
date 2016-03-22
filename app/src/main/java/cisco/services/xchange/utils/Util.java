package cisco.services.xchange.utils;

/**
 * Created by yogi on 23/03/16.
 */
public class Util {
        public final static boolean isValidEmail(CharSequence target) {
            if (target == null) {
                return false;
            } else {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            }
        }
}
