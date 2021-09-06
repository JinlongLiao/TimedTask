import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {
  public static void main(String[] args) throws IOException {
    final ServerSocket serverSocket = new ServerSocket(9999);
    while (true) {
      final Socket socket = serverSocket.accept();
      new Thread(() -> {
        try {
          final InputStream inputStream = socket.getInputStream();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }).start();
    }
  }
}
