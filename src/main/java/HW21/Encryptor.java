package HW21;

import java.security.MessageDigest;

public class Encryptor {

    public Encryptor() {
    }

    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("что-топошло не так");
            e.printStackTrace();
        }
        return null;
    }
}
