package PuzzleGame.Lib;

import java.net.URI;

public class Gets {
    public static String getMyPath(){
        URI uri = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(s -> s
                        .filter(frame -> "main".equals(frame.getMethodName()))
                        .reduce((a, b) -> b)
                        .map(frame -> {
                            try {
                                return frame.getDeclaringClass()
                                        .getProtectionDomain()
                                        .getCodeSource()
                                        .getLocation()
                                        .toURI();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .orElseThrow()
                );
        return new java.io.File(uri).getAbsolutePath();
    }
}
