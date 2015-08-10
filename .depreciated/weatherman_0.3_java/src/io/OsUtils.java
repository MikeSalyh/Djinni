package io;

public final class OsUtils {
	public static String getOsName()
   {
      return System.getProperty("os.name");
   }
	
   public static boolean isWindows()
   {
      return getOsName().startsWith("Windows");
   }
   
   public static boolean isMac()
   {
      return getOsName().startsWith("Mac");
   }
}
