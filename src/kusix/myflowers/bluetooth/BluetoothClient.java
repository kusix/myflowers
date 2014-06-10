package kusix.myflowers.bluetooth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import kusix.myflowers.protocol.ProtocolParser;
import kusix.myflowers.protocol.impl.FlowerDataProtocolParser;
import kusix.myflowers.protocol.impl.JsonProtocolParser;
import kusix.myflowers.util.Tags;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BluetoothClient {

	protected static final int CONNECT_FAILED = 1;
	protected static final int CONNECT_SUCCESS = 0;
	protected static final int READ_FAILED = 2;
	protected static final int WRITE_FAILED = 3;
	protected static final int DATA = 4;
	
	protected static final int CR = 13;
	protected static final int LF = 10;

	private BluetoothDevice device;
	private Handler handler;
	private BluetoothSocket socket;
	private boolean hasConnected;

	public BluetoothClient(BluetoothDevice device, Handler handler) {
		this.device = device;
		this.handler = handler;
	}

	public boolean hasConnected() {
		return hasConnected;
	}

	public void connect() {
		hasConnected = false;
		Thread thread = new Thread(new Runnable() {

			public void run() {
				BluetoothSocket tmp = null;
				Method method;
				try {
					method = device.getClass().getMethod("createRfcommSocket",
							new Class[] { int.class });
					tmp = (BluetoothSocket) method.invoke(device, 1);

					if (null != tmp) {
						socket = tmp;
						socket.connect();
						hasConnected = true;
						setState(CONNECT_SUCCESS);
						// while (hasConnected) {
						// write(message);
						// read(new FlowerDataProtocolParser());
						// Thread.sleep(1000);
						// }
					}
				} catch (Exception e) {
					Log.e(Tags.ME, e.toString());
					close();
					setState(CONNECT_FAILED);

				}
			}

		});
		thread.start();
	}

	public Object read(ProtocolParser parser) throws IOException {
		InputStream inputStream = socket.getInputStream();
//		byte[] bytes = readStream(inputStream);
		// Message msg = handler.obtainMessage();
		// msg.what = DATA;
		// msg.obj = parser.parse(new String(bytes));
		// handler.sendMessage(msg);
		return parser.parse(readStream(inputStream));
	}

	// private byte[] readStream(InputStream inputStream) throws IOException {
	// ByteArrayOutputStream bout = new ByteArrayOutputStream();
	// byte[] buffer = new byte[1024];
	// int len = 0;
	// len = inputStream.read(buffer);
	// bout.write(buffer, 0, len);
	// bout.close();
	//
	// return buffer;
	// }

//	private byte[] readStream(InputStream in) throws IOException {
//		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		while ((len = in.read(buffer)) != -1) {
//			outStream.write(buffer, 0, len);
//		}
//		in.close();
//		return outStream.toByteArray();
//	}

	public String readStream(InputStream in) throws IOException {
		int ch;
		StringBuffer temper = new StringBuffer();
		while ((ch = in.read()) > 0) {
			if(ch == CR && in.read() == LF){
				break;
			}
			temper.append((char) ch);
		}
		return temper.toString();
	}

	public void write(final String message) {
		if (null != message && message.length() > 0) {
			try {
				OutputStream outStream = socket.getOutputStream();
				// outStream.write(getHexBytes(message));
				outStream.write(message.getBytes());
			} catch (Exception e) {
				// setState(WRITE_FAILED);
				Log.e(Tags.ME, e.toString());
			}
		}
	}

	private void setState(int state) {
		handler.sendMessage(handler.obtainMessage(state));
	}

	public void close() {
		hasConnected = false;
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				Log.e(Tags.ME, e.toString());
				socket = null;
			}
		}
	}

	private byte[] getHexBytes(String message) {
		int len = message.length() / 2;
		char[] chars = message.toCharArray();
		String[] hexStr = new String[len];
		byte[] bytes = new byte[len];
		for (int i = 0, j = 0; j < len; i += 2, j++) {
			hexStr[j] = "" + chars[i] + chars[i + 1];
			bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
		}
		return bytes;
	}

}
